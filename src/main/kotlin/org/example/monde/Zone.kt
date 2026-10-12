package org.example.monde

import org.example.jeu.CombatMonstre
import org.example.joueur
import org.example.monstre.EspeceMonstre
import org.example.monstre.IndividuMonstre
import java.time.LocalDateTime

/**
 * Représente une zone du monde (une route, une caverne, une mer...).
 *
 * Une zone est un endroit où le joueur peut chercher un monstre sauvage et se
 * déplacer vers la zone suivante ou la zone précédente si elles existent.
 * Les zones forment ainsi une chaîne de routes reliées entre elles.
 *
 * La classe est `open` pour permettre l'héritage (voir [Ville]).
 *
 * @property id L'identifiant unique de la zone.
 * @property nom Le nom de la zone.
 * @property expZone L'expérience de référence des monstres sauvages de cette
 * zone (les monstres générés ont cette expérience à ±20 %).
 * @property especesMonstres La liste des espèces de monstres que l'on peut
 * rencontrer dans cette zone.
 * @property zoneSuivante La zone suivante dans la chaîne de routes, ou null
 * s'il n'y en a pas.
 * @property zonePrecedente La zone précédente dans la chaîne de routes, ou
 * null s'il n'y en a pas.
 */
open class Zone(
    var id: Int,
    var nom: String,
    var expZone: Int,
    var especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedente: Zone? = null
) {
    private companion object {
        /** Compteur pour donner un id différent à chaque monstre généré. */
        var prochainIdMonstre = 1000
    }

    /**
     * Génère un monstre sauvage d'une espèce de la zone (choisie au
     * hasard). Son expérience vaut l'expérience de la zone à ±20 %.
     *
     * @return Le monstre généré.
     */
    fun genereMonstre(): IndividuMonstre {
        val uneEspece = especesMonstres.random()
        val variation = (-20..20).random() / 100.0
        val expMonstre = expZone * (1 + variation)
        return IndividuMonstre(
            id = prochainIdMonstre++,
            nom = uneEspece.nom,
            expInit = expMonstre,
            espece = uneEspece
        )
    }

    /**
     * Démarre un combat entre un monstre sauvage généré (voir
     * [genereMonstre]) et le premier monstre de l'équipe du joueur qui a
     * des PV > 0.
     */
    fun rencontreMonstre() {
        val premierMonstre = joueur.equipeMonstre.firstOrNull { it.pv > 0 }
        if (premierMonstre == null) {
            println("Aucun de vos monstres n'est en état de combattre !")
            return
        }
        val monstreSauvage = genereMonstre()
        println("Un ${monstreSauvage.nom} sauvage apparaît !")
        CombatMonstre(premierMonstre, monstreSauvage).lanceCombat()
    }
}
