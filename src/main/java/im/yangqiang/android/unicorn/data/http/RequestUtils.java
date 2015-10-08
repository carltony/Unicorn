package im.yangqiang.android.unicorn.data.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import im.yangqiang.android.unicorn.core.toolbox.BitmapUtils;
import im.yangqiang.android.unicorn.data.http.server.HttpJsonRequest;
import im.yangqiang.android.unicorn.data.http.server.HttpStringRequest;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 网络请求工具类
 * Created by Carlton on 14-5-13.
 */
public class RequestUtils
{
    private static RequestQueue mRequestQueue;
    private static ImageLoader  mLoader;

    private RequestUtils()
    {
    }

//    public static void loadImage(final boolean isRound, final ImageView imageView, final int defaultId, final int errorId, String url)
//    {
//        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>()
//        {
//            @Override
//            public void onResponse(Bitmap response)
//            {
//                if (response != null)
//                {
//                    if (isRound)
//                    {
//                        response = BitmapUtils.conversionBitmapToFillet(response, 360);
//                    }
//                    imageView.setImageBitmap(response);
//                }
//                else if (defaultId != 0)
//                {
//                    imageView.setImageResource(defaultId);
//                }
//            }
//        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                if (errorId != 0)
//                {
//                    imageView.setImageResource(errorId);
//                }
//            }
//        });
//        imageRequest.setShouldCache(true);
//        getRequestQueue(imageView.getContext()).add(imageRequest);
//    }
//
//    public static void loadImage(ImageView imageView, String url)
//    {
//        loadImage(true, imageView, 0, 0, url);
//    }
//
//    /**
//     * 加载图片
//     */
//    public static void loadImage(NetworkImageView imageView, int defaultId, int errorId, String url)
//    {
//        if (imageView == null)
//        {
//            return;
//        }
//        if (mLoader == null)
//        {
//            mLoader = new ImageLoader(getRequestQueue(imageView.getContext()), new BitmapCache());
//        }
//        if (defaultId != 0)
//        {
//            imageView.setDefaultImageResId(defaultId);
//        }
//        if (errorId != 0)
//        {
//            imageView.setErrorImageResId(errorId);
//        }
//        imageView.setImageUrl(url, mLoader);
//    }
//
//    public static void loadImage(NetworkImageView imageView, String url)
//    {
//        loadImage(imageView, 0, 0, url);
//    }

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
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    public static void postJson(boolean isCache, Context context, Object tag, String url, Map<String, String> param, IResponse<JSONObject> response)
    {
        HttpJsonRequest jsonResponse = new HttpJsonRequest();
        Request request = jsonResponse.request(url, param, response);
        request.setTag(tag);
        request.setShouldCache(isCache);
        getRequestQueue(context).add(request);
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
     * 提交String数据
     *
     * @param method   数据提交方式
     * @param isCache  是否使用缓存
     * @param context  Context
     * @param tag      标签
     * @param url      请求地址
     * @param param    请求参数
     * @param response 响应网络的回调
     */
    private static void stringRequest(int method, boolean isCache, Context context, Object tag, String url, Map<String, String> param, IResponse<String>
            response)
    {
        HttpStringRequest stringRequest = new HttpStringRequest(method);
        Request request = stringRequest.request(url, param, response);
        request.setTag(tag);
        request.setShouldCache(isCache);
        getRequestQueue(context).add(request);
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
        stringRequest(Request.Method.POST, isCache, context, tag, url, param, response);
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
        stringRequest(Request.Method.GET, isCache, context, tag, url, param, response);
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

    private static RequestQueue getRequestQueue(Context context)
    {
        if (mRequestQueue == null)
        {
            synchronized (RequestUtils.class)
            {
                if (mRequestQueue == null)
                {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return mRequestQueue;
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
