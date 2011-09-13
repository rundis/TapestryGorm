package no.rundis.pensions.domain

import groovy.util.logging.Slf4j
import no.rundis.pensions.util.SampleDataPopulator
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import org.joda.time.LocalDate

/**
 *
 * Created by: magnus
 *
 */
@Slf4j
@Transactional @TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
class EmployeeTest extends spock.lang.Specification {
    @Autowired
    private SessionFactory sessionFactory

    @Autowired
    private SampleDataPopulator sampleDataPopulator

    def "Verify that dynamic finder works for employee entity"() {

        given:
        sampleDataPopulator.populate()
        sessionFactory.currentSession.clear()  // clear 1.st level cache ...

        when:
        def employeeFound = Employee.findByLastNameLike("Rundberget")

        then:
        log.info "Validating results and stuff"
        employeeFound != null
        employeeFound.company != null
        employeeFound.schemeMemberships.size() > 0
    }

    def "addSchemeMembershipValidFromDate where last membership is open"() {
        given:
        sampleDataPopulator.populate()
        sessionFactory.currentSession.clear()  // clear 1.st level cache ...
        def emp = Employee.findByLastNameLike("Rundberget")
        def scheme = Scheme.findByNameLike("Platinum scheme")

        when:
        emp.addSchemeMembershipValidFromDate(scheme, new LocalDate("2011-01-01"))
        emp.save(flush: true)

        then:
        emp.schemeMemberships.first().scheme == scheme

    }




}
