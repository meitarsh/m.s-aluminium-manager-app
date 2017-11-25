package com.example.chaosruler.msa_manager


/*
    an object representation of a cache command (single)
 */
class cache_command(public val __command:String, public val __user:String)
{
    override fun toString(): String
    {
        return __command
    }


}