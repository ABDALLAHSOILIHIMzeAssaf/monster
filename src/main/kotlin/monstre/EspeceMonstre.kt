package monstre

import java.io.File

/**
 * Représente une espèce de monstre (le "modèle" partagé par tous les individus
 * de cette espèce), par opposition à [IndividuMonstre] qui représente un monstre
 * particulier possédé ou rencontré par un dresseur.
 *
 * Exemple : "Aquamy" est une espèce ; deux monstres différents (deux individus)
 * peuvent tous les deux être de l'espèce Aquamy tout en ayant des statistiques
 * individuelles différentes.
 *
 * @property id L'identifiant unique de l'espèce (ex: 007 pour Aquamy).
 * @property nom Le nom de l'espèce (ex: "Aquamy").
 * @property type La catégorie de l'espèce (ex: "Meteo", "Animal").
 * @property baseAttaque La statistique de base d'attaque de l'espèce, utilisée
 * comme référence pour calculer l'attaque de chaque individu de cette espèce.
 * @property baseDefense La statistique de base de défense de l'espèce, utilisée
 * comme référence pour calculer la défense de chaque individu de cette espèce.
 * @property baseVitesse La statistique de base de vitesse de l'espèce, utilisée
 * comme référence pour calculer la vitesse de chaque individu de cette espèce.
 * @property baseAttaqueSpe La statistique de base d'attaque spéciale de
 * l'espèce, utilisée comme référence pour calculer l'attaque spéciale de
 * chaque individu de cette espèce.
 * @property baseDefenseSpe La statistique de base de défense spéciale de
 * l'espèce, utilisée comme référence pour calculer la défense spéciale de
 * chaque individu de cette espèce.
 * @property basePv Le nombre de base de points de vie de l'espèce, utilisé
 * comme référence pour calculer les PV maximum de chaque individu de cette
 * espèce.
 * @property modAttaque Le multiplicateur appliqué au gain d'attaque lors de la
 * montée de niveau d'un individu de cette espèce.
 * @property modDefense Le multiplicateur appliqué au gain de défense lors de
 * la montée de niveau d'un individu de cette espèce.
 * @property modVitesse Le multiplicateur appliqué au gain de vitesse lors de
 * la montée de niveau d'un individu de cette espèce.
 * @property modAttaqueSpe Le multiplicateur appliqué au gain d'attaque
 * spéciale lors de la montée de niveau d'un individu de cette espèce.
 * @property modDefenseSpe Le multiplicateur appliqué au gain de défense
 * spéciale lors de la montée de niveau d'un individu de cette espèce.
 * @property modPv Le multiplicateur appliqué au gain de points de vie maximum
 * lors de la montée de niveau d'un individu de cette espèce.
 * @property description Un texte présentant l'espèce (255 caractères maximum).
 * @property particularites Une information notable ou un comportement propre
 * à l'espèce.
 * @property caractères Un ou plusieurs traits de tempérament typiques de
 * l'espèce (ex: "Calme, rêveur, mystérieux.").
 */
class EspeceMonstre(
    var id: Int,
    var nom: String,
    var type: String,
    val baseAttaque: Int,
    val baseDefense: Int,
    val baseVitesse: Int,
    val baseAttaqueSpe: Int,
    val baseDefenseSpe: Int,
    val basePv: Int,
    val modAttaque: Double,
    val modDefense: Double,
    val modVitesse: Double,
    val modAttaqueSpe: Double,
    val modDefenseSpe: Double,
    val modPv: Double,
    val description: String = "",
    val particularites: String = "",
    val caractères: String = ""
) {
    /**
     * Affiche la représentation artistique ASCII du monstre.
     *
     * @param deFace Détermine si l'art affiché est de face (true) ou de dos
     * (false). La valeur par défaut est true.
     * @return Une chaîne de caractères contenant l'art ASCII du monstre avec
     * les codes couleur ANSI. L'art est lu à partir d'un fichier texte dans
     * le dossier resources/art.
     */
    fun afficheArt(deFace: Boolean = true): String {
        val nomFichier = if (deFace) "front" else "back"
        val art = File("src/main/resources/art/${this.nom.lowercase()}/$nomFichier.txt").readText()
        val safeArt = art.replace("/", "∕")
        return safeArt.replace("\\u001B", "\u001B")
    }
}