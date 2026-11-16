package de.ronny_h.aoc.extensions

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

/**
 * Converts string to md5 hash.
 */
fun String.md5() = MessageDigest.getInstance("MD5")
        .digest(toByteArray(UTF_8))
        .toHexString()
