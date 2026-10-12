package org.example.monstre

import org.example.creerEspeceTest
import org.example.especeFlamkip
import org.example.especeSpringleaf
import org.example.feu
import org.example.normal
import org.example.plante
import org.example.valoriserElements
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TechniqueTest {

    private fun technique(
        element: Element,
        puissance: Double = 1.0,
        precision: Double = 100.0,
        special: Boolean = false,
        degats: Boolean = true
    ) = Technique(1, "Test", precision, puissance, false, false, special, degats, element)

    @BeforeTest
    fun setUp() {
        valoriserElements()
    }

    @Test
    fun precision100TouchePresqueToujours() {
        val t = technique(normal, precision = 100.0)
        repeat(200) { assertTrue(t.calculPrecision()) }
    }

    @Test
    fun precision0NeToucheJamais() {
        val t = technique(normal, precision = 0.0)
        repeat(200) { assertFalse(t.calculPrecision()) }
    }

    @Test
    fun precision50EstEquilibree() {
        val t = technique(normal, precision = 50.0)
        val reussites = (1..1000).count { t.calculPrecision() }
        assertTrue(reussites in 350..650, "réussites : $reussites")
    }

    @Test
    fun bonusStab() {
        val monstreFeu = IndividuMonstre(1, "feu", 1.0, especeFlamkip)
        val monstrePlante = IndividuMonstre(2, "plante", 1.0, especeSpringleaf)
        val techFeu = technique(feu)

        // même élément : +0.15
        assertEquals(1.15, techFeu.calculBonusStab(monstreFeu), 1e-9)
        // autre élément : -0.15
        assertEquals(0.85, techFeu.calculBonusStab(monstrePlante), 1e-9)
        // la technique n'est pas modifiée
        assertEquals(1.0, techFeu.multiplicateurPuissance)
    }

    @Test
    fun bonusStabJamaisSousZeroVirguleUn() {
        val monstre = IndividuMonstre(1, "m", 1.0, creerEspeceTest())
        assertEquals(0.1, technique(feu, puissance = 0.2).calculBonusStab(monstre), 1e-9)
    }

    @Test
    fun testEffetInfligeDegats() {
        val techPlante = Technique(3, "Technique plante", 100.0, 1.0, false, false, false, true, plante)
        val monstreFeu = IndividuMonstre(1, "attaquant", 1.0, especeFlamkip)
        val monstrePlante = IndividuMonstre(2, "defenseur", 1.0, especeSpringleaf)

        // Pour éviter les variations de score d'attaque
        monstreFeu.attaque = 10
        monstrePlante.attaque = 10

        val degats1 = techPlante.effet(monstreFeu, monstrePlante)
        // degats1 = (10 * 0.85) * 1.0 = 8.5
        assertEquals(8.5, degats1, 1e-9)

        val degats2 = techPlante.effet(monstrePlante, monstreFeu)
        // degats2 = (10 * 1.15) * 0.5 = 5.75
        assertEquals(5.75, degats2, 1e-9)
    }

    @Test
    fun effetAttaqueSpeciale() {
        val techFeuSpeciale = technique(feu, special = true)
        val monstreFeu = IndividuMonstre(1, "attaquant", 1.0, especeFlamkip)
        val monstrePlante = IndividuMonstre(2, "defenseur", 1.0, especeSpringleaf)
        monstreFeu.attaque = 1        // ne doit PAS être utilisé
        monstreFeu.attaqueSpe = 20

        // (20 * 1.15) * 2.0 (feu fort contre plante) = 46.0
        assertEquals(46.0, techFeuSpeciale.effet(monstreFeu, monstrePlante), 1e-9)
    }

    @Test
    fun effetSansDegats() {
        val t = technique(feu, degats = false)
        val a = IndividuMonstre(1, "a", 1.0, especeFlamkip)
        val b = IndividuMonstre(2, "b", 1.0, especeSpringleaf)
        assertEquals(0.0, t.effet(a, b))
    }
}
