package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation

import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_inventory_table_helper
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
    public fun get_VENDOR_ID():String? = VENDOR_ID
    /*
      vendor dataraid
   */
    public fun get_DATAAREAID():String? = DATAAREAID
    /*
      vendor recversion
   */
    public fun get_RECVERSION():String? = RECVERSION
    /*
      vendor recid
   */
    public fun get_RECID():String? = RECID
    /*
      vendor projectid
   */
    public fun get_PROJECT_ID():String? = PROJECTS_ID
    /*
      vendor inventory id
   */
    public fun get_INVENTORY_ID():String? = INVENTORY_ID
    /*
      vendor flat
   */
    public fun get_FLAT():String? = FLAT
    /*
      vendor floor
   */
    public fun get_FLOOR():String? = FLOOR
    /*
      vendor qty
   */
    public fun get_QTY():String? = QTY
    /*
      vendor salesprice
   */
    public fun get_SALESPRICE():String? = SALESPRICE
    /*
      vendor oprid
   */
    public fun get_OPRID():String? = OPR_ID
    /*
      vendor milestone_percent
   */
    public fun get_MILESTONEPERCENT():String? = MILESTONEPERCENT
    /*
      vendor qtyforaccount
   */
    public fun get_QTYFORACCOUNT():String? = QTYFORACCOUNT
    /*
      vendor percentforaccount
   */
    public fun get_PERCENTFORACCOUNT():String? = PERCENTFORACCOUNT
    /*
      vendor totalsum
   */
    public fun get_TOTALSUM():String? = TOTALSUM
    /*
      vendor salprog
   */
    public fun get_SALPROG():String? = SALPROG
    /*
      vendor printorder
   */
    public fun get_PRINTORDER():String? = PRINTORDER
    /*
      vendor itemnumber
   */
    public fun get_ITEMNUMBER():String? = ITEMNUMBER
    /*
      vendor komanum
   */
    public fun get_KOMANUM():String? = KOMANUM
    /*
      vendor diranum
   */
    public fun get_DIRANUM():String? = DIRANUM
    /*
      vendor get username
   */
    public fun get_USERNAME():String? = USERNAME

    /*
      set vendorid
   */
    public fun set_VENDOR_ID(string: String)
    {
            VENDOR_ID = string.trim()
    }
    /*
    set dataraid
 */
    public fun set_DATAAREAID(string: String)
    {
        DATAAREAID = string.trim()
    }
    /*
    set recversion
 */
    public fun set_RECVERSION(string: String)
    {
        RECVERSION = string.trim()
    }
    /*
    set recid
 */
    public fun set_RECID(string: String)
    {
        RECID = string.trim()
    }
    /*
    set project id
 */
    public fun set_PROJECT_ID(string: String)
    {
        PROJECTS_ID = string.trim()
    }
    /*
    set inventory id
 */
    public fun set_INVENTORY_ID(string: String)
    {
        INVENTORY_ID = string.trim()
    }
    /*
    set flat
 */
    public fun set_FLAT(string: String)
    {
        FLAT=string.trim()
    }
    /*
    set floor
 */
    public fun set_FLOOR(string: String)
    {
        FLOOR = string.trim()
    }
    /*
    set qty
 */
    public fun set_QTY(string: String)
    {
        QTY = string.trim()
    }
    /*
    set salesprice
 */
    public fun set_SALESPRICE(string: String)
    {
        SALESPRICE = string.trim()
    }
    /*
    set oprid
 */
    public fun set_OPRID(string: String)
    {
        OPR_ID = string.trim()
    }
    /*
    set milestone percent
 */
    public fun set_MILESTONEPERCENT(string: String)
    {
        MILESTONEPERCENT = string.trim()
    }
    /*
    set qtyforaccount
 */
    public fun set_QTYFORACCOUNT(string: String)
    {
        QTYFORACCOUNT = string.trim()
    }
    /*
    set percentforaccount
 */
    public fun set_PERCENTFORACCOUNT(string: String)
    {
        PERCENTFORACCOUNT = string.trim()
    }
    /*
    set totalsum
 */
    public fun set_TOTALSUM(string: String)
    {
        TOTALSUM = string.trim()
    }
    /*
    set salprog
 */
    public fun set_SALPROG(string: String)
    {
        SALPROG = string.trim()
    }
    /*
    set printorder
 */
    public fun set_PRINTORDER(string: String)
    {
        PRINTORDER = string.trim()
    }
    /*
    set itemnumber
 */
    public fun set_ITEMNUMBER(string: String)
    {
        ITEMNUMBER = string.trim()
    }
    /*
    set komanum
 */
    public fun set_KOMANUM(string: String)
    {
        KOMANUM = string.trim()
    }
    /*
    set diranum
 */
    public fun set_DIRANUM(string: String)
    {
        DIRANUM = string.trim()
    }
    /*
    set username
 */
    public fun set_USERNAME(string: String)
    {
        USERNAME = string.trim()
    }

    /*
        identifies
     */
    override fun toString(): String
    {
        var opr = global_variables_dataclass.DB_OPR!!.get_opr_by_id((OPR_ID?:"").trim())
        var project = global_variables_dataclass.DB_project!!.get_project_by_id((PROJECTS_ID?:"").trim())
        var vendor = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id((VENDOR_ID?:"").trim())
        var item = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id((INVENTORY_ID?:"").trim())
        return "OPR: " + (opr?.toString()?:"").trim() + " Project: " + (project?.toString()?:"").trim() + " Vendor: " + (vendor?.toString()?:"").trim() +" Inventory: " +(item?.toString()?:"").trim()
    }

    /*
        copies
     */
    override public fun copy():big_table_data = big_table_data(get_VENDOR_ID(),get_DATAAREAID(),get_RECVERSION(),get_RECID(),get_PROJECT_ID(),get_INVENTORY_ID(),get_FLAT(),get_FLOOR(),get_QTY(),get_SALESPRICE(),get_OPRID(),get_MILESTONEPERCENT(),get_QTYFORACCOUNT(),get_PERCENTFORACCOUNT(),get_TOTALSUM(),get_SALPROG(),get_PRINTORDER(),get_ITEMNUMBER(),get_KOMANUM(),get_DIRANUM(),get_USERNAME())
}
