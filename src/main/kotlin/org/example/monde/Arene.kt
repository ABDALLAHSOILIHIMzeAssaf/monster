package org.example.monde

import org.example.dresseur.Entraineur
import org.example.item.Badge
import org.example.jeu.CombatDresseur
import org.example.joueur

/**
 * Représente une arène située dans une [Ville]. Le joueur y affronte une
 * série de dresseurs puis le champion, et gagne un [Badge] s'il les bat tous.
 *
 * @property id L'identifiant de l'arène.
 * @property nom Le nom de l'arène.
 * @property dresseurs Les dresseurs à battre avant le champion.
 * @property champion Le champion de l'arène (dernier adversaire).
 * @property badge Le badge remporté en cas de victoire.
 */
class Arene(
    val id: Int,
    var nom: String,
    var dresseurs: MutableList<Entraineur>,
    val champion: Entraineur
) {
    val badge: Badge = Badge(
        id,
        "Badge $nom",
        "Badge gagné en battant ${champion.nom} à l'arène $nom.",
        champion
    )

    /**
     * Enchaîne les combats contre les dresseurs (seulement ceux qui ont
     * encore au moins un monstre valide) puis contre le champion.
     * En cas de victoire, le badge est ajouté au DÉBUT de l'inventaire du
     * joueur. Une défaite arrête le défi.
     */
    fun challenger() {
        if (joueur.sacAItems.contains(badge)) {
            println("Vous possédez déjà le ${badge.nom} !")
            return
        }
        if (joueur.equipeMonstre.none { it.pv > 0 }) {
            println("Soignez d'abord vos monstres avant de challenger l'arène.")
            return
        }

        val listeDresseurs = (dresseurs + champion)
            .filter { d -> d.equipeMonstre.any { it.pv > 0 } }
            .toMutableList()

        while (listeDresseurs.isNotEmpty()) {
            val adversaire = listeDresseurs.first()
            val combat = CombatDresseur(1, joueur, adversaire)
            if (combat.lanceCombat()) {
                println("✅ ${joueur.nom} a vaincu ${adversaire.nom}")
                listeDresseurs.remove(adversaire)
            } else {
                println("❌ ${joueur.nom} a été vaincu par ${adversaire.nom}")
                return
            }
        }

        println("🏆 ${joueur.nom} a gagné tous ses combats dans l'arène $nom")
        joueur.sacAItems.add(0, badge)
        println("🎖 ${joueur.nom} remporte le ${badge.nom}")
    }
}
