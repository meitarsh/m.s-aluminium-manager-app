package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation


class opr_data(private var ID:String,private var NAME:String,private var DATAAREAID:String,private var USERNAME:String)
{
    fun get_oprid() = this.ID

    fun get_opr_name() = this.NAME

    fun get_DATAREAID() = this.DATAAREAID

    fun get_USERNAME() = this.USERNAME

    fun set_oprid(itemid:String)
    {
        this.ID = itemid
    }

    fun set_oprname(name:String)
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

    fun copy(): opr_data = opr_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)
}