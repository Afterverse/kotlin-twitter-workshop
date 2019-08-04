package com.playkids.workshop.adapter

import com.playkids.workshop.model.SimpleUser
import com.playkids.workshop.model.Tweet
import com.playkids.workshop.model.toSimpleUser
import com.playkids.workshop.model.toTweet
import twitter4j.IDs
import twitter4j.TwitterFactory

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
object TwitterClient {
    val twitter = TwitterFactory.getSingleton()

    fun getLoggedUser() = twitter.verifyCredentials().toSimpleUser()

    fun getHomeTweets(): List<Tweet> =
        twitter.getHomeTimeline().map {
            it.toTweet()
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
}