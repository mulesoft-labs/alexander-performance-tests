import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AlexanderTokenizer extends Simulation {

  object Tokenize {

    val tokenize = tryMax(2) { // let's try at max 2 times
      exec(http("Tokenizing input")
        .post("/api/v1/tokenize").asJSON
        .body(RawFileBody("data/config.json")).asJSON
        .check(status.is(200)))
    }.exitHereIfFailed // if the chain didn't finally succeed, have the user exit the whole scenario
  }

  val httpConf = http
    .baseURL("https://alexander-preprocessing-service.devx.msap.io")
    .acceptHeader("text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val users = scenario("Users").exec(Tokenize.tokenize)

  setUp(
    users.inject(rampUsers(500) over (15 seconds)),
  ).protocols(httpConf)

}

