# Quiz en images — Application Android

## 1. Présentation du projet

Cette application Android est un quiz interactif sur les formes géométriques. L'utilisateur répond à cinq questions illustrées et choisit une réponse parmi trois propositions.

L'application calcule le score, affiche la correction de chaque réponse, met à jour la progression et présente le résultat final.

## 2. Technologies utilisées

- **Langage :** Kotlin
- **Interface utilisateur :** XML
- **Environnement de développement :** Android Studio
- **SDK minimum :** API 24 — Android 7.0 Nougat
- **Compile SDK :** API 37
- **Architecture :** une seule activité `MainActivity`
- **Stockage :** tableau statique Kotlin, sans base de données
- **Images :** ressources locales dans `res/drawable/`

## 3. Fonctionnalités

- Cinq questions illustrées sur les formes géométriques.
- Trois boutons de réponse par question.
- Vérification immédiate de la réponse.
- Affichage d'un message de correction.
- Calcul automatique du score sur 5.
- Barre de progression des réponses données.
- Désactivation des boutons après une réponse.
- Affichage du résultat final.
- Bouton pour recommencer le quiz.

## 4. Structure du projet

```text
QuizImages/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/quizimages/
│           │   └── MainActivity.kt
│           └── res/
│               ├── drawable/
│               │   ├── triangle.png
│               │   ├── carre.png
│               │   ├── cercle.png
│               │   ├── etoile.png
│               │   └── rectangle.png
│               └── layout/
│                   └── activity_main.xml
├── build.gradle.kts
└── README.md
```

## 5. Configuration du SDK

Dans `app/build.gradle.kts`, configurer les versions Android comme suit :

```kotlin
android {
    namespace = "com.example.quizimages"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.quizimages"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}
```

**Remarque :** `targetSdk` peut être conservé à 36 indépendamment de `compileSdk`. Ces paramètres ont des rôles différents.

## 6. Installation et exécution

1. Installer Android Studio.
2. Ouvrir le projet dans Android Studio.
3. Installer Android SDK Platform 37 depuis le SDK Manager.
4. Synchroniser le projet avec Gradle.
5. Vérifier la présence des cinq images dans `res/drawable/`.
6. Démarrer un émulateur Android ou connecter un téléphone compatible.
7. Cliquer sur **Run** pour lancer l'application.

## 7. Tests à réaliser

| Test | Résultat attendu |
|---|---|
| Démarrage du quiz | Score 0/5 et progression 0/5 |
| Bonne réponse | Score augmenté de 1 |
| Mauvaise réponse | Score inchangé |
| Validation d'une réponse | Progression augmentée de 1 |
| Clics répétés | Aucun point supplémentaire |
| Cinq bonnes réponses | Résultat final 5/5 |
| Cinq mauvaises réponses | Résultat final 0/5 |
| Rejouer | Score et progression réinitialisés |

## 8. Conclusion

Ce projet met en pratique les bases du développement Android avec Kotlin et XML, notamment la création d'interfaces, la gestion des événements de clic, les tableaux d'objets et la mise à jour dynamique des composants graphiques.