package org.example.item

import org.example.monstre.IndividuMonstre
import org.example.monstre.Technique

/**
 * Capsule Technique (CT) : un objet qui apprend une technique précise à un
 * monstre. Elle hérite de [Item] et implémente [Utilisable]. Pour
 * l'instant, une capsule est réutilisable à l'infini.
 *
 * Exemple : la CT "Flamme Éclair" apprend la technique Flamme Éclair.
 *
 * @property technique La technique enseignée par la capsule.
 */
class CapsuleTechnique(
    id: Int,
    nom: String,
    description: String,
    val technique: Technique
) : Item(id, nom, description), Utilisable {

    /**
     * Apprend la technique de la capsule au monstre cible (si le monstre
     * a déjà 4 techniques, il devra en oublier une).
     *
     * @param cible Le monstre qui doit apprendre la technique.
     * @return `true` si le monstre connaît la technique après utilisation,
     * `false` s'il la connaissait déjà ou a refusé de l'apprendre.
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous utilisez $nom sur ${cible.nom}.")
        if (cible.techniques.contains(technique)) {
            println("${cible.nom} connaît déjà ${technique.nom}.")
            return false
        }
        cible.apprendreTechnique(technique)
        return cible.techniques.contains(technique)
    }
}
