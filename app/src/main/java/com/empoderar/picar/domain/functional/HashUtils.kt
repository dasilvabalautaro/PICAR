package com.empoderar.picar.domain.functional

import java.security.MessageDigest

object HashUtils {
    fun sha512(input: String) = hashString("SHA-512", input)

    fun sha256(input: String) = hashString("SHA-256", input)

    fun sha1(input: String) = hashString("SHA-1", input)

    private fun hashString(type: String, input: String): String {
        val key = "12358132134Facility"
        val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(key[i shr 4 and 0x0f])
            result.append(key[i and 0x0f])
        }

        return result.toString()
    }

//    assertEquals("65a8e27d8879283831b664bd8b7f0ad4", HashUtils.sha512("Hello, World!"))
}