package no.rundis.pensions.util

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import groovy.util.logging.Slf4j

import no.rundis.pensions.domain.DateInterval
import org.joda.time.LocalDate

/**
 * 
 * Created by: magnus
 * 
 */
@Slf4j
@Transactional @TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
class PensionDataBuilderTest extends spock.lang.Specification {


    def "composite dsl test"() {
        when:
        def builder = new PensionDataBuilder()
        def rundisCorp
        def empAnne

        builder.pensiondata {
            def scheme1, scheme2
            def scheme1Plan1, scheme1Plan2, scheme2Plan1, scheme2Plan2

            rundisCorp = company (name: "Rundis Corp", officialNo: "912123452",
                    address (street: "Gold street 1", zip: "1234", city: "Springfield")) {
                scheme1 = scheme (name: "Silver scheme", insured: false)  {
                    scheme1Plan1 = plan (name: "Nice silver plan", yearsOfService: 30, retirementAge: 62)
                    scheme1Plan2 = plan (name: "Optimal silver plan", yearsOfService: 30, retirementAge: 67)
                }
                scheme2 = scheme (name: "Gold scheme", insured: false) {
                    scheme2Plan1 = plan (name: "Chill early plan", yearsOfService: 30, retirementAge: 62)
                    scheme2Plan2 = plan (name: "Chill later with more doh plan", yearsOfService: 30, retirementAge: 67)
                }
            }

            empAnne = employee (ssn: "111-111", firstName: "Anne", lastName: "Foss", company: rundisCorp,
                address (street: "Quietcomfort street 1C", zip: "8888", city: "Springfield")) {
                schemeMembership(scheme: scheme1, validFor: DateInterval.rightOpened("2001-01-01")) {
                    planAssignment(plan: scheme1Plan1, validFor: DateInterval.rightOpened("2001-01-01"))
                }
            }

            employee (ssn: "111-222", firstName: "Magnus", lastName: "Rundberget", company: rundisCorp,
                address (street: "Quietcomfort street 1C", zip: "8888", city: "Springfield")) {
            }

        }

        then:
        rundisCorp.id
        rundisCorp.schemes.find{it.name == "Silver scheme"}.id
        rundisCorp.schemes.find{it.name == "Silver scheme"}.plans.find{it.name == "Nice silver plan"}.id

        empAnne.id
        empAnne.schemeMemberships.toList()[0].id
        empAnne.schemeMemberships.toList()[0].planAssignments.toList()[0].id
    }
}
