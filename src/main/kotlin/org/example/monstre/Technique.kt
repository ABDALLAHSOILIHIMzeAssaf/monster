package org.example.monstre

import kotlin.math.max

/**
 * Représente une technique (attaque, buff, debuff...) utilisable par un
 * monstre en combat. Un monstre peut connaître au maximum 4 techniques.
 *
 * @property id L'identifiant de la technique.
 * @property nom Le nom de la technique.
 * @property precision Chance de toucher la cible, entre 0 et 100.
 * @property multiplicateurPuissance Multiplicateur de puissance de base.
 * @property estBuff Vrai si la technique améliore un monstre (futur).
 * @property estDebuff Vrai si la technique affaiblit un monstre (futur).
 * @property estSpecial Vrai si la technique utilise l'attaque spéciale.
 * @property faireDegats Vrai si la technique inflige des dégâts.
 * @property elementTechnique L'élément de la technique.
 */
class Technique(
    val id: Int,
    val nom: String,
    val precision: Double,
    val multiplicateurPuissance: Double,
    val estBuff: Boolean,
    val estDebuff: Boolean,
    val estSpecial: Boolean,
    val faireDegats: Boolean,
    val elementTechnique: Element
) {

    /**
     * Indique si la technique touche sa cible.
     * On tire un nombre entre 1 et 100 : si ce nombre est inférieur ou égal
     * à la précision, la technique touche.
     *
     * @return `true` si la technique touche, `false` sinon.
     */
    fun calculPrecision(): Boolean {
        val nb = (1..100).random()
        return nb <= precision
    }

    /**
     * Calcule le multiplicateur de puissance avec le bonus/malus STAB.
     * Si l'attaquant possède l'élément de la technique : puissance + 0.15,
     * sinon : puissance - 0.15. Le résultat ne descend jamais sous 0.1.
     * La propriété [multiplicateurPuissance] n'est jamais modifiée.
     *
     * @param attaquant Le monstre qui utilise la technique.
     * @return Le multiplicateur de puissance ajusté (minimum 0.1).
     */
    fun calculBonusStab(attaquant: IndividuMonstre): Double {
        val resultat = if (attaquant.espece.elements.contains(elementTechnique)) {
            multiplicateurPuissance + 0.15
        } else {
            multiplicateurPuissance - 0.15
        }
        return max(resultat, 0.1)
    }

    /**
     * Applique l'effet principal de la technique :
     * - inflige des dégâts
     * - (TODO) applique des buffs/debuffs
     *
     * @param attaquant IndividuMonstre utilisateur de la technique
     * @param defenseur IndividuMonstre cible de la technique
     * @return Les dégâts infligés (0.0 si pas de dégâts)
     */
    fun effet(attaquant: IndividuMonstre, defenseur: IndividuMonstre): Double {
        if (estBuff) {
            // TODO déclencher le buff (futur système de Statut)
        }
        if (estDebuff) {
            // TODO déclencher le debuff (futur système de Statut)
        }
        if (!faireDegats) {
            return 0.0
        }

        val degatsBase = if (estSpecial) attaquant.attaqueSpe.toDouble() else attaquant.attaque.toDouble()
        val multiplicateur = calculBonusStab(attaquant)

        var multiElement = 1.0
        for (element in defenseur.espece.elements) {
            multiElement *= elementTechnique.efficaciteContre(element)
        }

        return (degatsBase * multiplicateur) * multiElement
    }

    override fun toString(): String = nom
}
