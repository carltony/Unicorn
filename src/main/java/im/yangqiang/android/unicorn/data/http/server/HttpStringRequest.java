package im.yangqiang.android.unicorn.data.http.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import im.yangqiang.android.unicorn.core.log.ULog;
import im.yangqiang.android.unicorn.data.http.HttpLog;
import im.yangqiang.android.unicorn.data.http.server.inf.IRequest;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 用Json数据作为请求参数。
 * Created by Carlton on 2014/12/24.
 */
public class HttpStringRequest implements IRequest<Map<String, String>, String>
{

    private int mMethod = Request.Method.POST;

    public HttpStringRequest(int method)
    {
        mMethod = method;
    }

    @Override
    public Request request(final String url, final Map<String, String> param, IResponse<String> response)
    {
        if(response == null)
        {
            response = new NoneResponse<>();
        }
        final IResponse<String> finalResponse = response;
        return new StringRequest(mMethod, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                HttpLog.response(url, s);
                finalResponse.onResponse(s);
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HttpLog.requestParams(getUrl(), param);
                return param;
            }
        };
    }
}
