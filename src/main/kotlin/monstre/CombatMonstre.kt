package monstre
Import org.ldv.joueur

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

    fun joueurGagne(){
        var winner : Boolean = monstreSauvage.pv <= 0)
        if (winner){
            println("[joueur.nom] a gagné !")
        }
    }
}