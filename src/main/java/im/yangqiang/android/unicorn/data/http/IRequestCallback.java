package im.yangqiang.android.unicorn.data.http;

/**
 * 网络请求回调
 * Created by Carlton on 15/10/4.
 */
public interface IRequestCallback<T>
{
    public void onCreate(Object rawObj);
    public void onSuccess(T obj);
    public void onError();
}
