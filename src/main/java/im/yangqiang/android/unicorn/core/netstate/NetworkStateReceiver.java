package im.yangqiang.android.unicorn.core.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

import im.yangqiang.android.unicorn.core.log.ULog;

/**
 * 检测网络状态需要配置：
 * #receiver android:name="com.chanlytech.unicorn.core.netstate.UinNetworkStateReceiver"#
 * #intent-filter#
 * #action android:name="android.net.conn.CONNECTIVITY_CHANGE"#
 * #action android:name="uin.android.net.conn.CONNECTIVITY_CHANGE"#
 * #intent-filter#
 * #receiver#
 */
public class NetworkStateReceiver extends BroadcastReceiver
{
    private final  String  TAG               = "UinNetworkStateReceiver";
    /**
     * 网络是否有效
     */
    private static Boolean mNetworkAvailable = false;
    private static NetWorkUtil.NetType mNetType;
    private static       ArrayList<NetChangeObserver> mNetChangeObserverArrayList  = new ArrayList<NetChangeObserver>();
    private final static String                       ANDROID_NET_CHANGE_ACTION    = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static  String                       DK_ANDROID_NET_CHANGE_ACTION = "uin.android.net.conn.CONNECTIVITY_CHANGE";
    private static BroadcastReceiver mReceiver;

    private static BroadcastReceiver getReceiver()
    {
        if (mReceiver == null)
        {
            mReceiver = new NetworkStateReceiver();
        }
        return mReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mReceiver = NetworkStateReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(DK_ANDROID_NET_CHANGE_ACTION))
        {
            ULog.i(TAG, "网络状态改变.");
            if (!NetWorkUtil.isNetworkAvailable(context))
            {
                ULog.i(TAG, "没有网络连接.");
                mNetworkAvailable = false;
            }
            else
            {
                ULog.i(TAG, "网络连接成功.");
                mNetType = NetWorkUtil.getAPNType(context);
                mNetworkAvailable = true;
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络状态广播
     *
     * @param mContext
     */
    public static void registerNetworkStateReceiver(Context mContext)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DK_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 检查网络状态
     *
     * @param mContext
     */
    public static void checkNetworkState(Context mContext)
    {
        Intent intent = new Intent();
        intent.setAction(DK_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注销网络状态广播
     *
     * @param mContext
     */
    public static void unRegisterNetworkStateReceiver(Context mContext)
    {
        if (mReceiver != null)
        {
            try
            {
                mContext.getApplicationContext().unregisterReceiver(mReceiver);
            }
            catch (Exception e)
            {
                ULog.d("UinNetworkStateReceiver", e.getMessage());
            }
        }

    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public static Boolean isNetworkAvailable()
    {
        return mNetworkAvailable;
    }

    public static NetWorkUtil.NetType getAPNType()
    {
        return mNetType;
    }

    private void notifyObserver()
    {

        for (int i = 0; i < mNetChangeObserverArrayList.size(); i++)
        {
            NetChangeObserver observer = mNetChangeObserverArrayList.get(i);
            if (observer != null)
            {
                if (isNetworkAvailable())
                {
                    observer.onConnect(mNetType);
                }
                else
                {
                    observer.onDisConnect();
                }
            }
        }

    }

    /**
     * 注册网络连接观察者
     */
    public static void registerObserver(NetChangeObserver observer)
    {
        if (mNetChangeObserverArrayList == null)
        {
            mNetChangeObserverArrayList = new ArrayList<NetChangeObserver>();
        }
        mNetChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     */
    public static void removeRegisterObserver(NetChangeObserver observer)
    {
        if (mNetChangeObserverArrayList != null)
        {
            mNetChangeObserverArrayList.remove(observer);
        }
    }
}
