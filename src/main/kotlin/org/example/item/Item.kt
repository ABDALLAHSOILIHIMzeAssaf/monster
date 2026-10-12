package org.example.item

/**
 * Représente un objet du jeu. C'est la classe parent de tous les objets
 * (Badge, MonsterKube, CapsuleTechnique...).
 *
 * @property id L'identifiant de l'objet.
 * @property nom Le nom de l'objet.
 * @property description Une description de l'objet.
 */
open class Item(val id: Int, var nom: String, var description: String) {
    override fun toString(): String = nom
}
