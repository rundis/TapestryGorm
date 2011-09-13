package no.rundis.pensions.util

import no.rundis.pensions.domain.DateInterval
import org.springframework.transaction.annotation.Transactional

/**
 * 
 * Created by: magnus
 * 
 */
@Transactional
class SampleDataPopulator {

    def populate() {

        def builder = new PensionDataBuilder()
        builder.pensiondata {
            def scheme1, scheme2
            def scheme1Plan1, scheme1Plan2, scheme2Plan1, scheme2Plan2

            def rundisCorp = company (name: "Rundis Corp", officialNo: "912123452",
                    address (street: "Gold street 1", zip: "1234", city: "Springfield")) {
                scheme1 = scheme (name: "Silver scheme", insured: false)  {
                    scheme1Plan1 = plan (name: "Nice silver plan", yearsOfService: 30, retirementAge: 62)
                    scheme1Plan2 = plan (name: "Optimal silver plan", yearsOfService: 30, retirementAge: 67)
                }
                scheme2 = scheme (name: "Gold scheme", insured: false) {
                    scheme2Plan1 = plan (name: "Chill early plan", yearsOfService: 30, retirementAge: 62)
                    scheme2Plan2 = plan (name: "Chill later with more doh plan", yearsOfService: 30, retirementAge: 67)
                }
                scheme(name: "Platinum scheme", insured: true)
            }

            employee (ssn: "111-111", firstName: "Anne", lastName: "Foss", company: rundisCorp,
                address (street: "Quietcomfort street 1C", zip: "8888", city: "Springfield")) {
                schemeMembership(scheme: scheme1, validFor: DateInterval.rightOpened("2001-01-01")) {
                    planAssignment(plan: scheme1Plan1, validFor: DateInterval.rightOpened("2001-01-01"))
                }
            }

            employee (ssn: "111-222", firstName: "Magnus", lastName: "Rundberget", company: rundisCorp,
                address (street: "Quietcomfort street 1C", zip: "8888", city: "Springfield")) {
                schemeMembership(scheme: scheme1, validFor: DateInterval.closed("2000-01-01", "2007-01-01")) {
                    planAssignment(plan: scheme1Plan1, validFor: DateInterval.closed("2001-01-01", "2004-01-01"))
                    planAssignment(plan: scheme1Plan2, validFor: DateInterval.closed("2004-01-02", "2007-01-01"))
                }
                schemeMembership(scheme: scheme2, validFor: DateInterval.rightOpened("2007-01-02")) {
                    planAssignment(plan: scheme2Plan1, validFor: DateInterval.closed("2007-01-02", "2009-01-01"))
                    planAssignment(plan: scheme2Plan2, validFor: DateInterval.rightOpened("2009-01-02"))
                }
            }


            def darkCorp = company (name: "Dark Corp", officialNo: "912123453",
                    address (street: "Sleep street 1", zip: "1234", city: "Bensfield")) {
                scheme1 = scheme (name: "Fast scheme", insured: false)  {
                    scheme1Plan1 = plan (name: "Nice fast plan", yearsOfService: 30, retirementAge: 62)
                    scheme1Plan2 = plan (name: "Optimal fast plan", yearsOfService: 30, retirementAge: 67)
                }
                scheme2 = scheme (name: "Rich scheme", insured: false) {
                    scheme2Plan1 = plan (name: "Chill early plan", yearsOfService: 30, retirementAge: 62)
                    scheme2Plan2 = plan (name: "Chill later with more doh plan", yearsOfService: 30, retirementAge: 67)
                }
            }

            employee (ssn: "123-111", firstName: "Peter", lastName: "Jackson", company: darkCorp,
                address (street: "Less street 1C", zip: "8767", city: "Dudesfield")) {
                schemeMembership(scheme: scheme1, validFor: DateInterval.rightOpened("2001-01-01")) {
                    planAssignment(plan: scheme1Plan1, validFor: DateInterval.rightOpened("2001-01-01"))
                }
            }

            employee (ssn: "123-222", firstName: "Tracy", lastName: "Watts", company: darkCorp,
                address (street: "Mean street 1C", zip: "9876", city: "Darktown")) {
                schemeMembership(scheme: scheme1, validFor: DateInterval.closed("2000-01-01", "2007-01-01")) {
                    planAssignment(plan: scheme1Plan1, validFor: DateInterval.closed("2001-01-01", "2004-01-01"))
                    planAssignment(plan: scheme1Plan2, validFor: DateInterval.closed("2004-01-02", "2007-01-01"))
                }
                schemeMembership(scheme: scheme2, validFor: DateInterval.rightOpened("2007-01-02")) {
                    planAssignment(plan: scheme2Plan1, validFor: DateInterval.closed("2007-01-02", "2009-01-01"))
                    planAssignment(plan: scheme2Plan2, validFor: DateInterval.rightOpened("2009-01-02"))
                }
            }


        }


    }





}