package com.example.chaosruler.msa_manager

/**
 * Created by chaosruler on 11/18/17.
 */
class cache_command(public val __command:String, public val __user:String)
{
    override fun toString(): String
    {
        return __command
    }
}