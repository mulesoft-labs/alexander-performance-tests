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

        stage("Performance") {
            withMaven(
                // Maven installation declared in the Jenkins "Global Tool Configuration"
                maven: 'M3',
                // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
                // Maven settings and global settings can also be defined in Jenkins Global Tools Configuration
                mavenSettingsConfig: 'my-maven-settings',
                mavenLocalRepo: '.repository') {

              // Run the maven build
              sh "mvn gatling:test"

            }
            gatlingArchive()
        }
    }
}