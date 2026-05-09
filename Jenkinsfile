pipeline {
    agent any
    
    tools {
        jdk 'jdk25'
        maven 'Maven 3.x'
    }

    stages {
        stage('1. Preparación') {
            steps {
                // Aquí usamos el ID 'github-token' que creamos en Jenkins
                git url: 'https://github.com/SauroUwU/clinica-integracion.git', 
                    branch: 'main', 
                    credentialsId: 'github-token'
            }
        }

        stage('2. Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('3. Pruebas y Análisis (Paralelo)') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh 'mvn test'
                    }
                }
                stage('Static Analysis') {
                    steps {
                        echo 'Ejecutando análisis de estilo y calidad...'
                        sh 'mvn verify -DskipTests'
                    }
                }
            }
        }

        stage('4. Empaquetado') {
            when { branch 'main' }
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }
}
