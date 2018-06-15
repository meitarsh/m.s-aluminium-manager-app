package com.example.chaosruler.msa_manager.object_types.vendor_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_vendors_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_vendor_table_helper.local_vendor_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

object vendor_builder: table_dataclass_hashmap_createable()
{
    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_local_sql_hashmap(hashMap: HashMap<String, String>): vendor_data
    {
        return vendor_data(
                (hashMap[global_variables_dataclass.DB_VENDOR!!.hashmap_of_variables[local_vendor_enum.ID]!!] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_VENDOR!!.hashmap_of_variables[local_vendor_enum.NAME]!!] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_VENDOR!!.hashmap_of_variables[local_vendor_enum.DATAARAEID]!!] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_VENDOR!!.hashmap_of_variables[local_vendor_enum.USER]!!] ?: "").trim())
    }

    /**
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_remote_sql_hashmap(hashMap: HashMap<String, String>): vendor_data
    {
        return vendor_data(
                (hashMap[remote_vendors_table_helper.ID] ?: "").trim(),
                (hashMap[remote_vendors_table_helper.NAME] ?: "").trim(),
                (hashMap[remote_vendors_table_helper.DATAAREAID] ?: "").trim(),
                remote_SQL_Helper.getusername().trim())
    }

}