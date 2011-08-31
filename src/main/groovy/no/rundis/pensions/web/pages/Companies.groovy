package no.rundis.pensions.web.pages

import org.apache.tapestry5.annotations.Property
import no.rundis.pensions.domain.Company
import org.apache.tapestry5.json.JSONObject
import groovy.json.JsonBuilder

/**
 *
 * Created by: magnus
 *
 */
class Companies {
    @Property
    private Company company

    def getCompanies() {
        Company.findAll();
    }

    def getEditParams() {
        def builder = new JsonBuilder()
        builder.icons {
            primary 'ui-icon-pencil'
        }
        new JSONObject(builder.toString())
    }



}
