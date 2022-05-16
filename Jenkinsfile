pipeline {
    agent any
    tools {
        maven "MAVEN"
    }
    stages {
        stage('Build') {
            steps {
            bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
        stage('Archive') {
            steps {
            archiveArtifacts artifacts: 'bank-microservice/target/*.jar', followSymlinks: false
            }
        }
    }
}