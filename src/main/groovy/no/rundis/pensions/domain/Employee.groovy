package no.rundis.pensions.domain

import grails.persistence.Entity
import groovy.transform.ToString
import org.joda.time.LocalDate

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class Employee {
    String firstName
    String lastName
    String ssn;
    Address address = new Address()
    Company company
    SortedSet schemeMemberships

    def addSchemeMembershipValidFromDate(Scheme scheme, LocalDate fromDate) {
        if(scheme.company.id != company.id) {
            throw new IllegalArgumentException("Company that employee belongs to does not define the scheme: $scheme")
        }

        def lastMembership = schemeMemberships.first()
        lastMembership.validFor = lastMembership.validFor.rightClose(fromDate.minusDays(1))
        addToSchemeMemberships(new SchemeMembership(scheme: scheme, validFor: DateInterval.rightOpened(fromDate)))
        return this
    }

    static embedded = ['address']
    static hasMany = [schemeMemberships: SchemeMembership]

}
