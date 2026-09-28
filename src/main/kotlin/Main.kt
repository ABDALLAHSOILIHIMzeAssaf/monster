package org.ldv

import dresseur.Entraineur
import item.Badge

import monstre.EspeceMonstre
import monstre.IndividuMonstre
import monstre.MonsterKube
import org.example.monde.Zone

/**
 * Change la couleur du message donné selon le nom de la couleur spécifié.
 * Cette fonction utilise les codes d'échappement ANSI pour appliquer une
 * couleur à la sortie console. Si un nom de couleur non reconnu ou une
 * chaîne vide est fourni, aucune couleur n'est appliquée.
 *
 * @param message Le message auquel la couleur sera appliquée.
 * @param couleur Le nom de la couleur à appliquer (ex: "rouge", "vert",
 * "bleu"). Par défaut c'est une chaîne vide, ce qui n'applique aucune
 * couleur.
 * @return Le message coloré sous forme de chaîne, ou le même message si
 * aucune couleur n'est appliquée.
 */
fun changeCouleur(message: String, couleur: String = ""): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge" -> "\u001B[31m"
        "vert" -> "\u001B[32m"
        "orange" -> "\u001B[32m"
        "jaune" -> "\u001B[33m"
        "bleu" -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan" -> "\u001B[36m"
        "blanc" -> "\u001B[37m"
        "marron" -> "\u001B[33m"
        else -> "" // pas de couleur si non reconnu
    }
    return "$codeCouleur$message$reset"
}

var joueur = Entraineur(1, "Sacha", 100)
var rival = Entraineur(2, "Regis", 200)

// --- Espèces de monstres ---
// Valeurs reprises des fiches monstres fournies (Aquamy, Flamkip).
// TODO : compléter especeSpringLeaf, especeBugsyFace, especeGalum, especeLaoumi
// à partir de especes_monstres.xlsx

val especeAquamy = EspeceMonstre(
    id = 7,
    nom = "Aquamy",
    type = "Meteo",
    baseAttaque = 10,
    baseDefense = 11,
    baseVitesse = 9,
    baseAttaqueSpe = 14,
    baseDefenseSpe = 14,
    basePv = 55,
    modAttaque = 9.0,
    modDefense = 10.0,
    modVitesse = 7.5,
    modAttaqueSpe = 12.0,
    modDefenseSpe = 12.0,
    modPv = 13.5,
    description = "Une créature vaporeuse qui ressemble à un petit nuage. Les gouttes qui tombent de son corps sont pures et rafraîchissantes.",
    particularites = "Fait légèrement baisser la température autour de lui quand il s'endort.",
    caractères = "Calme, rêveur, mystérieux."
)

val especeFlamkip = EspeceMonstre(
    id = 4,
    nom = "Flamkip",
    type = "Animal",
    baseAttaque = 12,
    baseDefense = 8,
    baseVitesse = 13,
    baseAttaqueSpe = 16,
    baseDefenseSpe = 7,
    basePv = 50,
    modAttaque = 10.0,
    modDefense = 5.5,
    modVitesse = 9.5,
    modAttaqueSpe = 9.5,
    modDefenseSpe = 6.5,
    modPv = 12.0,
    description = "Ce petit animal est toujours entouré d'une flamme dansante. Il déteste le froid et s'énerve facilement quand on tente d'éteindre son feu.",
    particularites = "Sa flamme change d'intensité selon son niveau d'énergie.",
    caractères = "Impulsif, joueur, loyal."
)

// --- Zones ---
val route1 = Zone(id = 1, nom = "Route 1", expZone = 10, especesMonstres = mutableListOf(especeFlamkip, especeAquamy))
val route2 = Zone(id = 2, nom = "Route 2", expZone = 20, especesMonstres = mutableListOf(especeAquamy))
var MontreKube1 = MonsterKube(1, "Baymourat", "dshg hfbguer ierbfuer eriur", 12.9)

fun main() {
    /*// Chaînage des zones
    route1.zoneSuivante = route2
    route2.zonePrecedante = route1

    // Test : ascii art front/back
    println(especeFlamkip.afficheArt(true))
    println(especeFlamkip.afficheArt(false))
    println(especeAquamy.afficheArt(true))
    println(especeAquamy.afficheArt(false))

    // Test : construction des individus (starters)
    val monstre1 = IndividuMonstre(1, "flamkip", 1500.0, especeFlamkip)
    val monstre2 = IndividuMonstre(2, "aquamy", 1500.0, especeAquamy)

    // Test : afficheDetail()
    monstre1.afficheDetail()
    monstre2.afficheDetail()

    // Test : attaquer()
    monstre1.attaquer(monstre2)

    // Test : renommer()
    monstre1.renommer()

    // Test : clamp des pv (ne doit pas dépasser pvMax ni descendre sous 0)
    monstre2.pv = 999999
    println("PV après tentative de dépassement : ${monstre2.pv}")
    monstre2.pv = -50
    println("PV après tentative en négatif : ${monstre2.pv}")*/

    // Test : Badge()
    var badgePierre = Badge(1, "Badge Roche", description = "Badge gagné lorsque le joeur atteint la arène de pierre")
    println(badgePierre)
}