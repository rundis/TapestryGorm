package no.rundis.pensions.web.services

import org.apache.tapestry5.ValueEncoder

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/4/11
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
class GormObjectEncoder implements ValueEncoder {
    Collection gormObjects

    String toClient(Object gormObject) {
        gormObject.id as String
    }

    Object toValue(String id) {
        gormObjects.find{it.id as String == id}
    }
}
