pipeline {
    agent any

    // Definimos las herramientas que Jenkins necesita (deben estar configuradas en Jenkins)
    tools {
        maven 'Maven 3.x' 
        jdk 'jdk25'
    }

    stages {
        stage('Checkout') {
            steps {
                // Jenkins descarga el código automáticamente desde GitHub
                echo 'Bajando el código del repo de Mauro...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilando el proyecto de SaludVital...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Ejecutando pruebas unitarias...'
                // Si tienes tests, esto los corre. Si no, al menos valida la estructura.
                sh 'mvn test -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Package') {
            steps {
                echo 'Generando el archivo JAR ejecutable...'
                sh 'mvn package'
            }
        }
        
        stage('Archiving Artifacts') {
            steps {
                // Guarda el JAR generado para que lo puedas descargar desde Jenkins
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado. ¡Bacán!'
        }
        success {
            echo '¡Éxito! El código está listo para facturar.'
        }
        failure {
            echo 'Algo se rompió. Revisa los logs de Camel o Maven.'
        }
    }
}
