package monstre

import dresseur.Entraineur
import kotlin.math.pow
import kotlin.math.round

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
 * @property espece L'espèce à laquelle appartient ce monstre.
 * @property entraineur Le dresseur qui possède ce monstre, ou null s'il est
 * sauvage.
 * @param expInit L'expérience initiale du monstre à sa création (déclenche
 * un éventuel gain de niveau dès la construction).
 */
class IndividuMonstre(
    var id: Int,
    var nom: String,
    expInit: Double,
    val espece: EspeceMonstre,
    var entraineur: Entraineur? = null
) {

    var niveau: Int = 1

    var attaque: Int = espece.baseAttaque + (-2..2).random()
    var defense: Int = espece.baseDefense + (-2..2).random()
    var vitesse: Int = espece.baseVitesse + (-2..2).random()
    var attaqueSpe: Int = espece.baseAttaqueSpe + (-2..2).random()
    var defenseSpe: Int = espece.baseDefenseSpe + (-2..2).random()
    var pvMax: Int = espece.basePv + (-5..5).random()

    val potentiel: Double = (50..200).random() / 100.0

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
     */
    fun levelUp() {
        niveau++

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
     * Attaque un autre [IndividuMonstre] et inflige des dégâts.
     *
     * Les dégâts sont calculés de manière très simple pour le moment :
     * `dégâts = attaque - (défense / 2)` (minimum 1 dégât).
     *
     * @param cible Monstre cible de l'attaque.
     */
    fun attaquer(cible: IndividuMonstre) {
        val degatBrut = this.attaque
        var degatTotal = degatBrut - (this.defense / 2)
        if (degatTotal < 1) {
            degatTotal = 1
        }

        val pvAvant = cible.pv
        cible.pv -= degatTotal
        val pvApres = cible.pv

        println("${this.nom} inflige ${pvAvant - pvApres} dégâts à ${cible.nom}")
    }

    /**
     * Demande au joueur de renommer le monstre.
     * Si l'utilisateur entre un texte vide, le nom n'est pas modifié.
     */
    fun renommer() {
        println("Renommer $nom ?")
        val nouveauNom = readln()
        if (nouveauNom!="") {
            this.nom = nouveauNom
        }
    }

    /**
     * Affiche les caractéristiques du monstre côte à côte avec son art ASCII
     * (vue de face).
     */
    fun afficheDetail() {
        val art = espece.afficheArt()
        val artLines = art.split("\n")

        val details = listOf(
            "======================",
            "Nom: $nom   Niveau: $niveau",
            "Exp: $exp",
            "PV: $pv / $pvMax",
            "======================",
            "Atq : $attaque  Def : $defense  Vitesse : $vitesse",
            "AtqSpe : $attaqueSpe  DefSpe : $defenseSpe",
            "======================"
        )

        val maxArtWidth = artLines.maxOf { it.length }
        val maxLines = maxOf(artLines.size, details.size)

        for (i in 0 until maxLines) {
            val artLine = if (i < artLines.size) artLines[i] else ""
            val detailLine = if (i < details.size) details[i] else ""
            val paddedArt = artLine.padEnd(maxArtWidth + 4)
            println(paddedArt + detailLine)
        }
    }
}