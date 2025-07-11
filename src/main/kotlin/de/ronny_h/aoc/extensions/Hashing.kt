package de.ronny_h.aoc.extensions

import java.security.MessageDigest

private val messageDigest = MessageDigest.getInstance("MD5")

/**
 * Converts string to md5 hash.
 */
fun String.md5() = messageDigest
        .digest(toByteArray())
        .toHexString()
