package im.yangqiang.android.unicorn.data.http;

import com.android.volley.Request;

import java.util.Map;

/**
 * 网络请求Aop，定制化请求内容等
 */
public interface IRequestHandler
{
    public Map<String, String> onParams(Map<String, String> param);

    public Request onRequest(Request request);
}