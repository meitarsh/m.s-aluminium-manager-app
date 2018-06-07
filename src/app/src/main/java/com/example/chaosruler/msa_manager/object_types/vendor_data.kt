package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_vendors_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass

/**
 * a dataclass for vendor data
 * @author Chaosruler972
 * @param DATAAREAID for filtering during testing
 * @param ID the id of the vendor
 * @param NAME the name of the vendor
 * @param USERNAME the username that required that data
 */
class vendor_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
    : table_dataclass {


    /**
     * inits everything to null and trims the strings
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
     * returns a key hashmap
     * @author Chaosruler972
     * @return a key hashmap
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair(global_variables_dataclass.DB_VENDOR!!.ID, ID!!)

    /**
     * gets the vendor account ID
     * @author Chaosruler972
     * @return the account id
     */
    fun get_accountnum():String? = this.ID

    /**
     * gets the account name
     * @author Chaosruler972
     * @return the account's name
     */
    fun get_accountname():String? = this.NAME

    /**
     * gets the daaraaeid (testing phase only)
     * @author Chaosruler972
     * @return the dataaraeid strings
     */
    fun get_DATAREAID():String? = this.DATAAREAID

    /**
     * gets the username that synced that data
     * @author Chaosruler972
     * @return the username that synced that data
     */
    fun get_USERNAME():String? = this.USERNAME


    /**
     * sets a new ID (shouldn't be used)
     * @author Chaosruler972
     * @param accountnum the new id
     */
    fun set_accountnum(accountnum:String)
    {
        this.ID = accountnum.trim()
    }

    /**
     * sets a new account name
     * @author Chaosruler972
     * @param name the new account name
     */
    fun set_accountname(name:String)
    {
        this.NAME = name.trim()
    }


    /**
     * sets a dataaraeid (testing phase only)
     * @author Chaosruler972
     * @param new_dataareaid the new dataaraeid
     */
    fun set_dataareaid(new_dataareaid:String)
    {
        this.DATAAREAID = new_dataareaid.trim()
    }

    /**
     * resetns a new username that synced that data
     * @author Chaosruler972
     * @param new_username the new username that synced that data
     */
    fun set_username(new_username:String)
    {
        this.USERNAME = new_username.trim()
    }

    /**
     * Identifies this data with the ID
     * @author Chaosruler972
     * @return a string with the ID of the vendor to identify it
     */
    override fun toString(): String = get_accountname() ?: ""

    /**
     * Copy constructor for this object precisely
     * @author Chaosruler972
     * @return a copy of this object
     */
    override fun copy(): vendor_data = vendor_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)

    /**
     * creates vendor data into hashmap
     * @return vendor data hashmap
     * @author Chaosruler972
     */
    override fun to_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[remote_vendors_table_helper.ID] = get_accountnum() ?: ""
        map[remote_vendors_table_helper.DATAAREAID] = get_DATAREAID() ?: ""
        map[remote_vendors_table_helper.NAME] = get_accountname() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val data: HashMap<String, String> = HashMap()
        data[global_variables_dataclass.DB_VENDOR!!.ID] = (get_accountnum() ?: "").trim()
        data[global_variables_dataclass.DB_VENDOR!!.NAME] = (get_accountname() ?: "").trim()
        data[global_variables_dataclass.DB_VENDOR!!.DATAARAEID] = (get_DATAREAID() ?: "").trim()
        data[global_variables_dataclass.DB_VENDOR!!.USER] = (get_USERNAME() ?: "").trim()
        return data
    }

    override fun toUserName(): String
    {
        return get_USERNAME()!!
    }
}