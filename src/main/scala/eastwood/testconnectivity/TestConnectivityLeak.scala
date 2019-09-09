package eastwood.testconnectivity

import eastwood.Base
import io.gatling.core.Predef._

import scala.concurrent.duration._

class TestConnectivityLeak extends Simulation {

  def base = new Base

  val testConnectivtyLeakScenario = scenario("Test Connectivity With Leak").during(5 minutes) {
    exec(
      base.request.createSession,
      base.request.runTestConnectivityLeak,
      base.request.deleteSession
    )
  }

  setUp(
    testConnectivtyLeakScenario.inject(
      atOnceUsers(5)
    ).protocols(base.httpConf))
}
