package com.example.chaosruler.msa_manager.abstraction_classes

import android.content.Context


abstract class remote_helper
{


        /*
        to init variables
     */
        public abstract fun init_variables(context: Context)

        /*
        to make a type map for table
     */
        abstract fun make_type_map(): HashMap<String, String>

        /*
                pushes an update to db
         */
        protected abstract fun push_update(obj: table_dataclass, map:HashMap<String,String>, context: Context)
}