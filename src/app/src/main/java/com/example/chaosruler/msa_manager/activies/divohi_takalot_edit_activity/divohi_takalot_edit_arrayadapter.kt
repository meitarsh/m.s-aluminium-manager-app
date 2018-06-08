package com.example.chaosruler.msa_manager.activies.divohi_takalot_edit_activity

import android.app.Activity
import android.content.Intent
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.project_data.project_data
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*

/**
 * Array adapter responsible for populating the listview of table for divohi takalot (edit type) activity
 * @author Chaosruler972
 * @constructor the context and the list of data that we want to populate
 */
class divohi_takalot_edit_arrayadapter(
        /**
         * the context of the activity we are going to work with
         * @author Chaosruler972
         */
        private var context: Activity
        , arr: Vector<takala_data>) : ArrayAdapter<takala_data>(context, R.layout.item_divohi_takalot_edit,arr.toTypedArray())
{
    /**
     * inflates a view and generates it, writes the data in it and initates logic on press and edit
     * @author Chaosruler972
     * @param convertView the "listview" motherview
     * @param parent the parent that holds all the views together
     * @param position the position in the vector (auto iterate)
     * @return the view we inflated with all the logic initated
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_divohi_takalot_edit, parent, false)

        val mispar_parit = themer.get_view(convertView, R.id.item_divohi_takalot_edit_mispar_parit) as EditText
        val shem_parit = themer.get_view(convertView, R.id.item_divohi_takalot_edit_shem_parit) as EditText
        val mispar_project = themer.get_view(convertView, R.id.item_divohi_takalot_edit_mispar_project) as EditText
        val shem_project = themer.get_view(convertView, R.id.item_divohi_takalot_edit_shem_project) as EditText
        val kamot = themer.get_view(convertView, R.id.item_divohi_takalot_edit_kamot) as EditText
        val sog_takala = themer.get_view(convertView, R.id.item_divohi_takalot_edit_sog_takala) as EditText
        val koma = themer.get_view(convertView, R.id.item_divohi_takalot_edit_koma) as EditText
        val bnian = themer.get_view(convertView, R.id.item_divohi_takalot_edit_bnian) as EditText
//        val dira = themer.get_view(convertView, R.id.item_divohi_takalot_edit_dira) as EditText
        val tiaor_takala = themer.get_view(convertView, R.id.item_divohi_takalot_edit_tiaaor_takala) as EditText
        val peolot_ltikon = themer.get_view(convertView, R.id.item_divohi_takalot_edit_peolot_ltikon) as EditText
        val peolot_monoot = themer.get_view(convertView, R.id.item_divohi_takalot_edit_peolot_monoot) as EditText
        val tgovat_mnaal = themer.get_view(convertView, R.id.item_divohi_takalot_edit_tgovat_mnaal) as EditText
        val alot_takala = themer.get_view(convertView, R.id.item_divohi_takalot_edit_alot_takala) as EditText
//        val upload_btn = themer.get_view(convertView, R.id.item_divohi_takalot_edit_upload_btn) as Button


        val all_txtviews = Vector<View>()
        all_txtviews.add(mispar_parit)
        all_txtviews.add(shem_parit)
        all_txtviews.add(mispar_project)
        all_txtviews.add(shem_project)
        all_txtviews.add(kamot)
        all_txtviews.add(sog_takala)
        all_txtviews.add(koma)
        all_txtviews.add(bnian)
//        all_txtviews.add(dira)
        all_txtviews.add(tiaor_takala)
        all_txtviews.add(peolot_ltikon)
        all_txtviews.add(peolot_monoot)
        all_txtviews.add(tgovat_mnaal)
        all_txtviews.add(alot_takala)
//        all_txtviews.addElement(upload_btn)

        val takala_data: takala_data = getItem(position)
        val project_item: project_data = try {
            global_variables_dataclass.db_project_vec.filter { it.getProjID()?:"" == takala_data.get_projid()?:"" }[0]!!
        }
        catch (e: IndexOutOfBoundsException)
        {
            project_data(takala_data.get_projid()
                    ?: "", takala_data.get_projid() ?: "", "", "")
        }
        mispar_parit.hint = (takala_data.get_ITEMID() ?: "").trim()
        mispar_parit.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        shem_parit.hint = (takala_data.get_ITEMTXT() ?: "").trim()
        mispar_project.hint = (project_item.getProjID() ?: "").trim()
        mispar_project.isEnabled = false
        shem_project.hint = (project_item.get_project_name() ?: "").trim()
        kamot.hint = (takala_data.get_QTY() ?: "").trim()
        kamot.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        sog_takala.hint = (takala_data.get_SUG() ?: "").trim()
        sog_takala.isEnabled = false
        koma.hint = (takala_data.get_KOMA() ?: "").trim()
        bnian.hint = (takala_data.get_BINYAN() ?: "").trim()
//        dira.hint = (big_item.get_FLAT() ?: "").trim()
//        dira.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        tiaor_takala.hint = (takala_data.get_TEUR() ?: "").trim()
        tiaor_takala.isEnabled = false
        peolot_ltikon.hint = (takala_data.get_MUMLATZ() ?: "").trim()
        peolot_ltikon.isEnabled = false
        peolot_monoot.hint = (takala_data.get_MONAAT() ?: "").trim()
        peolot_monoot.isEnabled = false
        tgovat_mnaal.hint = (takala_data.get_TGUVA() ?: "").trim()
        tgovat_mnaal.isEnabled = false
        alot_takala.hint = (takala_data.get_ALUT() ?: "").trim()
        alot_takala.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

//        upload_btn.text= context.getString(R.string.divohi_takalot_upload)
//        upload_btn.background = context.getDrawable(R.drawable.button_sample1)

        mispar_parit.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || mispar_parit.text.isEmpty() )
                return@OnFocusChangeListener
            val str = mispar_parit.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.ITEMID] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_BINYAN(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context,mispar_parit)
            }).start()
            mispar_parit.hint = str.trim()
            mispar_parit.text.clear()
        }

        shem_parit.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || shem_parit.text.isEmpty())
                return@OnFocusChangeListener
            val str = shem_parit.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.ITEMTXT] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_ITEMTXT(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context, shem_parit)
            }).start()
            shem_parit.hint = str.trim()
            shem_parit.text.clear()
        }

        shem_project.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || shem_project.text.isEmpty() )
                return@OnFocusChangeListener
            val str = shem_project.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_projects_table_helper.NAME] = str
                remote_projects_table_helper.push_update(project_item, update_value, context)
                project_item.set_project_name(str)
                global_variables_dataclass.DB_project!!.add_to_table(project_item)
                themer.hideKeyboard(context,shem_project)
            }).start()
            shem_project.hint = str.trim()
            shem_project.text.clear()
        }

        kamot.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || kamot.text.isEmpty())
                return@OnFocusChangeListener
            val str = kamot.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.QTY] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_QTY(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context, kamot)
            }).start()
            kamot.hint = str.trim()
            kamot.text.clear()
        }


        koma.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || koma.text.isEmpty())
                return@OnFocusChangeListener
            val str = koma.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.KOMA] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_KOMA(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context, koma)
            }).start()
            koma.hint = str.trim()
            koma.text.clear()
        }

        bnian.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || bnian.text.isEmpty())
                return@OnFocusChangeListener
            val str = bnian.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.BINYAN] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_BINYAN(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context, bnian)
            }).start()
            bnian.hint = str.trim()
            bnian.text.clear()
        }


        alot_takala.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || alot_takala.text.isEmpty() )
                return@OnFocusChangeListener
            val str = alot_takala.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.ALUT] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_ALUT(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context,alot_takala)
            }).start()
            alot_takala.hint = str.trim()
            alot_takala.text.clear()
        }

//        upload_btn.setOnClickListener({
//            @Suppress("NAME_SHADOWING")
//            val parent = it.parent as View
//            val listview = parent.parent as ListView
//            val index = listview.getPositionForView(parent)
//            showFileChooser(index)
//        })
//
        peolot_ltikon.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || peolot_ltikon.text.isEmpty())
                return@OnFocusChangeListener
            val str = peolot_ltikon.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.MUMLATZ] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_MUMLATZ(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context, peolot_ltikon)
            }).start()
            peolot_ltikon.hint = str.trim()
            peolot_ltikon.text.clear()
        }

        peolot_monoot.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || peolot_monoot.text.isEmpty())
                return@OnFocusChangeListener
            val str = peolot_monoot.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_takala_table_helper.MONAAT] = str
                remote_takala_table_helper.push_update(takala_data, update_value, context)
                takala_data.set_MONAAT(str)
                global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
                themer.hideKeyboard(context, peolot_monoot)
            }).start()
            peolot_monoot.hint = str.trim()
            peolot_monoot.text.clear()
        }

        themer.center_all_views(all_txtviews)

        return convertView

    }

    /**
     *   shows the file chooser to choose a file
     *   @author Chaosruler972
     *   @param code the request code we will send to activity
     */
    @Suppress("unused")
    private fun showFileChooser(code:Int)
    {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try
        {
            context.startActivityForResult(
                    Intent.createChooser(intent, context.getString(R.string.divohi_takalot_choose_file)),
                    code)
        } catch (ex: android.content.ActivityNotFoundException)
        {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(context, context.getString(R.string.divohi_takalot_please_install),
                    Toast.LENGTH_SHORT).show()
        }

    }
}