package muhaitian.androidcollectionproject.mvp;


import android.text.TextUtils;

import muhaitian.androidcollectionproject.mvc.Student;

/**
 * Created by muhaitian on 2017/8/7.
 */

public class LoginPresenter {
    /**
     * 数据处理者
     */
    private LoginModel loginModel;

    public LoginPresenter() {
        loginModel = new LoginModel();
    }

    //视图接口对象
    private LoginView loginView;

    //绑定视图接口对象
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login() {
        String Account = loginView.getAccount();
        String Password = loginView.getPassword();
        if (checkParameter(Account, Password)) {
            doSomePrepare();
            loginModel.login(Account, Password, new LoginModel.OnLoginResultListener() {
                @Override
                public void loginSuccess(Student user) {
                    loginView.loginSuccess(user);
                }

                @Override
                public void loginFailure() {
                    loginView.showVerifyFailed();
                }
            });
        }

    }

    private void doSomePrepare() {

    }

    private boolean checkParameter(String name, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            loginView.showVerifyFailed();
            return false;
        } else if (!checkNetwork()) {
            loginView.showNetworkError();
            return false;
        }
        return true;
    }

    private boolean checkNetwork() {
        return true;
    }

}
