package org.example.jeu

import org.example.dresseur.Entraineur

/**
 * Représente un combat entre deux dresseurs : une suite de duels entre
 * monstres, en réutilisant la logique de [CombatMonstre].
 *
 * @property id L'identifiant du combat.
 * @property joueur Le dresseur joué par l'utilisateur.
 * @property entraineurAdversaire Le dresseur adverse.
 */
class CombatDresseur(
    val id: Int,
    val joueur: Entraineur,
    val entraineurAdversaire: Entraineur
) {

    /**
     * @return `true` si tous les monstres de l'adversaire sont K.O.
     */
    fun avoirGagner(): Boolean {
        return entraineurAdversaire.equipeMonstre.none { it.pv > 0 }
    }

    /**
     * @return `true` si tous les monstres du joueur sont K.O.
     */
    fun avoirPerdu(): Boolean {
        return joueur.equipeMonstre.none { it.pv > 0 }
    }

    /**
     * Lance le combat : tant qu'aucun des deux dresseurs n'a perdu, le
     * joueur choisit un monstre, l'adversaire en envoie un au hasard et
     * un [CombatMonstre] est lancé.
     *
     * - Victoire : le joueur gagne la moitié de l'argent de l'adversaire.
     * - Défaite : les deux équipes sont soignées.
     *
     * @return `true` si le joueur a gagné, `false` s'il a perdu.
     */
    fun lanceCombat(): Boolean {
        println("⚔ Combat entre ${joueur.nom} et ${entraineurAdversaire.nom} commence !")

        while (!avoirGagner() && !avoirPerdu()) {
            val monstreJoueur = joueur.choisirMonstre()
            val monstreAdverse = entraineurAdversaire.equipeMonstre.filter { it.pv > 0 }.random()
            println("${joueur.nom} envoie ${monstreJoueur.nom}")
            println("${entraineurAdversaire.nom} envoie ${monstreAdverse.nom}")

            val combat = CombatMonstre(monstreJoueur, monstreAdverse, contreDresseur = true)
            combat.lanceCombat()
        }

        if (avoirGagner()) {
            val recompense = entraineurAdversaire.argents / 2
            println("Victoire contre ${entraineurAdversaire.nom} ! Vous gagnez $recompense pièces.")
            joueur.argents += recompense
            return true
        }

        println("Défaite contre ${entraineurAdversaire.nom}...")
        joueur.soignerEquipe()
        entraineurAdversaire.soignerEquipe()
        return false
    }
}
