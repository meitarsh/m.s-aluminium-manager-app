package com.example.chaosruler.msa_manager.object_types.salprojluz_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojluz_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

object salprojluz_builder: table_dataclass_hashmap_createable()
{
    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_local_sql_hashmap(hashMap: HashMap<String, String>): salprojluz_data
    {
        return salprojluz_data(
                hashMap[global_variables_dataclass.DB_SALPROJ!!.ID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.STARTDATE] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.FINISHDATE] ?: "",
                ((hashMap[global_variables_dataclass.DB_SALPROJ!!.IS_FINISHED] ?: "0") == "0"),
                hashMap[global_variables_dataclass.DB_SALPROJ!!.SIUMBPOAL] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.NOTES] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.KOMA] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.BUILDING] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.PERCENTEXC] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.DATAARAEID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.RECID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.RECVERSION] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJ!!.USERNAME] ?: ""
        )

    }

    /**
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_remote_sql_hashmap(hashMap: HashMap<String, String>): salprojluz_data
    {
        return salprojluz_data(
                (hashMap[remote_salprojluz_table_helper.ID] ?: ""),
                (hashMap[remote_salprojluz_table_helper.STARTDATE] ?: ""),
                (hashMap[remote_salprojluz_table_helper.FINISHDATE] ?: ""),
                ((hashMap[remote_salprojluz_table_helper.IS_FINISHED] ?: "") == "true"),
                (hashMap[remote_salprojluz_table_helper.SIUMBPOAL] ?: ""),
                (hashMap[remote_salprojluz_table_helper.NOTES] ?: ""),
                (hashMap[remote_salprojluz_table_helper.KOMA] ?: ""),
                (hashMap[remote_salprojluz_table_helper.BUILDING] ?: ""),
                (hashMap[remote_salprojluz_table_helper.PERCENTEXC] ?: ""),
                (hashMap[remote_salprojluz_table_helper.DATAAREAID] ?: ""),
                (hashMap[remote_salprojluz_table_helper.RECID] ?: ""),
                (hashMap[remote_salprojluz_table_helper.RECVERION] ?: ""),
                remote_SQL_Helper.getusername()
        )
    }

}