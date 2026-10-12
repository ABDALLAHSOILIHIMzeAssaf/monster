# Kotlin Monsters

Petit jeu de monstres en console, écrit en Kotlin (projet de BTS SIO, Lycée Léonard de Vinci).

## Lancer le jeu
1. Ouvrir le projet dans IntelliJ IDEA (JDK 21 ou plus, Gradle).
2. Lancer `org.example.MainKt` **depuis la racine du projet** (les art ASCII sont dans `src/main/resources/art`).

## Lancer les tests
`./gradlew test` (ou le bouton ▶ à côté de chaque classe de test).

## Déroulement
- Choix du monstre de départ (Springleaf, Flamkip ou Aquamy), qui peut être renommé.
- Exploration : Route 1 → Route 2 → RacailleCity (ville avec hôpital et arène).
- Combats contre des monstres sauvages : attaquer (techniques), utiliser un objet
  (Monster Kube, capsules techniques), changer de monstre, fuir, attendre.
- Arène de RacailleCity : deux dresseurs puis le champion ; la victoire donne un badge.

## Structure (`src/main/kotlin/org/example`)
| Package    | Contenu |
|------------|---------|
| `dresseur` | `Entraineur` |
| `item`     | `Item`, `Badge`, `Utilisable`, `MonsterKube`, `CapsuleTechnique` |
| `jeu`      | `Partie`, `CombatMonstre`, `CombatDresseur` |
| `monde`    | `Zone`, `Ville`, `Arene` |
| `monstre`  | `EspeceMonstre`, `IndividuMonstre`, `Element`, `Technique`, `PalierTechnique`, `PalierEvolution` |

## Fonctionnalités des sprints
- **Sprint 1** : espèces, individus (niveaux, stats, exp), zones, items, combats, parties.
- **Sprint 2** : éléments (forces/faiblesses/STAB), techniques et capsules techniques,
  évolution (Flamkip → Pyrokip au niveau 7), villes, arènes et combats de dresseurs.
- Tests unitaires JUnit 5 dans `src/test`.
