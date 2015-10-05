package im.yangqiang.android.unicorn.data.http;

import java.util.Map;

/**
 * 网络请求Aop，定制化请求内容等
 */
public interface IRequestHandler
{
    public Map<String, String> getParams(Map<String, String> param);
}