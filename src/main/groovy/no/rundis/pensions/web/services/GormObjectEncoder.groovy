package no.rundis.pensions.web.services

import org.apache.tapestry5.ValueEncoder

/**
 * Created by magnus.
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
