package ms_alum.azrieli_college_of_engineering.com.msamanager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.Vector;

/*

    This is a database of the local user database that is stored on the application file data, the local user database is in chrage of importing and exporting user data into the
    auto-complete mechanism present in the application in the format of a spinner
 */

public class user_database_helper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Users.db";
    public static final String DATABASE = "Users";
    public static final String USERS_ID = "id";
    public static final String PASSWORD = "password";
    public user_database_helper(Context context)
    {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table "+DATABASE+" ("+USERS_ID+" text primary key, "+PASSWORD+" text" + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE);
        onCreate(db);
    }

    public void dropDB(Context con) // subroutine to delete the entire database, including the file
    {
        if(con == null)
            return;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE);
        db.close();
    }
    public void createDB(Context con)
    {
        if(con == null)
            return;
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
        db.close();
    }
    public boolean add_user(Context con, String username, String password) // subroutine that manages the user adding operation to the database
    {
            if(username==null || password==null || username.length()==0 || password.length()==0 || con == null)
                return false;
            SQLiteDatabase db = this.getReadableDatabase();
            if(check_user(con,username) == true) // checks if user exists in database
            {
                update_user(con,username,password); // if it does, lets update its password
            }
            else // if it doesn't lets create a new entry for the user
            {
                insert_user(con,username,password);
            }
            db.close();
            return true;

    }
    public boolean check_user(Context con, String username) // subroutine to check if users exists on the database
    {
        if(username == null || username.length() == 0 || con==null)
            return false;
        User user = get_user_by_id(con,username);
        if(user != null)
            return true;
        return false;
    }
    private boolean insert_user(Context con, String username, String password) // subroutine to insert a user to the database
    {
        if(username==null || password==null || username.length()==0 || password.length() == 0 || con == null)
            return false;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_ID, username);
        values.put(PASSWORD,password);
        database.insert(DATABASE, null, values);
        database.close();
        return true;
    }
    public boolean update_user(Context con, String username, String password) // subroutine to update data of a user that exists on the database
    {
        if(username == null || password==null || username.length() == 0 || password.length() == 0 || con==null)
            return false;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD,password);
        database.update(DATABASE,values,USERS_ID + "=?",new String[] {username});
        database.close();
        return true;

    }
    public boolean delete_user(Context con, String username) // subroutine to delete a user from the database (local)
    {
        if(username == null || username.length() == 0 || con == null)
            return false;
        if(check_user(con,username)==false)
            return false;
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DATABASE,USERS_ID + "=?",new String[] {username});
        database.close();
        return true;
    }
    public Vector<User> get_entire_db(Context con) // subroutine to get the entire database as an iterateable vector
    {
        if(con == null)
            return null;
        SQLiteDatabase db = this.getReadableDatabase();
        Vector<User> users = new Vector<>();
        Cursor c = db.rawQuery("SELECT * FROM "+DATABASE,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            User usr = new User(c.getString(c.getColumnIndex(USERS_ID)),c.getString(c.getColumnIndex(PASSWORD)));
            users.add(usr);
            c.moveToNext();
        }
        db.close();
        return users;
    }
    public User get_user_by_id(Context con, String username) // subroutine to get a User object representing a user by the user id (username)
    {
        if(username==null || username.length() == 0 || con == null)
            return null;
        Vector<User> users = get_entire_db(con);
        for(int i=0; i<users.size(); i++)
        {
            if(users.elementAt(i).get__username() != null && users.elementAt(i).get__username().equals(username))
                return users.elementAt(i);
        }
        return null;
    }

}
