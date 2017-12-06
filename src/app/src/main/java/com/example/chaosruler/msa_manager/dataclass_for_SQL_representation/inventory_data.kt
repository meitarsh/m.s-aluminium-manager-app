package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation


class inventory_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
{

    init
    {
        if(ID!=null)
            ID=ID!!.trim()
        if(NAME!=null)
            NAME=NAME!!.trim()
        if(DATAAREAID!=null)
            DATAAREAID=DATAAREAID!!.trim()
        if(USERNAME!=null)
            USERNAME=USERNAME!!.trim()
    }
    fun get_itemid():String? = this.ID

    fun get_itemname():String? = this.NAME

    fun get_DATAREAID():String? = this.DATAAREAID

    fun get_USERNAME():String? = this.USERNAME

    fun set_itemid(itemid:String)
    {
        this.ID = itemid
    }

    fun set_itemname(name:String)
    {
        this.NAME = name
    }

    fun set_dataareaid(new_dataareaid:String)
    {
        this.DATAAREAID = new_dataareaid
    }

    fun set_username(new_username:String)
    {
        this.USERNAME = new_username
    }

    fun copy(): inventory_data = inventory_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)
}