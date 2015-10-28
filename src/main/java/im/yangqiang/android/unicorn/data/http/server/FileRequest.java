package im.yangqiang.android.unicorn.data.http.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import im.yangqiang.android.unicorn.core.log.ULog;
import im.yangqiang.android.unicorn.data.http.HttpLog;
import im.yangqiang.android.unicorn.data.http.server.inf.IRequest;
import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * 文件上传请求
 * Created by Carlton on 10/28/15.
 */
public class FileRequest implements IRequest<Map<String, String>, String>
{
    @Override
    public Request request(final String url, final Map<String, String> param, IResponse<String> response)
    {
        if (response == null)
        {
            response = new NoneResponse<>();
        }
        final IResponse<String> finalResponse = response;
        return new UploadRequest(url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                HttpLog.response(url, response);
                finalResponse.onResponse(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                HttpLog.error(url, "请求服务器错误:" + ULog.getPrintException(error));
                finalResponse.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HttpLog.requestParams(getUrl(), param);
                return param;
            }
        };
    }
}
