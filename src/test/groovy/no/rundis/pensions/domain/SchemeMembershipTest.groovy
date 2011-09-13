package no.rundis.pensions.domain

import spock.lang.Specification
import org.joda.time.LocalDate

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/5/11
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
class SchemeMembershipTest extends Specification {

    def "verify sorting, newest first"() {
        when:
        def memberships = [
                new SchemeMembership(validFor: DateInterval.closed("2011-05-01", "2011-05-31")),
                new SchemeMembership(validFor: DateInterval.closed("2011-01-01", "2011-01-31")),
                new SchemeMembership(validFor: DateInterval.rightOpened("2011-06-01")),
                new SchemeMembership(validFor: DateInterval.closed("2011-03-01", "2011-03-31"))
        ] as SortedSet

        println memberships

        then:
        memberships.last().validFor.end == new LocalDate("2011-01-31")
        memberships.first().validFor.end == null
    }
}
