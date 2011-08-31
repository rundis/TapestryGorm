package no.rundis.pensions.domain

import grails.persistence.Entity

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class Scheme {
    String name
    Boolean insured

    static belongsTo = [company:Company]
    static hasMany = [plans: Plan]

    static mapping = {
        plans cascade:"all, delete-orphan"
    }
}
