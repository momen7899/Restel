package com.momen.restel

import android.content.Context
import com.momen.restel.login.model.UserModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class Utils {
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

        private var currentUser: UserModel? = null

        fun setUser(userModel: UserModel) {
            currentUser = userModel
        }

        fun getUser() = currentUser

        fun setRtl(rtl: Boolean, activity: MainActivity) {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putBoolean("RTL", rtl)
                apply()
            }
        }

        fun getRtl(activity: MainActivity): Boolean {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
            return sharedPref.getBoolean("RTL", true)
        }

        fun setLanguage(language: String, activity: MainActivity) {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString("lan", language)
                apply()
            }
        }

        fun getLanguage(activity: MainActivity): String {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
            return sharedPref.getString("lan", "fa").toString()
        }

        fun setAppLocale(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = context.resources.configuration
            config.setLocale(locale)
            context.createConfigurationContext(config)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

    }
}