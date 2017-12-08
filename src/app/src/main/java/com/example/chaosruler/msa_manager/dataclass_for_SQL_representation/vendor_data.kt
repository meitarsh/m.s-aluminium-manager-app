package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


class vendor_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
    : table_dataclass {
    init
    {
        if(ID!=null)
            ID=(ID?:"").trim()
        if(NAME!=null)
            NAME=(NAME?:"").trim()
        if(DATAAREAID!=null)
            DATAAREAID=(DATAAREAID?:"").trim()
        if(USERNAME!=null)
            USERNAME=(USERNAME?:"").trim()
    }
    /*
       get account num
    */
    fun get_accountnum():String? = this.ID
    /*
           get account name
        */
    fun get_accountname():String? = this.NAME
    /*
           get dataaraid
        */
    fun get_DATAREAID():String? = this.DATAAREAID
    /*
           get username
        */
    fun get_USERNAME():String? = this.USERNAME

    /*
      set account num
   */
    fun set_accountnum(accountnum:String)
    {
        this.ID = accountnum.trim()
    }
    /*
       set account name
    */
    fun set_accountname(name:String)
    {
        this.NAME = name.trim()
    }
    /*
       set dataaraid
    */
    fun set_dataareaid(new_dataareaid:String)
    {
        this.DATAAREAID = new_dataareaid.trim()
    }
    /*
       set username
    */
    fun set_username(new_username:String)
    {
        this.USERNAME = new_username.trim()
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