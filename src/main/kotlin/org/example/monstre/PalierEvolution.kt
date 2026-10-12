package org.example.monstre

/**
 * Représente une évolution possible pour une espèce de monstre.
 *
 * Exemple : Magicarp évolue au niveau 50 : le palier a donc
 * `niveauRequis = 50` et `evolution` désigne l'espèce suivante (Léviator).
 *
 * @property id L'identifiant du palier d'évolution.
 * @property niveauRequis Le niveau à atteindre pour pouvoir évoluer.
 * @property evolution L'espèce dans laquelle le monstre évolue.
 */
class PalierEvolution(
    val id: Int,
    val niveauRequis: Int,
    val evolution: EspeceMonstre
) {
    /**
     * Indique si un individu peut évoluer.
     *
     * @param individu L'individu monstre à tester.
     * @return `true` si son niveau atteint le niveau requis, `false` sinon.
     */
    fun peutEvoluer(individu: IndividuMonstre): Boolean {
        return individu.niveau >= niveauRequis
    }
}
