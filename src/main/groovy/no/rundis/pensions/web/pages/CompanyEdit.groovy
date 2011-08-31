package no.rundis.pensions.web.pages

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import no.rundis.pensions.domain.Company
import no.rundis.pensions.domain.Scheme
import org.apache.tapestry5.annotations.InjectComponent
import org.apache.tapestry5.annotations.Property
import org.apache.tapestry5.annotations.Retain
import org.apache.tapestry5.annotations.SetupRender
import org.apache.tapestry5.beaneditor.BeanModel
import org.apache.tapestry5.corelib.components.Zone
import org.apache.tapestry5.ioc.Messages
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.json.JSONObject
import org.apache.tapestry5.services.BeanModelSource
import org.apache.tapestry5.services.PropertyConduitSource
import org.got5.tapestry5.jquery.utils.JQueryTabData
import no.rundis.pensions.domain.Plan
import org.apache.tapestry5.services.SelectModelFactory
import org.apache.tapestry5.SelectModel
import org.apache.tapestry5.ValueEncoder

/**
 *
 * Created by: magnus
 *
 */
@Slf4j
class CompanyEdit {

    @Inject
    private BeanModelSource beanModelSource

    @Inject
    private PropertyConduitSource propertyConduitSource

    @Inject
    private Messages messages

    @Property(write = false)
    @Retain
    private BeanModel model;

    @Property
    private Company company
    {
        // Add support for embedded address object in bean editor
        model = beanModelSource.create(Company.class, true, messages)
        model.add("streetAddress", propertyConduitSource.create(Company.class, "address.street")).dataType = "text"
        model.add("zipAddress", propertyConduitSource.create(Company.class, "address.zip")).dataType = "text"
        model.add("cityAddress", propertyConduitSource.create(Company.class, "address.city")).dataType = "text"
        model.exclude("version", "id")
    }

    @Property
    private Scheme scheme

    @Property
    private Scheme newScheme

    @Property
    private Plan plan

    @Property
    private Plan newPlan


    @Property
	private List<JQueryTabData> listTabData

    @InjectComponent
    private Zone schemeZone

    @InjectComponent
    private Zone planZone


    void onActivate(companyId) {
        company = Company.findById(companyId);
        newScheme = new Scheme()
        newPlan = new Plan()

        schemeModel = selectModelFactory.create(company.schemes.toList(), "name");
    }

    def onPassivate() {
        return company?.id;
    }

    void onSuccessFromEditCompany() {
        log.info "About to save updated company"
        Company.withTransaction {
            company.save()
        }
    }

    @SetupRender
    void onSetupRender()  {
        listTabData = [
                new JQueryTabData("Schemes", "schemesBlock"),
                new JQueryTabData("Plans", "plansBlock")
        ]
    }

    def getDeleteOptions() {
        def builder = new JsonBuilder()
        builder {
            icons {
                primary 'ui-icon-trash'
            }
            text false
        }

        new JSONObject(builder.toString())
    }

    def onSuccessFromAddScheme() {
        log.info "About to add new scheme to company !"
        Company.withTransaction {
            company.addToSchemes(newScheme)
            company.save()
        }
        newScheme = new Scheme()

        return schemeZone.body
    }

    def onActionFromDeleteScheme(Integer schemeId) {
        Company.withTransaction {
            def schemeToDelete = company.schemes.find{it.id == schemeId}
            company.schemes.remove(schemeToDelete)
            //schemeToDelete.delete()
            company.save()
        }
        return schemeZone.body
    }

    def getPlans() {
        company.schemes*.plans.flatten();
    }

    def onSuccessFromAddPlan() {
        log.info "About to add plan to scheme for a company"
        Scheme.withTransaction {
            scheme.addToPlans(newPlan)
            scheme.save()
        }
        newPlan = new Plan()
        return planZone.body
    }

    def onActionFromDeletePlan(Integer planId) {
        Scheme.withTransaction {
            def planToDelete = company*.schemes*.plans.flatten().find{it.id == planId}
            def owningScheme = planToDelete.scheme

            owningScheme.plans.remove(planToDelete)
            owningScheme.save()
        }
        return planZone.body
    }


    @Inject
	private SelectModelFactory selectModelFactory

    @Property
	private SelectModel schemeModel

    private SchemeEncoder schemeEncoder


    def getSchemeEncoder() {
        if(!schemeEncoder)
            schemeEncoder = new SchemeEncoder()

        return schemeEncoder
    }


    public class SchemeEncoder implements ValueEncoder<Scheme> {
        String toClient(Scheme value) {
            value.id + ""
        }
        Scheme toValue(String clientValue) {
            log.info "Finding a scheme for id: $clientValue"
            def scheme = company.schemes.find{it.id == Integer.parseInt(clientValue)}
            log.info "Found scheme returning it: $scheme"
            return scheme
        }
    }


}
