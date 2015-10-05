package im.yangqiang.android.unicorn.data.http;

import java.util.Map;

import im.yangqiang.android.unicorn.core.log.ULog;

/**
 * 显示请求日志
 * Created by Carlton on 2014/12/24.
 */
public class HttpLog
{
    private static final String TAG = "HttpLog";

    /**
     * 请求参数
     *
     * @param url
     * @param param
     */
    public static void requestParams(String url, Map<String, String> param)
    {
        ULog.d(TAG, "【网络请求】" + url + ":\n" + ULog.getMapString(param));
    }

    /**
     * 响应
     *
     * @param url
     */
    public static void response(String url, String str)
    {
        ULog.d(TAG, "【网络响应】" + url + ":\n信息：" + str);
    }

    /**
     * 错误
     *
     * @param url
     */
    public static void error(String url, String str)
    {
        ULog.d(TAG, "【网络错误】" + url + ":\n信息" + str);
    }

    /**
     * 取消
     *
     * @param tag
     */
    public static void cancel(Object tag)
    {
        ULog.d(TAG, "【网络取消】" + tag);
    }
}
