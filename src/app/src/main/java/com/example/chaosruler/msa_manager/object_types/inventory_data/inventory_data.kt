@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.object_types.inventory_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_inventory_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_inventory_table_helper.local_inventory_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * Inventory data-class
 * REMOVED FROM USE
 * @author Chaosruler972
 * @constructor all the data from inventory table
 */
class inventory_data(
        /**
         * the inventory ID
         * @author Chaosruler972
         */
        private var ID:String?,
        /**
         * The inventory name
         * @author Chaosruler972
         */
        private var NAME:String?,
        /**
         * The inventory dataaraeid
         * @author Chaosruler972
         */
        private var DATAAREAID:String?,
        /**
         * The username that synced this data
         * @author Chaosruler972
         */
        private var USERNAME:String?)
    : table_dataclass {


    /**
     * converts inventory to key hashamp
     * @author Chaosruler972
     * @return key hashmap of inventory
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair(global_variables_dataclass.DB_INVENTORY!!.hashmap_of_variables[local_inventory_enum.ID]!!, ID!!)

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
     * the current item id field name (sqlite)
     * @author Chaosruler972
     * @return the current item id (sqlite)
     */
    fun get_itemid():String? = this.ID
    /**
     * the current item name field name (sqlite)
     * @author Chaosruler972
     * @return the current item name (sqlite)
     */
    fun get_itemname():String? = this.NAME
    /**
     * the current dataaraeidfield name (sqlite)
     * @author Chaosruler972
     * @return the current dataaraeid(sqlite)
     */
    fun get_DATAREAID():String? = this.DATAAREAID
    /**
     * the current username that synced this data
     * @author Chaosruler972
     * @return the current username that synced this data
     */
    fun get_USERNAME():String? = this.USERNAME

    /**
     * Sets the current item id
     * @author Chaosruler972
     * @param itemid the new item id
     */
    fun set_itemid(itemid:String)
    {
        this.ID = itemid.trim()
    }
    /**
     * Sets the current item name
     * @author Chaosruler972
     * @param name the new item name
     */
    fun set_itemname(name:String)
    {
        this.NAME = name.trim()
    }

    /**
     * Sets the current dataaraeid
     * @author Chaosruler972
     * @param new_dataareaid the new dataaraeid
     */
    fun set_dataareaid(new_dataareaid:String)
    {
        this.DATAAREAID = new_dataareaid.trim()
    }

    /**
     * Sets the current username
     * @author Chaosruler972
     * @param new_username the new username
     */
    fun set_username(new_username:String)
    {
        this.USERNAME = new_username.trim()
    }

    /**
     * Identifies this data
     * @author Chaosruler972
     * @return a string to identify this data
     */
    override fun toString(): String = get_itemname() ?: ""

    /**
     * a copy constructor
     * @return a copy of this data class
     * @author Chaosruler972
     */
    override fun copy(): inventory_data = inventory_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)

    override fun to_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[remote_inventory_table_helper.ID] = get_itemid() ?: ""
        map[remote_inventory_table_helper.DATAAREAID] = get_DATAREAID() ?: ""
        map[remote_inventory_table_helper.NAME] = get_itemname() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[global_variables_dataclass.DB_INVENTORY!!.hashmap_of_variables[local_inventory_enum.ID]!!] = get_itemid() ?: ""
        map[global_variables_dataclass.DB_INVENTORY!!.hashmap_of_variables[local_inventory_enum.DATAARAEID]!!] = get_DATAREAID() ?: ""
        map[global_variables_dataclass.DB_INVENTORY!!.hashmap_of_variables[local_inventory_enum.NAME]!!] = get_itemname() ?: ""
        map[global_variables_dataclass.DB_INVENTORY!!.hashmap_of_variables[local_inventory_enum.USER]!!] = get_USERNAME() ?: ""
        return map
    }

    override fun toUserName(): String
    {
        return get_USERNAME()!!
    }

}
