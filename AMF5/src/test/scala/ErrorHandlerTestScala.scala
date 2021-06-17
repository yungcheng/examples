import amf.apicontract.client.scala.RAMLConfiguration
import amf.core.client.scala.errorhandling.UnhandledErrorHandler
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should

class ErrorHandlerTestScala extends AsyncFlatSpec with should.Matchers {

  "AMF client" should "use a custom error handler provider" in {
    val client =
      RAMLConfiguration
        .RAML10()
        .withErrorHandlerProvider(() =>
          UnhandledErrorHandler
        ) // throws an exception when an error is found
        .createClient()

    client.parse("file://resources/examples/resolution-error.raml") map { parseResult =>
      assertThrows[java.lang.Exception] {
        client.transform(parseResult.bu)
      }
    }
  }
}
