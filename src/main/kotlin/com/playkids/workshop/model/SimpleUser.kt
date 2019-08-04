package com.playkids.workshop.model

import twitter4j.URLEntity
import twitter4j.User
import java.time.Instant

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
typealias UserId = Long

data class SimpleUser(
    val id: UserId,
    val name: String,
    val screenName: String,
    val email: String? = null,
    val location: String? = null,
    val description: String,
    val profileImageURL: String,
    val url: String,
    val isProtected: Boolean,
    val followersCount: Int,
    val profileBackgroundColor: String,
    val profileTextColor: String,
    val profileLinkColor: String,
    val profileSidebarFillColor: String,
    val profileSidebarBorderColor: String,
    val isProfileUseBackgroundImage: Boolean,
    val followingCount: Int,
    val createdAt: Instant,
    val utcOffset: Int,
    val timeZone: String,
    val profileBackgroundImageURL: String,
    val profileBannerURL: String,
    val lang: String,
    val statusesCount: Int,
    val isGeoEnabled: Boolean,
    val isVerified: Boolean,
    val isFollowRequestSent: Boolean,
    val descriptionURLEntities: List<URLEntity>
)

fun User.toSimpleUser(): SimpleUser =
    SimpleUser(
        id = id,
        name = name,
        screenName = screenName,
        email = email,
        location = location,
        description = description,
        profileImageURL = profileImageURL,
        url = url,
        isProtected = isProtected,
        followersCount = followersCount,
        profileBackgroundColor = profileBackgroundColor,
        profileTextColor = profileTextColor,
        profileLinkColor = profileLinkColor,
        profileSidebarFillColor = profileSidebarFillColor,
        profileSidebarBorderColor = profileSidebarBorderColor,
        isProfileUseBackgroundImage = isProfileUseBackgroundImage,
        followingCount = friendsCount,
        createdAt = createdAt.toInstant(),
        utcOffset = utcOffset,
        timeZone = timeZone,
        profileBackgroundImageURL = profileBackgroundImageURL,
        profileBannerURL = profileBannerURL,
        lang = lang,
        statusesCount = statusesCount,
        isGeoEnabled = isGeoEnabled,
        isVerified = isVerified,
        isFollowRequestSent = isFollowRequestSent,
        descriptionURLEntities = descriptionURLEntities.toList()
    )