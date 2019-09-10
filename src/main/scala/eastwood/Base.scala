/**
 * (c) 2003-2016 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */
package eastwood

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Defines all the base pieces of any Eastwood test.
 */
class Base extends Simulation {

  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("eastwoodUrl")
  val organizationId = conf.getString("orgId")

  val httpConf = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  object request {

    /**
     * Creates a Design Session in Eastwood and saves type `sessionId` for any other request.
     */
    val createSession = exec(http("Create Design Session")
      .post("/api/v1/organizations/" + organizationId + "/sessions")
      .check(status.is(200))
      .check(jsonPath("$.id").saveAs("sessionId")));

    /**
     * Deletes the existing `sessionId` created for the current user.
     */
    val deleteSession = exec(http("Delete Design Session")
      .delete("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}")
      .check(status.is(200)))

    /**
     * Executes a basic Test Connectivity.
     */
    val runTestConnectivity =  exec(http("Make Test Connectivity Test")
      .post("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/test-connectivity")
      .body(RawFileBody("data/eastwood/testConnectivity-mysql.json")).asJson
      .check(status.is(200)))

    /**
     * Executes a Test Connectivity over an app with a known memory leak.
     */
    val runTestConnectivityLeak =  exec(http("Make Test Connectivity Test w/Leak")
      .post("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/test-connectivity")
      .body(RawFileBody("data/eastwood/testConnectivity-mysql-leak.json")).asJson
      .check(status.is(200)))

    /**
     * Begins a DataWeave session.
     */
    val dataWeaveStart =  exec(http("DataWeave Create Session")
      .post("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/dataweave")
      .body(RawFileBody("data/eastwood/dataweave-start-mysql.json")).asJson
      .check(status.is(200)))

    /**
     * Executes a DataWeave script.
     */
    val dataWeaveExecute =  exec(http("DataWeave Execute")
      .post("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/dataweave/execute")
      .body(RawFileBody("data/eastwood/dataweave-execute.json")).asJson
      .check(status.is(200)))

    /**
     * Stops the ongoing DataWeave session.
     */
    val dataWeaveStop =  exec(http("DataWeave Delete Session")
      .delete("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/dataweave")
      .check(status.is(200)))

    /**
     * Executes a simple Metadata propagation request.
     */
    val metadataPropagation = exec(http("Metadata Propagation")
      .post("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/metadata")
      .body(RawFileBody("data/eastwood/metadata-mysql.json")).asJson
      .check(status.is(200)))

    /**
     * Executes a base Autocomplete request.
     */
    val autocomplete = exec(http("Autocomplete")
      .post("/api/v1/organizations/" + organizationId + "/sessions/${sessionId}/autocompletion")
      .body(RawFileBody("data/eastwood/autocomplete.json")).asJson
      .check(status.is(200)))

  }
}