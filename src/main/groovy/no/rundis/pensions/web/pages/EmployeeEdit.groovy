package no.rundis.pensions.web.pages

import no.rundis.pensions.domain.Employee
import no.rundis.pensions.domain.PlanAssignment
import no.rundis.pensions.domain.SchemeMembership
import no.rundis.pensions.web.services.BeanModelBuilder
import org.apache.tapestry5.annotations.Property
import org.apache.tapestry5.annotations.Retain
import org.apache.tapestry5.beaneditor.BeanModel
import org.apache.tapestry5.ioc.annotations.Inject

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/4/11
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
class EmployeeEdit {

    @Inject
    private BeanModelBuilder beanModelBuilder


    @Property(write = false)
    @Retain
    private BeanModel model;

    @Property
    private Employee employee
    {
        model = beanModelBuilder.Employee {
            add(name: "streetAddress", beanPath: "address.street", type: "text")
            add(name: "zipAddress", beanPath: "address.zip", type: "text")
            add(name: "cityAddress", beanPath: "address.city", type: "text")
            remove(properties: ["version", "id"])
        }
    }

    @Property
    private SchemeMembership schemeMembership

    @Property
    private PlanAssignment planAssignment


    void onActivate(id) {
        employee = Employee.findById(id)
    }


    def onPassivate() {
        employee.id
    }

    void onSuccessFromEditEmployee() {
        Employee.withTransaction {
            employee.save()
        }
    }

}
