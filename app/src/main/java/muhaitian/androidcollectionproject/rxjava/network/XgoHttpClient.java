package muhaitian.androidcollectionproject.rxjava.network;


import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by muhaitian on 2017/8/11.
 */

public class XgoHttpClient {

    private static final String TAG = "XgoHttpClient";

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    private static final XgoHttpClient XGO_HTTP_CLIENT = new XgoHttpClient();
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    static {
        OK_HTTP_CLIENT.setConnectTimeout(5000, TimeUnit.MILLISECONDS);
        //添加过滤日志
        OK_HTTP_CLIENT.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        });
    }

    public static XgoHttpClient getClient() {
        return XGO_HTTP_CLIENT;
    }

    /**
     * 同步模式,获取数据
     *
     * @param request
     * @return String(json)
     */
    public String execute2String(Request request) {

        String result = null;
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Request getRequest(String url, String method, Map<String, Object> params) {
        if (params == null) {
            params = new TreeMap<>();
        }

        Request.Builder request = new Request.Builder();

        if (XgoHttpClient.METHOD_GET.equalsIgnoreCase(method)) {
            request.url(initGetRequest(url, params)).get();
        } else if (XgoHttpClient.METHOD_DELETE.equalsIgnoreCase(method)) {
            if (params.size() == 0) {
                request.url(url).delete();
            } else {
                request.url(url).delete(initRequestBody(params));
            }

        } else if (XgoHttpClient.METHOD_POST.equalsIgnoreCase(method)) {
            request.url(url).post(initRequestBody(params));
        } else if (XgoHttpClient.METHOD_PUT.equalsIgnoreCase(method)) {
            request.url(url).put(initRequestBody(params));
        }

        return request.build();
    }

    /**
     * 初始化Get参数
     *
     * @param url
     * @param params
     * @return
     */
    private String initGetRequest(String url, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        if (params.size() > 0) {
            stringBuilder.append('?');
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            int count = 0;
            for (Map.Entry entry : entries) {
                count++;
                stringBuilder.append(entry.getKey()).append('=').append(entry.getValue().toString());
                if (count == params.size()) {
                    break;
                }

                stringBuilder.append('&');
            }
        }
        url = new String(stringBuilder);
        return url;
    }

    private RequestBody initRequestBody(Map<String, Object> params) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof File) {
                File file = (File) value;
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                String mineType = fileNameMap.getContentTypeFor(file.getAbsolutePath());
                Log.d(TAG, "initRequestBody: mineType==" + mineType);
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(mineType), file));
            } else {
                Log.d(TAG, "initRequestBody: value==" + value);
                builder.addFormDataPart(key, value.toString());
            }

        }
        return builder.build();
    }
}
