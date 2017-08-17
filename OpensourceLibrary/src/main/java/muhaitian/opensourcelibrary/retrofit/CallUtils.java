package muhaitian.opensourcelibrary.retrofit;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by muhaitian on 2017/8/17.
 */

public class CallUtils {
    public static class CustomCall<R> {
        public final Call<R> call;

        public CustomCall(Call<R> call) {
            this.call = call;
        }

        public R get() throws IOException {
            return call.execute().body();
        }
    }




}
