@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.object_types.big_table

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper.local_big_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * a dataclass to represent big table data
 * @author Chaosruler972
 * @constructor all the data from the big table each column, null if no data present, non nullable is data that can't be null from db
 */
class big_table_data(
        /**
         * The vendor id field name for local sqlite
         * @author Chaosruler972
         */
        private var VENDOR_ID:String?,
        /**
         * the dataraeid field name for local sqlite
         * @author Chaosruler972
         */
        private var DATAAREAID:String?,
        /**
         * The reversion field name for local sqlite
         * @author Chaosruler972
         */
        private var RECVERSION:String?,
        /**
         * the recversion field name for local sqlite
         * @author Chaosruler972
         */
        private var RECID:String?,
        /**
         * the project id field name for local sqlite
         * @author Chaosruler972
         */
        private var PROJECTS_ID:String?,
        /**
         * the inventory id field name for local sqlite
         * @author Chaosruler972
         */
        private var INVENTORY_ID:String?,
        /**
         * the flat field name for local sqlite
         * @author Chaosruler972
         */
        private var FLAT:String?,
        /**
         * the flat field name for local sqlite
         * @author Chaosruler972
         */
        private var FLOOR:String?,
        /**
         * the quanity field name for local sqlite
         * @author Chaosruler972
         */
        private var QTY:String?,
        /**
         * the salesprice field name for local sqlite
         * @author Chaosruler972
         */
        private var SALESPRICE:String?,
        /**
         * the Operation id field name for local sqlite
         * @author Chaosruler972
         */
        private var OPR_ID:String?,
        /**
         * the milestone to percent field name for local sqlite
         * @author Chaosruler972
         */
        private var MILESTONEPERCENT:String?,
        /**
         * the quanity for account field name for local sqlite
         * @author Chaosruler972
         */
        private var QTYFORACCOUNT:String?,
        /**
         * the percent for account field name for local sqlite
         * @author Chaosruler972
         */
        private var PERCENTFORACCOUNT:String?,
        /**
         * the total sum field name for local sqlite
         * @author Chaosruler972
         */
        private var TOTALSUM:String?,
        /**
         * The salprog field name for local sqlite
         * @author Chaosruler972
         */
        private var SALPROG:String?,
        /**
         * the print order field name for local sqlite
         * @author Chaosruler972
         */
        private var PRINTORDER:String?,
        /**
         * the itemnumber field name for local sqlite
         * @author Chaosruler972
         */
        private var ITEMNUMBER:String?,
        /**
         * The koma num field name for local sqlite
         * @author Chaosruler972
         */
        private var KOMANUM:String?,
        /**
         * the dira num field name for local sqlite
         * @author Chaosruler972
         */
        private var DIRANUM:String?,
        /**
         * the username that synced that data
         * @author Chaosruler972
         */
        private var USERNAME:String?,
        /**
         * get partial qty
         */
        private var QTYINPARTIALACC:String?
)
    : table_dataclass, Comparable<big_table_data> {

    /**
     * Only gets keys
     * @author Chaosruler972
     * @return a pair of key value of the key
     */
    override fun to_key_hashmap(): Pair<String, String> {
        return Pair(global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.RECID]!!, RECID!!)
    }

    /**
     * Function responisble for inflating the data from strings.xml
     * @author Chaosruler972
     */
    init
    {
        if(VENDOR_ID!=null)
            VENDOR_ID=(VENDOR_ID?:"").trim()
        if(DATAAREAID!=null)
            DATAAREAID=(DATAAREAID?:"").trim()
        if(RECVERSION!=null)
            RECVERSION=(RECVERSION?:"").trim()
        if(RECID!=null)
            RECID=(RECID?:"").trim()
        if(PROJECTS_ID!=null)
            PROJECTS_ID=(PROJECTS_ID?:"").trim()
        if(INVENTORY_ID!=null)
            INVENTORY_ID=(INVENTORY_ID?:"").trim()
        if(FLAT!=null)
            FLAT = (FLAT?:"").trim()
        if(FLOOR!=null)
            FLOOR!=(FLOOR?:"").trim()
        if(QTY!=null)
            QTY = (QTY?:"").trim()
        if(SALESPRICE!=null)
            SALESPRICE = (SALESPRICE?:"").trim()
        if(OPR_ID!=null)
            OPR_ID = (OPR_ID?:"").trim()
        if(MILESTONEPERCENT!=null)
            MILESTONEPERCENT = (MILESTONEPERCENT?:"").trim()
        if(QTYFORACCOUNT!=null)
            QTYFORACCOUNT = (QTYFORACCOUNT?:"").trim()
        if(PERCENTFORACCOUNT!=null)
            PERCENTFORACCOUNT = (PERCENTFORACCOUNT?:"").trim()
        if(TOTALSUM != null)
            TOTALSUM = (TOTALSUM?:"").trim()
        if(SALPROG != null)
            SALPROG = (SALPROG?:"").trim()
        if(PRINTORDER != null)
            PRINTORDER = (PRINTORDER?:"").trim()
        if(ITEMNUMBER != null)
            ITEMNUMBER = (ITEMNUMBER?:"").trim()
        if(KOMANUM != null)
            KOMANUM = (KOMANUM?:"").trim()
        if(DIRANUM != null)
            DIRANUM = (DIRANUM?:"").trim()
        if(USERNAME != null)
            USERNAME = (USERNAME?:"").trim()
        if(QTYINPARTIALACC != null)
            QTYINPARTIALACC = (QTYINPARTIALACC?:"").trim()
    }

    /**
     * returns current vendor id field name (sqlite)
     * @return current vendor id field name (sqlite)
     * @author Chaosruler972
     */
    fun get_VENDOR_ID(): String? = VENDOR_ID

    /**
     * returns current dataraeid field name (sqlite)
     * @author Chaosruler972
     * @return current dataraeid field name (sqlite)
     */
    fun get_DATAAREAID(): String? = DATAAREAID

    /**
     * the current recversion field name (sqlite)
     * @author Chaosruler972
     * @return the current recversion (sqlite)
     */
    fun get_RECVERSION(): String? = RECVERSION

    /**
     * the current recid field name (sqlite)
     * @author Chaosruler972
     * @return the current recid (sqlite)
     */
    fun get_RECID(): String? = RECID

    /**
     * the current project id field name (sqlite)
     * @author Chaosruler972
     * @return the current project id (sqlite)
     */
    fun get_PROJECT_ID(): String? = PROJECTS_ID

    /**
     * the current inventory id field name (sqlite)
     * @author Chaosruler972
     * @return the current inventory id (sqlite)
     */
    fun get_INVENTORY_ID(): String? = INVENTORY_ID

    /**
     * the current flat field name (sqlite)
     * @author Chaosruler972
     * @return the current flat(sqlite)
     */
    fun get_FLAT(): String? =
            try {
                FLAT!!.toDouble().toInt().toString()
            }
            catch (e: Exception)
            {
                FLAT
            }

    /**
     * the current flat field name (sqlite)
     * @author Chaosruler972
     * @return the current flat (sqlite)
     */
    fun get_FLOOR(): String? = try {
        FLOOR!!.toDouble().toInt().toString()
    }
    catch (e: Exception)
    {
        FLOOR
    }

    /**
     * the current qty field name (sqlite)
     * @author Chaosruler972
     * @return the current qty (sqlite)
     */
    fun get_QTY(): String? =
            try
            {
                QTY!!.toDouble().toInt().toString()
            }
            catch (e: Exception)
            {
                QTY
            }

    /**
     * the current qty field name (sqlite) in parital
     * @author Chaosruler972
     * @return the current qty (sqlite) in partial
     */
    fun get_QTYINPARTIALACC(): String? =
            try {
                QTYINPARTIALACC!!.toDouble().toInt().toString()
            }
            catch (e: Exception)
            {
                QTYINPARTIALACC
            }

    /**
     * the current sales price field name (sqlite)
     * @author Chaosruler972
     * @return the current sales price (sqlite)
     */
    fun get_SALESPRICE(): String? =
            try {
                SALESPRICE!!.toDouble().toInt().toString()
            } catch (e: Exception) {
                SALESPRICE
            }

    /**
     * the current operation id field name (sqlite)
     * @author Chaosruler972
     * @return the current operation id (sqlite)
     */
    fun get_OPRID(): String? = OPR_ID

    /**
     * the current milestone percent field name (sqlite)
     * @author Chaosruler972
     * @return the current milestone percent (sqlite)
     */
    fun get_MILESTONEPERCENT(): String? =
            try {
                MILESTONEPERCENT!!.toDouble().toInt().toString()
            }
            catch (e: Exception)
            {
                MILESTONEPERCENT
            }

    /**
     * the current quanity for account field name (sqlite)
     * @author Chaosruler972
     * @return the current quanity for account (sqlite)
     */
    fun get_QTYFORACCOUNT(): String? = try {
        QTYFORACCOUNT!!.toDouble().toInt().toString()
    }
    catch (e: Exception)
    {
        QTYFORACCOUNT
    }

    /**
     * the current percent for account field name (sqlite)
     * @author Chaosruler972
     * @return the current percent for account (sqlite)
     */
    fun get_PERCENTFORACCOUNT(): String? =
            try {
                PERCENTFORACCOUNT!!.toDouble().toInt().toString()
            } catch (e: Exception) {
                PERCENTFORACCOUNT
            }

    /**
     * the current total sum field name (sqlite)
     * @author Chaosruler972
     * @return the current total sum (sqlite)
     */
    fun get_TOTALSUM(): String? = try{
        TOTALSUM!!.toDouble().toInt().toString()
    }
    catch (e: Exception)
    {
        TOTALSUM
    }

    /**
     * the current sale progress field name (sqlite)
     * @author Chaosruler972
     * @return the current sale progress (sqlite)
     */
    fun get_SALPROG(): String? = SALPROG

    /**
     * the current print order field name (sqlite)
     * @author Chaosruler972
     * @return the current print order (sqlite)
     */
    fun get_PRINTORDER(): String? =
            try {
                PRINTORDER!!.toDouble().toInt().toString()
            } catch (e: Exception) {
                PRINTORDER
            }

    /**
     * the current item number field name (sqlite)
     * @author Chaosruler972
     * @return the current item number (sqlite)
     */
    fun get_ITEMNUMBER(): String? = try {
        ITEMNUMBER!!.toDouble().toInt().toString()
    }
    catch (e: Exception)
    {
        ITEMNUMBER
    }

    /**
     * the current koma num field name (sqlite)
     * @author Chaosruler972
     * @return the current koma num (sqlite)
     */
    fun get_KOMANUM(): String? =
            try {
                KOMANUM!!.toDouble().toInt().toString()
            } catch (e: Exception) {
                KOMANUM
            }

    /**
     * the current dira num field name (sqlite)
     * @author Chaosruler972
     * @return the current dira num (sqlite)
     */
    fun get_DIRANUM(): String? = if(DIRANUM == "קרקע")
        0.toString()
    else
        try {
            DIRANUM!!.toDouble().toInt().toString()
        }
        catch (e: Exception)
        {
            DIRANUM
        }


    /**
     * the current username that synced this data (sqlite)
     * @author Chaosruler972
     * @return the current user
     */
    fun get_USERNAME(): String? = USERNAME


    /**
     * Sets the current vendor id
     * @author Chaosruler972
     * @param string the new vendor id
     */
    fun set_VENDOR_ID(string: String)
    {
            VENDOR_ID = string.trim()
    }

    /**
     * Sets the dataaraeid
     * @author Chaosruler972
     * @param string the new dataaraeid
     */
    @Suppress("unused")
    fun set_DATAAREAID(string: String)
    {
        DATAAREAID = string.trim()
    }

    /**
     * Sets the current rec version
     * @author Chaosruler972
     * @param string the new rec version
     */
    fun set_RECVERSION(string: String)
    {
        RECVERSION = string.trim()
    }

    /**
     * Sets the current rec id
     * @author Chaosruler972
     * @param string the new rec id
     */
    fun set_RECID(string: String)
    {
        RECID = string.trim()
    }

    /**
     * Sets the current project id
     * @author Chaosruler972
     * @param string the new project id
     */
    @Suppress("unused")
    fun set_PROJECT_ID(string: String)
    {
        PROJECTS_ID = string.trim()
    }

    /**
     * Sets the current inventory id
     * @author Chaosruler972
     * @param string the new inventory id
     */
    fun set_INVENTORY_ID(string: String)
    {
        INVENTORY_ID = string.trim()
    }

    /**
     * Sets the current flat
     * @author Chaosruler972
     * @param string the new flat
     */
    fun set_FLAT(string: String)
    {
        FLAT=string.trim()
    }

    /**
     * Sets the current flat
     * @author Chaosruler972
     * @param string the new flat
     */
    fun set_FLOOR(string: String)
    {
        FLOOR = string.trim()
    }

    /**
     * Sets the current quanity
     * @author Chaosruler972
     * @param string the new quanity
     */
    fun set_QTY(string: String)
    {
        QTY = string.trim()
    }

    /**
     * Sets the quanity in partial
     * @author Chaosruler972
     * @param string the new quanity
     */
    fun set_QTYINPARTIALACC(string: String)
    {
        QTYINPARTIALACC = string.trim()
    }

    /**
     * Sets the current sales price
     * @author Chaosruler972
     * @param string the new sales price
     */
    fun set_SALESPRICE(string: String)
    {
        SALESPRICE = string.trim()
    }

    /**
     * Sets the current operation id
     * @author Chaosruler972
     * @param string the new operation id
     */
    fun set_OPRID(string: String)
    {
        OPR_ID = string.trim()
    }

    /**
     * Sets the current milestone percent
     * @author Chaosruler972
     * @param string the new milestone percent
     */
    fun set_MILESTONEPERCENT(string: String)
    {
        MILESTONEPERCENT = string.trim()
    }

    /**
     * Sets the current quanity for account
     * @author Chaosruler972
     * @param string the new quanity for account
     */
    fun set_QTYFORACCOUNT(string: String)
    {
        QTYFORACCOUNT = string.trim()
    }

    /**
     * Sets the current percent for account
     * @author Chaosruler972
     * @param string the new percent for account
     */
    fun set_PERCENTFORACCOUNT(string: String)
    {
        PERCENTFORACCOUNT = string.trim()
    }

    /**
     * Sets the current total sum
     * @author Chaosruler972
     * @param string the new total sum
     */
    fun set_TOTALSUM(string: String)
    {
        TOTALSUM = string.trim()
    }

    /**
     * Sets the current sale progress
     * @author Chaosruler972
     * @param string the new sale progress
     */
    fun set_SALPROG(string: String)
    {
        SALPROG = string.trim()
    }

    /**
     * Sets the current print order
     * @author Chaosruler972
     * @param string the new print order
     */
    fun set_PRINTORDER(string: String)
    {
        PRINTORDER = string.trim()
    }

    /**
     * Sets the current item number
     * @author Chaosruler972
     * @param string the new item number
     */
    fun set_ITEMNUMBER(string: String)
    {
        ITEMNUMBER = string.trim()
    }

    /**
     * Sets the current koma num
     * @author Chaosruler972
     * @param string the new koma num
     */
    fun set_KOMANUM(string: String)
    {
        KOMANUM = string.trim()
    }

    /**
     * Sets the current dira num
     * @author Chaosruler972
     * @param string the new dira num
     */
    fun set_DIRANUM(string: String)
    {
        DIRANUM = string.trim()
    }

    /**
     * Sets the current username that synced that data
     * @author Chaosruler972
     * @param string the new username
     */
    fun set_USERNAME(string: String)
    {
        USERNAME = string.trim()
    }

    /**
     * Identifies this data
     * @author Chaosruler972
     * @return a string to identify this data
     */
    override fun toString(): String
    {
        return "OPR: " + (OPR_ID?.toString()?:"").trim() + " Project: " + (PROJECTS_ID?.toString()?:"").trim() + " Vendor: " + (VENDOR_ID?.toString()?:"").trim() +" Inventory: " +(INVENTORY_ID?.toString()?:"").trim()
//        val opr = global_variables_dataclass.DB_OPR!!.get_opr_by_id((OPR_ID ?: "").trim())
//        val project = global_variables_dataclass.DB_project!!.get_project_by_id((PROJECTS_ID ?: "").trim())
//        val vendor = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id((VENDOR_ID ?: "").trim())
//        val item = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id((INVENTORY_ID ?: "").trim())
//        return "OPR: " + (opr?.toString()?:"").trim() + " Project: " + (project?.toString()?:"").trim() + " Vendor: " + (vendor?.toString()?:"").trim() +" Inventory: " +(item?.toString()?:"").trim()
    }

    /**
     * Compare function to sort big_table_data, sorts by komanum and than by diranum
     * @author Chaosruler972
     * @param other the big_table_data to compare to
     * @return 1 if I am "bigger" than other, -1 if I am smaller than other, 0 if I am exactly like the other (in koma\dira) or if those couldn't be parsed
     */
    override fun compareTo(other: big_table_data): Int
    {
        if(this.FLOOR == null || other.FLOOR == null)
            return 0
        try {
            if(this.get_FLOOR()!!.toInt() < other.get_FLOOR()!!.toInt())
                return -1
            else if(this.get_FLOOR()!!.toInt() > other.get_FLOOR()!!.toInt())
                return 1
        }
        catch (e: NumberFormatException)
        {
            global_variables_dataclass.log("Big_table_data", "Komanum is not a number!")
            return 0
        }
        if(this.DIRANUM==null || other.DIRANUM==null)
            return 0
        try {
            when {
                this.get_DIRANUM()!!.toInt() < other.get_DIRANUM()!!.toInt() -> return -1
                this.get_DIRANUM()!!.toInt() > other.get_DIRANUM()!!.toInt() -> return 1
//                this.DIRANUM!!.toInt() == other.DIRANUM!!.toInt() -> return 0
                else -> {
                }
            }
        }
        catch (e: NumberFormatException)
        {
            global_variables_dataclass.log("Big_table_data", "DIRANUM is not a number!")
            return 0
        }
        if (this.get_PRINTORDER() == null || other.get_PRINTORDER() == null)
            return 0
        try {
            when {
                this.get_PRINTORDER()!!.toInt() < other.get_PRINTORDER()!!.toInt() -> return -1
                this.get_PRINTORDER()!!.toInt() > other.get_PRINTORDER()!!.toInt() -> return 1
            }
        } catch (e: NumberFormatException) {
            global_variables_dataclass.log("Big_table_data", "PRINTORDER is not a number!")
            return 0
        }
        if (this.get_ITEMNUMBER() == null || other.get_ITEMNUMBER() == null)
            return 0
        try {
            when {
                this.get_ITEMNUMBER()!!.toInt() < other.get_ITEMNUMBER()!!.toInt() -> return -1
                this.get_ITEMNUMBER()!!.toInt() > other.get_ITEMNUMBER()!!.toInt() -> return 1
            }
        } catch (e: NumberFormatException) {
            global_variables_dataclass.log("Big_table_data", "ITEMNUMBER is not a number!")
            return 0
        }
        return 0
    }

    /**
     * Compute total SUM
     * @return total sum
     */
    fun totalSumCompute() : Double =(PERCENTFORACCOUNT?:"0").toDouble()*(QTYFORACCOUNT?:"0").toDouble()*(SALESPRICE?:"0").toDouble()*0.01


    /**
     * a copy constructor
     * @return a copy of this data class
     * @author Chaosruler972
     */
    override fun copy(): big_table_data = big_table_data(get_VENDOR_ID(), get_DATAAREAID(), get_RECVERSION(), get_RECID(), get_PROJECT_ID(), get_INVENTORY_ID(), get_FLAT(), get_FLOOR(), get_QTY(), get_SALESPRICE(), get_OPRID(), get_MILESTONEPERCENT(), get_QTYFORACCOUNT(), get_PERCENTFORACCOUNT(), get_TOTALSUM(), get_SALPROG(), get_PRINTORDER(), get_ITEMNUMBER(), get_KOMANUM(), get_DIRANUM(), get_USERNAME(), get_QTYINPARTIALACC())

    override fun to_hashmap(): HashMap<String, String> {
        val map =  HashMap<String, String>()
        map[remote_big_table_helper.VENDOR_ID] = get_VENDOR_ID() ?: ""
        map[remote_big_table_helper.FLAT] = get_FLAT() ?: ""
        map[remote_big_table_helper.INVENTORY_ID] = get_INVENTORY_ID() ?: ""
        map[remote_big_table_helper.PROJECTS_ID] = get_PROJECT_ID() ?: ""
        map[remote_big_table_helper.RECID] = get_RECID() ?: ""
        map[remote_big_table_helper.RECVERSION] = get_RECVERSION() ?: ""
        map[remote_big_table_helper.DATAREAID] = get_DATAAREAID() ?: ""
        map[remote_big_table_helper.FLOOR] = get_FLOOR() ?: ""
        map[remote_big_table_helper.DIRANUM] = get_DIRANUM() ?: ""
        map[remote_big_table_helper.KOMANUM] = get_KOMANUM() ?: ""
        map[remote_big_table_helper.SALPROG] = get_SALPROG() ?: ""
        map[remote_big_table_helper.SALESPRICE] = get_SALESPRICE() ?: ""
        map[remote_big_table_helper.TOTALSUM] = get_TOTALSUM() ?: ""
//        map[remote_big_table_helper.QTYINPARTIALACC] = get_QTYINPARTIALACC()?:""
        map[remote_big_table_helper.MILESTONEPERCENT] = get_MILESTONEPERCENT() ?: ""
        map[remote_big_table_helper.QTYFORACCOUNT] = get_QTYFORACCOUNT() ?: ""
        map[remote_big_table_helper.ITEMNUMBER] = get_ITEMNUMBER() ?: ""
        map[remote_big_table_helper.PERCENTFORACCOUNT] = get_PERCENTFORACCOUNT() ?: ""
        map[remote_big_table_helper.QTY] = get_QTY() ?: ""
        map[remote_big_table_helper.OPR_ID] = get_OPRID() ?: ""
        map[remote_big_table_helper.PRINTORDER] = get_PRINTORDER() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.ACCOUNT_NUM]!!] = get_VENDOR_ID() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.FLAT]!!] = get_FLAT() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.ITEMID]!!] = get_INVENTORY_ID() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.PROJID]!!] = get_PROJECT_ID() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.RECID]!!] = get_RECID() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.RECVERSION]!!] = get_RECVERSION() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.DATAARAEID]!!] = get_DATAAREAID() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.FLOOR]!!] = get_FLOOR() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.DIRANUM]!!] = get_DIRANUM() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.KOMANUM]!!] = get_KOMANUM() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.SALPROG]!!] = get_SALPROG() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.SALESPRICE]!!] = get_SALESPRICE() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.PERCENTFORACCOUNT]!!] = get_PERCENTFORACCOUNT() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.QTY]!!] = get_QTY() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.TOTAL_SUM]!!] = get_TOTALSUM() ?: ""
//        map[global_variables_dataclass.DB_BIG!!.] = get_QTYINPARTIALACC()?:""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.MILESTONEPERCENTAGE]!!] = get_MILESTONEPERCENT() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.QTYFORACCOUNT]!!] = get_QTYFORACCOUNT() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.ITEMNUMBER]!!] = get_ITEMNUMBER() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.OPR_ID]!!] = get_OPRID() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.PRINTORDER]!!] = get_PRINTORDER() ?: ""
        map[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.USER]!!] = get_USERNAME() ?: ""
        return map
    }

    override fun toUserName(): String
    {
        return get_USERNAME()!!
    }

}