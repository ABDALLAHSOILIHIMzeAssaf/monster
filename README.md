# 🐾 Monster

> Un jeu de rôle en console développé en Kotlin, centré sur l’exploration, les dresseurs et les combats de monstres.

**Monster** est un projet personnel de programmation qui s’inspire des jeux de capture et de combat de créatures. Le jeu utilise une interface textuelle et des illustrations ASCII pour représenter les monstres pendant les combats.

## ✨ Fonctionnalités

- **Combats de monstres** : système de combat géré par le code du jeu.
- **Dresseur et équipe** : représentation du joueur et de ses monstres.
- **Espèces et individus** : distinction entre les caractéristiques d’une espèce et celles d’un monstre particulier.
- **Objets utilisables** : système d’objets et de badges.
- **Zones du monde** : organisation du monde en différentes zones.
- **Illustrations ASCII** : affichage des monstres à l’aide de fichiers texte.
- **Jeu en console** : interaction via le terminal, sans interface graphique obligatoire.

> Le projet est en cours de développement : les mécaniques disponibles et le déroulement complet du jeu peuvent évoluer.

## 🧰 Technologies

- **Kotlin** — langage principal
- **JVM** — environnement d’exécution
- **Gradle** — configuration et gestion de la construction du projet
- **Fichiers texte ASCII** — illustrations des monstres

## 🗂️ Organisation du code

```text
monster/
├── src/
│   └── main/
│       ├── kotlin/
│       │   ├── Main.kt                 # Point d’entrée du jeu
│       │   ├── dresseur/
│       │   │   └── Entraineur.kt       # Dresseur et équipe
│       │   ├── item/
│       │   │   ├── Badge.kt            # Badges
│       │   │   ├── Item.kt             # Modèle des objets
│       │   │   └── Utilisable.kt       # Objets utilisables
│       │   ├── jeu/
│       │   │   └── Partie.kt           # Gestion d’une partie
│       │   ├── monde/
│       │   │   └── Zone.kt             # Zones du monde
│       │   └── monstre/
│       │       ├── CombatMonstre.kt    # Logique de combat
│       │       ├── EspeceMonstre.kt    # Données d’une espèce
│       │       ├── IndividuMonstre.kt  # Monstre individuel
│       │       └── MonsterKube.kt      # Gestion/affichage associé aux monstres
│       └── resources/
│           └── art/                    # Illustrations ASCII des monstres
├── gradle/
│   └── wrapper/                        # Wrapper Gradle
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

## 🚀 Lancer le projet

### Prérequis

- Un JDK compatible avec la configuration Gradle du projet
- Git (facultatif si le dépôt est téléchargé en ZIP)

### 1. Cloner le dépôt

```bash
git clone https://github.com/ABDALLAHSOILIHIMzeAssaf/monster.git
cd monster
```

### 2. Lancer le jeu

Sous Windows :

```powershell
.\gradlew.bat run
```

Sous Linux ou macOS :

```bash
chmod +x ./gradlew
./gradlew run
```

Si la tâche `run` n’est pas disponible, ouvre le projet dans IntelliJ IDEA et vérifie `build.gradle.kts` ainsi que la configuration du plugin Gradle `application`. Pour lancer le jeu depuis l’IDE, ouvre `src/main/kotlin/Main.kt` et exécute la fonction `main` si IntelliJ reconnaît correctement la configuration Kotlin/JVM.

## 🧠 Concepts travaillés

- Programmation orientée objet (POO)
- Classes et responsabilités distinctes
- Modélisation d’un univers de jeu
- Séparation entre espèces de monstres et individus
- Logique de combat et gestion d’objets
- Ressources embarquées et affichage de texte dans le terminal
- Organisation d’un projet Kotlin avec Gradle

## 🛣️ Pistes d’amélioration

Quelques idées pour faire évoluer le projet :

- Ajouter de nouvelles espèces, attaques et objets.
- Développer davantage l’exploration et les interactions avec les dresseurs.
- Améliorer la gestion des sauvegardes et de la progression.
- Ajouter des tests automatisés pour les combats et les règles du jeu.
- Enrichir les illustrations et l’expérience utilisateur dans le terminal.

## 👤 Auteur

**Mzé Assaf ABDALLAH SOILIHI**

- GitHub : [@ABDALLAHSOILIHIMzeAssaf](https://github.com/ABDALLAHSOILIHIMzeAssaf)

---

*Projet en développement — le code et les mécaniques peuvent changer au fil des mises à jour.*
