package no.rundis.pensions.domain

import grails.persistence.Entity

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class Plan {
    String name
    Integer retirementAge
    Integer yearsOfService

    static belongsTo = [scheme: Scheme]

}
