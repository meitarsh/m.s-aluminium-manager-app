package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass

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
    : table_dataclass {
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
        map[remote_opr_table_helper.ID] = ID?:""
        map[remote_opr_table_helper.DATAAREAID] = DATAAREAID?:""
        map[remote_opr_table_helper.NAME] = NAME?:""
        return map
    }

}