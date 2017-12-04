package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation


class vendor_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
{
    fun get_accountnum():String? = this.ID

    fun get_accountname():String? = this.NAME

    fun get_DATAREAID():String? = this.DATAAREAID

    fun get_USERNAME():String? = this.USERNAME

    fun set_accountnum(accountnum:String)
    {
        this.ID = accountnum
    }

    fun set_accountname(name:String)
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

    fun copy(): vendor_data = vendor_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)
}