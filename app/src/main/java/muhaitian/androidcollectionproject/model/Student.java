package muhaitian.androidcollectionproject.model;

import java.io.Serializable;

/**
 * Created by muhaitian on 2017/8/4.
 */

public class Student implements Serializable{
    public static final String ROOT_NAME = "Muhaitian";
    public static final String PASSWARD = "kangkang";

    private String name;
    private String passward;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}
