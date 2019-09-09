/**
 * (c) 2003-2016 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */
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
