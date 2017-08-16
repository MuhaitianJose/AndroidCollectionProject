package muhaitian.androidcollectionproject.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import muhaitian.androidcollectionproject.UserTypeAdapter;

/**
 * Created by muhaitian on 2017/8/15.
 */

@JsonAdapter(UserTypeAdapter.class)
public class User {
    @SerializedName(value = "userName",alternate = {"name"})
    public String userName;
    public int age;
    @SerializedName(value = "email_Address",alternate = {"email","emailAddress"})
    public String emailAddress;

    public User() {
        this("");
    }

    public User(String name) {
        this(name, 0);

    }

    public User(String name, int age) {
        this(name, age, "");
    }

    public User(String name, int age, String email) {
        userName = name;
        this.age = age;
        emailAddress = email;
    }
}
