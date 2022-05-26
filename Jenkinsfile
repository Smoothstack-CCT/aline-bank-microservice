pipeline {
    environment {
        DOCKERHUB_CREDENTIALS=credentials("Dockerhub")
    }
    agent any

    tools {
        maven "MAVEN"
    }

    stages {

    stages {
        stage('SonarQube Analysis') {
            steps{
                script{
                    withSonarQubeEnv(installationName: "sonarqube") {
                        bat "mvn clean verify sonar:sonar -Dsonar.projectKey=aline-bank-microservice-KDL"
                        }
                }

            }       
        }

        stage('Quality Gate Check'){
            steps{
                script{
                    timeout (time:5, unit:"MINUTES")
                        def qg = waitForQualityGate()
                        if(qg.status != 'OK'){
                            error "Pipeline aborted due to quality gate failure"
                    }
                    }
                }
                
            }
        
        stage("Build MVN") {
            steps {
                bat "mvn -Dmaven.test.failure.ignore=true clean install"
            }
        }


    //     stage("Build Docker"){
    //         steps{
    //             bat "docker build -t laxwalrus/capstone-bank:$BUILD_NUMBER ."

    //         }            
    //     }


    //     stage("login to docker"){
    //         steps{
    //             bat "echo $DOCKERHUB_CREDENTIALS_PSW| docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
    //         }
    //     }

    //     stage("Deploy Docker"){
    //         steps{
    //             bat "docker push laxwalrus/capstone-bank:$BUILD_NUMBER"
    //         }
    //     }
            


    //     stage("Cleaning"){
    //         steps{
    //             bat "docker logout"
    //         }
    //     }
        
    //     stage('Archive') {
    //         steps {
    //         archiveArtifacts artifacts: 'bank-microservice/target/*.jar', followSymlinks: false
    //         }
    //     }
    }
    }
}