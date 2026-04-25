pipeline {
    agent any

    tools {
        // Asegúrate de que el nombre 'jdk25' coincida con el que pusiste en Jenkins
        jdk 'jdk25'
        maven 'Maven 3.x'
    }

    stages {
        // ETAPA 1: Clonar el código
        stage('Clonar') {
            steps {
                echo 'Bajando el código desde GitHub...'
                checkout scm
            }
        }

        // ETAPA 2: Verificar (Compilación y Sintaxis)
        stage('Verificar') {
            steps {
                echo 'Verificando que el código de SaludVital no tenga errores...'
                sh 'mvn clean compile'
            }
        }

        // ETAPA 3: Empaquetar (Generar el JAR)
        stage('Empaquetar') {
            steps {
                echo 'Generando el archivo JAR ejecutable...'
                sh 'mvn package -DskipTests' 
                echo 'El paquete está listo en la carpeta target.'
            }
        }
    }

    post {
        success {
            echo 'El pipeline terminó sin errores'
        }
        failure {
            echo 'Algo falló en la compilación o el empaquetado.'
        }
    }
}
