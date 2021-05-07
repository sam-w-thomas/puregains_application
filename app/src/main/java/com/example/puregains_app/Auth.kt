package com.example.puregains_app

import android.app.Activity
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import okhttp3.*
import org.json.JSONObject
import kotlin.Exception

private val mediaType : MediaType = MediaType.parse("application/json")
private val client : OkHttpClient = OkHttpClient()
const val AUTH_PREF : String = "AUTHENTICATION_PREF_USER"
const val USER_USERNAME : String = "user_username"
const val USER_PASSWORD : String = "user_password"
const val USER_TOKEN : String = "user_token"

class Auth {

    /***
     * Set user username and password in Shared Preferences
     */
    companion object {
        /**
         * Set user username and password
         */
        fun setUser(
                username : String,
                password : String,
                activity: Activity
        ) {


            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)

            with (preferences.edit()) {
                putString(USER_USERNAME, username)
                putString(USER_PASSWORD, password)
                apply()
            }
        }

        /**
         * Get user username
         */
        fun getUsername(activity: Activity) : String{
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            return preferences.getString(USER_USERNAME, "null").toString()
        }

        /**
         * Get user password
         */
        fun getPassword(activity: Activity) : String{
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            return preferences.getString(USER_PASSWORD, "null").toString()
        }

        /***
         * Set token in Shared Preferences
         */
        fun setToken(
                token : String,
                activity: Activity
        ) {

            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)

            with (preferences.edit()) {
                putString(USER_TOKEN, token)
                apply()
            }
        }

        /**
         * Retrive current token
         */
        fun getToken(activity: Activity) : String {
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            return preferences.getString(USER_TOKEN, "null").toString()
        }

        /**
         * Check if user has token, does not have to be valid
         */
        fun hasToken(activity: Activity) : Boolean {
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            return preferences.contains(USER_TOKEN)
        }

        /**
         * Checks if username and password present
         */
        fun hasAccount(activity: Activity) : Boolean {
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            return preferences.contains(USER_USERNAME) and preferences.contains(USER_PASSWORD)
        }

        /**
         * Request token using username and password
         * Returns token
         */
        fun requestToken(activity: Activity) : String {
            val username = activity.getSharedPreferences(AUTH_PREF, 0).getString(USER_USERNAME,"null")
            val password = activity.getSharedPreferences(AUTH_PREF, 0).getString(USER_PASSWORD, "null")

            if ((username=="null") or (password == "null")) {
                throw Exception("User not set")
            }

            val request: Request = Request.Builder()
                .url(SERVER_URL + "api/token/" + username)
                .get()
                .addHeader("password", password)
                .build()

            val response = client.newCall(request).execute()

            if (response.code() != 200) {
                throw Exception("Invalid password")
            }

            return JSONObject(response?.body()?.string()).getString("token").toString()
        }


        /**
         * Clear user
         */
        fun clearUser(
            activity: Activity
        ) {
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            with(preferences.edit()) {
                remove(USER_USERNAME)
                remove(USER_PASSWORD)
                apply()
            }
        }

        /**
         * Clear user
         */
        fun clearToken(
            activity: Activity
        ) {
            val preferences = activity.getSharedPreferences(AUTH_PREF, 0)
            with(preferences.edit()) {
                remove(USER_TOKEN)
                apply()
            }
        }

        /**
         * Create a user profile
         * Return create user
         */
        fun createUser(
                name : String,
                birth : String,
                avatar : String,
                password : String,
                activity : Activity
        ) : String {
            val body : RequestBody = RequestBody.create(
                    mediaType,
                    JsonObject(
                            mapOf(
                                    "name" to JsonPrimitive(name),
                                    "birth_date" to JsonPrimitive(birth),
                                    "avatar_path" to JsonPrimitive(avatar),
                                    "password" to JsonPrimitive(password)
                            )
                    ).toString()
            )

            val request: Request = Request.Builder()
                    .url(SERVER_URL + "api/user")
                    .post(body)
                    .build()


            val response = client.newCall(request).execute()
            val username = JSONObject(response?.body()?.string()).getString("username").toString()

            return username
        }
    }



}
