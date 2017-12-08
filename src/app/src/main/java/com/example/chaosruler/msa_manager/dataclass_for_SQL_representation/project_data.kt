package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass


class project_data(private var projectID:String?,private var project_name:String?,private var DATAAREAID:String?,private var USERNAME:String?)
    : table_dataclass {
    init
    {
        if(projectID!=null)
            projectID=projectID!!.trim()
        if(project_name!=null)
            project_name=project_name!!.trim()
        if(DATAAREAID!=null)
            DATAAREAID=DATAAREAID!!.trim()
        if(USERNAME!=null)
            USERNAME=USERNAME!!.trim()
    }
    /*
        getters
     */
    fun getProjID():String? = this.projectID

    fun get_project_name():String? = this.project_name

    fun get_DATAREAID():String? = this.DATAAREAID

    fun get_USERNAME():String? = this.USERNAME

    /*
        setters
     */
    fun set_projid(new_projid:String)
    {
        this.projectID = new_projid
    }

    fun set_project_name(new_projname:String)
    {
        this.project_name = new_projname
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
    override fun toString(): String = this.project_name ?: ""
    /*
        makes copy of
     */
    override fun copy(): project_data = project_data(this.projectID, this.project_name, this.DATAAREAID, this.USERNAME)
}