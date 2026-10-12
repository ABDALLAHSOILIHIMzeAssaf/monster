package org.example.dresseur

import org.example.item.Item
import org.example.lireEntier
import org.example.monstre.IndividuMonstre
import java.time.LocalDate

/**
 * Représente un entraîneur dans le contexte du jeu.
 *
 * Un entraîneur est responsable de gérer une équipe de monstres, une boîte
 * pour stocker des monstres supplémentaires et un sac contenant des objets
 * appelés MonsterKubes. L'entraîneur a également une somme d'argent associée.
 *
 * Exemple : Ondine est une entraîneuse qui gère son équipe de monstres, une
 * boîte de monstres supplémentaires et un sac de MonsterKubes.
 *
 * @property id L'identifiant unique de l'entraîneur.
 * @property nom Le nom de l'entraîneur.
 * @property argents La quantité d'argent en possession de l'entraîneur.
 * @property equipeMonstre L'équipe de monstres actuellement utilisable par
 * l'entraîneur.
 * @property boiteMonstre Les monstres supplémentaires de l'entraîneur, non
 * présents dans l'équipe active.
 * @property sacAItems Les objets (kubes, badges, capsules...) que possède
 * l'entraîneur.
 */
class Entraineur(
    var id: Int,
    var nom: String,
    var argents: Int,
    var equipeMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var boiteMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var sacAItems: MutableList<Item> = mutableListOf()
) {

    /**
     * Affiche les détails de l'entraîneur, y compris son nom et la quantité
     * d'argent en sa possession.
     *
     * Cette méthode affiche les informations de l'entraîneur sous la forme
     * de deux lignes :
     * 1. Le nom de l'entraîneur.
     * 2. La somme d'argent qu'il possède.
     */
    fun afficheDetail() {
        println("Dresseur : ${this.nom}")
        println("Argents: ${this.argents} ")
    }

    /**
     * Soigne tous les monstres de l'équipe. Les PV sont mis à `pvMax + 1` :
     * le setter de [IndividuMonstre.pv] plafonne la valeur à `pvMax`.
     */
    fun soignerEquipe() {
        equipeMonstre.forEach { it.pv = it.pvMax + 1 }
    }

    /**
     * Permet de choisir un monstre valide dans l'équipe.
     * - Si un seul monstre est en état de combattre, il est choisi
     * automatiquement.
     * - Sinon, on affiche un menu pour choisir.
     *
     * @return le monstre choisi
     * @throws IllegalStateException si aucun monstre n'est en état de
     * combattre.
     */
    fun choisirMonstre(): IndividuMonstre {
        val monstresVivants = equipeMonstre.filter { it.pv > 0 }
        check(monstresVivants.isNotEmpty()) { "Aucun monstre en état de combattre" }

        if (monstresVivants.size == 1) {
            return monstresVivants[0]
        }

        println("Choisir un monstre de l'équipe")
        while (true) {
            monstresVivants.forEachIndexed { i, m ->
                println("${i + 1} - ${m.nom} (niv. ${m.niveau}, PV ${m.pv}/${m.pvMax})")
            }
            val choixIndex = lireEntier()
            if (choixIndex != null && choixIndex in 1..monstresVivants.size) {
                return monstresVivants[choixIndex - 1]
            }
            println("Choix invalide, réessayez.")
        }
    }
}
