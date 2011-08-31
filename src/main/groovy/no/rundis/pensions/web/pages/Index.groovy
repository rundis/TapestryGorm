package no.rundis.pensions.web.pages

import org.apache.tapestry5.annotations.Property
import no.rundis.pensions.domain.Employee
import groovy.util.logging.Slf4j
import no.rundis.pensions.util.SampleDataPopulator

/**
 *
 * Created by: magnus
 *
 */
@Slf4j
class Index {

    @Property
    private String ssn;

    void onActivate() {
        if (Employee.findAll().size() < 1) {
            log.info "adding a couple of members..."
            new SampleDataPopulator().populate()
        }

    }

    def onProvideCompletionsFromSSN(String partial) {
        log.info "Providing completions for: $partial"
        Employee.findAllBySsnLike(partial + "%").collect {it.ssn}
    }
}
