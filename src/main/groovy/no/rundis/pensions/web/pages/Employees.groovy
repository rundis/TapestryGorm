package no.rundis.pensions.web.pages

import org.apache.tapestry5.annotations.Property
import no.rundis.pensions.domain.Employee
import org.apache.tapestry5.json.JSONObject
import groovy.json.JsonBuilder

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/4/11
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
class Employees {

    @Property
    private Employee employee


    def getEmployees() {
        Employee.findAll()
    }

    def getEditParams() {
        def builder = new JsonBuilder()
        builder.icons {
            primary 'ui-icon-pencil'
        }
        new JSONObject(builder.toString())
    }
}
