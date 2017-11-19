package com.example.chaosruler.msa_manager

/*
    an object representation of a user
 */
class User() {

    private lateinit var  __username: String
    private lateinit var __password: String

    constructor(__username: String, __password: String) : this() {
        this.__username = __username
        this.__password = __password
    }

    override fun toString(): String
    {
        return this.__username
    }

    fun get__username(): String {
        return this.__username
    }

    fun set__username(username: String) {
        this.__username = username
    }

    fun get__password(): String {
        return __password
    }

    fun set__password(password: String) {
        this.__password = password
    }
}
