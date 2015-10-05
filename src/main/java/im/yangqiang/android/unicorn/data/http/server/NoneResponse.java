package im.yangqiang.android.unicorn.data.http.server;


import im.yangqiang.android.unicorn.data.http.server.inf.IResponse;

/**
 * NoneResponse
 * Created by Carlton on 2014/12/19.
 */
public class NoneResponse<T> implements IResponse<T>
{
    @Override
    public void onResponse(T data)
    {

    }

    @Override
    public void onError(Object error)
    {

    }
}
