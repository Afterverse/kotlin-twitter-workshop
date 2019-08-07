package com.playkids.workshop.adapter

import com.playkids.workshop.model.*
import twitter4j.IDs
import twitter4j.Paging
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
object TwitterClient {
    val twitter = TwitterFactory.getSingleton()

    fun getLoggedUser(): SimpleUser =
        twitter.verifyCredentials().toSimpleUser()

    fun getHomeTweets(maxTweets: Int): List<Tweet> {
        var page = 1
        var totalTweets = 0

        val tweetSequence = generateSequence {
            if (totalTweets >= maxTweets) null
            else
                twitter.getHomeTimeline(Paging(page++, maxTweets - totalTweets))
                    .toList()
                    .also { totalTweets += it.size }
                    .takeIf { it.isNotEmpty() }
        }

        return tweetSequence.flatten().map { it.toTweet() }.toList()
    }

    fun getUserTweets(screenName: String, maxTweets: Int): List<Tweet> {
        var page = 1
        var totalTweets = 0

        val userSequence = generateSequence {
            if (totalTweets >= maxTweets) null
            else
                twitter.getUserTimeline(screenName, Paging(page++, maxTweets - totalTweets))
                    .toList()
                    .also { totalTweets += it.size }
                    .takeIf { it.isNotEmpty() }
        }

        return userSequence.flatten().map { it.toTweet() }.toList()
    }

    fun getUserFollowers(user: SimpleUser, startingAt: Long = -1): List<SimpleUser> {
        val response = twitter.friendsFollowers().getFollowersIDs(user.id, startingAt)

        return lookupUsers(response)
    }

    fun getUserFollowing(user: SimpleUser, startingAt: Long = -1): List<SimpleUser> {
        val response = twitter.friendsFollowers().getFriendsIDs(user.id, startingAt)

        return lookupUsers(response)
    }

    private fun lookupUsers(response: IDs): List<SimpleUser> {
        return response.iDs.toList()
            .chunked(95)
            .flatMap {
                twitter.users().lookupUsers(*it.toLongArray())
            }
            .map { it.toSimpleUser() }
    }

    fun doOauth(): UserAuthProperties {

        val requestToken = twitter.oAuthRequestToken
        var accessToken: AccessToken? = null
        val br = BufferedReader(InputStreamReader(System.`in`))
        while (accessToken == null) {
            println("Open the following URL and grant access to your account: ")
            println(requestToken.authorizationURL)
            print("Enter the PIN (if available) or just hit enter.[PIN]: ")
            val pin = br.readLine()
            try {
                accessToken = if (pin.isNotEmpty()) {
                    twitter.getOAuthAccessToken(requestToken, pin)
                } else {
                    twitter.oAuthAccessToken
                }
            } catch (te: TwitterException) {
                if (401 == te.statusCode) {
                    println("Unable to get the access token.")
                } else {
                    te.printStackTrace()
                }
            }

        }
        //persist to the accessToken for future reference.
        return UserAuthProperties(
            userId = twitter.verifyCredentials().id,
            token = accessToken.token,
            tokenSecret = accessToken.tokenSecret
        )
    }

//    val dbFile = File("/Users/julio.barros/tokens.txt")
//
//    private fun storeAccessToken(userId: UserId, accessToken: AccessToken): UserAuthProperties {
//        dbFile.createNewFile()
//
//        val properties = UserAuthProperties(
//            userId = userId,
//            token = accessToken.token,
//            tokenSecret = accessToken.tokenSecret
//        )
//
//        dbFile.appendText(mapper.writeValueAsString(properties))
//        dbFile.appendText("\n")
//
//        return properties
//    }
//
//    private fun getAuthProperties(userId: UserId): UserAuthProperties? {
//        if (!dbFile.exists()) { return null }
//
//        return dbFile.readLines()
//            .filter { it.isNotBlank() }
//            .map { mapper.readValue(it, UserAuthProperties::class.java) }
//            .find { it.userId == userId }
//    }
//
//    val mapper = ObjectMapper().registerModule(KotlinModule())
}

data class UserAuthProperties(
    val userId: UserId,
    val token: String,
    val tokenSecret: String
)