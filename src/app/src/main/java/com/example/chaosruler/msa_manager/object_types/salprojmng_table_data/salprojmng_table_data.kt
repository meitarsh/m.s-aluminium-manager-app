@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.object_types.salprojmng_table_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojmng_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

class salprojmng_table_data(
        private var projid: String?,
        private var userid: String?,
        private var dataaraeid: String?,
        private var recversion: String?,
        private var recid: String?,
        private var username: String
) : table_dataclass
{

    /**
     * inits everything to null and trims the strings
     * @author Chaosruler972
     */
    init
    {
        if(projid!=null)
            projid=(projid?:"").trim()
        if(userid!=null)
            userid=(userid?:"").trim()
        if(dataaraeid!=null)
            dataaraeid=(dataaraeid?:"").trim()
        if(recversion!=null)
            recversion=(recversion?:"").trim()
        if(recid!=null)
            recid=(recid?:"").trim()
    }

    /**
     * returns a key hashmap
     * @author Chaosruler972
     * @return a key hashmap
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair(global_variables_dataclass.DB_VENDOR!!.ID, recid!!)

    /**
     * projid getter
     * @author Chaosruler972
     * @return the projid
     */
    fun get_projid():String? = projid

    /**
     * user getter
     * @author Chaosruler972
     * @return the user
     */
    fun get_userid():String? = userid

    /**
     * DATAAREAID getter
     * @author Chaosruler972
     * @return the DATAAREAID
     */
    fun get_DATAREAID():String? = dataaraeid

    /**
     * RECVERSION getter
     * @author Chaosruler972
     * @return the RECVERSION
     */
    fun get_RECVERSION():String? = recversion

    /**
     * RECID getter
     * @author Chaosruler972
     * @return the RECID
     */
    fun get_RECID():String? = recid


    /**
     * username getter
     * @author Chaosruler972
     * @return the username
     */
    fun get_username():String? = username

    /**
     * set username
     * @param str the username
     * @author Chaosruler972
     */
    fun set_username(str: String) = {
        this.username = str
    }

    /**
     * set RECID
     * @param str the RECID
     * @author Chaosruler972
     */
    fun set_RECID(str: String) = {
        this.recid = str
    }

    /**
     * set RECVERSION
     * @param str the RECVERSION
     * @author Chaosruler972
     */
    fun set_RECVERSION(str: String) = {
        this.recversion = str
    }


    /**
     * set DATAAREAID
     * @param str the DATAAREAID
     * @author Chaosruler972
     */
    fun set_DATAAREAID(str: String) = {
        this.dataaraeid = str
    }

    /**
     * set USERID
     * @param str the USERID
     * @author Chaosruler972
     */
    fun set_USERID(str: String) = {
        this.userid = str
    }

    /**
     * set PROJID
     * @param str the PROJID
     * @author Chaosruler972
     */
    fun set_PROJID(str: String) = {
        this.projid = str
    }



    /**
     * Identifies this data with the ID
     * @author Chaosruler972
     * @return a string with the ID of the vendor to identify it
     */
    override fun toString(): String = get_RECID() ?: ""

    /**
     * Copy constructor for this object precisely
     * @author Chaosruler972
     * @return a copy of this object
     */
    override fun copy(): salprojmng_table_data = salprojmng_table_data(projid, userid, dataaraeid, recversion, recid, username)

    /**
     * creates vendor data into hashmap
     * @return vendor data hashmap
     * @author Chaosruler972
     */
    override fun to_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[remote_salprojmng_table_helper.PROJID] = get_projid() ?: ""
        map[remote_salprojmng_table_helper.DATAAREAID] = get_DATAREAID() ?: ""
        map[remote_salprojmng_table_helper.USERID] = get_userid() ?: ""
        map[remote_salprojmng_table_helper.RECVERSION] = get_RECVERSION() ?: ""
        map[remote_salprojmng_table_helper.RECID] = get_RECID() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val data: HashMap<String, String> = HashMap()
        data[global_variables_dataclass.DB_SALPROJMNG!!.PROJID] = (get_projid() ?: "").trim()
        data[global_variables_dataclass.DB_SALPROJMNG!!.USERID] = (get_userid() ?: "").trim()
        data[global_variables_dataclass.DB_SALPROJMNG!!.DATAARAEID] = (get_DATAREAID() ?: "").trim()
        data[global_variables_dataclass.DB_SALPROJMNG!!.RECVERSION] = (get_RECVERSION() ?: "").trim()
        data[global_variables_dataclass.DB_SALPROJMNG!!.RECID] = (get_RECID() ?: "").trim()
        data[global_variables_dataclass.DB_SALPROJMNG!!.USER] = (get_username() ?: "").trim()
        return data
    }

    override fun toUserName(): String
    {
        return get_username()!!
    }

}

