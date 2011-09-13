package no.rundis.pensions.domain

import grails.persistence.Entity

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class PlanAssignment {
    DateInterval validFor
    Plan plan

    static belongsTo = [schemeMembership: SchemeMembership]
    static embedded = ['validFor']
}
