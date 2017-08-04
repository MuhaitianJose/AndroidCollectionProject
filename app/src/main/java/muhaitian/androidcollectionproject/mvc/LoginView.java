package muhaitian.androidcollectionproject.mvc;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import muhaitian.androidcollectionproject.R;

/**
 * Created by muhaitian on 2017/8/4.
 */

public class LoginView implements View.OnClickListener, LoginModel.LoginStatusChange {

    private static final String TAG = "LoginView";

    private AppCompatActivity appCompatActivity;
    private LoginModel model;
    private LoginViewListener mLoginViewListener;

    private TextInputLayout userName, passwd;
    private Button submit;
    private TextView loginResultsTips;

    public LoginView(AppCompatActivity activity, LoginViewListener viewListener) {
        appCompatActivity = activity;
        mLoginViewListener = viewListener;
        userName = (TextInputLayout) appCompatActivity.findViewById(R.id.Title_UserName);
        passwd = (TextInputLayout) appCompatActivity.findViewById(R.id.Title_Passwd);
        submit = (Button) appCompatActivity.findViewById(R.id.Submit);
        loginResultsTips = (TextView) appCompatActivity.findViewById(R.id.loginResultsTips);
        submit.setOnClickListener(this);
    }

    public void setLogModel(LoginModel model) {
        this.model = model;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: v==" + v);
        if (v.getId() == R.id.Submit) {
            Student student = new Student();
            Log.d(TAG, "onClick: Username = " + userName.getEditText().getText().toString());
            Log.d(TAG, "onClick: Password = " + passwd.getEditText().getText().toString());
            student.setName(userName.getEditText().getText().toString());
            student.setPassward(passwd.getEditText().getText().toString());
            mLoginViewListener.loginAccount(student);
        }


    }

    @Override
    public void loginResults(String results) {
        Log.d(TAG, "loginResults: ");
        if (results.equals("successful")) {
            Toast.makeText(appCompatActivity, "login successful", Toast.LENGTH_LONG).show();

        } else if (results.equals("failure")) {
            Toast.makeText(appCompatActivity, "login successful", Toast.LENGTH_LONG).show();
        }
    }

    public interface LoginViewListener {
        void loginAccount(Student student);
    }

    public void showLoginResults(String result) {
        Log.d(TAG, "showLoginResults: ");
        if (result.equals("successful")) {
            loginResultsTips.setText("login successfully root.name==" + model.getRoot().getName() + " || root.passwd=" + model.getRoot().getPassward());
        } else if (result.equals("failure")) {
            loginResultsTips.setText("login failure root.name==" + model.getRoot().getName() + " || root.passwd=" + model.getRoot().getPassward());
        }
    }


}
