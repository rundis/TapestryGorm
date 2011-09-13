package no.rundis.pensions.web.pages

import org.apache.tapestry5.annotations.Property
import no.rundis.pensions.domain.Employee
import org.apache.tapestry5.json.JSONObject
import groovy.json.JsonBuilder

/**
 * Created by magnus.
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
