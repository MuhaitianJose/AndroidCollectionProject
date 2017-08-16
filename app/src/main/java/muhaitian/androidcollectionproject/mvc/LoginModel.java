package muhaitian.androidcollectionproject.mvc;

import android.util.Log;

import muhaitian.androidcollectionproject.model.Student;

/**
 * Created by muhaitian on 2017/8/4.
 */

public class LoginModel {
    private static final String TAG = "LoginModel";

    private LoginStatusChange mLoginStatusChange;
    private Student root;

    public LoginModel() {
        root = new Student();
        root.setName(Student.ROOT_NAME);
        root.setPassward(Student.PASSWARD);
    }

//    public LoginModel(LoginStatusChange statusChange) {
//        mLoginStatusChange = statusChange;
//    }

    public void setLoginStatusChange(LoginStatusChange loginStatusChange) {
        mLoginStatusChange = loginStatusChange;
    }

    public interface LoginStatusChange {
        void loginResults(String results);
    }

    public void login(Student student, LoginCallBack callBack) {
        Log.d(TAG, "login: student==" + student);
        if (student.getName().equals(root.getName()) && student.getPassward().equals(root.getPassward())) {
            mLoginStatusChange.loginResults("successful");
            callBack.loginsuccess();
        } else {
            mLoginStatusChange.loginResults("failure");
            callBack.loginfailure();
        }
    }

    public Student getRoot() {
        return root;
    }

    public interface LoginCallBack {
        void loginfailure();

        void loginsuccess();
    }
}
