package com.example.chaosruler.msa_manager.abstraction_classes


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