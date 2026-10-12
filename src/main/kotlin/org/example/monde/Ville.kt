package org.example.monde

import org.example.monstre.EspeceMonstre

/**
 * Représente une ville : un type de [Zone] où l'on trouve tout ce que l'on
 * trouve dans une zone, et en plus une arène, un hôpital (pour soigner les
 * monstres) et, plus tard, un magasin.
 *
 * @property arene L'arène de la ville, ou null si elle n'en a pas.
 */
class Ville(
    id: Int,
    nom: String,
    expZone: Int,
    especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    zoneSuivante: Zone? = null,
    zonePrecedente: Zone? = null,
    var arene: Arene? = null
    // TODO lignesMagasin: MutableList<LigneMagasin> (module magasin)
) : Zone(id, nom, expZone, especesMonstres, zoneSuivante, zonePrecedente)
