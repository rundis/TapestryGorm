package no.rundis.pensions.domain

import grails.persistence.Entity

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class SchemeMembership {
    DateRange validFor
    Scheme scheme

    static hasMany = [planAssignments: PlanAssignment]
    static belongsTo = [employee:Employee]
    static embedded = ['validFor']
}
