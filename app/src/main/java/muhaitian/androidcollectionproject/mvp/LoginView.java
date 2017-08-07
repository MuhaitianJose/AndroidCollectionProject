package muhaitian.androidcollectionproject.mvp;

import muhaitian.androidcollectionproject.mvc.Student;

/**
 * Created by muhaitian on 2017/8/7.
 */

public interface LoginView {
    String getAccount();

    String getPassword();

    void loginSuccess(Student user);

    void showNetworkError();

    void showVerifyFailed();
}
