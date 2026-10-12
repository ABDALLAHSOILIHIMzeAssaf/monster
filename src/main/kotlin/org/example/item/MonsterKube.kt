package org.example.item

import org.example.joueur
import org.example.monstre.IndividuMonstre
import kotlin.random.Random

/**
 * Objet de capture de monstre : l'équivalent d'une "pokéball". Il permet de
 * tenter de capturer un monstre sauvage.
 *
 * Cette classe hérite de [Item] et implémente [Utilisable].
 *
 * @property chanceCapture Pourcentage de base de chance de capture (0 à 100).
 */
class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double
) : Item(id, nom, description), Utilisable {

    /**
     * Lance le Kube sur un monstre. La chance effective dépend des PV
     * restants de la cible :
     * `chanceEffective = chanceCapture * (1.5 - pv / pvMax)`, avec un
     * minimum de 5 %. Si la capture réussit, le joueur peut renommer le
     * monstre, qui rejoint l'équipe (ou la boîte si l'équipe compte déjà
     * 6 monstres).
     *
     * @param cible Le monstre visé.
     * @return `true` si le monstre est capturé, `false` sinon.
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous lancez le Monster Kube !")

        if (cible.entraineur != null) {
            println("Le monstre ne peut pas être capturé.")
            return false
        }

        val ratioVie = cible.pv.toDouble() / cible.pvMax
        val chanceEffective = (chanceCapture * (1.5 - ratioVie)).coerceAtLeast(5.0)
        val nbAleatoire = Random.nextInt(0, 100)

        if (nbAleatoire < chanceEffective) {
            println("Le monstre est capturé !")
            cible.renommer()

            if (joueur.equipeMonstre.size >= 6) {
                joueur.boiteMonstre.add(cible)
                println("${cible.nom} est envoyé dans la boîte.")
            } else {
                joueur.equipeMonstre.add(cible)
            }
            cible.entraineur = joueur
            return true
        }

        println("Presque ! Le Kube n'a pas pu capturer le monstre !")
        return false
    }
}
