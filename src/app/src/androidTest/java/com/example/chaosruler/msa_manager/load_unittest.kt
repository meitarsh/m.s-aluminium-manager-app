package com.example.chaosruler.msa_manager


import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.chaosruler.msa_manager.MSSQL_helpers.*
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_OPR_table_helper.local_OPR_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper.local_big_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_projects_table_helper.local_projects_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojluz_table_helper.local_salprojluz_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojtakala_table_helpe.local_salprojtakala_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_vendor_table_helper.local_vendor_enum
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.object_types.big_table.big_builder
import com.example.chaosruler.msa_manager.object_types.big_table.big_table_data
import com.example.chaosruler.msa_manager.object_types.opr_data.opr_builder
import com.example.chaosruler.msa_manager.object_types.opr_data.opr_data
import com.example.chaosruler.msa_manager.object_types.project_data.project_builder
import com.example.chaosruler.msa_manager.object_types.project_data.project_data
import com.example.chaosruler.msa_manager.object_types.salprojluz_data.salprojluz_builder
import com.example.chaosruler.msa_manager.object_types.salprojluz_data.salprojluz_data
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_builder
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_data
import com.example.chaosruler.msa_manager.object_types.vendor_data.vendor_builder
import com.example.chaosruler.msa_manager.object_types.vendor_data.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.HashMap

@RunWith(AndroidJUnit4::class)
class load_unittest {
    private fun ClosedRange<Int>.random() =
            Random().nextInt(endInclusive - start) +  start

