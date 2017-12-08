package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


class vendor_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
    : table_dataclass {
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
    /*
    getters
     */
    fun get_accountnum():String? = this.ID

    fun get_accountname():String? = this.NAME

    fun get_DATAREAID():String? = this.DATAAREAID

    fun get_USERNAME():String? = this.USERNAME

    /*
        setters
     */
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

    /*
        identifies
     */
    override fun toString(): String = get_accountname() ?: ""
    /*
        makes copy of
     */
    override fun copy(): vendor_data = vendor_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)
}