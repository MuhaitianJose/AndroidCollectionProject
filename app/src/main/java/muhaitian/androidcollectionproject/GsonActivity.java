package muhaitian.androidcollectionproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import muhaitian.androidcollectionproject.jsontest.GsonTestDemo;

/**
 * Created by muhaitian on 2017/8/15.
 */

public class GsonActivity extends AppCompatActivity {

    private static final String TAG = "GsonActivity";

    private GsonTestDemo gsonTestDemo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gjso_mainview);
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        gsonTestDemo = new GsonTestDemo();
//        gsonTestDemo.basicTypes();
//        gsonTestDemo.objectToString();
//        gsonTestDemo.JsonToObject();
//        try {
//            gsonTestDemo.flowDeserialization();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gsonTestDemo.serializationAutomaticity();
//        try {
//            gsonTestDemo.serializationManua();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gsonTestDemo.gsonBuilder();
        gsonTestDemo.useTypeAdapter();
//        gsonTestDemo.useJsonSerializerOrDeserializer();
        gsonTestDemo.annotations();
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
