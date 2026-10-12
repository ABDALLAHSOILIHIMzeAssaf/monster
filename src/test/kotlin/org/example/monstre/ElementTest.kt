package org.example.monstre

import org.example.eau
import org.example.feu
import org.example.insecte
import org.example.normal
import org.example.plante
import org.example.roche
import org.example.valoriserElements
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ElementTest {

    @BeforeTest
    fun setUp() {
        valoriserElements()
    }

    @Test
    fun efficaciteContre() {
        assertEquals(1.0, feu.efficaciteContre(normal)) // ni force ni faiblesse
        assertEquals(2.0, feu.efficaciteContre(plante)) // force
        assertEquals(0.5, feu.efficaciteContre(feu))    // faiblesse
        assertEquals(0.5, feu.efficaciteContre(eau))    // faiblesse
        assertEquals(2.0, insecte.efficaciteContre(plante))
    }

    @Test
    fun efficaciteContreAutresElements() {
        assertEquals(2.0, eau.efficaciteContre(feu))
        assertEquals(0.5, plante.efficaciteContre(feu))
        assertEquals(0.5, normal.efficaciteContre(roche))
        assertEquals(1.0, roche.efficaciteContre(normal))
    }

    @Test
    fun immunite() {
        val spectre = Element(98, "Spectre")
        val neutre = Element(97, "Neutre")
        neutre.immunises.add(spectre)
        // une immunité l'emporte sur une éventuelle force
        neutre.forces.add(spectre)
        assertEquals(0.0, neutre.efficaciteContre(spectre))
    }

    @Test
    fun pasDeDoublonDansLesForces() {
        val taille = feu.forces.size
        feu.forces.add(plante)
        valoriserElements()
        assertEquals(taille, feu.forces.size)
    }
}
