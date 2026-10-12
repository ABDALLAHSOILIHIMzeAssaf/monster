package org.example

import org.example.dresseur.Entraineur
import org.example.item.CapsuleTechnique
import org.example.item.MonsterKube
import org.example.jeu.Partie
import org.example.monde.Arene
import org.example.monde.Ville
import org.example.monde.Zone
import org.example.monstre.Element
import org.example.monstre.EspeceMonstre
import org.example.monstre.IndividuMonstre
import org.example.monstre.PalierEvolution
import org.example.monstre.PalierTechnique
import org.example.monstre.Technique

/**
 * Change la couleur du message donné selon le nom de la couleur spécifié.
 * Cette fonction utilise les codes d'échappement ANSI pour appliquer une
 * couleur à la sortie console. Si un nom de couleur non reconnu ou une chaîne
 * vide est fourni, aucune couleur n'est appliquée.
 *
 * @param message Le message auquel la couleur sera appliquée.
 * @param couleur Le nom de la couleur à appliquer (ex: "rouge", "vert",
 * "bleu"). Par défaut c'est une chaîne vide, ce qui n'applique aucune couleur.
 * @return Le message coloré sous forme de chaîne, ou le même message si
 * aucune couleur n'est appliquée.
 */
fun changeCouleur(message: String, couleur: String = ""): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge" -> "\u001B[31m"
        "vert" -> "\u001B[32m"
        "orange" -> "\u001B[32m" // orange : même code que le vert (demandé)
        "jaune" -> "\u001B[33m"
        "marron" -> "\u001B[33m"
        "bleu" -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan" -> "\u001B[36m"
        "blanc" -> "\u001B[37m"
        else -> "" // pas de couleur si non reconnu
    }
    return "$codeCouleur$message$reset"
}

// ============================ Dresseurs ============================

/** Déclaration du joueur principal. */
var joueur = Entraineur(1, "Sacha", 100)
var rival = Entraineur(2, "Régis", 500)

/** Dresseurs de l'arène : deux dresseurs puis le champion. */
val dresseur1 = Entraineur(10, "Léo le Cadet", 200)
val dresseur2 = Entraineur(11, "Mira la Rocailleuse", 300)
val championFlamboyant = Entraineur(12, "Flora la Flamboyante", 600)

// ============================ Éléments ============================

val lumiere = Element(1, "Lumière")
val feu = Element(2, "Feu")
val plante = Element(3, "Plante")
val eau = Element(4, "Eau")
val insecte = Element(5, "Insecte")
val roche = Element(6, "Roche")
val normal = Element(7, "Normal")

/**
 * Renseigne les forces et faiblesses de chaque élément. Les relations sont
 * stockées dans des ensembles : appeler cette fonction plusieurs fois ne
 * crée pas de doublons.
 */
fun valoriserElements() {
    feu.forces.addAll(listOf(plante, insecte))
    feu.faiblesses.addAll(listOf(eau, roche, feu))

    plante.forces.addAll(listOf(eau, roche))
    plante.faiblesses.addAll(listOf(feu, insecte))

    eau.forces.addAll(listOf(feu, roche))
    eau.faiblesses.addAll(listOf(plante))

    insecte.forces.addAll(listOf(plante))
    insecte.faiblesses.addAll(listOf(feu, roche))

    roche.forces.addAll(listOf(feu, insecte))
    roche.faiblesses.addAll(listOf(eau, plante))

    normal.faiblesses.addAll(listOf(roche))
}

// ============================ Techniques ============================

// ⚪ Normal
val charge = Technique(
    id = 1, nom = "Charge", precision = 95.0, multiplicateurPuissance = 1.0,
    estSpecial = false, estBuff = false, estDebuff = false, faireDegats = true,
    elementTechnique = normal
)

// 🔥 Feu
val flammeche = Technique(
    id = 2, nom = "Flammèche", precision = 90.0, multiplicateurPuissance = 1.2,
    estSpecial = true, estBuff = false, estDebuff = false, faireDegats = true,
    elementTechnique = feu
)

// 🔥 Feu : attaque spéciale qui pourra infliger le debuff Brûlure (futur)
val flammeEclair = Technique(
    id = 3, nom = "Flamme Éclair", precision = 90.0, multiplicateurPuissance = 1.5,
    estSpecial = true, estBuff = false, estDebuff = true, faireDegats = true,
    elementTechnique = feu
)

