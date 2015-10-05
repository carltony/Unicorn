package im.yangqiang.android.unicorn.data.http;

/**
 * 进度回调监听
 */
public interface IProgressListener
{
    /**
     * 正在执行
     *
     * @param totalSize
     * @param nowSize
     */
    public void onProgress(long totalSize, long nowSize);
    /**
     * 操作完成
     *
     * @param object
     */
    public void onFinish(Object object);

    /**
     * 失败
     *
     * @param object
     */
    public void onError(Object object);
}

