package no.rundis.pensions.domain

import groovy.util.logging.Slf4j
import no.rundis.pensions.util.SampleDataPopulator
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional

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

        def emps = Employee.findAllBySsnLike("111%")
        println emps

        def employeeFound = Employee.findByLastNameLike("Rundberget")

        then:
        log.info "Validating results and stuff"
        employeeFound != null
        employeeFound.company != null
        employeeFound.schemeMemberships.size() > 0

    }

}
