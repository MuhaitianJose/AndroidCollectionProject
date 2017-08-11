package muhaitian.rxjavaessentialsdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import muhaitian.rxjavaessentialsdemo.model.AppInfo;
import muhaitian.rxjavaessentialsdemo.model.AppInfoInterface;

public class MainActivity extends AppCompatActivity implements AppInfoInterface.IAppInfoView{


    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    public void reFreshListView(List<AppInfo> list) {
        Observable.fromArray("").filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<String>() {
            @Override
            public boolean test(String s) {
                return false;
            }
        });

    }
}
