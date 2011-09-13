package no.rundis.pensions.domain

import spock.lang.Specification
import org.joda.time.LocalDate

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/5/11
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
class DateIntervalTest extends Specification {

    private static final String START = "2011-01-01"
    private static final String END = "2011-05-05"

    def "create closed interval"() {
        given:
            def start = new LocalDate(START)
            def end = new LocalDate(END)
        when:
            def closedInterval = DateInterval.closed(START, END)
        then:
            start == closedInterval.start
            end == closedInterval.end
    }


    def "right close a right-open interval"() {
        given:
            def rightOpen = DateInterval.rightOpened("2011-01-01")
        when:
            def rightClosed = rightOpen.rightClose(new LocalDate(2011, 5, 5))
        then:
            rightClosed.end == new LocalDate(2011, 5, 5)

    }



}
