import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class AlexanderLearner extends Simulation {

  object Fit {

    val fit = tryMax(2) { // let's try at max 2 times
      exec(http("Fitting model")
        .post("/api/v1/fit")
        .queryParam("targetDirectory", "deepModels/FastText/test")
        .check(status.is(200)))
    }.exitHereIfFailed // if the chain didn't finally succeed, have the user exit the whole scenario
  }

  val httpConf = http
    .baseURL("https://alexander-service.devx.msap.io")
    .acceptHeader("text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val users = scenario("Users").exec(Fit.fit)

  setUp(
    users.inject(rampUsers(100) over (15 seconds)),
  ).protocols(httpConf)

}


