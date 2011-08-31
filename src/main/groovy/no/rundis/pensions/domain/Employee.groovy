package no.rundis.pensions.domain

import grails.persistence.Entity
import groovy.transform.ToString

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
    Address address
    Company company

    static embedded = ['address']
    static hasMany = [schemeMemberships: SchemeMembership]
}
