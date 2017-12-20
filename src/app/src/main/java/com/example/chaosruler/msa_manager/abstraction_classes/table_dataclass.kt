package com.example.chaosruler.msa_manager.abstraction_classes


interface table_dataclass {
    /*
        to identify it
     */
    override fun toString(): String

    /*
        to copy it
     */
    fun copy(): table_dataclass
}