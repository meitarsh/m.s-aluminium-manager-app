package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation


class big_table_data(private var VENDOR_ID:String?,private var DATAAREAID:String?,private var RECVERSION:String?,private var RECID:String?
                     ,private var PROJECTS_ID:String?,private var INVENTORY_ID:String?,private var FLAT:String?
                     , private var FLOOR:String?, private var QTY:String? , private var SALESPRICE:String?,
                     private var OPR_ID:String?,private var MILESTONEPERCENT:String?,private var QTYFORACCOUNT:String?,
                     private var PERCENTFORACCOUNT:String?,private var TOTALSUM:String?,private var SALPROG:String?,private var PRINTORDER:String?,
                     private var ITEMNUMBER:String?,private var KOMANUM:String? ,private var DIRANUM:String?,private var USERNAME:String?)
{
    init
    {
        if(VENDOR_ID!=null)
            VENDOR_ID=VENDOR_ID!!.trim()
        if(DATAAREAID!=null)
            DATAAREAID=DATAAREAID!!.trim()
        if(RECVERSION!=null)
            RECVERSION=RECVERSION!!.trim()
        if(RECID!=null)
            RECID=RECID!!.trim()
        if(PROJECTS_ID!=null)
            PROJECTS_ID=PROJECTS_ID!!.trim()
        if(INVENTORY_ID!=null)
            INVENTORY_ID=INVENTORY_ID!!.trim()
        if(FLAT!=null)
            FLAT = FLAT!!.trim()
        if(FLOOR!=null)
            FLOOR!=FLOOR!!.trim()
        if(QTY!=null)
            QTY = QTY!!.trim()
        if(SALESPRICE!=null)
            SALESPRICE = SALESPRICE!!.trim()
        if(OPR_ID!=null)
            OPR_ID = OPR_ID!!.trim()
        if(MILESTONEPERCENT!=null)
            MILESTONEPERCENT = MILESTONEPERCENT!!.trim()
        if(QTYFORACCOUNT!=null)
            QTYFORACCOUNT = QTYFORACCOUNT!!.trim()
        if(PERCENTFORACCOUNT!=null)
            PERCENTFORACCOUNT = PERCENTFORACCOUNT!!.trim()
        if(TOTALSUM != null)
            TOTALSUM = TOTALSUM!!.trim()
        if(SALPROG != null)
            SALPROG = SALPROG!!.trim()
        if(PRINTORDER != null)
            PRINTORDER = PRINTORDER!!.trim()
        if(ITEMNUMBER != null)
            ITEMNUMBER = ITEMNUMBER!!.trim()
        if(KOMANUM != null)
            KOMANUM = KOMANUM!!.trim()
        if(DIRANUM != null)
            DIRANUM = DIRANUM!!.trim()
        if(USERNAME != null)
            USERNAME = USERNAME!!.trim()
    }
    public fun get_VENDOR_ID():String? = VENDOR_ID
    public fun get_DATAAREAID():String? = DATAAREAID
    public fun get_RECVERSION():String? = RECVERSION
    public fun get_RECID():String? = RECID
    public fun get_PROJECT_ID():String? = PROJECTS_ID
    public fun get_INVENTORY_ID():String? = INVENTORY_ID
    public fun get_FLAT():String? = FLAT
    public fun get_FLOOR():String? = FLOOR
    public fun get_QTY():String? = QTY
    public fun get_SALESPRICE():String? = SALESPRICE
    public fun get_OPRID():String? = OPR_ID
    public fun get_MILESTONEPERCENT():String? = MILESTONEPERCENT
    public fun get_QTYFORACCOUNT():String? = QTYFORACCOUNT
    public fun get_PERCENTFORACCOUNT():String? = PERCENTFORACCOUNT
    public fun get_TOTALSUM():String? = TOTALSUM
    public fun get_SALPROG():String? = SALPROG
    public fun get_PRINTORDER():String? = PRINTORDER
    public fun get_ITEMNUMBER():String? = ITEMNUMBER
    public fun get_KOMANUM():String? = KOMANUM
    public fun get_DIRANUM():String? = DIRANUM
    public fun get_USERNAME():String? = USERNAME

    public fun set_VENDOR_ID(string: String)
    {
            VENDOR_ID = string
    }
    public fun set_DATAAREAID(string: String)
    {
        DATAAREAID = string
    }
    public fun set_RECVERSION(string: String)
    {
        RECVERSION = string
    }
    public fun set_RECID(string: String)
    {
        RECID = string
    }
    public fun set_PROJECT_ID(string: String)
    {
        PROJECTS_ID = string
    }
    public fun set_INVENTORY_ID(string: String)
    {
        INVENTORY_ID = string
    }
    public fun set_FLAT(string: String)
    {
        FLAT=string
    }
    public fun set_FLOOR(string: String)
    {
        FLOOR = string
    }
    public fun set_QTY(string: String)
    {
        QTY = string
    }
    public fun set_SALESPRICE(string: String)
    {
        SALESPRICE = string
    }
    public fun set_OPRID(string: String)
    {
        OPR_ID = string
    }
    public fun set_MILESTONEPERCENT(string: String)
    {
        MILESTONEPERCENT = string
    }
    public fun set_QTYFORACCOUNT(string: String)
    {
        QTYFORACCOUNT = string
    }
    public fun set_PERCENTFORACCOUNT(string: String)
    {
        PERCENTFORACCOUNT = string
    }
    public fun set_TOTALSUM(string: String)
    {
        TOTALSUM = string
    }
    public fun set_SALPROG(string: String)
    {
        SALPROG = string
    }
    public fun set_PRINTORDER(string: String)
    {
        PRINTORDER = string
    }
    public fun set_ITEMNUMBER(string: String)
    {
        ITEMNUMBER = string
    }
    public fun set_KOMANUM(string: String)
    {
        KOMANUM = string
    }
    public fun set_DIRANUM(string: String)
    {
        DIRANUM = string
    }
    public fun set_USERNAME(string: String)
    {
        USERNAME = string
    }

    override fun toString(): String = get_VENDOR_ID()?:"" + " " + get_PROJECT_ID() ?:"" + " " + get_OPRID() + get_INVENTORY_ID()

    public fun copy():big_table_data = big_table_data(get_VENDOR_ID(),get_DATAAREAID(),get_RECVERSION(),get_RECID(),get_PROJECT_ID(),get_INVENTORY_ID(),get_FLAT(),get_FLOOR(),get_QTY(),get_SALESPRICE(),get_OPRID(),get_MILESTONEPERCENT(),get_QTYFORACCOUNT(),get_PERCENTFORACCOUNT(),get_TOTALSUM(),get_SALPROG(),get_PRINTORDER(),get_ITEMNUMBER(),get_KOMANUM(),get_DIRANUM(),get_USERNAME())
}
