pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('jenkins-sonarrr')
        SERVICE_NAME = 'coaching-service'
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
        CONTAINER_NAME = "coaching-service-${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "✅ Code récupéré depuis GitHub"
            }
        }

        stage('Tests - Coaching Service') {
            steps {
                // Utiliser 'verify' pour générer JaCoCo et les rapports
                bat 'mvn clean verify'
                echo "✅ Tests du coaching-service passés"
            }
            post {
                always {
                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sq1') {
                    bat """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=coaching-service \
                          -Dsonar.projectName="Coaching Service" \
                          -Dsonar.login=%SONAR_TOKEN% \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
                echo "✅ Analyse SonarQube terminée"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Construction de l'image Docker pour ${SERVICE_NAME}..."
                bat """
                    docker build -t wallstreet/${SERVICE_NAME}:${BUILD_NUMBER} \
                                 -t wallstreet/${SERVICE_NAME}:latest .
                """
                echo "✅ Image prête : wallstreet/${SERVICE_NAME}:${BUILD_NUMBER}"
            }
        }

        stage('Create and Run Container') {
            steps {
                echo "🚀 Création et démarrage du conteneur ${CONTAINER_NAME}..."
                bat """
                    docker run -d --name ${CONTAINER_NAME} -p 5059:5059 \
                      -e "SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/coaching_db?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true" \
                      -e SPRING_DATASOURCE_USERNAME=root \
                      -e SPRING_DATASOURCE_PASSWORD=root \
                      wallstreet/${SERVICE_NAME}:${BUILD_NUMBER}
                """
                echo "✅ Conteneur ${CONTAINER_NAME} démarré sur le port 5059"
            }
        }
    }

    post {
        success {
            echo """
🎉 Pipeline CI terminé avec succès pour ${SERVICE_NAME} !
📊 Voir les résultats SonarQube sur : ${SONAR_HOST_URL}/dashboard?id=coaching-service
🐳 Image Docker : wallstreet/${SERVICE_NAME}:${BUILD_NUMBER}
🚢 Conteneur "${CONTAINER_NAME}" a été exécuté (maintenant arrêté/supprimé)
"""
        }
        failure {
            echo "❌ Pipeline échoué pour ${SERVICE_NAME}"
            echo "Vérifie les tests unitaires ou la qualité du code"
        }
        always {
            // Nettoyage : arrêter et supprimer le conteneur
            echo "🧹 Nettoyage du conteneur ${CONTAINER_NAME}..."
            bat """
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
            """
            echo "✅ Conteneur supprimé"
        }
    }
}