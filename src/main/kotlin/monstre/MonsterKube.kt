package monstre

import item.Item
import item.Utilisable
import org.ldv.joueur
import kotlin.random.Random


class MonsterKube(id:Int, nom: String, description: String, var chanceCapture:Double): Item(id, nom, description), Utilisable{
    override fun utiliser(cible: IndividuMonstre, add: Any.(IndividuMonstre) -> Boolean): Boolean {
        println("Vous lancez le Monster Kube !")

        if (cible.entraineur != null) {
            println("Le monstre ne peut pas être capturé.")
            return false
        }

        val nbAleatoire = Random.nextInt(0, 100)   // import kotlin.random.Random

        if (nbAleatoire < chanceCapture) {
            println("Le monstre est capturé !")

            print("Veuillez saisir un nouveau nom : ")
            val nouveauNom = readln()
            if (nouveauNom.isNotBlank()) {
                cible.nom = nouveauNom
            }

            if (joueur.equipeMonstre.size >= 6) {
                joueur.boite.add(cible)
            } else {
                joueur.equipeMonstre.add(cible)
            }

            cible.entraineur = joueur
            return true
        } else {
            println("Presque ! Le Kube n'a pas pu capturer le monstre !")
            return false
        }
    }



}