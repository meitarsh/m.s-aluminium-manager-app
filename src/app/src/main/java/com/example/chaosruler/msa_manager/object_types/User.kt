package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass

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
        private var __password: String
):table_dataclass {



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
    @Suppress("unused")
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
    @Suppress("unused")
    fun set__password(password: String)
    {
        this.__password = password
    }
    /**
     * a copy constructor
     * @return a copy of this data class
     * @author Chaosruler972
     */
    override fun copy(): User = User(this.__username, this.__password)
}
