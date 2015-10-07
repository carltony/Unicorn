package im.yangqiang.android.unicorn.core;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

import im.yangqiang.android.unicorn.data.http.HttpUtils;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 模型基类
 * Created by Carlton on 15/10/4.
 */
public class UinModel
{
    private Context mContext;

    public UinModel(Context context)
    {
        mContext = context;
    }
    public Context getContext()
    {
        return mContext;
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
        HttpUtils.postJson(isCache, mContext, tag, url, param, response);
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
        HttpUtils.postString(isCache, mContext, tag, url, param, response);
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
        HttpUtils.requestString(isCache, mContext, tag, url, param, response);
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
}
