package com.example.chaosruler.msa_manager.abstraction_classes

/**
 * Created by chaosruler on 12/8/17.
 */
interface table_dataclass {
    /*
        to identify it
     */
    override public fun toString():String

    /*
        to copy it
     */
    public fun copy(): table_dataclass
}