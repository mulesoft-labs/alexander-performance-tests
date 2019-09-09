package eastwood.testconnectivity

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class TestConnectivity extends Simulation {

  def base = new Base

  val testConnectivtyScenario = scenario("Test Connectivity Base").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.runTestConnectivity,
      base.request.deleteSession
    )
  }

  setUp(
    testConnectivtyScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
