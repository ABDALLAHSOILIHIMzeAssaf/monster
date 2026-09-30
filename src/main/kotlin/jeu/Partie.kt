package jeu

import dresseur.Entraineur
import monstre.IndividuMonstre
import org.example.monde.Zone
import org.ldv.especeAquamy
import org.ldv.especeFlamkip
import org.ldv.especeSpringleaf
import org.ldv.joueur

class Partie(val id: Int, joueur: Entraineur, zone: Zone) {
    fun choixStarter() {
        val monstre1 = IndividuMonstre(1, "Springleaf", 3286328.30, especeSpringleaf)
        val monstre2 = IndividuMonstre(2, "Flamkip", 1500.0, especeFlamkip)
        val monstre3 = IndividuMonstre(3, "Aquamy", 1500.0, especeAquamy)

        var choixSelection: Int

        val starter: IndividuMonstre


        do {
            // Affichage des détails de chaque monstre
            println(monstre1)
            println(monstre2)
            println(monstre3)

            // Menu de choix
            println("Selectionner le num de l'ID du monstre dont vous voulez choisir (1..3) :")

            choixSelection = readln().toIntOrNull() ?: 0

        } while (choixSelection !in 1..3)

        starter = when (choixSelection) {
            1 -> monstre1
            2 -> monstre2
            else -> monstre3
        }

        starter.rennomer()

        joueur.equipeMonstre.add(starter)
        starter.entraineur = joueur
    }

    fun modifierOrdreEquipe() {

    }

    fun examineEquipe() {

    }

    fun jouer() {

    }

}