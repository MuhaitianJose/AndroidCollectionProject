package muhaitian.androidcollectionproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import muhaitian.androidcollectionproject.mvc.Student;
import muhaitian.androidcollectionproject.mvp.LoginPresenter;
import muhaitian.androidcollectionproject.mvp.LoginView;

/**
 * Created by wangkang001 on 2017/8/4.
 */

public class MvpActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private TextInputLayout TitleName;
    private TextInputLayout TitlePassword;
    private Button Login;
    private TextView LoginTips;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mvp_main_view);

        initView();
    }

    private void initView() {
        TitleName = (TextInputLayout) findViewById(R.id.Title_mvp_name);
        TitlePassword = (TextInputLayout) findViewById(R.id.Title_mvp_password);
        Login = (Button) findViewById(R.id.Mvp_Login);
        LoginTips = (TextView) findViewById(R.id.loginTips);
        Login.setOnClickListener(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.setLoginView(this);
    }

    @Override
    public String getAccount() {
        return TitleName.getEditText().getText().toString();
    }

    @Override
    public String getPassword() {
        return TitlePassword.getEditText().getText().toString();
    }

    @Override
    public void loginSuccess(Student user) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, "当前网络错误", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showVerifyFailed() {
        Toast.makeText(this, "输入的账号或者密码错误", Toast.LENGTH_LONG).show();
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
    public void onClick(View v) {
        if (v.getId() == R.id.Mvp_Login) {
            loginPresenter.login();
        }
    }
}
