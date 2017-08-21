package muhaitian.opensourcelibrary.encapsulation;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;

/**
 * Created by muhaitian on 2017/8/21.
 */

public class PersistentCookieStore {

    private static final String TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "Cookies_Prefs";
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiesPrefs;

    public PersistentCookieStore(Context context) {
        cookiesPrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookies = new HashMap<>();
        //将持久化的cookies缓存到内存中，即map cookis
        Map<String,?> prefsMap = cookiesPrefs.getAll();

        for (Map.Entry<String,?> entry:prefsMap.entrySet()){

            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name:cookieNames){
                String encodeCookie = cookiesPrefs.getString(name,null);
                if(encodeCookie!=null){
                    Cookie cookie =
                }
            }

        }

    }

    /**
     *
     * 将字符串反序列化成cookies
     *
     * @param encodeCookie
     * @return
     */
    private Cookie decodeCookie(String encodeCookie){
        byte[] bytes = hexStringToByteArray(encodeCookie);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((Serria)objectInputStream.readObject())
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] hexStringToByteArray(String cookies){
        int len = cookies.length();
        byte[] data = new byte[len/2];
        for (int i=0;i<len;i+=2){
            data[i/2] = (byte)((Character.digit(cookies.charAt(i),16)<<4)+Character.digit(cookies.charAt(i+1),16));
        }
        return data;
    }
}
