package org.example.monstre

import org.example.charge
import org.example.creerEspeceTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PalierTest {

    @Test
    fun peutApprendreSeulementAuNiveauExact() {
        val palier = PalierTechnique(1, 3, charge)
        val monstre = IndividuMonstre(1, "test", 1.0, creerEspeceTest()) // niveau 2
        assertFalse(palier.peutApprendre(monstre))

        monstre.levelUp() // niveau 3
        assertTrue(palier.peutApprendre(monstre))

        monstre.levelUp() // niveau 4
        assertFalse(palier.peutApprendre(monstre))
    }
}
