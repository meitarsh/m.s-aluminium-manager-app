package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import java.util.*
import kotlin.collections.HashMap

@Suppress("unused")
/**
 * an object representation of a user
 * @author Chaosruler972
 * @constructor a constructor to fill the username and password in unencrypted form
 */
class User(
        /**
         * the username data
         * @author Chaosruler972
         */
        private var __username: String,
        /**
         * The password data
         * @author Chaosruler972
         */
        private var __password: String,

        /**
         * Last sync time by user
         */
        private var __synctime: Long
):table_dataclass {

    /**
     * to key hahsmap
     * @author Chaosruler972
     * @return a key hashmap
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair("user_id", __username)


    /**
     * Identifies this data
     * @author Chaosruler972
     * @return a string to identify this data
     */
    override fun toString(): String
    {
        return this.__username
    }

    /**
     * gets the username
     * @author Chaosruler972
     * @return the username
     */
    fun get__username(): String
    {
        return this.__username
    }

    /**
     * Sets the current username
     * @author Chaosruler972
     * @param username the new username
     */
    fun set__username(username: String)
    {
        this.__username = username
    }

    /**
     * gets the password
     * @author Chaosruler972
     * @return the password
     */
    fun get__password(): String
    {
        return __password
    }

    /**
     * Sets the current password
     * @author Chaosruler972
     * @param password the new password
     */
    fun set__password(password: String)
    {
        this.__password = password
    }

    /**
     * gets the latest synctime in dateformat
     * @author Chaosruler972
     * @return the latest synctime
     */
    fun get_last_sync_time() : Date
    {
        return Date(this.__synctime)
    }

    /**
     * sets the latest synctime
     * @author Chaosruler972
     */
    fun set_last_sync_time(time: Long)
    {
        if (this.__synctime < time)
            this.__synctime = time
    }

    /**
     * a copy constructor
     * @return a copy of this data class
     * @author Chaosruler972
     */
    override fun copy(): User = User(this.__username, this.__password, this.__synctime)

    override fun to_hashmap(): HashMap<String, String> {
        return HashMap()
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        return HashMap()
    }

    override fun toUserName(): String
    {
        return get__username()
    }
}
