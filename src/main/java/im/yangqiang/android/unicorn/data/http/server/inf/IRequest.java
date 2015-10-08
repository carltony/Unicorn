package im.yangqiang.android.unicorn.data.http.server.inf;

import com.android.volley.Request;

/**
 * 网络请求类
 * Created by Carlton on 14-5-13.
 */
public interface IRequest<T, Response>
{
    Request request(String url, T param, IResponse<Response> response);
}
