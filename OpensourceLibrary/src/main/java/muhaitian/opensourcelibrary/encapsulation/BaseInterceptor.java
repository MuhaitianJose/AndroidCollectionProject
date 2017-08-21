package muhaitian.opensourcelibrary.encapsulation;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by muhaitian on 2017/8/21.
 *
 * 构造基础拦截器
 *
 */

public class BaseInterceptor implements Interceptor {
    private Map<String, String> headers;

    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> set = headers.keySet();
            for (String headerKey : set) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }

        return chain.proceed(builder.build());
    }
}
