package scala

import amf.apicontract.client.scala.RAMLConfiguration
import amf.core.client.scala.execution.ExecutionEnvironment
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

class SchedulerTest extends AsyncFlatSpec with should.Matchers {

  "AMF configuration" should "allow defining custom execution context" in {
    /* Instantiating a ScheduledExecutorService with some example arguments. */
    val scheduler = Executors.newScheduledThreadPool(30);

    /* Instantiating a ExecutionEnvironment passing as argument the scheduler previously created. */
    val executionEnvironment = new ExecutionEnvironment(ExecutionContext.fromExecutorService(scheduler));

    val config = RAMLConfiguration.RAML10()
      .withExecutionEnvironment(executionEnvironment);
    val client = config.createClient();

    for {
      parseResult <-client.parse("file://resources/examples/banking-api.raml")
      _ <- client.validate(parseResult.bu)
    } yield {
      /* Shutting down the scheduler which kills the AMF threads created in the thread pool provided by that scheduler. */
      scheduler.shutdownNow();
      succeed
    }
  }
}
