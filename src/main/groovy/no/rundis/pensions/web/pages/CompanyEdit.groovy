package no.rundis.pensions.web.pages

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import no.rundis.pensions.domain.Company
import no.rundis.pensions.domain.Plan
import no.rundis.pensions.domain.Scheme
import no.rundis.pensions.web.services.BeanModelBuilder
import no.rundis.pensions.web.services.GormObjectEncoder
import org.apache.tapestry5.SelectModel
import org.apache.tapestry5.annotations.InjectComponent
import org.apache.tapestry5.annotations.Property
import org.apache.tapestry5.annotations.Retain
import org.apache.tapestry5.annotations.SetupRender
import org.apache.tapestry5.beaneditor.BeanModel
import org.apache.tapestry5.corelib.components.Zone
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.json.JSONObject
import org.apache.tapestry5.services.SelectModelFactory
import org.got5.tapestry5.jquery.utils.JQueryTabData

/**
 *
 * Created by: magnus
 *
 */
@Slf4j
class CompanyEdit {

    @Inject
    private BeanModelBuilder beanModelBuilder


    @Property(write = false)
    @Retain
    private BeanModel model;

    @Inject
    private SelectModelFactory selectModelFactory

    @Property
    private SelectModel schemeModel


    @Property
    private Company company
    {
        model = beanModelBuilder.Company {
            add(name: "streetAddress", beanPath: "address.street", type: "text")
            add(name: "zipAddress", beanPath: "address.zip", type: "text")
            add(name: "cityAddress", beanPath: "address.city",  type: "text")
            remove(properties: ["version", "id"])
        }
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
        company?.id;
    }

    void onSuccessFromEditCompany() {
        Company.withTransaction {
            company.save()
        }
    }

    @SetupRender
    void onSetupRender() {
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
            company.addToSchemes(newScheme).save()
        }
        newScheme = new Scheme()

        return schemeZone.body
    }

    def onActionFromDeleteScheme(Integer schemeId) {
        Company.withTransaction {
            def schemeToDelete = company.schemes.find {it.id == schemeId}
            company.removeFromSchemes(schemeToDelete).save()
        }
        return schemeZone.body
    }

    def getPlans() {
        company.schemes*.plans.flatten()
    }

    def onSuccessFromAddPlan() {
        log.info "About to add plan to scheme for a company"
        Scheme.withTransaction {
            scheme.addToPlans(newPlan).save()
        }
        newPlan = new Plan()
        return planZone.body
    }

    def onActionFromDeletePlan(Integer planId) {
        Scheme.withTransaction {
            def planToDelete = company.schemes*.plans.flatten().find {it.id == planId}
            def owningScheme = planToDelete.scheme

            owningScheme.plans.remove(planToDelete)
            owningScheme.save()
        }
        return planZone.body
    }


    def getSchemeEncoder() {
        return new GormObjectEncoder(gormObjects: company.schemes)
    }
}
