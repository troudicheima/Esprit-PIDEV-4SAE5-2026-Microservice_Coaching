pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('jenkins-sonarrr')
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
                bat 'mvn clean test'
                echo "✅ Tests du coaching-service passés"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sq1') {
                    bat 'mvn sonar:sonar -Dsonar.projectKey=coaching-service -Dsonar.projectName="Coaching Service" -Dsonar.login=%SONAR_TOKEN%'
                }
                echo "✅ Analyse SonarQube terminée"
            }
        }
    }

    post {
        success {
            echo "🎉 Pipeline CI terminé avec succès pour le coaching-service !"
            echo "📊 Voir les résultats sur: ${SONAR_HOST_URL}"
        }
        failure {
            echo "❌ Pipeline échoué"
            echo "Vérifie les tests unitaires ou la qualité du code"
        }
    }
}