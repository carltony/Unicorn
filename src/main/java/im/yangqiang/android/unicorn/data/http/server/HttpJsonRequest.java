package im.yangqiang.android.unicorn.data.http.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import im.yangqiang.android.unicorn.core.log.ULog;
import im.yangqiang.android.unicorn.data.http.HttpLog;
import im.yangqiang.android.unicorn.data.http.server.inf.IRequest;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 用Json数据作为请求参数。该请求对象只能作为POST请求的时候有效。
 * Created by carlton on 15/4/16.
 */
public class HttpJsonRequest implements IRequest<Map<String, String>, JSONObject>
{
    @Override
    public Request request(final String url, final Map<String, String> param, IResponse<JSONObject> response)
    {
        if (response == null)
        {
            response = new NoneResponse<>();
        }
        HttpLog.requestParams(url, param);
        final IResponse<JSONObject> finalResponse = response;
        return new JsonObjectRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject o)
            {
                HttpLog.response(url, o.toString());
                finalResponse.onResponse(o);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                HttpLog.error(url, "请求服务器错误:" + ULog.getPrintException(volleyError));
                finalResponse.onError(volleyError);
            }
        })
        {
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

                return headers;
            }
        };
    }
}
