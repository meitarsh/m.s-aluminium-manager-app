package com.example.chaosruler.msa_manager.object_types.takala_data

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

object takala_builder: table_dataclass_hashmap_createable()
{
    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_local_sql_hashmap(hashMap: HashMap<String, String>): takala_data
    {

        return takala_data(
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.ID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.ITEMID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.DATAAREAID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.QTY] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.KOMA] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.BINYAN] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.DIRA] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.TEUR] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.MUMLATZ] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.MONAAT] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.TGUVA] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.SUG] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.ALUT] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.ITEMTXT] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.RECVERSION] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.RECID] ?: "",
                hashMap[global_variables_dataclass.DB_SALPROJTAKALA!!.USERNAME] ?: ""
        )
    }

    /**
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_remote_sql_hashmap(hashMap: HashMap<String, String>): takala_data
    {
        return takala_data(
                (hashMap[remote_takala_table_helper.ID] ?: "").trim(),
                (hashMap[remote_takala_table_helper.ITEMID] ?: "").trim(),
                (hashMap[remote_takala_table_helper.DATAAREAID] ?: "").trim(),
                (hashMap[remote_takala_table_helper.QTY] ?: "").trim(),
                (hashMap[remote_takala_table_helper.KOMA] ?: "").trim(),
                (hashMap[remote_takala_table_helper.BINYAN] ?: "").trim(),
                (hashMap[remote_takala_table_helper.DIRA] ?: "").trim(),
                (hashMap[remote_takala_table_helper.TEUR] ?: "").trim(),
                (hashMap[remote_takala_table_helper.MUMLATZ] ?: "").trim(),
                (hashMap[remote_takala_table_helper.MONAAT] ?: "").trim(),
                (hashMap[remote_takala_table_helper.TGUVA] ?: "").trim(),
                (hashMap[remote_takala_table_helper.SUG] ?: "").trim(),
                (hashMap[remote_takala_table_helper.ALUT] ?: "").trim(),
                (hashMap[remote_takala_table_helper.ITEMTXT] ?: "").trim(),
                (hashMap[remote_takala_table_helper.RECVERSION] ?: "").trim(),
                (hashMap[remote_takala_table_helper.RECID] ?: "").trim(),
                remote_SQL_Helper.getusername())
    }

}