// 🌱 Plante : attaque physique
val tranchFeuille = Technique(
    id = 4, nom = "Tranch'Feuille", precision = 95.0, multiplicateurPuissance = 1.1,
    estSpecial = false, estBuff = false, estDebuff = false, faireDegats = true,
    elementTechnique = plante
)

// 💧 Eau
val pistoletAEau = Technique(
    id = 5, nom = "Pistolet à eau", precision = 100.0, multiplicateurPuissance = 1.0,
    estSpecial = true, estBuff = false, estDebuff = false, faireDegats = true,
    elementTechnique = eau
)

// 🐛 Insecte
val piqure = Technique(
    id = 6, nom = "Piqûre", precision = 100.0, multiplicateurPuissance = 1.0,
    estSpecial = false, estBuff = false, estDebuff = false, faireDegats = true,
    elementTechnique = insecte
)

// 🪨 Roche
val jetDePierre = Technique(
    id = 7, nom = "Jet de pierre", precision = 90.0, multiplicateurPuissance = 1.2,
    estSpecial = false, estBuff = false, estDebuff = false, faireDegats = true,
    elementTechnique = roche
)

// 🌱 Plante : technique enseignée par une capsule
val parfumMortel = Technique(
    id = 8, nom = "Parfum mortel", precision = 85.0, multiplicateurPuissance = 1.3,
    estSpecial = true, estBuff = false, estDebuff = true, faireDegats = true,
    elementTechnique = plante
)

// ============================ Espèces ============================

/**
 * Définition des espèces de monstres. Chaque [EspeceMonstre] contient ses
 * statistiques de base, ses modificateurs de progression, ses éléments ainsi
 * que sa description et ses particularités.
 */
val especeSpringleaf = EspeceMonstre(
    id = 1, nom = "Springleaf", type = "Graine",
    baseAttaque = 9, baseDefense = 11, baseVitesse = 10,
    baseAttaqueSpe = 12, baseDefenseSpe = 14, basePv = 60,
    modAttaque = 6.5, modDefense = 9.0, modVitesse = 8.0,
    modAttaqueSpe = 7.0, modDefenseSpe = 10.0, modPv = 14.0,
    description = "Un petit monstre espiègle au corps rond comme une graine. Il aime se cacher dans l'herbe haute et se dorer au soleil.",
    particularites = "Sa feuille sur la tête s'incline pour indiquer son humeur.",
    caractères = "Curieux, amical, un peu timide.",
    elements = mutableListOf(plante)
)

val especeFlamkip = EspeceMonstre(
    id = 4, nom = "Flamkip", type = "Animal",
    baseAttaque = 12, baseDefense = 8, baseVitesse = 13,
    baseAttaqueSpe = 16, baseDefenseSpe = 7, basePv = 50,
    modAttaque = 10.0, modDefense = 5.5, modVitesse = 9.5,
    modAttaqueSpe = 9.5, modDefenseSpe = 6.5, modPv = 12.0,
    description = "Ce petit animal est toujours entouré d'une flamme dansante. Il déteste le froid et s'énerve facilement quand on tente d'éteindre son feu.",
    particularites = "Sa flamme change d'intensité selon son niveau d'énergie.",
    caractères = "Impulsif, joueur, loyal.",
    elements = mutableListOf(feu)
)

val especeAquamy = EspeceMonstre(
    id = 7, nom = "Aquamy", type = "Meteo",
    baseAttaque = 10, baseDefense = 11, baseVitesse = 9,
    baseAttaqueSpe = 14, baseDefenseSpe = 14, basePv = 55,
    modAttaque = 9.0, modDefense = 10.0, modVitesse = 7.5,
    modAttaqueSpe = 12.0, modDefenseSpe = 12.0, modPv = 13.5,
    description = "Une créature vaporeuse qui ressemble à un petit nuage. Les gouttes qui tombent de son corps sont pures et rafraîchissantes.",
    particularites = "Fait légèrement baisser la température autour de lui quand il s'endort.",
    caractères = "Calme, rêveur, mystérieux.",
    elements = mutableListOf(eau)
)

