#!/usr/bin/env groovy
def name = "mozart-performance"
node {
    withCredentials([
        [$class: "StringBinding", credentialsId: "NPM_TOKEN", variable: "NPM_TOKEN"],
        [$class: "UsernamePasswordMultiBinding", credentialsId: "nexus", passwordVariable: "NEXUS_PASS", usernameVariable: "NEXUS_USER"],
        [$class: 'UsernamePasswordMultiBinding', credentialsId: 'sonarqube-official', passwordVariable: 'SONAR_PASS', usernameVariable: 'SONAR_USER'],
        [$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-registry', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER']
    ]) {
            stage("Checkout") {
                checkoutSCM(scm)
            }

            stage("Set Maven configuration") {
                env.MAVEN_CUSTOM_GOALS = "javadoc:javadoc install"
                env.MAVEN_CUSTOM_OPTS = "clean checkstyle:checkstyle -DskipTests"

                mvnHome = tool name: 'Maven 3', type: 'hudson.tasks.Maven$MavenInstallation'
                jdkHome = tool name: 'Java 8', type: 'hudson.model.JDK'
            }

            stage("Build & Test") {
                withEnv(["PATH=${jdkHome}/bin:${mvnHome}/bin:${env.PATH}"]) {
                    echo sh(script: 'env|sort', returnStdout: true)
                    sh "mvn gatling:test"
                }
                gatlingArchive()
            }

    }
}