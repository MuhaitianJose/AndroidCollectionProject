package muhaitian.androidcollectionproject.jsontest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import muhaitian.androidcollectionproject.UserTypeAdapter;
import muhaitian.androidcollectionproject.model.Result;
import muhaitian.androidcollectionproject.model.User;

/**
 * Created by muhaitian on 2017/8/15.
 */

public class GsonTestDemo {

    private static final String TAG = "GsonTestDemo";

    private Gson gson;

    public GsonTestDemo() {
        gson = new Gson();
        int gson_int = gson.fromJson("12", int.class);

    }

    /**
     * 基本类型的使用
     */
    public void basicTypes() {
        int gson_int = gson.fromJson("12", int.class);
        Log.d(TAG, "basicTypes: gson_int==" + gson_int);
        double gson_double = gson.fromJson("\"12.456\"", double.class);
        Log.d(TAG, "basicTypes: gson_double==" + gson_double);
        boolean gson_boolean = gson.fromJson("false", boolean.class);
        Log.d(TAG, "basicTypes: gson_boolean=" + gson_boolean);
        String gson_string = gson.fromJson("muhaitian", String.class);
        Log.d(TAG, "basicTypes: gson_string==" + gson_string);
        short gson_short = gson.fromJson("12", short.class);
        Log.d(TAG, "basicTypes: gson_short==" + gson_short);
        long gson_long = gson.fromJson("1289898", long.class);
        Log.d(TAG, "basicTypes: gson_long==" + gson_long);

    }

    /**
     * 对象转化成String
     */
    public void objectToString() {
        User user = new User("wangkang", 28);
        String userString = gson.toJson(user);
        Log.d(TAG, "GsonTestDemo: userString==" + userString);
    }

    /**
     * String 转化成对象
     * <p>
     * 当上面的三个属性(email_address、email、emailAddress)都中出现任意一个时均可以得到正确的结果。
     * 注：当多种情况同时出时，以最后一个出现的值为准。
     */
    public void JsonToObject() {
        String gjsonString = "{\"name\":\"怪盗kidou\",\"age\":24,\"emailAddress\":\"ikidou_1@example.com\",\"email\":\"ikidou_2@example.com\",\"email_address\":\"ikidou_3@example.com\"}";
        User user = gson.fromJson(gjsonString, User.class);

        Log.d(TAG, "JsonToObject: user.emailAddress=" + user.emailAddress);
    }

    /**
     * Gson泛型(genericity),泛型使用
     */
    public void gsongenericity() {
        String str = "[\"android\",\"java\",\"gson\"]";
        String[] stringarray = gson.fromJson(str, String[].class);
        Log.d(TAG, "gsongenericity: stringarray=" + stringarray);
        List<String> strList = gson.fromJson(str, new TypeToken<List<String>>() {
        }.getType());
        Log.d(TAG, "gsongenericity: strList==" + strList);
    }

    /**
     * Gson流式手动反序列化
     */
    public void flowDeserialization() throws IOException {
        String json = "{\"name\":\"怪盗kidou\",\"age\":\"24\",\"emailAddress\":\"mmmmm@meali\"}";
        User user = new User();
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String str = jsonReader.nextName();
            Log.d(TAG, "flowDeserialization: str=" + str);
            switch (str) {
                case "name":
                    user.userName = jsonReader.nextString();
                    break;
                case "age":
                    user.age = jsonReader.nextInt();
                    break;
                case "emailAddress":
                    user.emailAddress = jsonReader.nextString();
                    break;
            }

        }
        jsonReader.endObject();
        Log.d(TAG, "flowDeserialization: " + user.userName);
        Log.d(TAG, "flowDeserialization: " + user.age);
        Log.d(TAG, "flowDeserialization: " + user.emailAddress);
    }

    /**
     * Gson自动序列化
     */
    public void serializationAutomaticity() {
        User user = new User("muhaitian", 89, "muhatian@qq.com");
        gson.toJson(user);
        Log.d(TAG, "serialization: " + gson.toJson(user));
    }

    /**
     * Gson手动序列化
     */
    public void serializationManua() throws IOException {
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(System.out));
        jsonWriter.beginObject().name("name").value("muhaitian").name("age").value(89).name("emailAddress").value("kangkang@gmail.com").endObject();
        jsonWriter.flush();
    }

    /**
     * 使用GsonBuilder导出null值、格式化输出、日期时间
     */
    public void gsonBuilder() {
        Gson mmGson = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting().create();


        User user = new User("muhaitian", 56);
        Log.d(TAG, "gsonBuilder: " + mmGson.toJson(user));
    }

    /**
     * TypeAdapter
     * <p>
     * TypeAdapter 是Gson自2.0（源码注释上说的是2.1）开始版本提供的一个抽象类，用于接管某种类型的序列化和反序列化过程，
     * 包含两个注要方法 write(JsonWriter,T) 和 read(JsonReader) 其它的方法都是final方法并最终调用这两个抽象方法。
     */
    public void useTypeAdapter() {
        User user = new User("muahaitian", 23);
        user.emailAddress = "kljlhld@126.com";
        Gson mGson = new GsonBuilder().serializeNulls().registerTypeAdapter(User.class, new UserTypeAdapter()).create();
        Log.d(TAG, "useTypeAdapter: " + mGson.toJson(user));
    }

    /**
     * JsonSerializer与JsonDeserializer
     * <p>
     * JsonSerializer 和JsonDeserializer 不用像TypeAdapter一样，必须要实现序列化和反序列化的过程，你可以据需要选择，
     * 如只接管序列化的过程就用 JsonSerializer ，只接管反序列化的过程就用 JsonDeserializer
     */
    public void useJsonSerializerOrDeserializer() {
        JsonSerializer<User> jsonSerializer = new JsonSerializer<User>() {
            @Override
            public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
//                return new JsonPrimitive(String.valueOf(src));
                //序列化成Json对象
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("userName",src.userName);
                jsonObject.addProperty("age",src.age);
                jsonObject.addProperty("emailAddress",src.emailAddress);
                return jsonObject;
            }
        };
        User user = new User("muhaitian",45,"Jose@126.com");
        Gson mGson = new GsonBuilder().registerTypeAdapter(User.class,jsonSerializer).create();
        Log.d(TAG, "useJsonSerializerOrDeserializer: "+mGson.toJson(user));
        JsonDeserializer<User> jsonDeserializer = new JsonDeserializer<User>() {
            @Override
            public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return null;
            }
        };

    }
    /**
     *
     * @JsonAdatpter注解使用
     *
     */
    public void annotations(){
        User user = new User("muahaitian", 23);
        user.emailAddress = "annotations@126.com";
        Gson mGson = new GsonBuilder().serializeNulls().create();
        Log.d(TAG, "annotations: "+mGson.toJson(user));
    }
}

