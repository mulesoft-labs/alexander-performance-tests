#!/usr/bin/env groovy
def name = "mozart-performance"
node {
    withCredentials([
        [$class: "StringBinding", credentialsId: "NPM_TOKEN", variable: "NPM_TOKEN"],
        [$class: "UsernamePasswordMultiBinding", credentialsId: "nexus", passwordVariable: "NEXUS_PASS", usernameVariable: "NEXUS_USER"],
        [$class: 'UsernamePasswordMultiBinding', credentialsId: 'sonarqube-official', passwordVariable: 'SONAR_PASS', usernameVariable: 'SONAR_USER'],
        [$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-registry', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER']
    ]) {

        stage("Performance") {
                gatlingArchive()
        }
    }
}