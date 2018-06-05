package com.example.chaosruler.msa_manager.object_types

import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.global_variables_dataclass

/**
 * Takala dataclass
 * @author Chaosruler972
 */
class takala_data(
        /**
         * Projid
         * @author Chaosruler972
         */
        private var PROJID: String?,
        /**
         * ITEMID
         * @author Chaosruler972
         */
        private var ITEMID: String?,
        /**
         * Dataaraeid
         * @author Chaosruler972
         */
        private var DATAAREAID: String?,
        /**
         * Qty
         * @author Chaosruler972
         */
        private var QTY: String?,
        /**
         * Koma
         * @author Chaosruler972
         */
        private var KOMA: String?,
        /**
         * Binyan
         * @author Chaosruler972
         */
        private var BINYAN: String?,
        /**
         * Dira
         * @author Chaosruler972
         */
        private var DIRA: String?,
        /**
         * Tieur hatakala
         * @author Chaosruler972
         */
        private var TEUR: String?,
        /**
         * Mumlatz
         * @author Chaosruler972
         */
        private var MUMLATZ: String?,
        /**
         * Monaat
         * @author Chaosruler972
         */
        private var MONAAT: String?,
        /**
         * Tguva
         * @author Chaosruler972
         */
        private var TGUVA: String?,
        /**
         * sug
         * @author Chaosruler972
         */
        private var SUG: String?,
        /**
         * alut
         * @author Chaosruler972
         */
        private var ALUT: String?,
        /**
         * Item text
         * @author Chaosruler972
         */
        private var ITEMTXT: String?,
        /**
         * Rec version
         * @author Chaosruler972
         */
        private var RECVERSION: String?,
        /**
         * Recid
         * @author Chaosruler972
         */
        private var RECID: String?,
        /**
         * Username
         * @author Chaosruler972
         */
        private var USERNAME: String
) : table_dataclass {

    /**
     * returns a key hashmap
     * @author Chaosruler972
     * @return a key hashmap
     */
    override fun to_key_hashmap(): Pair<String, String> = Pair(global_variables_dataclass.DB_SALPROJTAKALA!!.ID, RECID!!)

    /**
     * Projid getter
     * @author Chaosruler972
     * @return the projid
     */
    fun get_projid() = PROJID

    /**
     * projid setter
     * @author Chaosruler972
     * @param projid the new project id
     */
    fun set_projid(projid: String) {
        this.PROJID = projid
    }


    /**
     * ITEMID getter
     * @author Chaosruler972
     * @return the ITEMID
     */
    fun get_ITEMID() = ITEMID

    /**
     * ITEMID setter
     * @author Chaosruler972
     * @param ITEMID the new ITEMID
     */
    fun set_ITEMID(ITEMID: String) {
        this.ITEMID = ITEMID
    }

    /**
     * DATAAREAID getter
     * @author Chaosruler972
     * @return the DATAAREAID
     */
    fun get_DATAAREAID() = DATAAREAID

    /**
     * DATAAREAID setter
     * @author Chaosruler972
     * @param DATAAREAID the new DATAAREAID
     */
    fun set_DATAAREAID(DATAAREAID: String) {
        this.DATAAREAID = DATAAREAID
    }


    /**
     * QTY getter
     * @author Chaosruler972
     * @return the QTY
     */
    fun get_QTY() = try {
        QTY!!.toDouble().toInt().toString()
    } catch (e: Exception) {
        QTY
    }


    /**
     * QTY setter
     * @author Chaosruler972
     * @param QTY the new QTY
     */
    fun set_QTY(QTY: String) {
        this.QTY = QTY
    }


    /**
     * KOMA getter
     * @author Chaosruler972
     * @return the KOMA
     */
    fun get_KOMA() = KOMA

    /**
     * KOMA setter
     * @author Chaosruler972
     * @param KOMA the new KOMA
     */
    fun set_KOMA(KOMA: String) {
        this.KOMA = KOMA
    }


    /**
     * BINYAN getter
     * @author Chaosruler972
     * @return the BINYAN
     */
    fun get_BINYAN() = BINYAN

    /**
     * BINYAN setter
     * @author Chaosruler972
     * @param BINYAN the new BINYAN
     */
    fun set_BINYAN(BINYAN: String) {
        this.BINYAN = BINYAN
    }


    /**
     * DIRA getter
     * @author Chaosruler972
     * @return the DIRA
     */
    fun get_DIRA() = BINYAN

    /**
     * DIRA setter
     * @author Chaosruler972
     * @param DIRA the new DIRA
     */
    fun set_DIRA(DIRA: String) {
        this.DIRA = DIRA
    }


    /**
     * TEUR getter
     * @author Chaosruler972
     * @return the TEUR
     */
    fun get_TEUR() = TEUR

    /**
     * TEUR setter
     * @author Chaosruler972
     * @param TEUR the new TEUR
     */
    fun set_TEUR(TEUR: String) {
        this.TEUR = TEUR
    }


    /**
     * MUMLATZ getter
     * @author Chaosruler972
     * @return the MUMLATZ
     */
    fun get_MUMLATZ() = MUMLATZ

    /**
     * MUMLATZ setter
     * @author Chaosruler972
     * @param MUMLATZ the new MUMLATZ
     */
    fun set_MUMLATZ(MUMLATZ: String) {
        this.MUMLATZ = MUMLATZ
    }

    /**
     * MONAAT getter
     * @author Chaosruler972
     * @return the MONAAT
     */
    fun get_MONAAT() = MONAAT

    /**
     * MONAAT setter
     * @author Chaosruler972
     * @param MONAAT the new MONAAT
     */
    fun set_MONAAT(MONAAT: String) {
        this.MONAAT = MONAAT
    }

    /**
     * TGUVA getter
     * @author Chaosruler972
     * @return the TGUVA
     */
    fun get_TGUVA() = TGUVA

    /**
     * TGUVA setter
     * @author Chaosruler972
     * @param TGUVA the new TGUVA
     */
    fun set_TGUVA(TGUVA: String) {
        this.TGUVA = TGUVA
    }

    /**
     * SUG getter
     * @author Chaosruler972
     * @return the SUG
     */
    fun get_SUG() = TGUVA

    /**
     * SUG setter
     * @author Chaosruler972
     * @param SUG the new SUG
     */
    fun set_SUG(SUG: String) {
        this.SUG = SUG
    }

    /**
     * ALUT getter
     * @author Chaosruler972
     * @return the ALUT
     */
    fun get_ALUT() = try {
        ALUT!!.toDouble().toString()
    } catch (e: Exception) {
        ALUT
    }


    /**
     * ALUT setter
     * @author Chaosruler972
     * @param ALUT the new ALUT
     */
    fun set_ALUT(ALUT: String) {
        this.ALUT = ALUT
    }


    /**
     * ITEMTXT getter
     * @author Chaosruler972
     * @return the ITEMTXT
     */
    fun get_ITEMTXT() = ITEMTXT

    /**
     * ITEMTXT setter
     * @author Chaosruler972
     * @param ITEMTXT the new ITEMTXT
     */
    fun set_ITEMTXT(ITEMTXT: String) {
        this.ITEMTXT = ITEMTXT
    }


    /**
     * RECVERSION getter
     * @author Chaosruler972
     * @return the RECVERSION
     */
    fun get_RECVERSION() = RECVERSION

    /**
     * RECVERSION setter
     * @author Chaosruler972
     * @param RECVERSION the new RECVERSION
     */
    fun set_RECVERSION(RECVERSION: String) {
        this.RECVERSION = RECVERSION
    }

    /**
     * RECID getter
     * @author Chaosruler972
     * @return the RECID
     */
    fun get_RECID() = RECID

    /**
     * RECID setter
     * @author Chaosruler972
     * @param RECID the new RECID
     */
    fun set_RECID(RECID: String) {
        this.RECID = RECID
    }

    /**
     * USERNAME getter
     * @author Chaosruler972
     * @return the USERNAME
     */
    fun get_USERNAME() = USERNAME

    /**
     * USERNAME setter
     * @author Chaosruler972
     * @param USERNAME the new USERNAME
     */
    fun set_USERNAME(USERNAME: String) {
        this.USERNAME = USERNAME
    }


    /**
     * Identifies this takala data
     * @author Chaosruler972
     */
    override fun toString(): String {
        return ITEMTXT ?: ""
    }

    /**
     * Returns a copy of this dataclass
     * @author Chaosruler972
     * @return a copy of this dataclass
     */
    override fun copy(): takala_data = takala_data(PROJID, ITEMID, DATAAREAID, QTY,
            KOMA, BINYAN, DIRA, TEUR, MUMLATZ, MONAAT,
            TGUVA, SUG, ALUT, ITEMTXT, RECVERSION, RECID, USERNAME)

    override fun to_hashmap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[remote_takala_table_helper.KOMA] = get_KOMA() ?: ""
        map[remote_takala_table_helper.BINYAN] = get_BINYAN() ?: ""
        map[remote_takala_table_helper.DIRA] = get_DIRA() ?: ""
        map[remote_takala_table_helper.TEUR] = get_TEUR() ?: ""
        map[remote_takala_table_helper.MUMLATZ] = get_MUMLATZ() ?: ""
        map[remote_takala_table_helper.MONAAT] = get_MONAAT() ?: ""
        map[remote_takala_table_helper.TGUVA] = get_TGUVA() ?: ""
        map[remote_takala_table_helper.SUG] = get_SUG() ?: ""
        map[remote_takala_table_helper.ALUT] = get_ALUT() ?: ""
        map[remote_takala_table_helper.ITEMTXT] = get_ITEMTXT() ?: ""
        map[remote_takala_table_helper.RECVERSION] = get_RECVERSION() ?: ""
        map[remote_takala_table_helper.RECID] = get_RECID() ?: ""
        map[remote_takala_table_helper.ID] = get_projid() ?: ""
        map[remote_takala_table_helper.ITEMID] = get_ITEMID() ?: ""
        map[remote_takala_table_helper.DATAAREAID] = get_DATAAREAID() ?: ""
        map[remote_takala_table_helper.QTY] = get_QTY() ?: ""
        return map
    }

    /**
     * to local sql hashmap
     * @author Chaosruler972
     * @return local sql hashmap
     */
    override fun to_sql_hashmap(): HashMap<String, String> {
        val data: HashMap<String, String> = HashMap()
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.ID] = (get_projid() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.ITEMID] = (get_ITEMID() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.DATAAREAID] = (get_DATAAREAID() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.QTY] = (get_QTY() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.KOMA] = (get_KOMA() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.BINYAN] = (get_BINYAN() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.DIRA] = (get_DIRA() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.TEUR] = (get_TEUR() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.MUMLATZ] = (get_MUMLATZ() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.MONAAT] = (get_MONAAT() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.TGUVA] = (get_TGUVA() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.SUG] = (get_SUG() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.ALUT] = (get_ALUT() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.ITEMTXT] = (get_ITEMTXT() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.RECVERSION] = (get_RECVERSION() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.RECID] = (get_RECID() ?: "")
        data[global_variables_dataclass.DB_SALPROJTAKALA!!.USERNAME] = (get_USERNAME())
        return data
    }

}