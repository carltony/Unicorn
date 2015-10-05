package im.yangqiang.android.unicorn.data.http;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 网络工具
 * Created by Carlton on 15/9/28.
 */
public class HttpUtils
{
    private static IRequestHandler requestCallback = new NoneCallback();

    public static void setRequestCallback(IRequestHandler requestCallback)
    {
        HttpUtils.requestCallback = requestCallback;
    }

    /**
     * 使用POST方式提交Json数据
     *
     * @param isCache  是否使用缓存
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public static void postJson(boolean isCache, Context context, Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        RequestUtils.postJson(isCache, context, tag, url, requestCallback.getParams(param), response);
    }

    /**
     * 使用POST方式提交Json数据，并使用缓存
     *
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public static void postJson(Context context, Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        postJson(true, context, tag, url, param, response);
    }

    /**
     * POST方式提交String数据
     *
     * @param isCache  是否使用缓存
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public static void postString(boolean isCache, Context context, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        RequestUtils.postString(isCache, context, tag, url, requestCallback.getParams(param), response);
    }

    /**
     * POST方式提交String数据,并使用缓存
     *
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public static void postString(Context context, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        postString(true, context, tag, url, param, response);
    }

    /**
     * GET方式提交String数据
     *
     * @param isCache  是否使用缓存
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public static void requestString(boolean isCache, Context context, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        RequestUtils.requestString(isCache, context, tag, url, requestCallback.getParams(param), response);
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
    public static void requestString(Context context, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        requestString(true, context, tag, url, param, response);
    }
}


class NoneCallback implements IRequestHandler
{

    @Override
    public Map<String, String> getParams(Map<String, String> param)
    {
        return new HashMap<>();
    }
}
