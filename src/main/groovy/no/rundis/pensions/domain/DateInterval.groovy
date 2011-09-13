package no.rundis.pensions.domain

import org.joda.time.LocalDate
import org.joda.time.contrib.hibernate.PersistentLocalDate
import groovy.transform.EqualsAndHashCode

/**
 * 
 * Created by: magnus
 * 
 */
@EqualsAndHashCode
class DateInterval {
    LocalDate start
    LocalDate end

    def static closed(String start, String end) {
        return new DateInterval(start: new LocalDate(start), end: new LocalDate(end))
    }

    def static rightOpened(String start) {
        return new DateInterval(start: new LocalDate(start), end: null)
    }

    def static rightOpened(LocalDate start) {
        return new DateInterval(start: start, end: null)
    }


    def rightClose(LocalDate date) {
        return new DateInterval(start: start, end: date);
    }

    static mapping = {
            start type:PersistentLocalDate
            end type:PersistentLocalDate
    }
    static constraints = {
        start nullable:true
        end nullable:true
    }
}
