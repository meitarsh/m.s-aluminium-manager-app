package com.example.chaosruler.msa_manager.object_types


/*
    an object representation of a cache command (single)
 */
class cache_command(val __command: String, val __user: String)
{
    /*
        identifies
     */
    override fun toString(): String
    {
        return __command
    }


}