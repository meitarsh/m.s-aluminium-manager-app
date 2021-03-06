@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.object_types.salprojluz_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojluz_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojluz_table_helper.local_salprojluz_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * Dataclass to represent salprojluz
 * @author Chaosruler972
 */
class salprojluz_data(
        /**
         * ID data
         * @author Chaosruler972
         */
        private var PROJID: String?,
        /**
         * Start date
         * @author Chaosruler972
         */
        private var STARTDATE: String?,
        /**
         * finish date
         * @author Chaosruler972
         */
        private var FINISHDATE: String?,
        /**
         * is finished?
         * @author Chaosruler972
         */
        private var IS_FINISHED: Boolean,
        /**
         * Sium bpoal
         * @author Chaosruler972
         */
        private var SIUM_BPOAL: String?,
        /**
         * Notes
         * @author Chaosruler972
         */
        private var NOTES: String?,
        /**
         * Koma
         * @author Chaosruler972
         */
        private var KOMA: String?,
        /**
         * Building
         * @author Chaosruler972
         */
        private var BUILDING: String?,
        /**
         * Percent Executed
         * @author Chaosruler972
         */
        private var PERCENTEXC: String?,
        /**
         * dataaraeid
         * @author Chaosruler972
         */
        private var DATAARAEID: String?,

        /**
         * RECID
         * @author Chaosruler972
         */
        private var RECID: String?,
        /**
         * RECVERSOIN
         * @author Chaosruler972
         */
        private var RECVERSOIN: String?,
        /**
         * username
         * @author Chaosruler972
         */
        private var USERNAME: String?
) : table_dataclass
{
    /**
     * converts to key hashmap
     * @author Chaosruler972
     * @return key hashmap
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair(global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.ID]!!, RECID!!)

    init {
        if(PROJID != null)
            PROJID = (PROJID?:"").trim()
        if(STARTDATE != null)
            STARTDATE = (STARTDATE?:"").trim()
        if(FINISHDATE != null)
            FINISHDATE = (FINISHDATE?:"").trim()
        if(SIUM_BPOAL != null)
            SIUM_BPOAL = (SIUM_BPOAL?:"").trim()
        if(NOTES != null)
            NOTES = (NOTES?:"").trim()
        if(KOMA != null)
            KOMA = (KOMA?:"").trim()
        if(BUILDING != null)
            BUILDING = (BUILDING?:"").trim()
        if(DATAARAEID != null)
            DATAARAEID = (DATAARAEID?:"").trim()
        if(USERNAME != null)
            USERNAME = (USERNAME?:"").trim()
        if(PERCENTEXC != null)
            PERCENTEXC = (PERCENTEXC?:"").trim()
        if(RECID != null)
            RECID = (RECID?:"").trim()
        if(RECVERSOIN != null)
            RECVERSOIN = (RECVERSOIN?:"").trim()
    }

    /**
     * projid getter
     * @author Chaosruler972
     * @return the project id
     */
    fun get_projid() = PROJID

    /**
     * projid setter
     * @author Chaosruler972
     * @param projid the new projid
     */
    fun set_projid(projid: String)
    {
        this.PROJID = projid.trim()
    }

    /**
     * start date getter
     * @author Chaosruler972
     * @return the start date
     */
    fun get_startdate() = STARTDATE

    /**
     * set start date
     * @author Chaosruler972
     * @param startdate the new startdate
     */
    fun set_startdate(startdate: String)
    {
        this.STARTDATE = startdate.trim()
    }

    /**
     * finish date getter
     * @author Chaosruler972
     * @return the finish date
     */
    fun get_finishdate() = FINISHDATE

    /**
     * finish date setter
     * @author Chaosruler972
     * @param finishdate the new finishdate
     */
    fun set_finishdate(finishdate: String)
    {
        this.FINISHDATE = finishdate.trim()
    }

    /**
     * sium bpoal getter
     * @author Chaosruler972
     * @return the siumbpoal date
     */
    fun get_siumbpoal() = SIUM_BPOAL

    /**
     * sium bpoal setter
     * @author Chaosruler972
     * @param siumbpal the new siumbpoal date
     */
    fun set_siumbpoal(siumbpal: String)
    {
        this.SIUM_BPOAL = siumbpal.trim()
    }

    /**
     * dataaraeid getter
     * @author Chaosruler972
     * @return the dataaraeid
     */
    fun get_dataaraeid() = DATAARAEID

    /**
     * dataaraeid setter
     * @author Chaosruler972
     * @param dataaraeid the new dataaraeid
     */
    fun set_dataaraed(dataaraeid: String)
    {
        this.DATAARAEID = dataaraeid.trim()
    }

    /**
     * is_finished getter
     * @author Chaosruler972
     * @return is project finished?
     */
    fun get_isfinished() = IS_FINISHED

    /**
     * is finished setter
     * @author Chaosruler972
     * @param is_finished is project finished?
     */
    fun set_isfinished(is_finished: Boolean)
    {
        this.IS_FINISHED = is_finished
    }

    /**
     * notes getter
     * @author Chaosruler972
     * @return the notes
     */
    fun get_notes() = NOTES

    /**
     * notes setter
     * @author Chaosruler972
     * @param notes the new notes
     */
    fun set_notes(notes: String)
    {
        this.NOTES = notes.trim()
    }

    /**
     * koma getter
     * @author Chaosruler972
     * @return the koma
     */
    fun get_koma() = KOMA

    /**
     * koma setter
     * @author Chaosruler972
     * @param koma the new koma
     */
    fun set_koma(koma: String)
    {
        this.KOMA = koma.trim()
    }

    /**
     * building getter
     * @author Chaosruler972
     * @return the building
     */
    fun get_building() = BUILDING

    /**
     * set building
     * @author Chaosruler972
     * @param building the new building
     */
    fun set_building(building: String)
    {
        this.BUILDING = building.trim()
    }

    /**
     * user getter
     * @author Chaosruler972
     * @return the username
     */
    fun get_username() = USERNAME

    /**
     * set username
     * @author Chaosruler972
     * @param username the new username
     */
    fun set_username(username: String)
    {
        this.USERNAME = username.trim()
    }

    /**
     * Get percent executed
     * @author Chaosruler972
     * @return the percent executed
     */
    fun get_percentexc() =
            try {
                (PERCENTEXC!!.toDouble()).toInt().toString()
            }
            catch (e: Exception)
            {
                PERCENTEXC
            }

    /**
     * set percent executed
     * @author Chaosruler972
     * @param percentexc new percent executed
     */
    fun set_percentexc(percentexc: String)
    {
        PERCENTEXC = percentexc.trim()
    }

    /**
     * recid getter
     * @author Chaosruler972
     * @return the record id
     */
    fun get_recid() = RECID

    /**
     * recid setter
     * @author Chaosruler972
     * @param recid the new recid
     */
    fun set_recid(recid: String)
    {
        RECID = recid
    }

    /**
     * rec version getter
     * @author Chaosruler972
     * @return the record version
     */
    fun get_recversion() = RECVERSOIN

    /**
     * rec version setter
     * @author Chaosruler972
     * @param recversion the new rec version
     */
    fun set_recversion(recversion: String)
    {
        RECVERSOIN = recversion
    }

    /**
     * Stringifies Salrpojluz
     * @author Chaosruler972
     * @return String data that represents this salrpojluz
     */
    override fun toString(): String
    {
        return (PROJID?:"").trim()
    }

    /**
     * Copy constructor for salprojluz
     * @author Chaosruler972
     * @return a copy of this dataclass data
     */
    override fun copy(): salprojluz_data = salprojluz_data(
            PROJID, STARTDATE, FINISHDATE, IS_FINISHED, SIUM_BPOAL,
            NOTES, KOMA, BUILDING, PERCENTEXC, DATAARAEID, RECID, RECVERSOIN, USERNAME
    )

    override fun to_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[remote_salprojluz_table_helper.ID] = get_projid() ?: ""
        map[remote_salprojluz_table_helper.STARTDATE] = STARTDATE?:""
        map[remote_salprojluz_table_helper.FINISHDATE] = FINISHDATE?:""
        map[remote_salprojluz_table_helper.IS_FINISHED] = if (IS_FINISHED)
            "255"
                else
            "0"
        map[remote_salprojluz_table_helper.SIUMBPOAL] = SIUM_BPOAL?:""
        map[remote_salprojluz_table_helper.NOTES] = get_notes() ?: ""
        map[remote_salprojluz_table_helper.KOMA] = get_koma() ?: ""
        map[remote_salprojluz_table_helper.BUILDING] = get_building() ?: ""
        map[remote_salprojluz_table_helper.PERCENTEXC] = get_percentexc() ?: ""
        map[remote_salprojluz_table_helper.RECID] = get_recid() ?: ""
        map[remote_salprojluz_table_helper.RECVERION] = get_recversion() ?: ""
        map[remote_salprojluz_table_helper.DATAAREAID] = get_dataaraeid() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.ID]!!] = get_projid() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.STARTDATE]!!] = STARTDATE ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.FINISHDATE]!!] = FINISHDATE ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.IS_FINISHED]!!] = if (IS_FINISHED)
            "255"
        else
            "0"
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.SIUMBPOAL]!!] = SIUM_BPOAL ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.NOTES]!!] = get_notes() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.KOMA]!!] = get_koma() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.BUILDING]!!] = get_building() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.PERCENTEXC]!!] = get_percentexc() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.RECID]!!] = get_recid() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.RECVERSION]!!] = get_recversion() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.USERNAME]!!] = get_username() ?: ""
        map[global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables[local_salprojluz_enum.DATAARAEID]!!] = get_dataaraeid() ?: ""
        return map
    }

    override fun toUserName(): String
    {
        return get_username()!!
    }

}

