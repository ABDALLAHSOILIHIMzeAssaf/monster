package org.example.jeu

import org.example.creerEspeceTest
import org.example.dresseur.Entraineur
import org.example.monstre.IndividuMonstre
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CombatTest {

    private fun monstre(id: Int, exp: Double = 1.0) = IndividuMonstre(id, "m$id", exp, creerEspeceTest())

    private fun dresseurAvec(nom: String, vararg monstres: IndividuMonstre): Entraineur {
        val d = Entraineur(1, nom, 100)
        monstres.forEach {
            it.entraineur = d
            d.equipeMonstre.add(it)
        }
        return d
    }

    @Test
    fun gameOverSeulementSiToutesLesPvSontAZero() {
        val m1 = monstre(1)
        val m2 = monstre(2)
        dresseurAvec("Joueur", m1, m2)
        val combat = CombatMonstre(m1, monstre(3))

        assertFalse(combat.gameOver())
        m1.pv = 0
        assertFalse(combat.gameOver()) // m2 est encore vivant
        m2.pv = 0
        assertTrue(combat.gameOver())
    }

    @Test
    fun joueurGagneQuandLeSauvageEstKO() {
        val m1 = monstre(1)
        dresseurAvec("Joueur", m1)
        val sauvage = monstre(2, exp = 500.0)
        val combat = CombatMonstre(m1, sauvage)

        assertFalse(combat.joueurGagne())
        val expAvant = m1.exp
        sauvage.pv = 0
        assertTrue(combat.joueurGagne())
        assertEquals(expAvant + 100.0, m1.exp, 1e-9) // 20 % de 500
    }

    @Test
    fun capturePasDeGainDExperience() {
        val m1 = monstre(1)
        val joueur = dresseurAvec("Joueur", m1)
        val sauvage = monstre(2, exp = 500.0)
        val combat = CombatMonstre(m1, sauvage)

        sauvage.entraineur = joueur // capturé
        val expAvant = m1.exp
        assertTrue(combat.joueurGagne())
        assertEquals(expAvant, m1.exp)
    }

    @Test
    fun combatDresseurVictoireEtDefaite() {
        val joueur = dresseurAvec("Joueur", monstre(1))
        val adversaire = dresseurAvec("Adversaire", monstre(2), monstre(3))
        val combat = CombatDresseur(1, joueur, adversaire)

        assertFalse(combat.avoirGagner())
        assertFalse(combat.avoirPerdu())

        adversaire.equipeMonstre.forEach { it.pv = 0 }
        assertTrue(combat.avoirGagner())
        assertFalse(combat.avoirPerdu())

        joueur.equipeMonstre.forEach { it.pv = 0 }
        assertTrue(combat.avoirPerdu())
    }

    @Test
    fun soignerEquipeRestaurePvMax() {
        val m1 = monstre(1)
        val joueur = dresseurAvec("Joueur", m1)
        m1.pv = 1
        joueur.soignerEquipe()
        assertEquals(m1.pvMax, m1.pv)
    }

    @Test
    fun choisirMonstreRetourneLUniqueMonstreValide() {
        val m1 = monstre(1)
        val m2 = monstre(2)
        val joueur = dresseurAvec("Joueur", m1, m2)
        m1.pv = 0
        assertEquals(m2, joueur.choisirMonstre())
    }
}
