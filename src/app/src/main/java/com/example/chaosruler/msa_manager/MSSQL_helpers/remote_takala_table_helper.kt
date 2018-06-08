@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * Remote dataclass for salprojtakala
 * @author Chaosruler972
 */
class remote_takala_table_helper {

    companion object : remote_helper() {
        /**
         * Database name
         * @author Chaosruler972
         */
        var DATABASE_NAME: String = ""
        /**
         * Table name for projects
         * @author Chaosruler972
         */
        var TABLE_NAME: String = ""

        /**
         * ID for projects
         * @author Chaosruler972
         */
        var ID: String = ""

        /**
         * type for ID for projects
         * @author Chaosruler972
         */
        var ID_TYPE: String = ""

        /**
         * ITEMID
         * @author Chaosruler972
         */
        var ITEMID: String = ""

        /**
         * type for ITEMID
         * @author Chaosruler972
         */
        var ITEMID_TYPE: String = ""

        /**
         * DATAAREAID
         * @author Chaosruler972
         */
        var DATAAREAID: String = ""

        /**
         * type for DATAAREAID
         * @author Chaosruler972
         */
        var DATAAREAID_TYPE: String = ""

        /**
         * QTY
         * @author Chaosruler972
         */
        var QTY: String = ""

        /**
         * type for QTY
         * @author Chaosruler972
         */
        var QTY_TYPE: String = ""

        /**
         * KOMA
         * @author Chaosruler972
         */
        var KOMA: String = ""

        /**
         * type for KOMA
         * @author Chaosruler972
         */
        var KOMA_TYPE: String = ""

        /**
         * BINYAN
         * @author Chaosruler972
         */
        var BINYAN: String = ""

        /**
         * type for BINYAN
         * @author Chaosruler972
         */
        var BINYAN_TYPE: String = ""

        /**
         * DIRA
         * @author Chaosruler972
         */
        var DIRA: String = ""

        /**
         * type for DIRA
         * @author Chaosruler972
         */
        var DIRA_TYPE: String = ""


        /**
         * TEUR
         * @author Chaosruler972
         */
        var TEUR: String = ""

        /**
         * type for TEUR
         * @author Chaosruler972
         */
        var TEUR_TYPE: String = ""


        /**
         * MUMLATZ
         * @author Chaosruler972
         */
        var MUMLATZ: String = ""

        /**
         * type for MUMLATZ
         * @author Chaosruler972
         */
        var MUMLATZ_TYPE: String = ""


        /**
         * MONAAT
         * @author Chaosruler972
         */
        var MONAAT: String = ""

        /**
         * type for MONAAT
         * @author Chaosruler972
         */
        var MONAAT_TYPE: String = ""

        /**
         * TGUVA
         * @author Chaosruler972
         */
        var TGUVA: String = ""

        /**
         * type for ]TGUVA
         * @author Chaosruler972
         */
        var TGUVA_TYPE: String = ""

        /**
         * SUG
         * @author Chaosruler972
         */
        var SUG: String = ""

        /**
         * type for SUG
         * @author Chaosruler972
         */
        var SUG_TYPE: String = ""

        /**
         * ALUT
         * @author Chaosruler972
         */
        var ALUT: String = ""

        /**
         * type for ALUT
         * @author Chaosruler972
         */
        var ALUT_TYPE: String = ""

        /**
         * ITEMTXT
         * @author Chaosruler972
         */
        var ITEMTXT: String = ""

        /**
         * type for ITEMTXT
         * @author Chaosruler972
         */
        var ITEMTXT_TYPE: String = ""

        /**
         * RECVERSION
         * @author Chaosruler972
         */
        var RECVERSION: String = ""

        /**
         * type for RECVERSION
         * @author Chaosruler972
         */
        var RECVERSION_TYPE: String = ""

        /**
         * RECID
         * @author Chaosruler972
         */
        var RECID: String = ""

        /**
         * type for RECID
         * @author Chaosruler972
         */
        var RECID_TYPE: String = ""


        /**
         * extracts data from info files
         * @author Chaosruler972
         * @param context the context to work with
         */
        override fun extract_variables(context: Context) {
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            TABLE_NAME = context.getString(R.string.TABLE_SALPROJBAKARA)

            ID = context.getString(R.string.TABLE_SALPROJBAKARA_PROJID)

            ID_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_PROJID_TYPE)


            ITEMID = context.getString(R.string.TABLE_SALPROJBAKARA_ITEMID)

            ITEMID_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_ITEMID_TYPE)


            DATAAREAID = context.getString(R.string.TABLE_SALPROJBAKARA_DATAAREAID)

