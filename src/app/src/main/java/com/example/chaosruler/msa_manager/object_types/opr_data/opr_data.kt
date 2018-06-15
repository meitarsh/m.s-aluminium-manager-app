@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.object_types.opr_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.cache_server_commands.local_cache_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_OPR_table_helper.local_OPR_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * operation data-class
 * @author Chaosruler972
 * @constructor all the data from inventory table
 */
class opr_data(
        /**
         * the operation id
         * @author Chaosruler972
         */
        private var ID:String?,
        /**
         * The operation name
         * @author Chaosruler972
         */
        private var NAME:String?,
        /**
         * the dataaraeid
         * @author Chaosruler972
         */
        private var DATAAREAID:String?,
        /**
         * the username that synced the data
         * @author Chaosruler972
         */
        private var USERNAME:String?
    )
    : table_dataclass, Comparable<opr_data> {

    /**
     * converts to key hashmap
     * @author Chaosruler972
     * @return key hashmap
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair(global_variables_dataclass.DB_OPR!!.hashmap_of_variables[local_OPR_enum.ID]!!, ID!!)

    /**
     * Compares this opr data to another
     * @author Chaosruler972
     * @param other the other opr data
     * @return 0 if equal, else -1
     */
    override fun compareTo(other: opr_data): Int
    {
        return if(this.get_oprid()?.toLowerCase() == other.get_oprid()?.toLowerCase())
            0
        else
            -1
    }

    /**
     * Function responisble for inflating the data from strings.xml
     * @author Chaosruler972
     */
    init
    {
        if(ID!=null)
            ID=(ID?:"").trim()
        if(NAME!=null)
            NAME=(NAME?:"").trim()
        if(DATAAREAID!=null)
            DATAAREAID=(DATAAREAID?:"").trim()
        if(USERNAME!=null)
            USERNAME=(USERNAME?:"").trim()
    }
    /**
     * the current operation id field name (sqlite)
     * @author Chaosruler972
     * @return the current operation id (sqlite)
     */
    fun get_oprid():String? = this.ID
    /**
     * the current operation name field name (sqlite)
     * @author Chaosruler972
     * @return the current operation name (sqlite)
     */
    fun get_opr_name():String? = this.NAME
    /**
     * the current dataaraeid field name (sqlite)
     * @author Chaosruler972
     * @return the current dataaraeid(sqlite)
     */
    fun get_DATAREAID():String? = this.DATAAREAID
    /**
     * the current username(sqlite)
     * @author Chaosruler972
     * @return the current username (sqlite)
     */
    fun get_USERNAME():String? = this.USERNAME

    /**
     * Sets the current opr id
     * @author Chaosruler972
     * @param oprid the new opr id
     */
    @Suppress("unused")
    fun set_oprid(oprid:String)
    {
        this.ID = oprid.trim()
    }
    /**
     * Sets the current opr name
     * @author Chaosruler972
     * @param name the new opr name
     */
    fun set_oprname(name:String)
    {
        this.NAME = name.trim()
    }

    /**
     * Sets the current dataaraeid
     * @author Chaosruler972
     * @param new_dataareaid the new dataaraeid
     */
    @Suppress("unused")
    fun set_dataareaid(new_dataareaid:String)
    {
        this.DATAAREAID = new_dataareaid.trim()
    }

    /**
     * Sets the current username
     * @author Chaosruler972
     * @param new_username the new username
     */
    @Suppress("unused")
    fun set_username(new_username:String)
    {
        this.USERNAME = new_username.trim()
    }

    /**
     * Identifies this data
     * @author Chaosruler972
     * @return a string to identify this data
     */
    override fun toString(): String = get_opr_name() ?: ""

    /**
     * a copy constructor
     * @return a copy of this data class
     * @author Chaosruler972
     */
    override fun copy(): opr_data = opr_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)

    override fun to_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[remote_opr_table_helper.ID] = get_oprid() ?: ""
        map[remote_opr_table_helper.DATAAREAID] = get_DATAREAID() ?: ""
        map[remote_opr_table_helper.NAME] = get_opr_name() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[global_variables_dataclass.DB_OPR!!.hashmap_of_variables[local_OPR_enum.ID]!!] = get_oprid() ?: ""
        map[global_variables_dataclass.DB_OPR!!.hashmap_of_variables[local_OPR_enum.DATAARAEID]!!] = get_DATAREAID() ?: ""
        map[global_variables_dataclass.DB_OPR!!.hashmap_of_variables[local_OPR_enum.NAME]!!] = get_opr_name() ?: ""
        map[global_variables_dataclass.DB_OPR!!.hashmap_of_variables[local_OPR_enum.USER]!!] = get_USERNAME() ?: ""
        return map
    }

    override fun toUserName(): String
    {
        return get_USERNAME()!!
    }


}

