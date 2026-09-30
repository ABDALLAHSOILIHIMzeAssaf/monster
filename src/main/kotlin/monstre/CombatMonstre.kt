package monstre

import item.Utilisable
import org.ldv.joueur
import kotlin.math.round


class CombatMonstre(var monstreJoueur: IndividuMonstre, var monstreSauvage: IndividuMonstre, var round : Int = 1) {
    /**
     * Vérifie si le joueur a perdu le combat.
     *
     * Condition de défaite :
     * - Aucun monstre de l'équipe du joueur n'a de PV > 0.
     *
     * @return `true` si le joueur a perdu, sinon `false`.
     */
    public final fun gameOver(): Boolean{
        if (monstreJoueur.pv <= 0) {
            return true
        } else {
            return false
        }
    }

    fun joueurGagne(): Boolean {
        val sauvageKO = monstreSauvage.pv <= 0
        if (sauvageKO) {
            println("${joueur.nom} a gagné !")
            val gainExp = monstreSauvage.exp * 0.2
            monstreJoueur.exp += gainExp
            println("${monstreJoueur.nom} gagne $gainExp points d'expérience")
            return true
        }
        else {
            if (monstreSauvage.entraineur == joueur) {
                println("${monstreSauvage.nom} a été capturé !")
                return true
            }
            else {
                return false
            }
        }

    }

    fun actionAdversaire(){
        if (monstreSauvage.pv > 0) {
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

    fun actionJoueur(): Boolean {
        if (gameOver()) {
            return false
        } else {
            println("tape 1 : le monstre du joueur attaque le monstre sauvage.")
            println("tape 2 : on donne la possibilité au joueur d'utiliser un item")
            println(
                "tape 3 : le joueur peut changer son monstre actuel contre un autre\n" +
                        "monstre de son équipe."
            )

            var choixAction: Int = readln().toInt()

            if (choixAction == 1) {
                monstreJoueur.attaquer(monstreSauvage)
            } else if (choixAction == 2) {
                println(joueur.sacAItems)

                var indexChoix: Int = readln().toInt()
                var objetChoisi = joueur.sacAItems[indexChoix]

                if (objetChoisi is Utilisable) {
                    var captureReussie = objetChoisi.utiliser(monstreSauvage)

                    if (captureReussie) {
                        return false
                    }
                } else {
                    println("Objet non utilisable")
                    return true
                }
            } else if (choixAction == 3) {
                joueur.equipeMonstre.forEach {
                    if (it.pv > 0) {
                        println(monstreSauvage)
                    }
                }
                var indexChoix: Int = readln().toInt()
                var choixMonstre = joueur.equipeMonstre[indexChoix]

                if (choixMonstre.pv <= 0) {
                    println("Impossible ! Ce monstre est KO")
                } else {
                    println("${choixMonstre} remplace ${monstreJoueur}")
                    monstreJoueur = choixMonstre


                }
                return true
            }
        }
        return true //TODO a verifier
    }


    fun afficheCombat(round : Int){
        println("============ Début Round : ${round} ===========")
        println("Niveau : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv / monstreSauvage.pvMax}")
        println()
        println()
        println("Niveau : ${monstreJoueur.niveau}")
        println("PV : ${monstreJoueur.pv / monstreJoueur.pvMax}")
    }

    fun jouer() {
        val joueurPlusRapide = monstreJoueur.vitesse >= monstreSauvage.vitesse
        afficheCombat(3)

        if (joueurPlusRapide) {
            // BRANCHE "OUI"
            val continuer = actionJoueur()
            if (continuer == false) {
                return // Fin
            } else {
                actionAdversaire()
            }
        } else {
            // BRANCHE "NON"
            actionAdversaire()

            // Si la partie n'est pas finie (gameOver == false)
            if (gameOver() == false) {
                val continuer = actionJoueur() // L'action se fait ICI avant le test

                if (continuer == false) {
                    return // Fin
                }
            } else {
                return // Si gameOver() == true (branche "Non" de l'algorigramme)
            }
        }
    }


        /**
         * Lance le combat et gère les rounds jusqu'à la victoire ou la
        défaite.
         *
         * Affiche un message de fin si le joueur perd et restaure les
        PV
         * de tous ses monstres.
         */
        fun lanceCombat() {
            while (!gameOver() && !joueurGagne()) {
                this.jouer()
                println("======== Fin du Round : $round ========")
                round++
            }
            if (gameOver()) {
                joueur.equipeMonstre.forEach { it.pv = it.pvMax }
                println("Game Over !")
            }
        }


}