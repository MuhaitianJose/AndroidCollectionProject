package muhaitian.androidcollectionproject.mvp;

import muhaitian.androidcollectionproject.model.Student;

/**
 * Created by muhaitian on 2017/8/7.
 */

public class LoginModel {

    public void login(String name, String password, OnLoginResultListener listener) {
        if (Student.ROOT_NAME.equals(name) && Student.PASSWARD.equals(password)) {
            Student user = new Student();
            user.setName(name);
            user.setPassward(password);
            listener.loginSuccess(user);
        } else {
            listener.loginFailure();
        }

    }


    /**
     * 回调接口，登录结果回调
     */
    public interface OnLoginResultListener {
        void loginSuccess(Student user);

        void loginFailure();
    }
}
