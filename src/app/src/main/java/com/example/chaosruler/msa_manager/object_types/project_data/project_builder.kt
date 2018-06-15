package com.example.chaosruler.msa_manager.object_types.project_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_projects_table_helper.local_projects_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

object project_builder: table_dataclass_hashmap_createable()
{
    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_local_sql_hashmap(hashMap: HashMap<String, String>): project_data
    {
        return project_data(
                hashMap[global_variables_dataclass.DB_project!!.hashmap_of_variables[local_projects_enum.ID]!!] ?: "".trim(),
                (hashMap[global_variables_dataclass.DB_project!!.hashmap_of_variables[local_projects_enum.NAME]!!] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_project!!.hashmap_of_variables[local_projects_enum.DATAAREAID]!!] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_project!!.hashmap_of_variables[local_projects_enum.USERNAME]!!] ?: "").trim())


    }

    /**
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_remote_sql_hashmap(hashMap: HashMap<String, String>): project_data
    {
        return project_data(
                (hashMap[remote_projects_table_helper.ID] ?: "").trim(),
                (hashMap[remote_projects_table_helper.NAME] ?: "").trim(),
                (hashMap[remote_projects_table_helper.DATAAREAID] ?: "").trim(),
                remote_SQL_Helper.getusername().trim())
    }

}