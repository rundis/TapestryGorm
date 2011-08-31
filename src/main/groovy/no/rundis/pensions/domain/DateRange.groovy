package no.rundis.pensions.domain

import org.joda.time.LocalDate
import org.joda.time.contrib.hibernate.PersistentLocalDate

/**
 * 
 * Created by: magnus
 * 
 */
class DateRange {
    LocalDate start
    LocalDate end

    def static range(String start, String end) {
        return new DateRange(start: new LocalDate(start), end: new LocalDate(end))
    }

    def static openEnded(String start) {
        return new DateRange(start: new LocalDate(start), end: null)
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
