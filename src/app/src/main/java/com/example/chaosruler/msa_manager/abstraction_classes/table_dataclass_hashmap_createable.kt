package com.example.chaosruler.msa_manager.abstraction_classes

abstract class table_dataclass_hashmap_createable{
    /**
     * table dataclass from local sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    abstract fun from_local_sql_hashmap(@Suppress("UNUSED_PARAMETER") hashMap: HashMap<String, String>): table_dataclass


    /**   const val RECID = 4
     * table dataclass from remote sql hashmap
     * @author Chaosruler972
     * @param hashMap the hashmap
     * @return this table dataclass
     */
    abstract fun from_remote_sql_hashmap(@Suppress("UNUSED_PARAMETER") hashMap: HashMap<String, String>): table_dataclass

}