package ms_alum.azrieli_college_of_engineering.com.msamanager;

/**
 * Created by Chaosruler on 9/17/2017.
 */

public class User
{
    private String __username;
    private String __password;
    public User(String username,String password)
    {
        this.__username=username;
        this.__password=password;
    }

    public String get__username() {
        return __username;
    }

    public void set__username(String __username) {
        this.__username = __username;
    }

    public String get__password() {
        return __password;
    }

    public void set__password(String __password) {
        this.__password = __password;
    }

    public String toString()
    {
        if(this.__username == null)
            return "";
        return this.__username;
    }
}
