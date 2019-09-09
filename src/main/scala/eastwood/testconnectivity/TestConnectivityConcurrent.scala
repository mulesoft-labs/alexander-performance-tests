package eastwood.testconnectivity

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class TestConnectivityConcurrent extends Simulation {

  def base = new Base

  val testConnectivtyMultipleScenario = scenario("Test Connectivity Multiple Requests Per User").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.runTestConnectivity,
      base.request.runTestConnectivity,
      base.request.runTestConnectivity,
      base.request.runTestConnectivity,
      base.request.runTestConnectivity,
      base.request.deleteSession
    )
  }

  setUp(
    testConnectivtyMultipleScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
