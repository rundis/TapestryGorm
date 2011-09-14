package no.rundis.pensions.web.services

import groovy.util.logging.Slf4j
import org.apache.tapestry5.beaneditor.BeanModel
import org.apache.tapestry5.ioc.Messages
import org.apache.tapestry5.services.BeanModelSource
import org.apache.tapestry5.services.PropertyConduitSource

/**
 * Make working with bean models in tapestry more pleasant.
 *
 * TODO: Probably overkill with a builder here.
 */
@Slf4j
class BeanModelBuilder extends BuilderSupport{

    BeanModelSource beanModelSource
    PropertyConduitSource propertyConduitSource
    Messages messages

    BeanModel model
    Class beanClass

    @Override
    protected void setParent(Object parent, Object child) {
       log.debug "Setting parent $parent for child $child"
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Object createNode(Object name) {
        log.debug "createNode By Name: $name"

        beanClass = "no.rundis.pensions.domain.$name" as Class
        model = beanModelSource.create(beanClass, true, messages)
    }

    @Override
    protected Object createNode(Object name, Object value) {
        log.info "createNode By name and value: $name - $value"
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        log.debug "createNode by name and attributes: $name - $attributes"

        if (name == "add") {
            model.add(attributes.name, propertyConduitSource.create(beanClass, attributes.beanPath)).dataType(attributes.type)
        }
        if (name == "remove") {
            model.exclude((String [])attributes.properties)
        }

        return model  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        log.debug "createNode by name, attributes and value: $name - $attributes - $value"
        return null
    }

    void nodeCompleted(parent, node) {
        log.debug "Completed node for parent:$parent, node: $node"
    }
}