val especeLaoumi = EspeceMonstre(
    id = 8, nom = "Laoumi", type = "Animal",
    baseAttaque = 11, baseDefense = 10, baseVitesse = 9,
    baseAttaqueSpe = 8, baseDefenseSpe = 11, basePv = 58,
    modAttaque = 11.0, modDefense = 8.0, modVitesse = 7.0,
    modAttaqueSpe = 6.0, modDefenseSpe = 11.5, modPv = 14.0,
    description = "Un petit ourson au pelage soyeux. Il adore se tenir debout et brandir ses petites pattes comme s'il dansait.",
    particularites = "Son grognement est plus mignon qu'effrayant, mais il devient redoutable pour défendre ses amis.",
    caractères = "Affectueux, protecteur, gourmand.",
    elements = mutableListOf(normal)
)

val especeBugsyface = EspeceMonstre(
    id = 10, nom = "Bugsyface", type = "Insecte",
    baseAttaque = 10, baseDefense = 13, baseVitesse = 8,
    baseAttaqueSpe = 7, baseDefenseSpe = 13, basePv = 45,
    modAttaque = 7.0, modDefense = 11.0, modVitesse = 6.5,
    modAttaqueSpe = 8.0, modDefenseSpe = 11.5, modPv = 21.0,
    description = "Insecte à carapace luisante, il se déplace par bonds et vibre des antennes.",
    particularites = "Sa carapace devient plus dure après chaque mue.",
    caractères = "Travailleur, sociable, infatigable.",
    elements = mutableListOf(insecte)
)

val especeGalum = EspeceMonstre(
    id = 13, nom = "Galum", type = "Minéral",
    baseAttaque = 12, baseDefense = 15, baseVitesse = 6,
    baseAttaqueSpe = 8, baseDefenseSpe = 12, basePv = 55,
    modAttaque = 9.0, modDefense = 13.0, modVitesse = 4.0,
    modAttaqueSpe = 6.5, modDefenseSpe = 10.5, modPv = 13.0,
    description = "Golem ancien de pierre, ses yeux lumineux montent la garde.",
    particularites = "Peut rester immobile des heures comme une statue.",
    caractères = "Sérieux, stoïque, fiable.",
    elements = mutableListOf(roche)
)

/** Évolution de Flamkip (niveau 7). */
val especePyrokip = EspeceMonstre(
    id = 5, nom = "Pyrokip", type = "Animal",
    baseAttaque = 18, baseDefense = 12, baseVitesse = 15,
    baseAttaqueSpe = 22, baseDefenseSpe = 11, basePv = 70,
    modAttaque = 12.0, modDefense = 8.0, modVitesse = 11.0,
    modAttaqueSpe = 12.5, modDefenseSpe = 8.0, modPv = 15.0,
    description = "Pyrokip, l'évolution de Flamkip. Son feu est devenu intense et ses flammes sont capables de fondre la pierre. Fier et courageux, il protège son dresseur à tout prix.",
    particularites = "Ses flammes changent de couleur selon son humeur : rouge vif en colère, dorées quand il est calme.",
    caractères = "Fier, protecteur, explosif.",
    elements = mutableListOf(feu)
)

// ============================ Paliers ============================

/** Chaque espèce apprend une technique au niveau 3 et une autre au niveau 6. */
val paliersSpringleaf = mutableListOf(PalierTechnique(1, 3, charge), PalierTechnique(2, 6, tranchFeuille))
val paliersFlamkip = mutableListOf(PalierTechnique(3, 3, charge), PalierTechnique(4, 6, flammeche))
val paliersAquamy = mutableListOf(PalierTechnique(5, 3, pistoletAEau), PalierTechnique(6, 6, charge))
val paliersLaoumi = mutableListOf(PalierTechnique(7, 3, charge), PalierTechnique(8, 6, piqure))
val paliersBugsyface = mutableListOf(PalierTechnique(9, 3, piqure), PalierTechnique(10, 6, charge))
val paliersGalum = mutableListOf(PalierTechnique(11, 3, jetDePierre), PalierTechnique(12, 6, charge))
val paliersPyrokip = mutableListOf(
    PalierTechnique(13, 3, charge), PalierTechnique(14, 6, flammeche), PalierTechnique(15, 10, flammeEclair)
)

/** Au niveau 7, Flamkip évolue en Pyrokip. */
val palierEvolutionFlamkip = PalierEvolution(1, 7, especePyrokip)

// ============================ Items ============================

var kube1 = MonsterKube(1, "Kube", "Un petit kube pour capturer un monstre", 50.0)
val ctFlammeEclair = CapsuleTechnique(2, "CT Flamme Éclair", "Apprend Flamme Éclair à un monstre", flammeEclair)
val ctTranchFeuille = CapsuleTechnique(3, "CT Tranch'Feuille", "Apprend Tranch'Feuille à un monstre", tranchFeuille)
val ctParfumMortel = CapsuleTechnique(4, "CT Parfum mortel", "Apprend Parfum mortel à un monstre", parfumMortel)

