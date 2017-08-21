package muhaitian.opensourcelibrary.encapsulation;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by muhaitian on 2017/8/21.
 */

public interface RetrofitApiService {

    public static final String BASE_URL = "http://ip.taobao.com/";

    /**
     * 普通写法
     *
     * @param ip
     * @return
     */
    @GET("service/getIptInfo.php")
    Observable<ResponseBody> getDate(@Query("ip") String ip);

    @GET("{url}")
    Observable<ResponseBody> executeGet(@Path("url") String url, @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @FieldMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(@Path("url") String url, @Body RequestBody body);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(@Path("url") String url, @Part("image\";filename\"image.jpg") RequestBody body);

    @POST("{url}")
    Observable<ResponseBody> uoLoadFiles(@Path("url") String url,
                                         @Path("Headers") Map<String, String> header,
                                         @Part("filename") String filename,
                                         @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@Url String fileUrl);

}
