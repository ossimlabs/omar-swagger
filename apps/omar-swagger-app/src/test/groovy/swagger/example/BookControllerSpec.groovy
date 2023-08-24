package swagger.example

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class BookControllerSpec extends Specification implements ControllerUnitTest<BookController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
