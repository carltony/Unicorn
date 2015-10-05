package im.yangqiang.android.unicorn.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.Map;

import im.yangqiang.android.unicorn.core.toolbox.SPUtils;
import im.yangqiang.android.unicorn.data.http.HttpUtils;
import im.yangqiang.android.unicorn.data.http.IRequestHandler;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 工具Application，包含常用的一些基本功能
 * Created by Carlton on 15/10/3.
 */
public class UtilApplication extends Application implements IRequestHandler
{
    public UtilApplication()
    {
        HttpUtils.setRequestCallback(this);
    }

    @Override
    public Map<String, String> getParams(Map<String, String> param)
    {
        return param;
    }

    /**
     * 使用POST方式提交Json数据
     *
     * @param isCache  是否使用缓存
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public void postJson(boolean isCache, Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        HttpUtils.postJson(isCache, this, tag, url, param, response);
    }

    /**
     * 使用POST方式提交Json数据，并使用缓存
     *
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public void postJson(Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        postJson(true, tag, url, param, response);
    }

    /**
     * POST方式提交String数据
     *
     * @param isCache  是否使用缓存
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public void postString(boolean isCache, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        HttpUtils.postString(isCache, this, tag, url, param, response);
    }

    /**
     * POST方式提交String数据,并使用缓存
     *
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public void postString(Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        postString(true, tag, url, param, response);
    }

    /**
     * GET方式提交String数据
     *
     * @param isCache  是否使用缓存
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public void requestString(boolean isCache, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        HttpUtils.requestString(isCache, this, tag, url, param, response);
    }

    /**
     * GET方式提交String数据，并使用缓存
     *
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public void requestString(Context context, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        requestString(true, tag, url, param, response);
    }

    /**
     * 返回SharedPreference编辑器
     *
     * @param name
     * @param mode
     * @return
     */
    public SharedPreferences.Editor getEditor(String name, int mode)
    {
        return SPUtils.getEditor(this, name, mode);
    }

    /**
     * 返回SharedPreference的私有编辑器
     *
     * @param name
     * @return
     */
    public SharedPreferences.Editor getEditor(String name)
    {
        return getEditor(name, MODE_PRIVATE);
    }

    public boolean saveBoolean(String name, String key, boolean value)
    {
        return saveBoolean(name, MODE_PRIVATE, key, value);
    }

    public boolean saveBoolean(String name, int mode, String key, boolean value)
    {
        return getEditor(name, mode).putBoolean(key, value).commit();
    }

    public boolean getBoolean(String name, int mode, String key, boolean defaultValue)
    {
        return getSharedPreferences(name, mode).getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String name, String key)
    {
        return getSharedPreferences(name, MODE_PRIVATE).getBoolean(key, false);
    }

    public boolean getBoolean(String name, String key, boolean defaultValue)
    {
        return getSharedPreferences(name, MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    /**
     * 保存String数据到私有SharedPreference
     *
     * @param name
     * @param key
     * @param value
     * @return
     */
    public boolean saveString(String name, String key, String value)
    {
        return saveString(name, MODE_PRIVATE, key, value);
    }

    /**
     * 保存String数据到SharedPreference
     *
     * @param name
     * @param mode
     * @param key
     * @param value
     * @return
     */
    public boolean saveString(String name, int mode, String key, String value)
    {
        return getEditor(name, mode).putString(key, value).commit();
    }

    /**
     * 返回SharedPreference中String数据
     *
     * @param name
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String name, int mode, String key, String defaultValue)
    {
        return getSharedPreferences(name, mode).getString(key, defaultValue);
    }

    /**
     * 返回SharedPreference中String数据，私有模型，默认值为：""
     *
     * @param name
     * @param key
     * @return
     */
    public String getString(String name, String key)
    {
        return getString(name, MODE_PRIVATE, key, "");
    }

    /**
     * 返回SharedPreference中int数据
     *
     * @param name
     * @param mode
     * @param key
     * @param defaultValue
     * @return
     */
    public int getInteger(String name, int mode, String key, int defaultValue)
    {
        return getSharedPreferences(name, mode).getInt(key, defaultValue);
    }

    public int getInteger(String name, String key, int defaultValue)
    {
        return getInteger(name, MODE_PRIVATE, key, defaultValue);
    }

    /**
     * 返回SharedPreference中String数据，私有模型，默认值为：0
     *
     * @param name
     * @param key
     * @return
     */
    public int getInteger(String name, String key)
    {
        return getInteger(name, MODE_PRIVATE, key, 0);
    }

    /**
     * 保存int数据到SharedPreference
     *
     * @param name
     * @param mode
     * @param key
     * @param value
     * @return
     */
    public boolean saveInteger(String name, int mode, String key, int value)
    {
        return getEditor(name, mode).putInt(key, value).commit();
    }

    /**
     * 保存int数据到私有SharedPreference
     *
     * @param name
     * @param key
     * @param value
     * @return
     */
    public boolean saveInteger(String name, String key, int value)
    {
        return saveInteger(name, MODE_PRIVATE, key, value);
    }

    /**
     * 清空SharedPreference数据
     *
     * @param name
     * @return
     */
    public boolean clearSharedPreference(String name)
    {
        return getEditor(name).clear().commit();
    }
}
