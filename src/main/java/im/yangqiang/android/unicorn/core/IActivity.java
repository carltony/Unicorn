package im.yangqiang.android.unicorn.core;

import im.yangqiang.android.unicorn.core.netstate.NetWorkUtil;

/**
 * Activity接口
 * Created by Carlton on 15/10/3.
 */
public interface IActivity
{
    public void onConnect(NetWorkUtil.NetType type);

    public void onDisConnect();
}
