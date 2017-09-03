package muhaitian.androidcollectionproject;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * 如果同时用@Inject注释两个对象，则会生成两个对象，如果想实现单例要用@Singleton 单例注解。
     * <p>
     * 需要在component和Module同时加注解@Singleton,否则会编译报错。
     */

//    @Inject
//    public Student student;
//    @Inject
//    public Student student2;

//    MainComponent mainComponent;
    @BindView(R.id.StartDagger2Activity)
    Button StartDagger2Activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule()).build();
//        mainComponent.inject(MainActivity.this);
//        Log.d(TAG, "onCreate: getName" + student);
//        Log.d(TAG, "onCreate: getPassward" + student2);

    }


    @OnClick(R.id.StartDagger2Activity)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked: ");
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("muhaitian.androidcollectionproject",
                "muhaitian.androidcollectionproject.dagger2.Dagger2Activity");
        intent.setComponent(componentName);
        startActivity(intent);
    }
}
