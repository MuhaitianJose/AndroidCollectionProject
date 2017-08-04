package muhaitian.androidcollectionproject.mvc;

/**
 * Created by muhaitian on 2017/8/4.
 */

public class LoginModel {

    private LoginStatusChange mLoginStatusChange;
    private Student root;

    public LoginModel() {
        root = new Student();
    }

    public LoginModel(LoginStatusChange statusChange) {
        mLoginStatusChange = statusChange;
    }

    public interface LoginStatusChange {
        void loginResults(String results);
    }

    public void login(Student student) {
        if (student.getName().equals(root.getName()) && student.getPassward().equals(root.getPassward())) {
            mLoginStatusChange.loginResults("successful");
        } else {
            mLoginStatusChange.loginResults("failure");
        }
    }
}
