package no.rundis.pensions.web.components

import org.apache.tapestry5.annotations.AfterRender
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.services.RequestGlobals
import org.apache.tapestry5.services.javascript.JavaScriptSupport

/**
 * 
 * Created by: magnus
 * 
 */
//@Import(stylesheet = ["context:css/pensionsmain.css"])
class Layout {
    @Inject
    def RequestGlobals requestGlobals

    @Inject
    def JavaScriptSupport scriptSupport;

    def getPageName() {
        requestGlobals.activePageName
    }


    @AfterRender
    void selectActiveMenuItem() {
        String tabName = "#" + pageName + "Menu";
        scriptSupport.addScript("\$('${tabName}').toggleClass('current');");
    }

}
