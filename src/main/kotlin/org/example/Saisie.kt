package org.example

/**
 * Lit une ligne saisie par l'utilisateur dans la console et tente de la
 * convertir en entier.
 *
 * @return L'entier saisi, ou `null` si la saisie n'est pas un nombre entier
 * valide (texte, ligne vide...). Cela évite les plantages dus à `toInt()`.
 */
fun lireEntier(): Int? = readln().trim().toIntOrNull()
