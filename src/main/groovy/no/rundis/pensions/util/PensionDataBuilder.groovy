package no.rundis.pensions.util

import groovy.util.logging.Slf4j
import no.rundis.pensions.domain.Address
import no.rundis.pensions.domain.Company
import no.rundis.pensions.domain.Scheme
import no.rundis.pensions.domain.Plan
import no.rundis.pensions.domain.Employee
import no.rundis.pensions.domain.SchemeMembership
import no.rundis.pensions.domain.PlanAssignment

/**
 * 
 * Created by: magnus
 * 
 */
@Slf4j
class PensionDataBuilder extends BuilderSupport{
    @Override
    protected void setParent(Object parent, Object child) {
        log.debug "Setting parent $parent for child $child"

        if (parent instanceof Company && child instanceof Scheme) {
           parent.addToSchemes(child)
        }
        if(parent instanceof Scheme && child instanceof Plan) {
            parent.addToPlans(child)
        }
        if(parent instanceof Employee && child instanceof SchemeMembership) {
            parent.addToSchemeMemberships(child)
        }
        if(parent instanceof SchemeMembership && child instanceof PlanAssignment) {
            parent.addToPlanAssignments(child)
        }
    }

    @Override
    protected Object createNode(Object name) {
        log.debug "createNode By Name: $name"

        return null
    }

    @Override
    protected Object createNode(Object name, Object value) {
        log.debug "createNode By name and value: $name - $value"
        return null
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        log.debug "createNode by name and attributes: $name - $attributes"

        switch (name) {
            case "address":
                return new Address(attributes)
            case "scheme":
                return new Scheme(attributes)
            case "plan":
                return new Plan(attributes)
            case "schemeMembership":
                return new SchemeMembership(attributes)
            case "planAssignment":
                return new PlanAssignment(attributes)
        }

        return null
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        log.debug "createNode by name, attributes and value: $name - $attributes - $value"


        switch (name) {
            case "company":
                def modAttr = attributes + [address:value]
                return new Company(modAttr)
            case "employee":
                def modAttr = attributes + [address:value]
                return new Employee(modAttr)
        }

        return null
    }

    void nodeCompleted(parent, node) {
        log.debug "Completed node for parent:$parent, node: $node"

        if(!parent && node instanceof Company) {
            node.save(flush: true, failOnError: true)
        }
        if(!parent && node instanceof Employee) {
            node.save(flush: true, failOnError: true)
        }
    }

}
