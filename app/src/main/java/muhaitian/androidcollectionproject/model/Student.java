package muhaitian.androidcollectionproject.model;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * Created by muhaitian on 2017/8/4.
 */

public class Student implements Serializable{

    private static final String TAG = "Student";

    public static final String ROOT_NAME = "Muhaitian";
    public static final String PASSWARD = "kangkang";

    private String name;
    private String passward;

    private Context mContext;

    /**
     * 类中的这个注解的作用：先判断module是否有实例化对象的方法（@Provides注解的方法），如果有返回，结束。如果没有则
     * 找该类的构造方法，是否有带有@Inject的构造方法，如果存在则返回。
     *
     */
    @Inject
    public Student(Context context){
        Log.d(TAG, "Student: context=="+context);
        mContext = context;
        name = "muhatian_001";
        passward = "123456789";

    }

    public Student(){

    }

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
