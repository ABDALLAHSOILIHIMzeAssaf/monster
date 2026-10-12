package org.example.monstre

/**
 * Représente un élément (Feu, Eau, Plante...) et ses relations avec les
 * autres éléments.
 *
 * Les propriétés [forces], [faiblesses] et [immunises] sont des
 * [MutableSet] : un ensemble ne peut pas contenir deux fois le même
 * élément, ce qui répond à la contrainte "un élément une seule fois".
 * (Autre solution possible : une `MutableList` avec un test `contains`
 * avant chaque ajout.)
 *
 * @property id L'identifiant de l'élément.
 * @property nom Le nom de l'élément (ex: "Feu").
 * @property forces Éléments contre lesquels celui-ci est efficace (x2.0).
 * @property faiblesses Éléments contre lesquels celui-ci est peu efficace
 * (x0.5).
 * @property immunises Éléments contre lesquels celui-ci est totalement
 * inefficace (x0.0).
 */
class Element(val id: Int, val nom: String) {
    val forces: MutableSet<Element> = mutableSetOf()
    val faiblesses: MutableSet<Element> = mutableSetOf()
    val immunises: MutableSet<Element> = mutableSetOf()

    /**
     * Détermine l'efficacité d'un élément courant contre un élément cible.
     *
     * Cette méthode compare l'élément cible avec les forces et faiblesses
     * de l'élément courant et retourne un multiplicateur d'efficacité.
     *
     * @param elementCible L'élément contre lequel l'efficacité est évaluée.
     * @return Un multiplicateur indiquant l'efficacité :
     * 2.0 si l'élément cible est dans les forces.
     * 0.5 si l'élément cible est dans les faiblesses.
     * 0.0 si l'élément cible est dans les éléments immunisés.
     * 1.0 si l'élément cible n'est ni une force ni une faiblesse.
     */
    fun efficaciteContre(elementCible: Element): Double {
        return when {
            immunises.contains(elementCible) -> 0.0
            forces.contains(elementCible) -> 2.0
            faiblesses.contains(elementCible) -> 0.5
            else -> 1.0
        }
    }

    override fun toString(): String = nom
}
