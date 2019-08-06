package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("everything wired up!!")
    println("Hello, 🌎!!")

    val user = TwitterClient.getLoggedUser()

    println(user)

    val tweets = TwitterClient.getHomeTweets(300)
}

private fun String.isConnective(): Boolean {
    return setOf( "de", "que", "no", "pra","na", "do", "da", "to", "and",
        "the", "com", "for", "um", "in", "of", "em", "your", "with", "te",
        "se", "as", "meu", "por", "para", "tambm", "tem", "muito", "ele",
        "sendo","sobre", "nas", "minha", "vc", "by", "os", "our", "its")
        .contains(this.toLowerCase()) || this.length <= 1
}
