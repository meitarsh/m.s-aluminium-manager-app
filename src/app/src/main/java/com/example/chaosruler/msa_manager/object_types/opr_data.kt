package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


class opr_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
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
        get oprid
     */
    fun get_oprid():String? = this.ID
    /*
           get opr name
        */
    fun get_opr_name():String? = this.NAME
    /*
           get dataaraid
        */
    fun get_DATAREAID():String? = this.DATAAREAID
    /*
           get username
        */
    fun get_USERNAME():String? = this.USERNAME

    /*
     set oprid
  */
    fun set_oprid(itemid:String)
    {
        this.ID = itemid.trim()
    }
    /*
         set oprname
      */
    fun set_oprname(name:String)
    {
        this.NAME = name.trim()
    }
    /*
         set dataraaid
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
    override fun toString(): String = get_opr_name() ?: ""
    /*
    makes a copy of
     */
    override fun copy(): opr_data = opr_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)
}