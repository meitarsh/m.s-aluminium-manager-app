package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


class project_data(private var projectID:String?,private var project_name:String?,private var DATAAREAID:String?,private var USERNAME:String?)
    : table_dataclass {
    init
    {
        if(projectID!=null)
            projectID=(projectID?:"").trim()
        if(project_name!=null)
            project_name=(project_name?:"").trim()
        if(DATAAREAID!=null)
            DATAAREAID=(DATAAREAID?:"").trim()
        if(USERNAME!=null)
            USERNAME=(USERNAME?:"").trim()
    }
    /*
       get projid
    */
    fun getProjID():String? = this.projectID
    /*
           get project name
        */
    fun get_project_name():String? = this.project_name
    /*
           get dataaraid
        */
    fun get_DATAREAID():String? = this.DATAAREAID
    /*
           get username
        */
    fun get_USERNAME():String? = this.USERNAME

    /*
      set projid
   */
    fun set_projid(new_projid:String)
    {
        this.projectID = new_projid.trim()
    }
    /*
         set projname
      */
    fun set_project_name(new_projname:String)
    {
        this.project_name = new_projname.trim()
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
    override fun toString(): String = this.project_name ?: ""
    /*
        makes copy of
     */
    override fun copy(): project_data = project_data(this.projectID, this.project_name, this.DATAAREAID, this.USERNAME)
}