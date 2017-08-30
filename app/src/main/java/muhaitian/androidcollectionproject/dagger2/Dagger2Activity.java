package muhaitian.androidcollectionproject.dagger2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import muhaitian.androidcollectionproject.App;
import muhaitian.androidcollectionproject.R;
import muhaitian.androidcollectionproject.model.Student;

/**
 * Created by muhaitian on 2017/8/29.
 */

public class Dagger2Activity extends AppCompatActivity {

    private static final String TAG = "Dagger2Activity";
    @StudentForContext
    @Inject
    public Student student;
    @StudentForName
    @Inject
    public Student studentName;


//    MainComponent mainComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger2_view);
//        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext())).build();
//        mainComponent.inject(this);

//        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();

        ActivityComponent activityComponent = DaggerActivityComponent
                .builder()
                .appComponent(App.appComponent)
                .activityModule(new ActivityModule())
                .build();
        activityComponent.inject(this);
        Log.d(TAG, "onCreate: student" + student.getName());
        Log.d(TAG, "onCreate: studentName" + studentName.getName());

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
}
