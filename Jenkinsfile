pipeline {
    agent any
    tools {
        jdk 'jdk25'
        maven 'Maven 3.x'
    }
    stages {
        stage('1. Preparación') {
            steps {
                git url: 'https://github.com/SauroUwU/clinica-integracion.git', branch: 'main'
            }
        }
        stage('2. Build') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }
        }
        stage('3. Validación Paralela') {
            parallel {
                stage('Cómputo') {
                    steps {
                        // REGLA DE ORO: No usar 'mvn test' aquí
                        sh 'mvn --version'
                    }
                }
                stage('Análisis') {
                    steps {
                        sh 'mvn compile -DskipTests'
                    }
                }
            }
        }
        stage('4. Empaquetado') {
            when { branch 'main' }
            steps {
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
