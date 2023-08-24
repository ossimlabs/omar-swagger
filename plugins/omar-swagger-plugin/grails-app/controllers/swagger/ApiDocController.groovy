package swagger

import groovy.util.logging.Slf4j
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@Slf4j
class ApiDocController implements ApplicationContextAware {
  static responseFormats = [ 'json' ]
  static namespace = 'v1'
  static allowedMethods = [ getDocuments: "GET" ]

  static defaultAction = "getDocuments"

  SwaggerService swaggerService

  @Value( "classpath*:**/webjars/swagger-ui/**/index.html" )
  Resource[] swaggerUiResources

  ApplicationContext applicationContext

  def getDocuments() {
    log.info( "url=${ request.getRequestURI() }" )

    if ( request.getHeader( 'accept' ) && request.getHeader( 'accept' ).indexOf( MediaType.APPLICATION_JSON_VALUE ) > -1 ) {

      log.info("Getting document")

      try {
        String swaggerJson = swaggerService.generateSwaggerDocument()
        render contentType: MediaType.APPLICATION_JSON_VALUE,
            text: swaggerJson
      } catch ( Exception e ) {
        e.printStackTrace()
        render status: HttpStatus.INTERNAL_SERVER_ERROR,
            text: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
      }
    } else {
      log.info("Getting UI")

//      redirect uri: "/webjars/swagger-ui${ getSwaggerUiFile() }?url=${ request.getRequestURI() }"
      redirect action: 'index', params: [ url: request.getRequestURI() ]
    }
  }

  def index() {
    log.info ("In index")
    [url: params.get( 'url' )]
  }

  protected String getSwaggerUiFile() {
    try {
      ( swaggerUiResources.getAt( 0 ) as Resource ).getURI().toString().split( "/webjars/swagger-ui" )[ 1 ]
    } catch ( Exception e ) {
      throw new Exception( "Unable to find swagger ui.. Please make sure that you have added swagger ui dependency eg:-\n compile 'org.webjars:swagger-ui:2.2.8' \nin your build.gradle file", e )
    }
  }

  @Override
  void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
    this.applicationContext = applicationContext
    swaggerUiResources = applicationContext.getResources( 'classpath*:**/webjars/swagger-ui/**/index.html' )
  }
}
