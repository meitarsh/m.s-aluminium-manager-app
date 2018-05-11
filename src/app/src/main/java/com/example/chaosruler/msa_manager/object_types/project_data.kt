package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass

/**
 * project data-class
 * @author Chaosruler972
 * @constructor all the data from inventory table
 */
class project_data(

        /**
         * Project id
         * @author Chaosruler972
         */
        private var projectID:String?,
        /**
         * project name
         * @author Chaosruler972
         */
        private var project_name:String?,
        /**
         * dataaraeid
         * @author Chaosruler972
         */
        private var DATAAREAID:String?,
        /**
         * current syncing username
         * @author Chaosruler972
         */
        private var USERNAME:String?
)
    : table_dataclass {
    /**
     * Function responisble for inflating the data from strings.xml
     * @author Chaosruler972
     */
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
    /**
     * the current project id field name (sqlite)
     * @author Chaosruler972
     * @return the current project id (sqlite)
     */
    fun getProjID():String? = this.projectID
    /**
     * the current project name field name (sqlite)
     * @author Chaosruler972
     * @return the current project name (sqlite)
     */
    fun get_project_name():String? = this.project_name
    /**
     * the current dataaraeid
     * @author Chaosruler972
     * @return the current dataaraeid
     */
    fun get_DATAREAID():String? = this.DATAAREAID
    /**
     * the current username(sqlite)
     * @author Chaosruler972
     * @return the current username (sqlite)
     */
    fun get_USERNAME():String? = this.USERNAME

    /**
     * Sets the current project id
     * @author Chaosruler972
     * @param new_projid the new project id
     */
    fun set_projid(new_projid:String)
    {
        this.projectID = new_projid.trim()
    }
    /**
     * Sets the current project name
     * @author Chaosruler972
     * @param new_projname the new project name
     */
    fun set_project_name(new_projname:String)
    {
        this.project_name = new_projname.trim()
    }

    /**
     * Sets the dataaraeid
     * @author Chaosruler972
     * @param new_dataareaid the new dataaraeid
     */
    fun set_dataareaid(new_dataareaid:String)
    {
        this.DATAAREAID = new_dataareaid.trim()
    }

    /**
     * Sets the current username
     * @author Chaosruler972
     * @param new_username the new username
     */
    fun set_username(new_username:String)
    {
        this.USERNAME = new_username.trim()
    }

    /**
     * Identifies this data
     * @author Chaosruler972
     * @return a string to identify this data
     */
    override fun toString(): String = this.project_name ?: ""
    /**
     * a copy constructor
     * @return a copy of this data class
     * @author Chaosruler972
     */
    override fun copy(): project_data = project_data(this.projectID, this.project_name, this.DATAAREAID, this.USERNAME)
}