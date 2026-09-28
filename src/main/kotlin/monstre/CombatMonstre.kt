package monstre

import org.ldv.joueur


class CombatMonstre(var monstreJoueur: IndividuMonstre, var monstreSauvage: IndividuMonstre) {
    /**
     * Vérifie si le joueur a perdu le combat.
     *
     * Condition de défaite :
     * - Aucun monstre de l'équipe du joueur n'a de PV > 0.
     *
     * @return `true` si le joueur a perdu, sinon `false`.
     */
    fun gameOver(){}

    fun joueurGagne(): Boolean {
        val sauvageKO = monstreSauvage.pv <= 0
        if (sauvageKO) {
            println("${joueur.nom} a gagné !")
            val gainExp = (monstreSauvage.exp * 0.2).toInt()
            monstreJoueur.exp += gainExp
            println("${monstreJoueur.nom} gagne $gainExp points d'expérience")
            return true
        }
        return false
    }
}