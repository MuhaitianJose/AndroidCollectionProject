package muhaitian.androidcollectionproject.mvc;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by muhaitian on 2017/8/4.
 */

public class LoginView implements View.OnClickListener, LoginModel.LoginStatusChange {

    private AppCompatActivity appCompatActivity;

    private LoginViewListener mLoginViewListener;

    public LoginView(AppCompatActivity activity, LoginViewListener viewListener) {
        appCompatActivity = activity;
        mLoginViewListener = viewListener;
    }

    @Override
    public void onClick(View v) {

        Student student = new Student();
        mLoginViewListener.loginAccount(student);

    }

    @Override
    public void loginResults(String results) {

    }

    public interface LoginViewListener {
        void loginAccount(Student student);
    }

}
