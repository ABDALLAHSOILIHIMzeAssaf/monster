# 🐾 Monster - RPG Console

Un jeu de rôle (RPG) textuel développé en Kotlin, jouable directement dans la console. Ce projet a été conçu pour mettre en pratique les concepts avancés de la Programmation Orientée Objet (POO) et découvrir l'écosystème Kotlin/JVM.

## 🎮 Fonctionnalités du jeu

- **Capture de monstres :** Explorez le monde et capturez différentes créatures pour agrandir votre équipe.
- **Système de combat au tour par tour :** Affrontez des adversaires dans des duels tactiques en console.
- **Gestion d'équipe :** Organisez vos monstres, surveillez leurs statistiques et optimisez votre stratégie.
- **Progression dynamique :** Faites évoluer vos créatures au fil des combats.

## 🛠️ Concepts POO & Architecture

Ce projet démontre la maîtrise des piliers du développement logiciel :
- **Encapsulation :** Protection des données des monstres (points de vie, attaque, défense) et contrôle des états via des getters/setters propres à Kotlin.
- **Héritage et Polymorphisme :** Création de classes de monstres spécialisées possédant leurs propres comportements et attaques spécifiques.
- **Gestion des collections :** Utilisation des listes et des maps Kotlin pour gérer l'équipe du joueur et l'inventaire des monstres capturés.

## 💻 Technologies utilisées

- **Langage :** Kotlin (100%)
- **Plateforme :** JVM (Java Virtual Machine)
- **Outil de build :** Gradle / Maven (selon votre configuration)

## ⚙️ Comment jouer

### Prérequis
- Java JDK 17 ou supérieur installé
- Un terminal ou un IDE (IntelliJ IDEA recommandé)

### Lancement rapide
1. **Cloner le dépôt :**
   ```bash
   git clone https://github.com
   cd monster
   ```

2. **Compiler et exécuter :**
   - **Sur Linux / macOS :**
     ```bash
     ./gradlew run
     ```
   - **Sur Windows (Invite de commandes / PowerShell) :**
     ```cmd
     gradlew.bat run
     ```
   *(Alternative : Ouvrez simplement le projet dans IntelliJ IDEA et cliquez sur la flèche verte "Run" sur votre fichier Principal).*

---
⭐️ *Si vous aimez les RPG textuels ou si ce projet Kotlin vous a intéressé, n'hésitez pas à laisser une étoile !*
