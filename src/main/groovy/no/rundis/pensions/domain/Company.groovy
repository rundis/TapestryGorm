package no.rundis.pensions.domain

import grails.persistence.Entity

/**
 * 
 * Created by: magnus
 * 
 */
@Entity
class Company {
    String name
    String officialNo
    Address address = new Address() // initialize here to make it pleasant to work with in ui !

    static embedded = ['address']
    static hasMany = [schemes: Scheme]
    static mapping = {
        schemes cascade:"all, delete-orphan"
    }
}
