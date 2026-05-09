pipeline {
    agent any

    tools {
        // Asegúrate que estos nombres coincidan con lo que pusiste en 'Manage Jenkins -> Tools'
        jdk 'jdk25'
        maven 'Maven 3.x'
    }

    stages {
        // ESTRATEGIA 1: División por etapas claras
        stage('1. Preparación') {
            steps {
                echo 'Clonando el repositorio de la Clínica (Público)...'
                git url: 'https://github.com/SauroUwU/clinica-integracion.git', branch: 'main'
            }
        }

        stage('2. Build y Compilación') {
            steps {
                echo 'Compilando el proyecto con Maven...'
                sh 'mvn clean compile'
            }
        }

        // ESTRATEGIA 4: Paralelismo (Ejecutar tareas simultáneas)
        stage('3. Validación y Calidad') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        echo 'Ejecutando pruebas unitarias...'
                        // Usamos -DskipTests=false para asegurar que corran
                        sh 'mvn test'
                    }
                }
                stage('Análisis de Código') {
                    steps {
                        // ESTRATEGIA 2: Modularidad (Llamada a función externa)
                        ejecutarAnalisisEstatico()
                    }
                }
            }
        }

        stage('4. Empaquetado') {
            // ESTRATEGIA 3: IC por rama (Solo empaqueta si es la rama principal)
            when {
                branch 'main'
            }
            steps {
                echo 'Generando el archivo JAR final...'
                sh 'mvn package -DskipTests'
                // Guarda el archivo generado para que lo puedas descargar de Jenkins
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '¡Misión cumplida! El pipeline de la Clínica terminó perfecto.'
        }
        failure {
            echo 'Algo salió mal. Revisa los logs de la etapa que se puso roja.'
        }
    }
}

// ESTRATEGIA 2: Definición de pipeline modular (Función reutilizable)
def ejecutarAnalisisEstatico() {
    echo 'Verificando estilo de código y bugs con Maven Verify...'
    sh 'mvn verify -DskipTests'
}
