package no.rundis.pensions.web

import spock.lang.Specification
import groovy.json.JsonBuilder

/**
 *
 * Created by: magnus
 *
 */
class JsonTrialTest extends Specification {


    def "Json with multiple roots"() {
        setup:
        def builder = new JsonBuilder()

        when:

        builder {
            icons {
                primary 'ui-icon-trash'
            }
            text false
        }

        then:
        println builder.toPrettyString()
        builder.toString()
    }
}
