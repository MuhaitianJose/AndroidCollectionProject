package muhaitian.androidcollectionproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import muhaitian.androidcollectionproject.mvc.LoginModel;
import muhaitian.androidcollectionproject.mvc.LoginView;
import muhaitian.androidcollectionproject.mvc.Student;

/**
 * Created by wangkang001 on 2017/8/4.
 */

public class MvcActivity extends AppCompatActivity implements LoginView.LoginViewListener {

    private LoginView loginView;
    private LoginModel loginModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAllInfo();
    }

    private void initAllInfo() {
        loginView = new LoginView(this, this);
        loginModel = new LoginModel(loginView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void loginAccount(Student student) {
        loginModel.login(student);
    }
}
