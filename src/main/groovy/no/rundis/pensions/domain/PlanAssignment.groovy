package no.rundis.pensions.domain

import grails.persistence.Entity

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class PlanAssignment {
    DateRange validFor
    Plan plan

    static belongsTo = [schemeMembership: SchemeMembership]
    static embedded = ['validFor']
}
