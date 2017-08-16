package muhaitian.androidcollectionproject;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import muhaitian.androidcollectionproject.model.User;

/**
 * Created by muhaitian on 2017/8/15.
 */

public class UserTypeAdapter extends TypeAdapter<User>{
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("userName").value(value.userName);
        out.name("age").value(value.age);
        out.name("emailAddress").value(value.emailAddress);
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        User user = new User();
        in.beginObject();
        while (in.hasNext()){
            String nextValue = in.nextName();
            switch (nextValue){
                case "userName":
                    user.userName = in.nextString();
                    break;
                case "age":
                    user.age = in.nextInt();
                    break;
                case "email_Address":
                case "email":
                case "emailAddress":
                    user.emailAddress = in.nextString();
                    break;

            }
        }
        in.endObject();
        return user;
    }
}
