package ms_alum.azrieli_college_of_engineering.com.msamanager;



public class Project
{
    private String __name;
    private String __manager_name;
    private String __status;
    private int __id;
    public Project(String name, String manager_name, String status, int id)
    {
        this.__name = name;
        this.__manager_name = manager_name;
        this.__status = status;
        this.__id = id;
    }

    public String get__name() {
        return __name;
    }

    public void set__name(String __name) {
        this.__name = __name;
    }

    public String get__manager_name() {
        return __manager_name;
    }

    public void set__manager_name(String __manager_name) {
        this.__manager_name = __manager_name;
    }

    public String get__status() {
        return __status;
    }

    public void set__status(String __status) {
        this.__status = __status;
    }

    public int get__id() {
        return __id;
    }

    public void set__id(int __id) {
        this.__id = __id;
    }

    public String toString()
    {
        return this.__name;
    }
}
