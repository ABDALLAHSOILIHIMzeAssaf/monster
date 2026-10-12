package org.example

import org.example.monstre.Element
import org.example.monstre.EspeceMonstre
import org.example.monstre.PalierTechnique

/**
 * Crée une espèce "de test" indépendante des espèces du jeu, pour des tests
 * unitaires prévisibles (pas d'art ASCII nécessaire).
 */
fun creerEspeceTest(
    elements: MutableList<Element> = mutableListOf(normal),
    paliers: MutableList<PalierTechnique> = mutableListOf(),
    mod: Double = 5.0
): EspeceMonstre {
    return EspeceMonstre(
        id = 99, nom = "Testmon", type = "Test",
        baseAttaque = 10, baseDefense = 10, baseVitesse = 10,
        baseAttaqueSpe = 10, baseDefenseSpe = 10, basePv = 100,
        modAttaque = mod, modDefense = mod, modVitesse = mod,
        modAttaqueSpe = mod, modDefenseSpe = mod, modPv = mod * 2,
        elements = elements, paliersTechniques = paliers
    )
}
