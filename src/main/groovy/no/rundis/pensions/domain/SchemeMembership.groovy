package no.rundis.pensions.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import org.joda.time.LocalDate

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
@EqualsAndHashCode
class SchemeMembership implements Comparable{
    DateInterval validFor
    Scheme scheme

    static hasMany = [planAssignments: PlanAssignment]
    static belongsTo = [employee:Employee]
    static embedded = ['validFor']


    @Override
    int compareTo(Object o) {
        if (!(o instanceof SchemeMembership)) throw new ClassCastException()

        nvl(o.validFor.end) <=> nvl(this.validFor.end)
    }

    def nvl(aDate) {
        aDate ?: new LocalDate(9999, 12, 31)
    }
}
