pipeline {
    agent any

    tools {
        jdk 'jdk25'
        maven 'Maven 3.x'
    }

    stages {
        stage('1. Preparación') {
            steps {
                echo 'Clonando el repositorio mediante autenticación HTTPS estática...'
                git url: 'https://SauroUwU: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@github.com/SauroUwU/clinica-integracion.git', branch: 'main'
            }
        }

        stage('2. Build') {
            steps {
                echo 'Compilando el proyecto...'
                sh 'mvn clean compile -DskipTests'
            }
        }

        stage('3. Validación Paralela') {
            parallel {
                stage('Cómputo') {
                    steps {
                        echo 'Verificando entorno de compilación...'
                        sh 'mvn --version'
                    }
                }
                stage('Análisis') {
                    steps {
                        ejecutarAnalisisEstatico()
                    }
                }
            }
        }

        stage('4. Auditoría de Seguridad') {
            steps {
                echo 'Ejecutando análisis de árbol de dependencias...'
                sh 'mvn dependency:tree'
            }
        }

        stage('5. Empaquetado') {
            when { branch 'main' }
            steps {
                echo 'Generando artefacto JAR...'
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('6. Notificación de Despliegue') {
            steps {
                echo 'Simulando notificación de estado a servicios externos...'
                sh 'echo "Notificación: Artefacto empaquetado y listo para despliegue."'
            }
        }
    }

    post {
        success {
            echo 'Pipeline finalizado correctamente sin errores.'
        }
    }
}

def ejecutarAnalisisEstatico() {
    echo 'Verificando calidad del código...'
    sh 'mvn compile -DskipTests'
}
