package org.example.jeu

import org.example.dresseur.Entraineur
import org.example.especeAquamy
import org.example.especeFlamkip
import org.example.especeSpringleaf
import org.example.lireEntier
import org.example.monde.Ville
import org.example.monde.Zone
import org.example.monstre.IndividuMonstre

/**
 * Représente une partie de jeu : un joueur et la zone où il se trouve.
 *
 * Deux objets [Partie] peuvent exister dans le même jeu (par exemple deux
 * sauvegardes différentes avec chacune son joueur et sa zone).
 *
 * @property id L'identifiant de la partie.
 * @property joueur Le dresseur contrôlé par l'utilisateur.
 * @property zone La zone où se situe actuellement le joueur.
 */
class Partie(val id: Int, val joueur: Entraineur, var zone: Zone) {

    /**
     * Crée trois monstres de départ (Springleaf, Flamkip, Aquamy) et
     * demande au joueur d'en choisir un. Le monstre choisi peut être
     * renommé, rejoint l'équipe du joueur et lui appartient.
     */
    fun choixStarter() {
        val starters = listOf(
            IndividuMonstre(1, "Springleaf", 1500.0, especeSpringleaf),
            IndividuMonstre(2, "Flamkip", 1500.0, especeFlamkip),
            IndividuMonstre(3, "Aquamy", 1500.0, especeAquamy)
        )

        var choixSelection: Int
        do {
            println("===== Choisissez votre monstre de départ =====")
            starters.forEachIndexed { i, monstre ->
                println("--- Monstre n°${i + 1} ---")
                monstre.afficheDetail()
            }
            println("Tapez le numéro du monstre choisi (1..3) :")
            choixSelection = lireEntier() ?: 0
        } while (choixSelection !in 1..3)

        val starter = starters[choixSelection - 1]
        starter.renommer()
        joueur.equipeMonstre.add(starter)
        starter.entraineur = joueur
    }

    /** Affiche la liste numérotée des monstres de l'équipe. */
    private fun afficherEquipe() {
        joueur.equipeMonstre.forEachIndexed { i, m ->
            println("${i + 1} - ${m.nom} (${m.espece.nom}, niv. ${m.niveau}, PV ${m.pv}/${m.pvMax})")
        }
    }

    /**
     * Inverse la position de deux monstres dans l'équipe : demande la
     * position du monstre, puis sa nouvelle position, et échange les deux.
     * Nécessite au moins deux monstres et des positions valides.
     */
    fun modifierOrdreEquipe() {
        val equipe = joueur.equipeMonstre
        if (equipe.size < 2) {
            println("Il faut au moins deux monstres dans l'équipe pour changer l'ordre.")
            return
        }
        afficherEquipe()
        println("Position du monstre à déplacer (1..${equipe.size}) :")
        val depart = lireEntier()
        println("Nouvelle position (1..${equipe.size}) :")
        val arrivee = lireEntier()

        if (depart == null || arrivee == null ||
            depart !in 1..equipe.size || arrivee !in 1..equipe.size
        ) {
            println("Positions invalides.")
            return
        }
        val temporaire = equipe[depart - 1]
        equipe[depart - 1] = equipe[arrivee - 1]
        equipe[arrivee - 1] = temporaire
        println("Les deux monstres ont été échangés.")
    }

    /**
     * Affiche les monstres de l'équipe avec leur position, puis laisse le
     * joueur : taper un numéro (détail du monstre), `m` (modifier l'ordre)
     * ou `q` (retourner au menu principal).
     */
    fun examineEquipe() {
        while (true) {
            if (joueur.equipeMonstre.isEmpty()) {
                println("Votre équipe est vide.")
                return
            }
            afficherEquipe()
            println("Numéro du monstre : voir le détail | m : modifier l'ordre | q : retour")
            val saisie = readln().trim().lowercase()
            when {
                saisie == "q" -> return
                saisie == "m" -> modifierOrdreEquipe()
                else -> {
                    val numero = saisie.toIntOrNull()
                    if (numero != null && numero in 1..joueur.equipeMonstre.size) {
                        joueur.equipeMonstre[numero - 1].afficheDetail()
                    } else {
                        println("Choix invalide.")
                    }
                }
            }
        }
    }

