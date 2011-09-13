package no.rundis.pensions.web.pages

import org.apache.tapestry5.annotations.Property
import no.rundis.pensions.domain.Employee
import groovy.util.logging.Slf4j
import no.rundis.pensions.util.SampleDataPopulator
import org.apache.tapestry5.ComponentResources
import org.apache.tapestry5.ioc.annotations.Inject

/**
 *
 * Created by: magnus
 *
 */
@Slf4j
class Index {

    @Inject
    private ComponentResources componentResources


    @Property
    private String ssn;

    void onActivate() {
        if (Employee.findAll().size() < 1) {
            new SampleDataPopulator().populate()
        }
    }

    def onProvideCompletionsFromSSN(String partial) {
        Employee.findAllBySsnLike(partial + "%").collect {it.ssn}
    }


    def onSuccessFromSearchEmployeeBySSN() {
        def employeesFound = Employee.findAllBySsn(ssn)

        if (employeesFound.size() == 1) {
            return componentResources.createPageLink(EmployeeEdit.class, false, employeesFound[0].id)
        }

        return null;
    }
}
