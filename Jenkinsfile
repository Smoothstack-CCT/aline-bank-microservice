pipeline {

    agent {
        node {
            label 'dev-node'
        }
    }

    environment{
        COMMIT = ""
        DATE = new Date().format('M-yy')
        DOCKER_USERNAME = credentials('Docker-username-KDL')
        DOCKER_PASSWORD = credentials('Docker-Password-KDL')
        githubtoken = credentials('Github-token-KDL')
        AWS_ACCESS_KEY = credentials('KDL-SECRET-KEY')
        AWS_SECRET_ACCESS_KEY = credentials('KDL-SECRET-KEY-2')
        THE_BUTLER_SAYS_SO=credentials('AWS-KDL')
    }

    stages {
        // stage('SonarQube Analysis') {
        //     steps{
        //         script{
        //             withSonarQubeEnv(installationName: "sonarqube") {
        //                 sh "mvn clean test verify sonar:sonar -Dsonar.projectKey=bank-microservice-kdl"
        //                 }
        //         }

        //     }       
        // }

        // stage('Quality Gate Check'){
        //     steps{
        //         script{
        //              timeout(time: 2, unit: 'MINUTES'){
        //                 def qg = waitForQualityGate()
        //                 if(qg.status != 'OK'){
        //                     error "Pipeline aborted due to quality gate failure"
        //             }
        //              }

        //             }
        //         }
                
        //     }
        
        stage("Build w/MVN") {
            steps {
                sh "mvn clean package -Dskiptests"
            }
        }

        stage("Build Image w/docker"){
            steps{
                script{
                    COMMIT = "${GIT_COMMIT}"
                    SLICE = COMMIT[1..7]
                    sh "sudo docker build -t $env.AWS_ECR_REGISTRY_WEST/bank-microservice-kdl:${SLICE}.${BUILD_NUMBER}.${DATE} ."
                    sh "sudo docker build -t $env.AWS_ECR_REGISTRY_WEST/bank-microservice-kdl:latest ."
                }
                

            }            
        }

        stage("Publish to AWS"){
            steps{
                script{
                    COMMIT = "${GIT_COMMIT}"
                    SLICE = COMMIT[1..7]
                   docker.withRegistry("https://$env.AWS_ECR_REGISTRY_WEST", "ecr:$env.AWS_REGION_WEST:AWS-KDL"){
                       docker.image("$env.AWS_ECR_REGISTRY_WEST/bank-microservice-kdl:${SLICE}.${BUILD_NUMBER}.${DATE}").push()
                       docker.image("$env.AWS_ECR_REGISTRY_WEST/bank-microservice-kdl:${SLICE}.${BUILD_NUMBER}.${DATE}").push("latest")
                   }
                }
            }
        }
        stage("Deploy to AWS"){
            // use docker compose to deploy/update microservies 
        }
            
        stage("Cleaning"){
            steps{
                sh "sudo docker system prune --all -f"
                sh "sudo docker logout"
                sh "sudo rm -rf ~/.aws/"
                sh "sudo rm -rf ~/.sonar/*"
                sh 'sudo rm -rf ~/jenkins/workspace/${JOB_NAME}/*'
                sh 'sudo rm -rf ~/jenkins/workspace/${JOB_NAME}/.git*' 


            }
        }
        
    }
    }
