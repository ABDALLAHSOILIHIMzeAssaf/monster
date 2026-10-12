package org.example.monstre

import org.example.dresseur.Entraineur
import org.example.lireEntier
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * Représente un individu de monstre, c'est-à-dire un monstre précis avec
 * lequel le joueur va interagir (monstre sauvage, monstre de l'équipe du
 * joueur, monstre d'un autre dresseur...).
 *
 * Plusieurs individus peuvent appartenir à la même espèce, par exemple deux
 * "Canaros" différents sont deux individus distincts de la même
 * [EspeceMonstre].
 *
 * @property id L'identifiant unique de l'individu.
 * @property nom Le nom (surnom) donné à ce monstre.
 * @property espece L'espèce à laquelle appartient ce monstre (elle change
 * lorsque le monstre évolue).
 * @property entraineur Le dresseur qui possède ce monstre, ou null s'il est
 * sauvage.
 * @property techniques Les techniques connues du monstre (4 maximum).
 * @param expInit L'expérience initiale du monstre à sa création (déclenche
 * un éventuel gain de niveau dès la construction).
 */
class IndividuMonstre(
    var id: Int,
    var nom: String,
    expInit: Double,
    var espece: EspeceMonstre,
    var entraineur: Entraineur? = null
) {

    companion object {
        /** Nombre maximum de techniques qu'un monstre peut connaître. */
        const val MAX_TECHNIQUES = 4
    }

    /** Vrai pendant la construction : on n'affiche alors aucun message. */
    private var enConstruction: Boolean = true

    var niveau: Int = 1

    var attaque: Int = espece.baseAttaque + (-2..2).random()
    var defense: Int = espece.baseDefense + (-2..2).random()
    var vitesse: Int = espece.baseVitesse + (-2..2).random()
    var attaqueSpe: Int = espece.baseAttaqueSpe + (-2..2).random()
    var defenseSpe: Int = espece.baseDefenseSpe + (-2..2).random()
    var pvMax: Int = espece.basePv + (-5..5).random()

    val potentiel: Double = (50..200).random() / 100.0

    val techniques: MutableList<Technique> = mutableListOf()

    /**
     * @property exp Expérience totale accumulée par le monstre.
     * Modifier cette valeur peut déclencher un ou plusieurs gains de niveau.
     */
    var exp: Double = 0.0
        get() = field
        set(value) {
            field = value
            val estNiveau1 = (niveau == 1)
            while (field >= palierExp(niveau)) {
                levelUp()
                if (!estNiveau1) {
                    println("Le monstre $nom est maintenant niveau $niveau !")
                }
            }
        }

    /**
     * @property pv Points de vie actuels.
     * Ne peut pas être inférieur à 0 ni supérieur à [pvMax].
     */
    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = when {
                nouveauPv < 0 -> 0
                nouveauPv > pvMax -> pvMax
                else -> nouveauPv
            }
        }

    init {
        this.exp = expInit // applique le setter et déclenche un éventuel level-up
        enConstruction = false
    }

    /** Affiche un message sauf pendant la construction de l'objet. */
    private fun message(texte: String) {
        if (!enConstruction) {
            println(texte)
        }
    }

    /**
     * Calcule l'expérience totale nécessaire pour atteindre un niveau donné.
     *
     * @param niveau Niveau cible.
     * @return Expérience cumulée nécessaire pour atteindre ce niveau.
     */
    fun palierExp(niveau: Int): Double {
        return 100 * (niveau - 1).toDouble().pow(2.0)
    }

    /**
     * Augmente le niveau du monstre de 1 et met à jour ses caractéristiques
     * (attaque, défense, vitesse, attaqueSpe, defenseSpe et pvMax) en
     * fonction des multiplicateurs de son espèce et de son potentiel.
     * Le nombre de pv gagné lors du passage de niveau est également ajouté
     * aux pv actuels.
     *
     * Après l'incrémentation du niveau, le monstre peut évoluer (si son
     * espèce a un palier d'évolution atteint) et apprendre les techniques
     * des paliers correspondant à son nouveau niveau.
     */
    fun levelUp() {
        niveau++

        // Évolution éventuelle
        val palierEvo = espece.palierEvolution
        if (palierEvo != null && palierEvo.peutEvoluer(this)) {
            evoluer()
        }

        // Techniques à apprendre à ce niveau
        val paliers = espece.paliersTechniques.filter { it.peutApprendre(this) }
        for (palier in paliers) {
            apprendreTechnique(palier.technique)
        }

        val gainAttaque = round(espece.modAttaque * potentiel).toInt() + (-2..2).random()
        val gainDefense = round(espece.modDefense * potentiel).toInt() + (-2..2).random()
        val gainVitesse = round(espece.modVitesse * potentiel).toInt() + (-2..2).random()
        val gainAttaqueSpe = round(espece.modAttaqueSpe * potentiel).toInt() + (-2..2).random()
        val gainDefenseSpe = round(espece.modDefenseSpe * potentiel).toInt() + (-2..2).random()
        val gainPv = round(espece.modPv * potentiel).toInt() + (-5..5).random()

        attaque += gainAttaque
        defense += gainDefense
        vitesse += gainVitesse
        attaqueSpe += gainAttaqueSpe
        defenseSpe += gainDefenseSpe
        pvMax += gainPv
        pv += gainPv
    }

    /**
     * Fait évoluer le monstre : son espèce est remplacée par l'évolution
     * prévue par le palier d'évolution de son espèce actuelle.
     * Ne fait rien si l'espèce n'a pas de palier d'évolution.
     */
    fun evoluer() {
        val palier = espece.palierEvolution ?: return
        espece = palier.evolution
        message("$nom évolue en ${espece.nom} !")
    }

    /**
     * Permet à un monstre de maîtriser une nouvelle technique. Si le
     * monstre a déjà atteint sa capacité maximale de techniques, il devra en
     * oublier une avant d'apprendre la nouvelle.
     *
     * @param nouvelleTechnique La nouvelle technique que le monstre
     * souhaite apprendre.
     */
    fun apprendreTechnique(nouvelleTechnique: Technique) {
        if (techniques.contains(nouvelleTechnique)) {
            message("$nom connaît déjà ${nouvelleTechnique.nom}.")
            return
        }

        if (techniques.size < MAX_TECHNIQUES) {
            techniques.add(nouvelleTechnique)
            message("$nom apprend ${nouvelleTechnique.nom} !")
            return
        }

        // Plus de place : un monstre en cours de création (PNJ, sauvage)
        // oublie simplement sa plus ancienne technique, sans demander.
        if (enConstruction) {
            techniques.removeAt(0)
            techniques.add(nouvelleTechnique)
            return
        }

        println("Quelle technique souhaitez-vous oublier ? (0 pour annuler)")
        while (true) {
            techniques.forEachIndexed { i, t -> println("${i + 1} - ${t.nom}") }
            val choix = lireEntier()
            when {
                choix == 0 -> {
                    println("$nom n'apprend pas ${nouvelleTechnique.nom}.")
                    return
                }
                choix != null && choix in 1..techniques.size -> {
                    val oubliee = techniques[choix - 1]
                    techniques[choix - 1] = nouvelleTechnique
                    println("$nom oublie ${oubliee.nom}.")
                    println("$nom apprend ${nouvelleTechnique.nom} !")
                    return
                }
                else -> println("Choix invalide")
            }
        }
    }

    /**
     * Attaque un autre [IndividuMonstre] et inflige des dégâts.
     *
     * - Si le monstre ne connaît aucune technique : attaque basique,
     * `dégâts = attaque - (défense de la cible / 2)` (minimum 1 dégât).
     * - Sinon, le joueur choisit une technique parmi celles du monstre,
     * qui est ensuite utilisée sur la cible (voir [utiliserTechnique]).
     *
     * @param cible Monstre cible de l'attaque.
     */
    fun attaquer(cible: IndividuMonstre) {
        if (techniques.isEmpty()) {
            val degatTotal = max(this.attaque - (cible.defense / 2), 1)
            val pvAvant = cible.pv
            cible.pv -= degatTotal
            val pvApres = cible.pv
            println("$nom inflige ${pvAvant - pvApres} dégâts à ${cible.nom}")
            return
        }

        utiliserTechnique(choisirTechnique(), cible)
    }

    /**
     * Affiche les techniques du monstre et demande au joueur d'en choisir
     * une (la question est reposée tant que le choix est invalide).
     *
     * @return La technique choisie.
     */
    private fun choisirTechnique(): Technique {
        while (true) {
            println("Choisissez une technique :")
            techniques.forEachIndexed { i, t ->
                println("${i + 1} - ${t.nom} (${t.elementTechnique.nom}, précision ${t.precision.toInt()})")
            }
            val choix = lireEntier()
            if (choix != null && choix in 1..techniques.size) {
                return techniques[choix - 1]
            }
            println("Choix invalide")
        }
    }

    /**
     * Utilise une technique sur une cible : teste la précision, calcule les
     * dégâts (via [Technique.effet]) puis réduit les PV de la cible.
     *
     * @param technique La technique utilisée.
     * @param cible Le monstre visé.
     */
    fun utiliserTechnique(technique: Technique, cible: IndividuMonstre) {
        println("$nom utilise ${technique.nom} !")
        if (!technique.calculPrecision()) {
            println("L'attaque de $nom échoue !")
            return
        }
        val degats = technique.effet(this, cible)
        if (technique.faireDegats) {
            val degatsFinaux = max(degats - (cible.defense / 2.0), 1.0).roundToInt()
            val pvAvant = cible.pv
            cible.pv -= degatsFinaux
            val pvApres = cible.pv
            println("$nom inflige ${pvAvant - pvApres} dégâts à ${cible.nom}")
        }
    }

    /**
     * Demande au joueur de renommer le monstre.
     * Si l'utilisateur entre un texte vide, le nom n'est pas modifié.
     */
    fun renommer() {
        println("Renommer $nom ?")
        val nouveauNom = readln()
        if (nouveauNom.isNotBlank()) {
            this.nom = nouveauNom.trim()
        }
    }

    /** Longueur visible d'une ligne (sans les codes couleur ANSI). */
    private fun longueurVisible(ligne: String): Int {
        return ligne.replace(Regex("\u001B\\[[0-9;]*m"), "").length
    }

    /**
     * Affiche les caractéristiques du monstre côte à côte avec son art ASCII
     * (vue de face).
     */
    fun afficheDetail() {
        val artLines = espece.afficheArt().lines()

        val details = listOf(
            "======================",
            "Nom: $nom   Niveau: $niveau",
            "Exp: ${exp.toInt()}",
            "PV: $pv / $pvMax",
            "======================",
            "Atq : $attaque  Def : $defense  Vitesse : $vitesse",
            "AtqSpe : $attaqueSpe  DefSpe : $defenseSpe",
            "======================",
            "Éléments : ${espece.elements.joinToString(", ") { it.nom }}",
            "Techniques : ${if (techniques.isEmpty()) "aucune" else techniques.joinToString(", ") { it.nom }}"
        )

        val maxArtWidth = artLines.maxOfOrNull { longueurVisible(it) } ?: 0
        val maxLines = maxOf(artLines.size, details.size)

        for (i in 0 until maxLines) {
            val artLine = if (i < artLines.size) artLines[i] else ""
            val detailLine = if (i < details.size) details[i] else ""
            val marge = " ".repeat(max(0, maxArtWidth + 4 - longueurVisible(artLine)))
            println(artLine + marge + detailLine)
        }
    }

    override fun toString(): String {
        return "$nom (${espece.nom}, niv. $niveau, PV $pv/$pvMax)"
    }
}
