package muhaitian.opensourcelibrary.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by muhaitian on 2017/8/16.
 */

public class RetrofitService {

    public interface RetrofitGetService {
        @GET("blog/{id}")
        Call<ResponseBody> getData(@Path("id")int id);
    }

    public interface RetrofitPostService {

    }

    public interface RetrofitPutService {

    }

    public interface RetrofitDeleteService {

    }

    public interface RetrofitPatchService {

    }

    public interface RetrofitHeadService {

    }

    public interface RetrofitOptionService {

    }

}
