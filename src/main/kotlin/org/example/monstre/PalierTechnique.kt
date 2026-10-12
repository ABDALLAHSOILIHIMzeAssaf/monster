package org.example.monstre

/**
 * Indique qu'une espèce apprend une technique à un niveau précis.
 *
 * @property id L'identifiant du palier.
 * @property niveauRequis Le niveau auquel la technique est apprise.
 * @property technique La technique apprise à ce niveau.
 */
class PalierTechnique(
    val id: Int,
    val niveauRequis: Int,
    val technique: Technique
) {
    /**
     * Vérifie si un individu monstre peut apprendre une technique
     * en fonction de son niveau.
     *
     * @param individu L'individu monstre à vérifier
     * @return true si le niveau de l'individu correspond à exactement
     * le niveau requis, false sinon
     */
    fun peutApprendre(individu: IndividuMonstre): Boolean {
        return individu.niveau == niveauRequis
    }
}
