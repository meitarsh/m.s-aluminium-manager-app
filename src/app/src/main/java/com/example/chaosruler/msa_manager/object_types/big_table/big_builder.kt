package com.example.chaosruler.msa_manager.object_types.big_table

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper.local_big_enum
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

object big_builder: table_dataclass_hashmap_createable() {
    /**
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_remote_sql_hashmap(hashMap: HashMap<String, String>): big_table_data {
        return big_table_data(
                hashMap[remote_big_table_helper.VENDOR_ID] ?: "",
                hashMap[remote_big_table_helper.DATAREAID] ?: "",
                hashMap[remote_big_table_helper.RECVERSION] ?: "",
                hashMap[remote_big_table_helper.RECID] ?: "",
                hashMap[remote_big_table_helper.PROJECTS_ID] ?: "",
                hashMap[remote_big_table_helper.INVENTORY_ID] ?: "",
                hashMap[remote_big_table_helper.FLAT] ?: "",
                hashMap[remote_big_table_helper.FLOOR] ?: "",
                hashMap[remote_big_table_helper.QTY] ?: "",
                hashMap[remote_big_table_helper.SALESPRICE] ?: "",
                hashMap[remote_big_table_helper.OPR_ID] ?: "",
                hashMap[remote_big_table_helper.MILESTONEPERCENT] ?: "",
                hashMap[remote_big_table_helper.QTYFORACCOUNT] ?: "",
                hashMap[remote_big_table_helper.PERCENTFORACCOUNT] ?: "",
                hashMap[remote_big_table_helper.TOTALSUM] ?: "",
                hashMap[remote_big_table_helper.SALPROG] ?: "",
                hashMap[remote_big_table_helper.PRINTORDER] ?: "",
                hashMap[remote_big_table_helper.ITEMNUMBER] ?: "",
                hashMap[remote_big_table_helper.KOMANUM] ?: "",
                hashMap[remote_big_table_helper.DIRANUM] ?: "",
                remote_SQL_Helper.getusername(),
                hashMap[remote_big_table_helper.QTYINPARTIALACC] ?: ""
        )
    }

    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    override fun from_local_sql_hashmap(hashMap: HashMap<String, String>): big_table_data {
        val big_data = big_table_data(
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.ACCOUNT_NUM]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.DATAARAEID]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.RECVERSION]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.RECID]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.PROJID]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.ITEMID]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.FLAT]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.FLOOR]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.QTY]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.SALESPRICE]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.OPR_ID]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.MILESTONEPERCENTAGE]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.QTYFORACCOUNT]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.PERCENTFORACCOUNT]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.TOTAL_SUM]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.SALPROG]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.PRINTORDER]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.ITEMNUMBER]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.KOMANUM]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.DIRANUM]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.USER]!!]
                        ?: "").trim(),
                (hashMap[global_variables_dataclass.DB_BIG!!.hashmap_of_variables[local_big_enum.QTYINPARTIALACC]!!]
                        ?: "").trim()
        )
        return big_data
    }
}
