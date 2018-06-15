package com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper

import com.example.chaosruler.msa_manager.abstraction_classes.local_table_enum

object local_user_enum : local_table_enum()
{
    const val USERS_ID = 0
    const val PASSWORD = 1
    const val USER_LAST_SYNC = 2
}