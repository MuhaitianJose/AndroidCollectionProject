package muhaitian.opensourcelibrary.encapsulation;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

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
        Map<String, ?> prefsMap = cookiesPrefs.getAll();

        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {

            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                String encodeCookie = cookiesPrefs.getString(name, null);
                if (encodeCookie != null) {
                    Cookie cookie = decodeCookie(encodeCookie);
                    if (cookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, cookie);
                    }
                }
            }

        }

    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        //将Cookies缓存到内存中，如果缓存过期，就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            }

        }
        //将cookies持久化到本地
        SharedPreferences.Editor Prefeditor = cookiesPrefs.edit();
        Prefeditor.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        Prefeditor.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
        Prefeditor.apply();
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    public boolean removeAll() {
        SharedPreferences.Editor PrefsEditor = cookiesPrefs.edit();
        PrefsEditor.clear();
        PrefsEditor.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)) {
            cookies.get(url.host()).remove(name);

            SharedPreferences.Editor PrefsEditor = cookiesPrefs.edit();
            if (cookiesPrefs.contains(name)) {
                PrefsEditor.remove(name);
            }
            PrefsEditor.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            PrefsEditor.apply();
            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet()) {
            ret.addAll(cookies.get(key).values());
        }
        return ret;
    }

    /**
     * 将cookie序列化成String
     *
     * @param cookie
     * @return
     */
    protected String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayToHexStriing(byteArrayOutputStream.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param encodeCookie
     * @return
     */
    private Cookie decodeCookie(String encodeCookie) {
        byte[] bytes = hexStringToByteArray(encodeCookie);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 十六进制字符串转化成二进制数组
     *
     * @param cookies
     * @return
     */
    private byte[] hexStringToByteArray(String cookies) {
        int len = cookies.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(cookies.charAt(i), 16) << 4) + Character.digit(cookies.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 二进制数组转化成十六进制字符串
     *
     * @param bytes
     * @return
     */
    protected String byteArrayToHexStriing(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }
}
