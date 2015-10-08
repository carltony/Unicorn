package im.yangqiang.android.unicorn.data.http.server.inf;

/**
 * 数据响应接口
 * Created by Carlton on 14-5-13.
 */
public interface IResponse<T>
{
    /**
     * 响应的数据回调
     *
     * @param data
     */
    void onResponse(T data);

    /**
     * 错误返回回掉
     *
     * @param error
     */
    void onError(Object error);
}
