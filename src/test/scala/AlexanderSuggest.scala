import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AlexanderSuggest extends Simulation {

  object Suggestion {
    val suggest = tryMax(2) { // let's try at max 2 times
      exec(http("Suggest input")
        .post("/api/v1/automapper/suggestion").asJson
        .body(RawFileBody("data/suggestion.json")).asJson
        .check(status.is(200)))
    }.exitHereIfFailed // if the chain didn't finally succeed, have the user exit the whole scenario
  }

  val httpConf = http
    .baseUrl("http://127.0.0.1:8080")
    .acceptHeader("text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val users = scenario("Users").exec(Suggestion.suggest)

  setUp(
    users.inject(constantUsersPerSec(0.3) during (5 minutes))
  ).protocols(httpConf)
}