            DATAAREAID_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_DATAAREAID_TYPE)


            QTY = context.getString(R.string.TABLE_SALPROJBAKARA_QTY)

            QTY_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_QTY_TYPE)


            KOMA = context.getString(R.string.TABLE_SALPROJBAKARA_KOMA)

            KOMA_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_KOMA_TYPE)


            BINYAN = context.getString(R.string.TABLE_SALPROJBAKARA_BINYAN)

            BINYAN_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_BINYAN_TYPE)


            DIRA = context.getString(R.string.TABLE_SALPROJBAKARA_DIRA)

            DIRA_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_DIRA_TYPE)


            TEUR = context.getString(R.string.TABLE_SALPROJBAKARA_TEUR)

            TEUR_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_TEUR_TYPE)


            MUMLATZ = context.getString(R.string.TABLE_SALPROJBAKARA_MUMLATZ)

            MUMLATZ_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_MUMLATZ_TYPE)


            MONAAT = context.getString(R.string.TABLE_SALPROJBAKARA_MONAAT)

            MONAAT_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_MONAAT_TYPE)


            TGUVA = context.getString(R.string.TABLE_SALPROJBAKARA_TGUVA)

            TGUVA_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_TGUVA_TYPE)


            SUG = context.getString(R.string.TABLE_SALPROJBAKARA_SUG)

            SUG_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_SUG_TYPE)


            ALUT = context.getString(R.string.TABLE_SALPROJBAKARA_ALUT)

            ALUT_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_ALUT_TYPE)


            ITEMTXT = context.getString(R.string.TABLE_SALPROJBAKARA_ITEMTXT)

            ITEMTXT_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_ITEMTXT_TYPE)


            RECVERSION = context.getString(R.string.TABLE_SALPROJBAKARA_RECVERSION)

            RECVERSION_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_RECVERSION_TYPE)


            RECID = context.getString(R.string.TABLE_SALPROJBAKARA_RECID)

            RECID_TYPE = context.getString(R.string.TABLE_SALPROJBAKARA_RECID_TYPE)

            TABLE_DATETIME_SYNCABLE= context.resources.getBoolean(R.bool.TABLE_SALPROJBAKARA_DATETIME_ENABLED)


        }

        /**
         * Defines table hashmap
         * @author Chaosruler972
         * @return a table hashmap defines by <value> <type>
         */
        override fun define_type_map(): HashMap<String, String> {
            val hashmap = HashMap<String, String>()
            hashmap[ID] = ID_TYPE
            hashmap[ITEMID] = ITEMID_TYPE
            hashmap[DATAAREAID] = DATAAREAID_TYPE
            hashmap[QTY] = QTY_TYPE
            hashmap[KOMA] = KOMA_TYPE
            hashmap[BINYAN] = BINYAN_TYPE
            hashmap[DIRA] = DIRA_TYPE
            hashmap[TEUR] = TEUR_TYPE
            hashmap[MUMLATZ] = MUMLATZ_TYPE
            hashmap[MONAAT] = MONAAT_TYPE
            hashmap[TGUVA] = TGUVA_TYPE
            hashmap[SUG] = SUG_TYPE
            hashmap[ALUT] = ALUT_TYPE
            hashmap[ITEMTXT] = ITEMTXT_TYPE
            hashmap[RECVERSION] = RECVERSION_TYPE
            hashmap[RECID] = RECID_TYPE
            return hashmap
        }

        /**
         * a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that
         * @author Chaosruler972
         * @param context a baseContext to work with
         * @param map a map of the variables we want to identify the object with
         * @param takala_data the data-object we want to update and take the data from
         */
        fun push_update(takala_data: takala_data, map: HashMap<String, String>, context: Context) {
            val typemap = remote_takala_table_helper.define_type_map()
            normalize_hashmap(map, typemap)
            val where_clause: HashMap<String, String> = HashMap()
            where_clause[remote_takala_table_helper.RECID] = (takala_data.get_RECID() ?: "").trim()
            val all_map = takala_data.to_hashmap()
            normalize_hashmap(all_map, typemap)
            for(item in map)
                all_map[item.key] = item.value
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_takala_table_helper.DATABASE_NAME, remote_takala_table_helper.TABLE_NAME, where_clause, "varchar", map, all_map, TABLE_DATETIME_SYNCABLE!!)
            query = query.replace("'", "&quote;")
            val str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }

        /**
         * Pushes an update of obj data
         * @author Chaosruler972
         * @param obj the data
         * @param map typemap
         * @param context the context to work with
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context) {
            if (obj is takala_data)
                push_update(obj, map, context)
        }

    }
}