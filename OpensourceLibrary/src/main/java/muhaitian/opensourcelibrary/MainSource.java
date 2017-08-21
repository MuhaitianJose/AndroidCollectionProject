package muhaitian.opensourcelibrary;

/**
 * Created by muhaitian on 2017/8/16.
 */


import android.appwidget.AppWidgetHost;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 方法注解包含：@GET、@POST、@PUT、@DELETE、@PATCH、@HEAD、@OPTIONS、@HTTP(可以替换前面七个以及其他扩展方法)。
 * 标记注解包含：@FormUrlEncoded、@Multipart、@Streaming。
 * 参数注解包含：@Path、@Query、@QueryMap、@Body、@Field、@FieldMap、@Part、@PartMap。
 * 其他参数包含：@Path、@Header、@Headers、@Url
 */

public class MainSource {
    /**
     *
     * HTTP有三个属性：method、path、hasbody。
     *
     */

    /**
     *
     * 标记类之FormUrlEncoded
     * 请求体是Form表单，登录界面使用：Content-Type:application/x-www-form-urlencoded
     * 标记类之Multipart
     * 请求体是支持文件上传的Form表单，上传文件使用：Content-Type:multipart/form-data
     * 标记类之Streaming
     * 未使用该注解，默认会把数据全部载入内存，之后通过流获取数据也是读取内存中的数据，所以返回数据较大是，需要使用该注解
     *
     */

    /**
     *
     * 参数类之Headers：添加请求头
     * 参数类之Header：添加不固定的Header
     * 参数类之Body：非表单请求体
     * 参数类之Field：表单字段，与FieldMap,FormUrlEncoded配合
     * 参数类之FieldMap：表单字段，与Field、FormUrlEncoded配合，接收Map<String,String></>类型，非String类型会调用toString()方法
     * 参数类之Part：表单字段，与PartMap配合，适合文件上传的情况
     * 参数类之PartMap：表单字段，与Part配合，适合文件上传情况；默认接受Map<String,RequestBody></>类型，非RequestBody会通过Converter转换
     * 参数类之Path：用于URL
     * 参数类之Query：用于URL
     * 参数类之QueryMap：用于URL
     * 参数类之Url：用于URL
     *
     */

    /**
     * 参数需要注意的
     * <p>
     * 1.Map用来组合复杂参数。
     * 2.Query、QueryMap和Field、FieldMap功能一样，生成的数据是一样的；
     * Query、QueryMap体现在Url；
     * Field、FieldMap的数据是请求体；
     * 3.{占位符}和Path尽量只用在URL的Path部分，url中的参数使用Query、QueryMap代替，保证接口的简洁。
     * 4.Query、Field、path支持数组和实现了Iterable接口的类型，如list、Set等。方便向后台传递数组
     */
    public interface IRetrofitInterface {

        /**
         * method 请求方法，不区分大小写
         * path 路径
         * hasBody 是否有请求体
         */
        @HTTP(method = "get", path = "blog/{id}", hasBody = false)
        Call<ResponseBody> httpDemo(@Path("id") int id);

        /**
         * 使用@Headers注解设置固定的请求头，所有请求头不会相互覆盖，即使名字相同
         */
        @Headers("Cache-Control:max-age=64000")
        @GET("widget/list")
        Call<List<AppWidgetHost>> headersDemo_01();

        @Headers({"Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-Sample-App"})
        @GET("user/{username}")
        Call<ResponseBody> headersDemo_02(@Path("username") String username);

        /**
         * 使用 @Header 注解动态更新请求头，匹配的参数必须提供给 @Header ，若参数值为 null ，
         * 这个头会被省略，否则，会使用参数值的 toString 方法的返回值。
         */
        @GET("user")
        Call<User> headerDemo(@Header("Authorization") String authorization);

        /**
         * 使用 @Body 注解，指定一个对象作为 request body 。
         */
        @POST("users/new")
        Call<User> bodyDemo(@Body User user);

        /**
         * 对象会被 Retrofit 实例中指定的转换器转换，若未添加转换器，只能使用 RequestBody ，如下
         */
        @POST("users/new")
        Call<ResponseBody> bodyDemo_(@Body User user);

        /**
         * Field 表单提交，如登录
         */
        @FormUrlEncoded
        @POST("v1/login")
        Call<ResponseBody> postLogin(@Path("userName") String username, @Path("Password") String password);

        /**
         *
         * FieldMap、Part、PartMap、Path
         *
         */
        @GET("blog/{id}")
        Call<ResponseBody> getBlog(@Path("id") int id);
        /**
         *
         * Query
         * 查询参数
         *
         */
        // 链接 http://baseurl/blog/id?sort=ShortStr
        @GET("blog/{id}")
        Call<ResponseBody> query(@Path("id") int id, @Query("sort")String sort);

        //传递数组
        @GET("blog/{id}")
        Call<ResponseBody> queryArray(@Path("id") int id,@Query("linked[]") String ... linked);

        /**
         *
         * QueryMap
         *
         */
        @GET("blog/{id}")
        Call<ResponseBody> queryMap(@Path("id") int id, @QueryMap Map<String ,String> querymap);

    }
}