// ============================ Zones ============================

/** Arène de RacailleCity (déclarée avant la ville qui la contient). */
val areneFlamboyante = Arene(
    id = 1,
    nom = "Arène Flamboyante",
    dresseurs = mutableListOf(dresseur1, dresseur2),
    champion = championFlamboyant
)

/** Chaque zone possède une expérience de référence et une liste de monstres sauvages. */
var route1 = Zone(
    id = 1, nom = "Route 1", expZone = 600,
    especesMonstres = mutableListOf(especeLaoumi, especeBugsyface)
)
var route2 = Zone(
    id = 2, nom = "Route 2", expZone = 800,
    especesMonstres = mutableListOf(especeSpringleaf, especeGalum)
)
var racailleCity = Ville(
    id = 3, nom = "RacailleCity", expZone = 1000,
    especesMonstres = mutableListOf(especeFlamkip),
    arene = areneFlamboyante
)

// ============================ Initialisation ============================

/** Donne les monstres [monstres] au dresseur [dresseur] (une équipe par dresseur). */
private fun constituerEquipe(dresseur: Entraineur, vararg monstres: IndividuMonstre) {
    dresseur.equipeMonstre.clear()
    for (monstre in monstres) {
        monstre.entraineur = dresseur
        dresseur.equipeMonstre.add(monstre)
    }
}

/**
 * Met en place toutes les associations du jeu : relations entre éléments,
 * paliers de techniques et d'évolution des espèces, chaîne de zones et
 * équipes des dresseurs de l'arène. Appelée au début de [main].
 */
fun initialiserJeu() {
    valoriserElements()

    especeSpringleaf.paliersTechniques = paliersSpringleaf
    especeFlamkip.paliersTechniques = paliersFlamkip
    especeAquamy.paliersTechniques = paliersAquamy
    especeLaoumi.paliersTechniques = paliersLaoumi
    especeBugsyface.paliersTechniques = paliersBugsyface
    especeGalum.paliersTechniques = paliersGalum
    especePyrokip.paliersTechniques = paliersPyrokip
    especeFlamkip.palierEvolution = palierEvolutionFlamkip

    route1.zoneSuivante = route2
    route2.zonePrecedente = route1
    route2.zoneSuivante = racailleCity
    racailleCity.zonePrecedente = route2

    // Un monstre ne doit être présent que dans une seule équipe.
    constituerEquipe(
        dresseur1,
        IndividuMonstre(101, "Laoumi", 900.0, especeLaoumi),
        IndividuMonstre(102, "Bugsyface", 900.0, especeBugsyface)
    )
    constituerEquipe(
        dresseur2,
        IndividuMonstre(103, "Galum", 1200.0, especeGalum),
        IndividuMonstre(104, "Springleaf", 1200.0, especeSpringleaf)
    )
    constituerEquipe(
        championFlamboyant,
        IndividuMonstre(105, "Aquamy", 1500.0, especeAquamy),
        IndividuMonstre(106, "Flamkip", 3700.0, especeFlamkip), // évolue en Pyrokip
        IndividuMonstre(107, "Laoumi", 1500.0, especeLaoumi)
    )
}

/**
 * Affiche un message d'introduction, demande le nom du joueur et crée une
 * nouvelle partie.
 *
 * @return La nouvelle [Partie], qui démarre sur la Route 1.
 */
fun nouvellePartie(): Partie {
    println(changeCouleur("===== Bienvenue dans Kotlin Monsters ! =====", "cyan"))
    println("Quel est votre nom ? (laisser vide pour garder \"${joueur.nom}\")")
    val nom = readln().trim()
    if (nom.isNotEmpty()) {
        joueur.nom = nom
    }
    println("Bonne aventure, ${joueur.nom} !")
    return Partie(1, joueur, route1)
}

fun main() {
    initialiserJeu()
    joueur.sacAItems.add(kube1)
    joueur.sacAItems.add(ctFlammeEclair)
    joueur.sacAItems.add(ctTranchFeuille)
    joueur.sacAItems.add(ctParfumMortel)

    val partie = nouvellePartie()
    partie.choixStarter()
    partie.jouer()
}
