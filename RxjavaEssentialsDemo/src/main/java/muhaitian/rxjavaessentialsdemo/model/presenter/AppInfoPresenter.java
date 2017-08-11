package muhaitian.rxjavaessentialsdemo.model.presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.functions.Consumer;
import muhaitian.rxjavaessentialsdemo.model.AppInfo;
import muhaitian.rxjavaessentialsdemo.model.AppInfoInterface;
import muhaitian.rxjavaessentialsdemo.model.AppInfoInterface.*;
import muhaitian.rxjavaessentialsdemo.model.AppInfoModel;

/**
 * Created by muhaitian on 2017/8/9.
 */

public class AppInfoPresenter {

    private IAppInfoView mIAppInfoView;
    private IAppIfoModel mIAppIfoModel;
    private Context mContext;

    public AppInfoPresenter(Context context) {
        mContext = context;
        mIAppIfoModel = new AppInfoModel(mContext);
    }

    public void setIAppInfoView(IAppInfoView mIAppInfoView) {
        this.mIAppInfoView = mIAppInfoView;
    }

    public void showList() {
        mIAppIfoModel.getApps().toSortedList().subscribe(new Consumer<List<AppInfo>>() {
            @Override
            public void accept(List<AppInfo> appInfos) throws Exception {
                mIAppInfoView.reFreshListView(appInfos);
            }
        });
    }
}
