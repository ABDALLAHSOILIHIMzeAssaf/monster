package org.example.jeu

import org.example.dresseur.Entraineur
import org.example.item.CapsuleTechnique
import org.example.item.MonsterKube
import org.example.item.Utilisable
import org.example.joueur
import org.example.lireEntier
import org.example.monstre.IndividuMonstre

/**
 * Représente un combat entre un monstre du joueur et un autre monstre
 * (sauvage, ou appartenant à un dresseur adverse).
 *
 * @property monstreJoueur Le monstre actuellement au combat côté joueur.
 * @property monstreSauvage Le monstre adverse.
 * @property contreDresseur Vrai si le combat fait partie d'un combat de
 * dresseurs : on ne peut alors pas fuir, et la défaite n'est pas gérée ici
 * (voir [CombatDresseur]).
 * @property round Le numéro du round en cours (commence à 1).
 */
class CombatMonstre(
    var monstreJoueur: IndividuMonstre,
    var monstreSauvage: IndividuMonstre,
    private val contreDresseur: Boolean = false
) {
    var round: Int = 1
    private var fuite: Boolean = false

    /** Le dresseur qui joue : le propriétaire du monstre, sinon le joueur global. */
    private val dresseurJoueur: Entraineur
        get() = monstreJoueur.entraineur ?: joueur

    /**
     * Vérifie si le joueur a perdu le combat.
     *
     * Condition de défaite :
     * - Aucun monstre de l'équipe du joueur n'a de PV > 0.
     *
     * @return `true` si le joueur a perdu, sinon `false`.
     */
    fun gameOver(): Boolean {
        return dresseurJoueur.equipeMonstre.none { it.pv > 0 }
    }

    /**
     * Indique si le joueur a gagné le combat. Deux façons de gagner :
     * - capturer le monstre sauvage ;
     * - amener les PV du monstre sauvage à 0 (le monstre du joueur gagne
     * alors de l'expérience, 20 % de celle du monstre vaincu).
     *
     * @return `true` si le joueur a gagné, `false` sinon.
     */
    fun joueurGagne(): Boolean {
        if (monstreSauvage.pv <= 0) {
            println("${dresseurJoueur.nom} a gagné !")
            val gainExp = monstreSauvage.exp * 0.20
            monstreJoueur.exp += gainExp
            println("${monstreJoueur.nom} gagne ${gainExp.toInt()} exp")
            return true
        }
        if (monstreSauvage.entraineur == dresseurJoueur) {
            println("${monstreSauvage.nom} a été capturé !")
            return true
        }
        return false
    }

    /**
     * Action du monstre adverse : s'il a des PV, il utilise une technique
     * choisie au hasard (ou une attaque basique s'il n'en connaît aucune).
     */
    fun actionAdversaire() {
        if (monstreSauvage.pv <= 0) {
            return
        }
        if (monstreSauvage.techniques.isNotEmpty()) {
            monstreSauvage.utiliserTechnique(monstreSauvage.techniques.random(), monstreJoueur)
        } else {
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

    /**
     * Si le monstre actif du joueur est K.O. alors qu'il reste des monstres
     * valides dans l'équipe, demande d'en choisir un autre.
     */
    private fun remplacerMonstreSiKO() {
        if (monstreJoueur.pv <= 0 && !gameOver()) {
            println("${monstreJoueur.nom} est K.O. ! Choisissez un autre monstre.")
            monstreJoueur = dresseurJoueur.choisirMonstre()
        }
    }

    /**
     * Permet au joueur d'effectuer une action :
     * 1 attaquer, 2 utiliser un objet, 3 changer de monstre, 4 fuir,
     * 5 attendre.
     *
     * @return `true` si le combat doit continuer, `false` sinon (partie
     * perdue, capture réussie ou fuite).
     */
    fun actionJoueur(): Boolean {
        remplacerMonstreSiKO()
        if (gameOver()) {
            return false
        }
        while (true) {
            println("Que voulez-vous faire ?")
            println("1 - Attaquer")
            println("2 - Utiliser un objet")
            println("3 - Changer de monstre")
            println("4 - Fuir")
            println("5 - Attendre")
            when (lireEntier()) {
                1 -> {
                    monstreJoueur.attaquer(monstreSauvage)
                    return true
                }
                2 -> {
                    val resultat = utiliserObjet()
                    if (resultat != null) {
                        return resultat
                    }
                }
                3 -> {
                    if (changerMonstre()) {
                        return true
                    }
                }
                4 -> {
                    if (tenterFuite()) {
                        return false
                    }
                }
                5 -> {
                    println("${monstreJoueur.nom} attend...")
                    return true
                }
                else -> println("Choix invalide.")
            }
        }
    }

    /**
     * Menu d'utilisation d'un objet du sac.
     *
     * @return `null` si le joueur annule (il revient au menu principal),
     * `false` si le combat est terminé (capture réussie), `true` si le
     * combat continue.
     */
    private fun utiliserObjet(): Boolean? {
        val sac = dresseurJoueur.sacAItems
        if (sac.isEmpty()) {
            println("Votre sac est vide.")
            return null
        }
        println("Choisissez un objet (0 pour revenir) :")
        sac.forEachIndexed { i, item -> println("${i + 1} - ${item.nom} : ${item.description}") }

        val choix = lireEntier()
        if (choix == null || choix !in 1..sac.size) {
            if (choix != 0) {
                println("Choix invalide.")
            }
            return null
        }

        val objet = sac[choix - 1]
        if (objet !is Utilisable) {
            println("Objet non utilisable")
            return null
        }

        // Une capsule technique s'utilise sur SON monstre, pas sur l'adversaire.
        val cible = if (objet is CapsuleTechnique) monstreJoueur else monstreSauvage
        val effet = objet.utiliser(cible)

        if (objet is CapsuleTechnique && !effet) {
            return null // rien n'a été appris : le tour n'est pas perdu
        }
        if (objet is MonsterKube && effet) {
            return false // capture réussie : fin du combat
        }
        return true
    }

    /**
     * Menu de changement de monstre.
     *
     * @return `true` si le monstre a été changé, `false` sinon.
     */
    private fun changerMonstre(): Boolean {
        val equipe = dresseurJoueur.equipeMonstre
        println("Choisissez un monstre (0 pour revenir) :")
        equipe.forEachIndexed { i, m ->
            val ko = if (m.pv <= 0) " (K.O.)" else ""
            println("${i + 1} - ${m.nom} (niv. ${m.niveau}, PV ${m.pv}/${m.pvMax})$ko")
        }
        val choix = lireEntier()
        if (choix == null || choix !in 1..equipe.size) {
            if (choix != 0) {
                println("Choix invalide.")
            }
            return false
        }
        val choisi = equipe[choix - 1]
        return when {
            choisi.pv <= 0 -> {
                println("Impossible ! Ce monstre est K.O.")
                false
            }
            choisi === monstreJoueur -> {
                println("${choisi.nom} est déjà au combat.")
                false
            }
            else -> {
                println("${choisi.nom} remplace ${monstreJoueur.nom} !")
                monstreJoueur = choisi
                true
            }
        }
    }

    /**
     * Tente de fuir le combat (impossible contre un dresseur).
     *
     * @return `true` si le joueur a fui, `false` sinon.
     */
    private fun tenterFuite(): Boolean {
        if (contreDresseur) {
            println("On ne peut pas fuir un combat de dresseur !")
            return false
        }
        fuite = true
        println("${dresseurJoueur.nom} prend la fuite !")
        return true
    }

    /**
     * Affiche l'état du combat : niveau, PV et art ASCII du monstre
     * adverse (de face) puis du monstre du joueur (de dos).
     */
    fun afficheCombat() {
        println("======== Début Round : $round ========")
        println("${monstreSauvage.nom} - Niveau : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreSauvage.pvMax}")
        println(monstreSauvage.espece.afficheArt(true))
        println(monstreJoueur.espece.afficheArt(false))
        println("${monstreJoueur.nom} - Niveau : ${monstreJoueur.niveau}")
        println("PV : ${monstreJoueur.pv} / ${monstreJoueur.pvMax}")
    }

    /**
     * Fait jouer les deux monstres (d'abord celui qui a la plus grande
     * vitesse). Si [actionJoueur] retourne faux, le round s'arrête.
     */
    fun jouer() {
        remplacerMonstreSiKO()
        val joueurPlusRapide = monstreJoueur.vitesse >= monstreSauvage.vitesse
        afficheCombat()

        if (joueurPlusRapide) {
            val continuer = actionJoueur()
            if (!continuer) {
                return
            }
            actionAdversaire()
        } else {
            actionAdversaire()
            if (gameOver()) {
                return
            }
            val continuer = actionJoueur()
            if (!continuer) {
                return
            }
        }
    }

    /**
     * Lance le combat et gère les rounds jusqu'à la victoire, la fuite ou
     * la défaite.
     *
     * Affiche un message de fin si le joueur perd et restaure les PV de
     * tous ses monstres (sauf dans un combat de dresseurs, où c'est
     * [CombatDresseur] qui gère la défaite).
     */
    fun lanceCombat() {
        while (!fuite && !gameOver() && !joueurGagne()) {
            this.jouer()
            println("======== Fin du Round : $round ========")
            round++
        }
        if (!fuite && gameOver() && !contreDresseur) {
            dresseurJoueur.equipeMonstre.forEach { it.pv = it.pvMax }
            println("Game Over !")
        }
    }
}
