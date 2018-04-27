@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_inventory_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.inventory_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on inventory database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_inventory_table_helper(private var context: Context) : local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),null,context.resources.getInteger(R.integer.LOCAL_INVENTORY_TABLE_VERSION),context.getString(R.string.LOCAL_INVENTORY_TABLE_NAME))
{
    /**
     * The id field name
     * @author Chaosruler972
     */
    private val ID: String = context.getString(R.string.LOCAL_INVENTORY_COLUMN_ID)
    /**
     * The name field name
     * @author Chaosruler972
     */
    private val NAME:String = context.getString(R.string.LOCAL_INVENTORY_COLUMN_NAME)
    /**
     * The dataaraeid field name
     * @author Chaosruler972
     */
    private val DATAARAEID:String = context.getString(R.string.LOCAL_INVENTORY_COLUMN_DATAARAEID)
    /**
     * The user field name
     * @author Chaosruler972
     */
    private val USER:String = context.getString(R.string.LOCAL_INVENTORY_COLUMN_USERNAME)

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init
    {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(NAME)
        vector.add(DATAARAEID)
        vector.add(USER)
        init_vector_of_variables(vector)

    }
    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        val map: HashMap<String, String> = HashMap()
        map[ID] = "TEXT primary key"
        map[NAME] = "TEXT"
        map[USER] = "TEXT"
        map[DATAARAEID] = "TEXT"
        createDB(db,map)
    }


    /**
     * adds all big, updates, inserts... whatever
     * @author Chaosruler972
     */
    fun sync_db()
    {
        if(!global_variables_dataclass.isLocal)
            return
        val server_vec = server_data_to_vector()
        for(item in server_vec)
        {
            add_inventory(item)
        }
    }

    /**
     *  converts DB to vector of inventory data
     *  @author Chaosruler972
     *  @return a vector of inventory table from local DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_local_DB():Vector<inventory_data>
    {
        Log.d("DB OF: ","Inventory")
        val vector: Vector<inventory_data> = Vector()
        val all_db: Vector<HashMap<String, String>> = get_db()
        Log.d("inventory","All db size ${all_db.size.toString()}")
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USER]?:null) != null && it[USER] == remote_SQL_Helper.getusername()
                }
                .forEach { vector.addElement(inventory_data((it[ID]?:"").trim(), (it[NAME]?:"").trim(), (it[DATAARAEID]?:"").trim(), (it[USER]?:"").trim())) }
        return vector
    }


    /**
     * get local DB by project name
     * @author Chaosruler972
     * @return a vector of inventory table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun get_local_DB_by_projname(projid:String): Vector<inventory_data>
    {
        val vector: Vector<inventory_data> = Vector()

        val all_db: Vector<big_table_data> = global_variables_dataclass.db_big_vec
//        val inventorydb: Vector<inventory_data> = global_variables_dataclass.db_inv_vec


//        for(inventory in inventorydb)
//        {
//            for (big in all_db)
//            {
//                if((big.get_PROJECT_ID()?:"") == projid && (big.get_INVENTORY_ID()?:"") == (inventory.get_itemid()?:"1")  ) {
//                    vector.addElement(inventory)
//                    break
//                }
//            }
//        }
        return vector
    }


    /**
     *     subroutine to convert server data to vector of inventory data
     *  @author Chaosruler972
     *  @return a vector of inventory table from server DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun server_data_to_vector():Vector<inventory_data>
    {
        val typemap: java.util.HashMap<String, String> = remote_inventory_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_INVENTORY),typemap,context.getString(R.string.INVENTORY_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_INVENTORY),typemap,null, null)
        }
        val result_vector: Vector<inventory_data> = Vector()
        server_data.mapTo(result_vector) {
            inventory_data(
                    (it[remote_inventory_table_helper.ID]?: "").trim(), (it[remote_inventory_table_helper.NAME]?: "").trim(),
                    (it[remote_inventory_table_helper.DATAAREAID]?: "").trim(), (remote_SQL_Helper.getusername()).trim()
            )
        }
        return result_vector
    }

    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of inventory table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun server_data_to_vector_by_projname(projid: String): Vector<inventory_data>
    {
        val typemap: HashMap<String, String> = remote_big_table_helper.define_type_map()
        val server_data_big: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,null, null)
                }

        val invent_typemap: HashMap<String, String> = remote_inventory_table_helper.define_type_map()
        val server_data_inventory: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_INVENTORY),invent_typemap,context.getString(R.string.INVENTORY_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_INVENTORY),invent_typemap,null, null)
                }
        val result_vector: Vector<inventory_data> = Vector()
        for(inventory in server_data_inventory)
        {
            for (big in server_data_big)
            {
                if((big[remote_big_table_helper.PROJECTS_ID]?:"").trim() == projid && (big[remote_big_table_helper.INVENTORY_ID]?:"").trim() == (inventory[remote_inventory_table_helper.ID]?:"1").trim()  ) {
                    result_vector.addElement(inventory_data( (inventory[remote_inventory_table_helper.ID]?:"").trim(),
                            (inventory[remote_inventory_table_helper.NAME]?:"").trim(),(inventory[remote_inventory_table_helper.DATAAREAID]?:"").trim()
                            ,(remote_SQL_Helper.getusername().trim())))

                    break
                }
            }
        }

        return result_vector
    }

    /**
     *   subroutine that is in charge of getting the inventory class
     * by query
     * @author Chaosruler972
     * @param inventory_data the inventoru data to filter by
     * @return the inventory data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_inventory_by_inventory(inventory_data: inventory_data) // subroutine to get a inventory object
            : inventory_data?
    {
        val input_map = HashMap<String, String>()
        input_map[ID] = "'${inventory_data.get_itemid()}'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            return inventory_data((vector.firstElement()[ID]?:"").trim(), (vector.firstElement()[NAME]?:"").trim(), (vector.firstElement()[DATAARAEID]?:"").trim(), (vector.firstElement()[USER]?:"").trim())
        }


        return null
    }



    /**
     * add inventory mechanism
     * if inventory is invalid, forget about it
     * if inventory is valid, and it exists, update it
     * if its a new inventory, add a new inventory to table
     * @author Chaosruler972
     * @param inventory_data the inventory data object to add
     * @return if add was successfull true, else false
     */
    fun add_inventory(inventory_data: inventory_data) // subroutine that manages the inventory adding operation to the database
            : Boolean
    {
        return if (remote_SQL_Helper.get_latest_sync_time().time > 0.toLong() &&
                check_inventory( inventory_data)) // checks if inventory exists in database
            update_inventory(inventory_data,inventory_data.copy()) // if it does, lets update
        else // if it doesn't lets create a new entry for the inventory
            insert_inventory(inventory_data)
    }


    /**
     * checks if inventory exists, query is not that smart, gets an ENTIRE table and than checks
     * if the user is there
     *
     * // on update
     * will select USERNAME only
     * @param inventory_data the inventory to check if exists or not
     * @return if the inventory data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_inventory(inventory_data: inventory_data) // subroutine to check if inventory exists on the database
            : Boolean
    {
        val inventory:inventory_data? = get_inventory_by_inventory( inventory_data)
        return inventory != null
    }

    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param inventory_data the inventory table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun insert_inventory(inventory_data: inventory_data):Boolean // subroutine to insert a inventory to the database
    {

        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[ID] = (inventory_data.get_itemid() ?: "").trim()
        data[NAME] = (inventory_data.get_itemname() ?: "").trim()
        data[DATAARAEID] = (inventory_data.get_DATAREAID() ?: "").trim()
        data[USER] = (inventory_data.get_USERNAME() ?: "").trim()
        everything_to_add.addElement(data)
        return add_data(everything_to_add, false)
    }

    /**
     *  subroutine in charge of feeding information and database information to
     * SQL abstraction on update queries
     * @author Chaosruler972
     * @param from the source that we want to update
     * @param to what we want to update it to
     * @return if update was successfull or not
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun update_inventory(from:inventory_data,to:inventory_data) // subroutine to update data of a inventory that exists on the database
            : Boolean {

        val change_to: HashMap<String, String> = HashMap()
        change_to[NAME] = (to.get_itemname() ?: "").trim()
        change_to[DATAARAEID] = (to.get_DATAREAID() ?: "").trim()
        change_to[USER] = (to.get_USERNAME() ?: "").trim()
        return update_data(ID, arrayOf(from.get_itemid()?:""),change_to)
    }

    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param inventory_data the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun delete_inventory( inventory_data: inventory_data):Boolean // subroutine to delete a inventory from the database (local)
    {
        if ( get_inventory_by_inventory(inventory_data)==null )
            return false
        return remove_from_db(ID, arrayOf((inventory_data.get_itemid()?:"").trim()))
    }

    /**
     * gets an inventory by ID
     * @author Chaosruler972
     * @param id the id that we want to filter by
     * @return the inventory itself if found, null otheerwise
     */
    @Suppress("RedundantVisibilityModifier")
    public fun get_inventory_by_id(id: String):inventory_data?
    {
        val mock_obj = inventory_data(id, null, null, remote_SQL_Helper.getusername())
        return get_inventory_by_inventory(mock_obj)
    }
}