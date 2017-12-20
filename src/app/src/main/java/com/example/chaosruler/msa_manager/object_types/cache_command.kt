package com.example.chaosruler.msa_manager.object_types


/*
    an object representation of a cache command (single)
 */
class cache_command(public val __command:String, public val __user:String)
{
    /*
        identifies
     */
    override fun toString(): String
    {
        return __command
    }


}