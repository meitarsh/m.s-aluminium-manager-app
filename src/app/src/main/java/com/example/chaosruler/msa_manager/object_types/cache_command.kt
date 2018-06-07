package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


/**
 * an object representation of a cache command (single)
 * @constructor the command and the user that sent that command
 * @author Chaosruler972
 */
class cache_command(
        /**
         * The command string itself
         * @author Chaosruler972
         */
        val __command: String,
        /**
         * the user that sent that command
         * @author Chaosruler972
         */
        val __user: String) : table_dataclass
{

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        return HashMap()
    }

    /**
     * to key pair
     * @author Chaosruler972
     * @return key of the data
     */
    override fun to_key_hashmap(): Pair<String, String> {
        return Pair("id", __command)
    }


    /**
     * identifies this command dataclass
     * @return a string that identifies this command dataclass
     * @author Chaosruler972
     */
    override fun toString(): String
    {
        return __command
    }

    /**
     * creates a copy of this dataclass
     * @author Chaosruler972
     * @return a copy of this dataclass
     */
    override fun copy(): cache_command = cache_command(this.__command,this.__user)

    override fun to_hashmap(): HashMap<String, String> {
        return HashMap()
    }

    override fun toUserName(): String
    {
        return __user!!
    }
}