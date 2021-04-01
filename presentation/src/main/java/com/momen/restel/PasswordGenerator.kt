package com.momen.restel

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class PasswordGenerator {
    companion object {
        fun md5(s: String): String {
            try {
                val digest = MessageDigest
                    .getInstance("MD5")
                digest.update(s.toByteArray())
                val messageDigest = digest.digest()
                val hexString = StringBuilder()
                for (aMessageDigest in messageDigest) {
                    val h = StringBuilder(Integer.toHexString(0xFF and aMessageDigest.toInt()))
                    while (h.length < 2) h.insert(0, "0")
                    hexString.append(h)
                }
                return hexString.toString().toUpperCase(Locale.ROOT)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}