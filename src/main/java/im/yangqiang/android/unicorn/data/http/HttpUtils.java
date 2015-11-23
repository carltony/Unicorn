package im.yangqiang.android.unicorn.data.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 网络工具
 * Created by Carlton on 15/9/28.
 */
public class HttpUtils
{
    private static IRequestHandler requestCallback = new NoneCallback();
    private static RequestQueue mRequestQueue;

    private static RequestManager request()
    {
        return RequestManager.instance();
    }

    public static RequestQueue getRequestQueue(Context context)
    {
        if (mRequestQueue == null)
        {
            synchronized (RequestManager.class)
            {
                if (mRequestQueue == null)
                {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return mRequestQueue;
    }

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
        Request request = request().postJson(isCache, tag, url, requestCallback.onParams(param), response);
        getRequestQueue(context).add(requestCallback.onRequest(request));
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
        getRequestQueue(context).add(request().postString(isCache, tag, url, requestCallback.onParams(param), response));
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
        getRequestQueue(context).add(request().requestString(isCache, tag, url, requestCallback.onParams(param), response));
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

    public static class NoneCallback implements IRequestHandler
    {

        @Override
        public Map<String, String> onParams(Map<String, String> param)
        {
            return param;
        }

        @Override
        public Request onRequest(Request request)
        {
            return request;
        }
    }

    /**
     * 取消
     *
     * @param context Context
     * @param tag     标签
     */
    public static void cancel(Context context, Object tag)
    {
        HttpLog.cancel(tag);
        getRequestQueue(context).cancelAll(tag);
    }

    /**
     * 取消
     *
     * @param context Context
     * @param filter  过滤
     */
    public static void cancel(Context context, RequestQueue.RequestFilter filter)
    {
        getRequestQueue(context).cancelAll(filter);
    }
}