    private val OPR_SIZE = 1000
    private val PROJECT_SIZE = 200
    private val ACCOUNT_NUM_SIZE = 500
    private val BIG_TABLE_DATA_SIZE = 17000
    private val TAKALA_TABLE_SIZE = 200
    private val LUZ_TABLE_SIZE = 200
    private fun generateRandomWord(length: Int): String
    {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var word = ""
        for (i in 0..length) {
            word += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return word
    }

    private fun generateRandomNums(length: Int): String
    {
        val chars = "0123456789"
        var word = ""
        for (i in 0..length) {
            word += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return word
    }


    @Suppress("UNUSED_VARIABLE")
    @Test
    fun test_stress()
    {
        val con = InstrumentationRegistry.getTargetContext()
        val intent = Intent()
        val test_username = generateRandomWord(8)
        global_variables_dataclass.init_dbs(con)
        global_variables_dataclass.DB_BIG!!.clearDB()
        global_variables_dataclass.DB_project!!.clearDB()
        global_variables_dataclass.DB_SALPROJ!!.clearDB()
        global_variables_dataclass.DB_SALPROJTAKALA!!.clearDB()
        global_variables_dataclass.DB_VENDOR!!.clearDB()
        global_variables_dataclass.DB_OPR!!.clearDB()
        remote_SQL_Helper.user = User(test_username, "", 0)
        remote_SQL_Helper.setusername(test_username)
//        offline_mode_service.init_cache(con, intent)
//        offline_mode_service.trd.interrupt()

        val project_ids = generate_unique_vector(PROJECT_SIZE)
        val oprs = generate_unique_vector(OPR_SIZE)
        val account_nums = generate_unique_vector(ACCOUNT_NUM_SIZE)

        val bigs = Vector<big_table_data>()
        val bigs_hashmap = HashMap<String, Boolean>()
        for(i in 0..(BIG_TABLE_DATA_SIZE-1))
        {
            var recid = generateRandomWord(8)
            while(bigs_hashmap.containsKey(recid))
            {
                recid = generateRandomWord(8)
            }
            bigs_hashmap[recid] = true
            val base = fill_hashmap_of_big(get_random_key_from_vec(project_ids), get_random_key_from_vec(oprs), get_random_key_from_vec(account_nums), recid, test_username)
            bigs.addElement(fill_all_data(remote_big_table_helper, big_builder, base) as big_table_data)
        }

        val bakara = Vector<takala_data>()
        val bakara_hashmap = HashMap<String, Boolean>()
        for(i in 0..(TAKALA_TABLE_SIZE-1))
        {
            var recid = generateRandomWord(8)
            while(bakara_hashmap.containsKey(recid))
            {
                recid = generateRandomWord(8)
            }
            bakara_hashmap[recid] = true
            val base = fill_hashmap_of_takala(get_random_key_from_vec(project_ids), recid, test_username)
            bakara.addElement(fill_all_data(remote_takala_table_helper, takala_builder, base) as takala_data)
        }


        val luz = Vector<salprojluz_data>()
        val luz_hashmap = HashMap<String, Boolean>()
        for(i in 0..(LUZ_TABLE_SIZE-1))
        {
            var recid = generateRandomWord(8)
            while(luz_hashmap.containsKey(recid))
            {
                recid = generateRandomWord(8)
            }
            luz_hashmap[recid] = true
            val base = fill_hashmap_of_luz(get_random_key_from_vec(project_ids), recid, test_username)
            luz.addElement(fill_all_data(remote_salprojluz_table_helper, salprojluz_builder, base) as salprojluz_data)
        }

        val projects = Vector<project_data>()
        for(i in 0..(PROJECT_SIZE-1))
        {
            val base = fill_hashmap_of_project(project_ids[i]!!,test_username)
            projects.addElement(fill_all_data(remote_projects_table_helper, project_builder, base) as project_data)
        }

        val opers = Vector<opr_data>()
        for(i in 0..(OPR_SIZE-1))
        {
            val base = fill_hashmap_of_opr(oprs[i]!!,test_username)
            opers.addElement(fill_all_data(remote_opr_table_helper, opr_builder, base) as opr_data)
        }

        val vendors = Vector<vendor_data>()
        for(i in 0..(ACCOUNT_NUM_SIZE-1))
        {
            val base = fill_hashmap_of_vendor(account_nums[i]!!,test_username)
            vendors.addElement(fill_all_data(remote_vendors_table_helper, vendor_builder, base) as vendor_data)
        }


        for(big in bigs)
        {
            global_variables_dataclass.DB_BIG!!.add_to_table(big)
        }

        for(project in projects)
        {
            global_variables_dataclass.DB_project!!.add_to_table(project)
        }

        for(opr in opers)
        {
            global_variables_dataclass.DB_OPR!!.add_to_table(opr)
        }

        for(takala in bakara)
        {
            global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala)
        }

        for(vendor in vendors)
        {
            global_variables_dataclass.DB_VENDOR!!.add_to_table(vendor)
        }

        for(luzit in luz)
        {
            global_variables_dataclass.DB_SALPROJ!!.add_to_table(luzit)
        }

        global_variables_dataclass.db_big_vec = global_variables_dataclass.DB_BIG!!.get_local_DB()
        global_variables_dataclass.db_project_vec = global_variables_dataclass.DB_project!!.get_local_DB()
        global_variables_dataclass.db_opr_vec = global_variables_dataclass.DB_OPR!!.get_local_DB()
        global_variables_dataclass.db_salproj_vec = global_variables_dataclass.DB_SALPROJ!!.get_local_DB()
        global_variables_dataclass.db_vendor_vec = global_variables_dataclass.DB_VENDOR!!.get_local_DB()
        global_variables_dataclass.db_salprojtakala_vec = global_variables_dataclass.DB_SALPROJTAKALA!!.get_local_DB()

        assert(global_variables_dataclass.db_big_vec.size == BIG_TABLE_DATA_SIZE)
        assert(global_variables_dataclass.db_project_vec.size == PROJECT_SIZE)
        assert(global_variables_dataclass.db_opr_vec.size == OPR_SIZE)
        assert(global_variables_dataclass.db_salproj_vec.size == LUZ_TABLE_SIZE)
        assert(global_variables_dataclass.db_salprojtakala_vec.size == TAKALA_TABLE_SIZE)
        assert(global_variables_dataclass.db_vendor_vec.size == ACCOUNT_NUM_SIZE)
    }

    private fun fill_hashmap_of_big(project_id: String, opr: String, account_num: String, recid: String, user: String): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        val hashmap_of_variables = global_variables_dataclass.DB_BIG!!.hashmap_of_variables
        hashmap[hashmap_of_variables[local_big_enum.ACCOUNT_NUM]!!] = account_num
        hashmap[hashmap_of_variables[local_big_enum.QTY]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.PERCENTFORACCOUNT]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.DATAARAEID]!!] = "mz11"
        hashmap[hashmap_of_variables[local_big_enum.RECVERSION]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.RECID]!!] = recid
        hashmap[hashmap_of_variables[local_big_enum.PROJID]!!] = project_id
        hashmap[hashmap_of_variables[local_big_enum.ITEMID]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.FLAT]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.FLOOR]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.SALESPRICE]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.OPR_ID]!!] = opr
        hashmap[hashmap_of_variables[local_big_enum.MILESTONEPERCENTAGE]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.QTYFORACCOUNT]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.TOTAL_SUM]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.SALPROG]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.PRINTORDER]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.ITEMNUMBER]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.KOMANUM]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.DIRANUM]!!] = ""
        hashmap[hashmap_of_variables[local_big_enum.USER]!!] = user
        hashmap[hashmap_of_variables[local_big_enum.QTYINPARTIALACC]!!] = ""
        return hashmap
    }


    private fun fill_hashmap_of_project(project_id: String, user: String): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        val hashmap_of_variables = global_variables_dataclass.DB_project!!.hashmap_of_variables
        hashmap[hashmap_of_variables[local_projects_enum.ID]!!] = project_id
        hashmap[hashmap_of_variables[local_projects_enum.DATAAREAID]!!] = "mz11"
        hashmap[hashmap_of_variables[local_projects_enum.NAME]!!] = ""
        hashmap[hashmap_of_variables[local_projects_enum.USERNAME]!!] = user
        return hashmap
    }

    private fun fill_hashmap_of_opr(project_id: String, user: String): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        val hashmap_of_variables = global_variables_dataclass.DB_OPR!!.hashmap_of_variables
        hashmap[hashmap_of_variables[local_OPR_enum.ID]!!] = project_id
        hashmap[hashmap_of_variables[local_OPR_enum.DATAARAEID]!!] = "mz11"
        hashmap[hashmap_of_variables[local_OPR_enum.NAME]!!] = ""
        hashmap[hashmap_of_variables[local_OPR_enum.USER]!!] = user
        return hashmap
    }

    private fun fill_hashmap_of_luz(project_id: String, recid: String, user: String): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        val hashmap_of_variables = global_variables_dataclass.DB_SALPROJ!!.hashmap_of_variables
        hashmap[hashmap_of_variables[local_salprojluz_enum.ID]!!] = project_id
        hashmap[hashmap_of_variables[local_salprojluz_enum.DATAARAEID]!!] = "mz11"
        hashmap[hashmap_of_variables[local_salprojluz_enum.STARTDATE]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.USERNAME]!!] = user
        hashmap[hashmap_of_variables[local_salprojluz_enum.FINISHDATE]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.IS_FINISHED]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.SIUMBPOAL]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.NOTES]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.KOMA]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.BUILDING]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.PERCENTEXC]!!] = ""
        hashmap[hashmap_of_variables[local_salprojluz_enum.RECID]!!] = recid
        hashmap[hashmap_of_variables[local_salprojluz_enum.RECVERSION]!!] = ""
        return hashmap
    }

    private fun fill_hashmap_of_takala(project_id: String, recid: String, user: String): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        val hashmap_of_variables = global_variables_dataclass.DB_SALPROJTAKALA!!.hashmap_of_variables
        hashmap[hashmap_of_variables[local_salprojtakala_enum.ID]!!] = project_id
        hashmap[hashmap_of_variables[local_salprojtakala_enum.DATAAREAID]!!] = "mz11"
        hashmap[hashmap_of_variables[local_salprojtakala_enum.TEUR]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.DIRA]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.BINYAN]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.KOMA]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.QTY]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.USERNAME]!!] = user
        hashmap[hashmap_of_variables[local_salprojtakala_enum.MUMLATZ]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.MONAAT]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.TGUVA]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.SUG]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.ALUT]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.ITEMTXT]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.ITEMID]!!] = ""
        hashmap[hashmap_of_variables[local_salprojtakala_enum.RECID]!!] = recid
        hashmap[hashmap_of_variables[local_salprojtakala_enum.RECVERSION]!!] = ""
        return hashmap
    }

    private fun fill_hashmap_of_vendor(project_id: String, user: String): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        val hashmap_of_variables = global_variables_dataclass.DB_VENDOR!!.hashmap_of_variables
        hashmap[hashmap_of_variables[local_vendor_enum.ID]!!] = project_id
        hashmap[hashmap_of_variables[local_vendor_enum.DATAARAEID]!!] = "mz11"
        hashmap[hashmap_of_variables[local_vendor_enum.NAME]!!] = ""
        hashmap[hashmap_of_variables[local_vendor_enum.USER]!!] = user
        return hashmap
    }

    private fun generate_unique_vector(size: Int, wordSize: Int = 8): Vector<String> {
        val hashmap = HashMap<String, Boolean>()
        while (hashmap.size < size)
        {
            hashmap[generateRandomWord(wordSize)] = true
        }
        return Vector(hashmap.keys)
    }

    private fun fill_all_data(@Suppress("UNUSED_PARAMETER") remote_helper: remote_helper, table_dataclass_hashmap_createable: table_dataclass_hashmap_createable, hashMap: HashMap<String, String>, sizeRan: Int = 10) : table_dataclass
    {
        val new_hashmap = HashMap<String, String>()
//        val typemap = remote_helper.define_type_map()
        for(key in hashMap.keys)
        {
            if(hashMap[key]!! == "")
            {
                new_hashmap[key] =
//                        if (typemap[key]!! == "datetime")
//                            generateRandomNums(5)
//                        else if (typemap[key]!! == "real" || typemap[key]!! == "numeric")
//                            generateRandomNums(2)
//                        else
                            generateRandomWord(sizeRan)
            }
            else
            {
                new_hashmap[key] = hashMap[key]!!
            }
        }
        return table_dataclass_hashmap_createable.from_local_sql_hashmap(new_hashmap)
    }

    private fun get_random_key_from_vec(vec: Vector<String>): String {
        return vec[(0..(vec.size-1)).random()]!!
    }
}