package no.rundis.pensions.util

import no.rundis.pensions.domain.Employee
import javax.servlet.*

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/13/11
 * Time: 8:57 PM
 * To change this template use File | Settings | File Templates.
 */
class SampleDataFilter implements Filter{
    @Override
    void init(FilterConfig filterConfig) {
        Employee.withTransaction {
            new SampleDataPopulator().populate()
        }
    }

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        chain.doFilter(request, response)
    }

    @Override
    void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
