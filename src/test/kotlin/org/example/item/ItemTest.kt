package org.example.item

import org.example.charge
import org.example.creerEspeceTest
import org.example.dresseur.Entraineur
import org.example.monde.Arene
import org.example.monde.Zone
import org.example.monstre.IndividuMonstre
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ItemTest {

    @Test
    fun capsuleTechniqueApprendLaTechnique() {
        val capsule = CapsuleTechnique(1, "CT Charge", "Apprend Charge", charge)
        val monstre = IndividuMonstre(1, "test", 1.0, creerEspeceTest())

        assertTrue(capsule.utiliser(monstre))
        assertTrue(charge in monstre.techniques)
        // réutilisable, mais déjà connue : aucun effet
        assertFalse(capsule.utiliser(monstre))
        assertEquals(1, monstre.techniques.size)
    }

    @Test
    fun kubeInutilisableSurUnMonstreDejaPossede() {
        val kube = MonsterKube(1, "Kube", "test", 100.0)
        val monstre = IndividuMonstre(1, "test", 1.0, creerEspeceTest())
        monstre.entraineur = Entraineur(1, "Autre", 0)
        assertFalse(kube.utiliser(monstre))
    }

    @Test
    fun badgeEstUnItemNonUtilisable() {
        val champion = Entraineur(1, "Champion", 0)
        val badge = Badge(1, "Badge Test", "desc", champion)
        assertTrue(badge is Item)
        assertFalse((badge as Any) is Utilisable)
    }

    @Test
    fun areneCreeSonBadge() {
        val champion = Entraineur(2, "Champion", 0)
        val arene = Arene(1, "Arène Test", mutableListOf(), champion)
        assertEquals(champion, arene.badge.champion)
    }

    @Test
    fun genereMonstreRespecteLExperienceDeLaZone() {
        val espece = creerEspeceTest()
        val zone = Zone(1, "Zone", 600, mutableListOf(espece))
        repeat(50) {
            val monstre = zone.genereMonstre()
            assertEquals(espece, monstre.espece)
            assertTrue(monstre.exp in 479.0..721.0, "exp = ${monstre.exp}")
        }
    }
}
