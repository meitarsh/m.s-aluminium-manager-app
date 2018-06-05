package com.example.chaosruler.msa_manager.abstraction_classes

@Suppress("KDocUnresolvedReference")
/**
 * represents the abstract requirements for a table data class object (object representing a row in the remote database)
 * @author Chaosruler972
 * @see object_types
 * @constructor empty, the responsibiltiy falls to extending class
 */
interface table_dataclass {

    /**
     * to identify the object, we must reimplement the toString funcion with identifying marks on the strings
     * @author Chaosruler972
     * @return an identifying string of the object
     */
    override fun toString(): String

    /**
     * a copy constructor of the object
     * @author Chaosruler972
     * @return a copy of this object represnetation
     */
    fun copy(): table_dataclass

    /**
     * to hashmap
     * @author Chaosruler972
     * @return hashmap of this dataclass
     */
    fun to_hashmap(): HashMap<String, String>

    /**
     * to hashmap only on keys
     * @author Chaosruler972
     * return hashmap of only key and values
     */
    fun to_key_hashmap(): Pair<String, String>

    /**
     * to hashmap only non keys
     * @author Chaosruler972
     * return hashmap of only non-key and values
     */
    fun to_non_key_hashmap(): HashMap<String, String> {
        val map = to_hashmap()
        map.remove(to_key_hashmap().first)
        return map
    }

    /**
     * to local table dataclass
     * @author Chaosruler972
     * @return hashmap of local dataclass
     */
    fun to_sql_hashmap(): HashMap<String, String>
}