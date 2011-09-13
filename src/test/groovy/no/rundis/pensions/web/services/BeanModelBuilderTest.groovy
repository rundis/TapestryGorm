package no.rundis.pensions.web.services

import spock.lang.Specification
import no.rundis.pensions.domain.Company
import org.apache.tapestry5.services.BeanModelSource
import org.apache.tapestry5.services.PropertyConduitSource
import org.apache.tapestry5.ioc.Messages
import org.apache.tapestry5.beaneditor.BeanModel
import org.apache.tapestry5.beaneditor.PropertyModel

/**
 * Created by IntelliJ IDEA.
 * User: mrundberget
 * Date: 9/4/11
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
class BeanModelBuilderTest extends Specification {

    def beanModelSourceMock, propertyConduitSourceMock, messagesMock, modelMock

    def setup() {
        beanModelSourceMock = Mock(BeanModelSource)
        propertyConduitSourceMock = Mock(PropertyConduitSource)
        messagesMock = Mock(Messages)
        modelMock = Mock(BeanModel)

    }


    def "as a tapestry page I would like to easily add bean model properties for embedded/composite objects"() {
        given:
        beanModelSourceMock.create(Company.class, true, messagesMock) >> modelMock
        modelMock.add(_, _) >> Mock(PropertyModel)

        def modelBuilder = new BeanModelBuilder(
                beanModelSource: beanModelSourceMock, propertyConduitSource: propertyConduitSourceMock, messages: messagesMock)


        when:
        def model = modelBuilder.Company {
            add(name: "streetAddress", beanPath: "address.street", type: "text")
        }

        then:
        model == modelMock
    }

    def "as a tapestry page I would like to easily remove bean model properties"() {
        given:
        beanModelSourceMock.create(Company.class, true, messagesMock) >> modelMock
        //modelMock.add(_, _) >> Mock(PropertyModel)

        def modelBuilder = new BeanModelBuilder(
                beanModelSource: beanModelSourceMock, propertyConduitSource: propertyConduitSourceMock, messages: messagesMock)

        when:
        def model = modelBuilder.Company {
            remove(properties: ["version", "id"])
        }

        then:
        1*modelMock.exclude((String[])["version", "id"])
    }


}
