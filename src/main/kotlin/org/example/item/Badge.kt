package org.example.item

import org.example.dresseur.Entraineur

/**
 * Badge obtenu en battant le champion d'une arène. Un badge est un [Item]
 * qui n'est pas utilisable en combat (il n'implémente pas [Utilisable]).
 *
 * @property champion Le dresseur à battre pour obtenir le badge.
 */
class Badge(
    id: Int,
    nom: String,
    description: String,
    var champion: Entraineur
) : Item(id, nom, description)
