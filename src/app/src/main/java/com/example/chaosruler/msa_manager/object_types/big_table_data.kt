package com.example.chaosruler.msa_manager.object_types

import android.util.Log
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass

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
        private var USERNAME:String?
)
    : table_dataclass, Comparable<big_table_data> {
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
    fun get_FLAT(): String? = FLAT

    /**
     * the current flat field name (sqlite)
     * @author Chaosruler972
     * @return the current flat (sqlite)
     */
    fun get_FLOOR(): String? = FLOOR

    /**
     * the current qty field name (sqlite)
     * @author Chaosruler972
     * @return the current qty (sqlite)
     */
    fun get_QTY(): String? = QTY

    /**
     * the current sales price field name (sqlite)
     * @author Chaosruler972
     * @return the current sales price (sqlite)
     */
    fun get_SALESPRICE(): String? = SALESPRICE

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
    fun get_MILESTONEPERCENT(): String? = MILESTONEPERCENT

    /**
     * the current quanity for account field name (sqlite)
     * @author Chaosruler972
     * @return the current quanity for account (sqlite)
     */
    fun get_QTYFORACCOUNT(): String? = QTYFORACCOUNT

    /**
     * the current percent for account field name (sqlite)
     * @author Chaosruler972
     * @return the current percent for account (sqlite)
     */
    fun get_PERCENTFORACCOUNT(): String? = PERCENTFORACCOUNT

    /**
     * the current total sum field name (sqlite)
     * @author Chaosruler972
     * @return the current total sum (sqlite)
     */
    fun get_TOTALSUM(): String? = TOTALSUM

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
    fun get_PRINTORDER(): String? = PRINTORDER

    /**
     * the current item number field name (sqlite)
     * @author Chaosruler972
     * @return the current item number (sqlite)
     */
    fun get_ITEMNUMBER(): String? = ITEMNUMBER

    /**
     * the current koma num field name (sqlite)
     * @author Chaosruler972
     * @return the current koma num (sqlite)
     */
    fun get_KOMANUM(): String? = KOMANUM

    /**
     * the current dira num field name (sqlite)
     * @author Chaosruler972
     * @return the current dira num (sqlite)
     */
    fun get_DIRANUM(): String? = DIRANUM

    /**
     * the current username that synced this data (sqlite)
     * @author Chaosruler972
     * @return the current user
     */
    fun get_USERNAME(): String? = USERNAME

    @Suppress("unused")

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
    @Suppress("unused")
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
    @Suppress("unused")
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
    @Suppress("unused")
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
        val opr = global_variables_dataclass.DB_OPR!!.get_opr_by_id((OPR_ID ?: "").trim())
        val project = global_variables_dataclass.DB_project!!.get_project_by_id((PROJECTS_ID ?: "").trim())
        val vendor = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id((VENDOR_ID ?: "").trim())
        val item = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id((INVENTORY_ID ?: "").trim())
        return "OPR: " + (opr?.toString()?:"").trim() + " Project: " + (project?.toString()?:"").trim() + " Vendor: " + (vendor?.toString()?:"").trim() +" Inventory: " +(item?.toString()?:"").trim()
    }

    /**
     * Compare function to sort big_table_data, sorts by komanum and than by diranum
     * @author Chaosruler972
     * @param other the big_table_data to compare to
     * @return 1 if I am "bigger" than other, -1 if I am smaller than other, 0 if I am exactly like the other (in koma\dira) or if those couldn't be parsed
     */
    override fun compareTo(other: big_table_data): Int
    {
        if(this.KOMANUM == null || other.KOMANUM == null)
            return 0
        try {
            if(this.KOMANUM!!.toInt() < other.KOMANUM!!.toInt())
                return -1
            else if(this.KOMANUM!!.toInt() > other.KOMANUM!!.toInt())
                return 1
        }
        catch (e: NumberFormatException)
        {
            Log.d("Big_table_data","Komanum is not a number!")
            return 0
        }
        if(this.DIRANUM==null || other.DIRANUM==null)
            return 0
        try {
            when {
                this.DIRANUM!!.toInt() < other.DIRANUM!!.toInt() -> return -1
                this.DIRANUM!!.toInt() > other.DIRANUM!!.toInt() -> return 1
//                this.DIRANUM!!.toInt() == other.DIRANUM!!.toInt() -> return 0
                else -> {
                }
            }
        }
        catch (e: NumberFormatException)
        {
            Log.d("Big_table_data","DIRANUM is not a number!")
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
            Log.d("Big_table_data", "PRINTORDER is not a number!")
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
            Log.d("Big_table_data", "ITEMNUMBER is not a number!")
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
    override fun copy(): big_table_data = big_table_data(get_VENDOR_ID(), get_DATAAREAID(), get_RECVERSION(), get_RECID(), get_PROJECT_ID(), get_INVENTORY_ID(), get_FLAT(), get_FLOOR(), get_QTY(), get_SALESPRICE(), get_OPRID(), get_MILESTONEPERCENT(), get_QTYFORACCOUNT(), get_PERCENTFORACCOUNT(), get_TOTALSUM(), get_SALPROG(), get_PRINTORDER(), get_ITEMNUMBER(), get_KOMANUM(), get_DIRANUM(), get_USERNAME())
}
