package im.yangqiang.android.unicorn.data.http;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.Map;

import im.yangqiang.android.unicorn.data.http.server.FileRequest;
import im.yangqiang.android.unicorn.data.http.server.HttpJsonRequest;
import im.yangqiang.android.unicorn.data.http.server.HttpStringRequest;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 网络请求工具类
 * Created by Carlton on 14-5-13.
 */
public class RequestManager
{
    private RequestManager()
    {
    }

    private static RequestManager instance;

    public static RequestManager instance()
    {
        if (instance == null)
        {
            synchronized (HttpUtils.class)
            {
                if (instance == null)
                {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    /**
     * 文件上传
     *
     * @param url      文件上传地址
     * @param param    参数
     * @param response 请求回调
     */
    public Request fileUpload(String url, Map<String, String> param, IResponse response)
    {
        FileRequest fileRequest = new FileRequest();
        Request request = fileRequest.request(url, param, response);
        request.setTag("file");
        return request;
    }

    /**
     * 下载文件
     *
     * @param url          文件地址
     * @param path         保存到本地的路径
     * @param loadListener 下载监听
     */
    public static void downloadFile(String url, String path, IProgressListener loadListener)
    {
        new HttpDownloader().downLoadFileInBack(url, path, loadListener);
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
    public Request postJson(boolean isCache, Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        HttpJsonRequest jsonResponse = new HttpJsonRequest();
        Request request = jsonResponse.request(url, param, response);
        request.setTag(tag);
        request.setShouldCache(isCache);
        return request;
    }

    /**
     * 使用POST方式提交Json数据，并使用缓存
     *
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public Request postJson(Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        return postJson(true, tag, url, param, response);
    }

    /**
     * 提交String数据
     *
     * @param method   数据提交方式
     * @param isCache  是否使用缓存
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    private Request stringRequest(int method, boolean isCache, Object tag, String url, Map<String, String> param, IResponse<String>
            response)
    {
        HttpStringRequest stringRequest = new HttpStringRequest(method);
        Request request = stringRequest.request(url, param, response);
        request.setTag(tag);
        request.setShouldCache(isCache);
        return request;
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
    public Request postString(boolean isCache, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        return stringRequest(Request.Method.POST, isCache, tag, url, param, response);
    }

    /**
     * POST方式提交String数据,并使用缓存
     *
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public Request postString(Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        return postString(true, tag, url, param, response);
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
    public Request requestString(boolean isCache, Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        return stringRequest(Request.Method.GET, isCache, tag, url, param, response);
    }

    /**
     * GET方式提交String数据，并使用缓存
     *
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public Request requestString(Object tag, String url, Map<String, String> param, IResponse<String> response)
    {
        return requestString(true, tag, url, param, response);
    }
}
