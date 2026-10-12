package org.example.monstre

import org.example.charge
import org.example.creerEspeceTest
import org.example.especeFlamkip
import org.example.especePyrokip
import org.example.flammeche
import org.example.palierEvolutionFlamkip
import org.example.piqure
import org.example.pistoletAEau
import org.example.jetDePierre
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IndividuMonstreTest {

    @BeforeTest
    fun setUp() {
        especeFlamkip.palierEvolution = palierEvolutionFlamkip
    }

    @Test
    fun levelUp() {
        // Création d'un flamkip au niveau 5
        val monstre1 = IndividuMonstre(2, "flamkip", 1500.0, especeFlamkip)
        assertEquals(especeFlamkip, monstre1.espece)
        assertEquals(5, monstre1.niveau)
        monstre1.levelUp()
        assertEquals(6, monstre1.niveau)
        assertEquals(especeFlamkip, monstre1.espece)
        monstre1.levelUp()
        assertEquals(7, monstre1.niveau)
        assertEquals(especePyrokip, monstre1.espece)
    }

    @Test
    fun peutEvoluer() {
        val monstre = IndividuMonstre(3, "flamkip", 1500.0, especeFlamkip)
        assertFalse(palierEvolutionFlamkip.peutEvoluer(monstre)) // niveau 5 < 7

        val monstreAvance = IndividuMonstre(4, "flamkip", 2400.0, especeFlamkip) // niveau 6 -> pas encore
        assertFalse(palierEvolutionFlamkip.peutEvoluer(monstreAvance))
        assertEquals(especeFlamkip, monstreAvance.espece)

        monstreAvance.levelUp() // niveau 7
        assertTrue(palierEvolutionFlamkip.peutEvoluer(monstreAvance))
    }

    @Test
    fun uneEspeceSansPalierNEvoluePas() {
        val espece = creerEspeceTest()
        val monstre = IndividuMonstre(5, "test", 5000.0, espece)
        assertEquals(espece, monstre.espece)
    }

    @Test
    fun pvBornes() {
        val monstre = IndividuMonstre(6, "test", 1.0, creerEspeceTest())
        monstre.pv = 999999
        assertEquals(monstre.pvMax, monstre.pv)
        monstre.pv = -50
        assertEquals(0, monstre.pv)
        monstre.pv = monstre.pvMax + 1
        assertEquals(monstre.pvMax, monstre.pv)
    }

    @Test
    fun statsAugmententAvecLeNiveau() {
        val espece = creerEspeceTest(mod = 40.0)
        val monstre = IndividuMonstre(7, "test", 1500.0, espece)
        assertEquals(5, monstre.niveau)
        assertTrue(monstre.attaque > espece.baseAttaque)
        assertTrue(monstre.pvMax > espece.basePv)
    }

    @Test
    fun techniqueDuPalierAppriseMaisPasCelleDuNiveauSuivant() {
        val espece = creerEspeceTest(
            paliers = mutableListOf(PalierTechnique(1, 3, charge), PalierTechnique(2, 6, flammeche))
        )
        val monstre = IndividuMonstre(8, "test", 1500.0, espece) // niveau 5
        assertEquals(listOf(charge), monstre.techniques)

        monstre.levelUp() // niveau 6
        assertEquals(listOf(charge, flammeche), monstre.techniques)
    }

    @Test
    fun apprendreTechniqueSansDoublonEtJusqua4() {
        val monstre = IndividuMonstre(9, "test", 1.0, creerEspeceTest())
        monstre.apprendreTechnique(charge)
        monstre.apprendreTechnique(charge) // déjà connue
        assertEquals(1, monstre.techniques.size)
        monstre.apprendreTechnique(flammeche)
        monstre.apprendreTechnique(piqure)
        monstre.apprendreTechnique(pistoletAEau)
        assertEquals(4, monstre.techniques.size)
        assertTrue(jetDePierre !in monstre.techniques)
    }

    @Test
    fun attaqueBasiqueReduitLesPv() {
        val attaquant = IndividuMonstre(10, "a", 1.0, creerEspeceTest())
        val cible = IndividuMonstre(11, "b", 1.0, creerEspeceTest())
        attaquant.attaque = 20
        cible.defense = 10
        val pvAvant = cible.pv
        attaquant.attaquer(cible) // pas de technique : 20 - 10/2 = 15
        assertEquals(pvAvant - 15, cible.pv)
    }

    @Test
    fun attaqueBasiqueInfligeAuMoinsUnDegat() {
        val attaquant = IndividuMonstre(12, "a", 1.0, creerEspeceTest())
        val cible = IndividuMonstre(13, "b", 1.0, creerEspeceTest())
        attaquant.attaque = 1
        cible.defense = 100
        val pvAvant = cible.pv
        attaquant.attaquer(cible)
        assertEquals(pvAvant - 1, cible.pv)
    }
}
