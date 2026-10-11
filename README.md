# 🐾 Monsters — Jeu de rôle en Kotlin

> Un projet de jeu de rôle en console autour de la découverte, de la capture et du combat de monstres, développé en Kotlin.

## 📌 Présentation

**Monsters** est un projet de jeu vidéo textuel qui explore les mécaniques classiques des jeux de rôle et de collection de créatures. Le joueur peut être représenté par un dresseur, gérer une équipe de monstres, utiliser des objets et faire combattre ses créatures.

Le projet est conçu pour pratiquer la **programmation orientée objet (POO)** avec Kotlin : les différentes responsabilités du jeu sont réparties entre des classes dédiées aux monstres, aux dresseurs, aux combats, aux objets et aux zones.

> **État du projet :** en cours de développement. Certaines mécaniques et parties du parcours de jeu restent à compléter.

## ✨ Fonctionnalités et mécaniques

Les éléments présents dans le code comprennent notamment :

- 🐲 **Espèces et individus de monstres** : statistiques, caractéristiques et descriptions propres à chaque espèce.
- ⚔️ **Combats** : une classe dédiée aux interactions de combat entre monstres.
- 🎒 **Objets utilisables** : système d'objets, dont le *Monster Kube*, prévu pour tenter de capturer un monstre.
- 🧑‍🎓 **Dresseurs** : représentation du joueur et gestion d'une équipe de monstres ainsi que d'une boîte de stockage.
- 🗺️ **Zones d'exploration** : structure pour organiser les zones et les espèces de monstres qui peuvent y apparaître.
- 🎨 **Art ASCII** : ressources textuelles permettant de représenter les monstres dans la console.
- 🏅 **Badges** : modèle d'objet permettant de représenter des récompenses de progression.

Toutes ces mécaniques ne sont pas encore réunies dans une boucle de jeu complète : certaines méthodes sont encore à implémenter et le point d'entrée actuel sert notamment à tester des fonctionnalités.

## 🛠️ Technologies

- **Kotlin** — langage principal
- **JVM** — environnement d'exécution
- **Gradle Kotlin DSL** — configuration et gestion du projet
- **JDK 21** — version configurée dans le projet
- **IntelliJ IDEA** — IDE conseillé
- **Git / GitHub** — gestion de versions et hébergement du code

## 📂 Structure du projet

```text
monster/
├── src/
│   └── main/
│       ├── kotlin/
│       │   ├── Main.kt                  # Point d'entrée et données de démonstration
│       │   ├── dresseur/
│       │   │   └── Entraineur.kt        # Dresseurs et équipe
│       │   ├── item/
│       │   │   ├── Item.kt              # Base des objets
│       │   │   ├── Utilisable.kt        # Contrat des objets utilisables
│       │   │   └── Badge.kt             # Badges
│       │   ├── jeu/
│       │   │   └── Partie.kt            # Logique d'une partie
│       │   ├── monde/
│       │   │   └── Zone.kt              # Zones du monde
│       │   └── monstre/
│       │       ├── EspeceMonstre.kt     # Données des espèces
│       │       ├── IndividuMonstre.kt   # Monstres individuels
│       │       ├── CombatMonstre.kt     # Système de combat
│       │       └── MonsterKube.kt       # Objet de capture
│       └── resources/
│           └── art/                     # Illustrations ASCII des monstres
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── gradlew.bat
```

*La structure ci-dessus présente les principaux fichiers source du projet ; certains fichiers peuvent évoluer au fil du développement.*

## 🚀 Installation et lancement

### Prérequis

- Un **JDK 21** installé
- **Git** pour cloner le dépôt
- IntelliJ IDEA (recommandé) ou un terminal compatible avec Gradle

### 1. Cloner le dépôt

```bash
git clone https://github.com/ABDALLAHSOILIHIMzeAssaf/monster.git
cd monster
```

### 2. Ouvrir le projet

Ouvre le dossier dans IntelliJ IDEA, puis laisse Gradle importer et synchroniser le projet. Vérifie que le projet utilise un JDK 21.

### 3. Lancer le programme

Depuis IntelliJ IDEA, ouvre `src/main/kotlin/Main.kt` et lance la fonction `main()` si la configuration Kotlin/JVM est correctement reconnue.

Tu peux aussi essayer le Gradle Wrapper :

**Windows :**

```powershell
.\gradlew.bat run
```

**Linux / macOS :**

```bash
./gradlew run
```

> **Remarque :** le fichier `build.gradle.kts` actuel ne déclare pas encore de tâche Gradle `run`. Si cette commande échoue, lance `Main.kt` depuis IntelliJ IDEA ou ajoute une configuration d'application Gradle avant d'utiliser cette commande. Le premier lancement peut également nécessiter le téléchargement de la distribution Gradle et des dépendances.

## 🧠 Concepts mis en pratique

Ce projet permet notamment de travailler sur :

- la modélisation d'un domaine avec des classes et des objets ;
- la séparation des responsabilités entre plusieurs packages ;
- les propriétés, méthodes et relations entre objets ;
- les interfaces Kotlin pour définir des comportements communs ;
- les collections mutables pour gérer équipes et zones ;
- les entrées et sorties dans une application console ;
- la configuration d'un projet Kotlin/JVM avec Gradle.

## 🗺️ Pistes d'amélioration

Les prochaines étapes possibles incluent :

- [ ] Finaliser la boucle de jeu et la progression entre les zones.
- [ ] Compléter les menus de gestion de l'équipe et de la partie.
- [ ] Équilibrer les statistiques, les dégâts et les chances de capture.
- [ ] Ajouter davantage d'espèces, de capacités et d'objets.
- [ ] Améliorer la gestion des erreurs de saisie.
- [ ] Ajouter des tests automatisés pour les combats et la capture.
- [ ] Documenter les règles du jeu et ajouter des captures de la console.

## 👨‍💻 Auteur

**Mzé Abdallah Soilihi**

- GitHub : [@ABDALLAHSOILIHIMzeAssaf](https://github.com/ABDALLAHSOILIHIMzeAssaf)
