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
                    sh 'docker context use default'
                    COMMIT = "${GIT_COMMIT}"
                    SLICE = COMMIT[1..7]
                   docker.withRegistry("https://$env.AWS_ECR_REGISTRY_WEST", "ecr:$env.AWS_REGION_WEST:AWS-KDL"){
                       docker.image("$env.AWS_ECR_REGISTRY_WEST/bank-microservice-kdl:${SLICE}.${BUILD_NUMBER}.${DATE}").push()
                       docker.image("$env.AWS_ECR_REGISTRY_WEST/bank-microservice-kdl:${SLICE}.${BUILD_NUMBER}.${DATE}").push("latest")
                   }
                }
            }
        }

        stage("Grab docker compose file"){
            steps {

                sh 'curl -H "Authorization: token ${githubtoken}" https://raw.githubusercontent.com/Cirrus-Biz/DevOPS-KDL/dev/AWS/docker-compose.yaml -o docker-compose.yaml'

                sh 'cat docker-compose.yaml'
            }
        }


        stage("docker compose up"){
            steps{
                script{  
        
                        sh 'docker logout'
                        sh 'docker context use default'


                        sh 'aws configure set aws_access_key_id ${AWS_ACCESS_KEY} --profile kdl-aws-profile'

                        sh 'aws configure set aws_secret_access_key ${AWS_SECRET_ACCESS_KEY} --profile kdl-aws-profile'

                        sh 'aws configure set region us-west-2 --profile kdl-aws-profile' 

                        sh 'aws configure set output json --profile kdl-aws-profile'

                        sh 'export AWS_PROFILE=kdl-aws-profile'

                        sh 'echo ${DOCKER_PASSWORD} | docker login --username ${DOCKER_USERNAME} --password-stdin'  
  

                        sh "aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin ${env.AWS_ECR_REGISTRY_WEST}"
               
                        // sh 'docker context create ecs kdl-ecs --profile kdl-aws-profile'

                        sh 'docker context use kdl-ecs'     

          
                        sh 'docker compose -p "kdl-ecs" up -d --no-color'
                                
                    
                }

            }

        }
            
        stage("Cleaning"){
            steps{
            sh 'docker context use default'
            sh 'docker system prune --all -f'
            sh 'docker logout'
            sh 'sudo rm -rf ~/.aws/'
            sh 'sudo rm -rf ~/.docker/'
            sh 'sudo rm -rf ~/.sonar/*'
            sh 'sudo rm -rf ~/jenkins/workspace/${JOB_NAME}/*'
            sh 'sudo rm -rf ~/jenkins/workspace/${JOB_NAME}/.git*'


            }
        }
        
    }
    }
