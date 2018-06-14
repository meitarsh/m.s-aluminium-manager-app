package com.example.chaosruler.msa_manager.object_types.salprojmng_table_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojmng_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

object salprojmng_builder: table_dataclass_hashmap_createable()
{
    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_local_sql_hashmap(hashMap: HashMap<String, String>): salprojmng_table_data
    {
        return salprojmng_table_data(
                (hashMap[global_variables_dataclass.DB_SALPROJMNG!!.ID] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_SALPROJMNG!!.USERID] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_SALPROJMNG!!.DATAARAEID] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_SALPROJMNG!!.RECVERSION] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_SALPROJMNG!!.RECID] ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_SALPROJMNG!!.USER] ?: "").trim()
        )

    }
    /**
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_remote_sql_hashmap(hashMap: HashMap<String, String>): salprojmng_table_data
    {
        return salprojmng_table_data(
                (hashMap[remote_salprojmng_table_helper.PROJID] ?: "").trim(),
                (hashMap[remote_salprojmng_table_helper.USERID] ?: "").trim(),
                (hashMap[remote_salprojmng_table_helper.DATAAREAID] ?: "").trim(),
                (hashMap[remote_salprojmng_table_helper.RECVERSION] ?: "").trim(),
                (hashMap[remote_salprojmng_table_helper.RECID] ?: "").trim(),
                (remote_SQL_Helper.getusername()).trim()
        )
    }

}