    /**
     * (Bonus) Transfère un monstre de l'équipe vers la boîte, ou
     * inversement. L'équipe garde toujours 1 à 6 monstres.
     */
    private fun gererBoite() {
        println("1 - Équipe -> Boîte | 2 - Boîte -> Équipe | 0 - Retour")
        when (lireEntier()) {
            1 -> {
                if (joueur.equipeMonstre.size <= 1) {
                    println("Vous devez garder au moins un monstre dans l'équipe.")
                    return
                }
                afficherEquipe()
                println("Numéro du monstre à envoyer dans la boîte :")
                val n = lireEntier()
                if (n != null && n in 1..joueur.equipeMonstre.size) {
                    val monstre = joueur.equipeMonstre.removeAt(n - 1)
                    joueur.boiteMonstre.add(monstre)
                    println("${monstre.nom} est envoyé dans la boîte.")
                } else {
                    println("Choix invalide.")
                }
            }
            2 -> {
                if (joueur.boiteMonstre.isEmpty()) {
                    println("La boîte est vide.")
                    return
                }
                if (joueur.equipeMonstre.size >= 6) {
                    println("L'équipe est pleine (6 monstres).")
                    return
                }
                joueur.boiteMonstre.forEachIndexed { i, m -> println("${i + 1} - $m") }
                println("Numéro du monstre à retirer de la boîte :")
                val n = lireEntier()
                if (n != null && n in 1..joueur.boiteMonstre.size) {
                    val monstre = joueur.boiteMonstre.removeAt(n - 1)
                    joueur.equipeMonstre.add(monstre)
                    println("${monstre.nom} rejoint l'équipe.")
                } else {
                    println("Choix invalide.")
                }
            }
            else -> return
        }
    }

    /**
     * Boucle principale du jeu. Indique la zone actuelle et propose les
     * actions : 1 rencontrer un monstre, 2 examiner l'équipe, 3 zone
     * suivante, 4 zone précédente. Dans une [Ville] : 5 soigner l'équipe,
     * 6 challenger l'arène, 7 gérer la boîte. 0 quitte la partie.
     */
    fun jouer() {
        while (true) {
            val zoneActuelle = zone
            val estVille = zoneActuelle is Ville
            val choixMax = if (estVille) 7 else 4

            println()
            println("===== Vous êtes à : ${zoneActuelle.nom} =====")
            println("1 - Rencontrer un monstre sauvage")
            println("2 - Examiner l'équipe de monstres")
            println("3 - Aller à la zone suivante")
            println("4 - Aller à la zone précédente")
            if (estVille) {
                println("5 - Soigner l'équipe (hôpital)")
                println("6 - Challenger l'arène")
                println("7 - Gérer la boîte de monstres")
            }
            println("0 - Quitter")

            val choix = lireEntier()
            if (choix == null || choix !in 0..choixMax) {
                println("Choix invalide.")
                continue
            }

            when (choix) {
                0 -> {
                    println("À bientôt, ${joueur.nom} !")
                    return
                }
                1 -> zoneActuelle.rencontreMonstre()
                2 -> examineEquipe()
                3 -> {
                    val suivante = zoneActuelle.zoneSuivante
                    if (suivante != null) {
                        zone = suivante
                    } else {
                        println("Pas de zone suivante")
                    }
                }
                4 -> {
                    val precedente = zoneActuelle.zonePrecedente
                    if (precedente != null) {
                        zone = precedente
                    } else {
                        println("Pas de zone précédente")
                    }
                }
                5 -> {
                    joueur.soignerEquipe()
                    println("L'hôpital soigne votre équipe")
                }
                6 -> {
                    val arene = (zoneActuelle as? Ville)?.arene
                    if (arene != null) {
                        arene.challenger()
                    } else {
                        println("Il n'y a pas d'arène ici.")
                    }
                }
                7 -> gererBoite()
            }
        }
    }
}
