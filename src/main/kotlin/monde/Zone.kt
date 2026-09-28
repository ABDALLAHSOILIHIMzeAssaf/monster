package org.example.monde
import monstre.EspeceMonstre

import java.time.LocalDateTime

/**
 * Représente une zone du monde (une route, une caverne, une mer...).
 *
 * Une zone est un endroit où le joueur peut chercher un monstre sauvage et se
 * déplacer vers la zone suivante ou la zone précédente si elles existent.
 * Les zones forment ainsi une chaîne de routes reliées entre elles.
 *
 * @property id L'identifiant unique de la zone.
 * @property nom Le nom de la zone.
 * @property expZone L'expérience gagnée en explorant cette zone.
 * @property especesMonstres La liste des espèces de monstres que l'on peut
 * rencontrer dans cette zone.
 * @property zoneSuivante La zone suivante dans la chaîne de routes, ou null
 * s'il n'y en a pas.
 * @property zonePrecedante La zone précédente dans la chaîne de routes, ou
 * null s'il n'y en a pas.
 */
class Zone(
    var id: Int,
    var nom: String,
    var expZone: Int,
    var especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedante: Zone? = null
) {
    // TODO : faire la méthode genereMonstre()
    // TODO : faire la méthode rencontreMonstre()
}