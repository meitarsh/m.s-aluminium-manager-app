package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


class inventory_data(private var ID:String?,private var NAME:String?,private var DATAAREAID:String?,private var USERNAME:String?)
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
        get itemid
     */
    fun get_itemid():String? = this.ID
    /*
           get itemname
        */
    fun get_itemname():String? = this.NAME
    /*
       get dataraid
    */
    fun get_DATAREAID():String? = this.DATAAREAID
    /*
           get username
        */
    fun get_USERNAME():String? = this.USERNAME

    /*
         set itemid
      */
    fun set_itemid(itemid:String)
    {
        this.ID = itemid.trim()
    }
    /*
         set itemname
      */
    fun set_itemname(name:String)
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
    override fun toString(): String = get_itemname() ?: ""
    /*
        copies
     */
    override fun copy(): inventory_data = inventory_data(this.ID, this.NAME, this.DATAAREAID, this.USERNAME)
}