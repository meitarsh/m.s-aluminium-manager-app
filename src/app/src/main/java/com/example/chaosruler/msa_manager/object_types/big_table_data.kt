package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass


class big_table_data(private var VENDOR_ID:String?,private var DATAAREAID:String?,private var RECVERSION:String?,private var RECID:String?
                     ,private var PROJECTS_ID:String?,private var INVENTORY_ID:String?,private var FLAT:String?
                     , private var FLOOR:String?, private var QTY:String? , private var SALESPRICE:String?,
                     private var OPR_ID:String?,private var MILESTONEPERCENT:String?,private var QTYFORACCOUNT:String?,
                     private var PERCENTFORACCOUNT:String?,private var TOTALSUM:String?,private var SALPROG:String?,private var PRINTORDER:String?,
                     private var ITEMNUMBER:String?,private var KOMANUM:String? ,private var DIRANUM:String?,private var USERNAME:String?)
    : table_dataclass {
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

    /*
        vendor id
     */
    fun get_VENDOR_ID(): String? = VENDOR_ID

    /*
      vendor dataraid
   */
    fun get_DATAAREAID(): String? = DATAAREAID

    /*
      vendor recversion
   */
    fun get_RECVERSION(): String? = RECVERSION

    /*
      vendor recid
   */
    fun get_RECID(): String? = RECID

    /*
      vendor projectid
   */
    fun get_PROJECT_ID(): String? = PROJECTS_ID

    /*
      vendor inventory id
   */
    fun get_INVENTORY_ID(): String? = INVENTORY_ID

    /*
      vendor flat
   */
    fun get_FLAT(): String? = FLAT

    /*
      vendor floor
   */
    fun get_FLOOR(): String? = FLOOR

    /*
      vendor qty
   */
    fun get_QTY(): String? = QTY

    /*
      vendor salesprice
   */
    fun get_SALESPRICE(): String? = SALESPRICE

    /*
      vendor oprid
   */
    fun get_OPRID(): String? = OPR_ID

    /*
      vendor milestone_percent
   */
    fun get_MILESTONEPERCENT(): String? = MILESTONEPERCENT

    /*
      vendor qtyforaccount
   */
    fun get_QTYFORACCOUNT(): String? = QTYFORACCOUNT

    /*
      vendor percentforaccount
   */
    fun get_PERCENTFORACCOUNT(): String? = PERCENTFORACCOUNT

    /*
      vendor totalsum
   */
    fun get_TOTALSUM(): String? = TOTALSUM

    /*
      vendor salprog
   */
    fun get_SALPROG(): String? = SALPROG

    /*
      vendor printorder
   */
    fun get_PRINTORDER(): String? = PRINTORDER

    /*
      vendor itemnumber
   */
    fun get_ITEMNUMBER(): String? = ITEMNUMBER

    /*
      vendor komanum
   */
    fun get_KOMANUM(): String? = KOMANUM

    /*
      vendor diranum
   */
    fun get_DIRANUM(): String? = DIRANUM

    /*
      vendor get username
   */
    fun get_USERNAME(): String? = USERNAME

    @Suppress("unused")
/*
      set vendorid
   */
    fun set_VENDOR_ID(string: String)
    {
            VENDOR_ID = string.trim()
    }

    @Suppress("unused")
/*
    set dataraid
 */
    fun set_DATAAREAID(string: String)
    {
        DATAAREAID = string.trim()
    }

    /*
    set recversion
 */
    fun set_RECVERSION(string: String)
    {
        RECVERSION = string.trim()
    }

    /*
    set recid
 */
    fun set_RECID(string: String)
    {
        RECID = string.trim()
    }

    @Suppress("unused")
/*
    set project id
 */
    fun set_PROJECT_ID(string: String)
    {
        PROJECTS_ID = string.trim()
    }

    @Suppress("unused")
/*
    set inventory id
 */
    fun set_INVENTORY_ID(string: String)
    {
        INVENTORY_ID = string.trim()
    }

    /*
    set flat
 */
    fun set_FLAT(string: String)
    {
        FLAT=string.trim()
    }

    /*
    set floor
 */
    fun set_FLOOR(string: String)
    {
        FLOOR = string.trim()
    }

    /*
    set qty
 */
    fun set_QTY(string: String)
    {
        QTY = string.trim()
    }

    /*
    set salesprice
 */
    fun set_SALESPRICE(string: String)
    {
        SALESPRICE = string.trim()
    }

    @Suppress("unused")
/*
    set oprid
 */
    fun set_OPRID(string: String)
    {
        OPR_ID = string.trim()
    }

    /*
    set milestone percent
 */
    fun set_MILESTONEPERCENT(string: String)
    {
        MILESTONEPERCENT = string.trim()
    }

    /*
    set qtyforaccount
 */
    fun set_QTYFORACCOUNT(string: String)
    {
        QTYFORACCOUNT = string.trim()
    }

    /*
    set percentforaccount
 */
    fun set_PERCENTFORACCOUNT(string: String)
    {
        PERCENTFORACCOUNT = string.trim()
    }

    /*
    set totalsum
 */
    fun set_TOTALSUM(string: String)
    {
        TOTALSUM = string.trim()
    }

    /*
    set salprog
 */
    fun set_SALPROG(string: String)
    {
        SALPROG = string.trim()
    }

    /*
    set printorder
 */
    fun set_PRINTORDER(string: String)
    {
        PRINTORDER = string.trim()
    }

    /*
    set itemnumber
 */
    fun set_ITEMNUMBER(string: String)
    {
        ITEMNUMBER = string.trim()
    }

    /*
    set komanum
 */
    fun set_KOMANUM(string: String)
    {
        KOMANUM = string.trim()
    }

    /*
    set diranum
 */
    fun set_DIRANUM(string: String)
    {
        DIRANUM = string.trim()
    }

    @Suppress("unused")
/*
    set username
 */
    fun set_USERNAME(string: String)
    {
        USERNAME = string.trim()
    }

    /*
        identifies
     */
    override fun toString(): String
    {
        val opr = global_variables_dataclass.DB_OPR!!.get_opr_by_id((OPR_ID ?: "").trim())
        val project = global_variables_dataclass.DB_project!!.get_project_by_id((PROJECTS_ID ?: "").trim())
        val vendor = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id((VENDOR_ID ?: "").trim())
        val item = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id((INVENTORY_ID ?: "").trim())
        return "OPR: " + (opr?.toString()?:"").trim() + " Project: " + (project?.toString()?:"").trim() + " Vendor: " + (vendor?.toString()?:"").trim() +" Inventory: " +(item?.toString()?:"").trim()
    }

    /*
        copies
     */
    override fun copy(): big_table_data = big_table_data(get_VENDOR_ID(), get_DATAAREAID(), get_RECVERSION(), get_RECID(), get_PROJECT_ID(), get_INVENTORY_ID(), get_FLAT(), get_FLOOR(), get_QTY(), get_SALESPRICE(), get_OPRID(), get_MILESTONEPERCENT(), get_QTYFORACCOUNT(), get_PERCENTFORACCOUNT(), get_TOTALSUM(), get_SALPROG(), get_PRINTORDER(), get_ITEMNUMBER(), get_KOMANUM(), get_DIRANUM(), get_USERNAME())
}
