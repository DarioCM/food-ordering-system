pipeline{

    agent any

    tools{
        maven "Maven 3.9"
    }

    stages{

        stage("SCM checkout"){
            steps{
checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-pat-dario', url: 'https://github.com/DarioCM/food-ordering-system.git']])            }
        }

        stage("Build"){
            steps{
                script{
                    sh 'mvn clean install'
                }
            }
        }

        stage("Deploy"){
            steps{
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat', path: '', url: 'http://localhost:9090/')], contextPath: 'order-domain-1.0-SNAPSHOT.jar', war: '**/*.jar'            }
        }

    }
}