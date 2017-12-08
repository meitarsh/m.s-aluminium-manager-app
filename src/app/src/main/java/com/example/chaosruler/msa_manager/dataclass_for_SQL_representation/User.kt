package com.example.chaosruler.msa_manager.dataclass_for_SQL_representation

/*
    an object representation of a user
 */
class User(private var __username: String, private var __password: String) {


    /*
        identifies user
     */
    override fun toString(): String
    {
        return this.__username
    }

    /*
    get username
     */
    fun get__username(): String
    {
        return this.__username
    }

    /*
        set username
     */
    fun set__username(username: String)
    {
        this.__username = username
    }

    /*
        get password
     */
    fun get__password(): String
    {
        return __password
    }

    /*
        set password
     */
    fun set__password(password: String)
    {
        this.__password = password
    }
}
