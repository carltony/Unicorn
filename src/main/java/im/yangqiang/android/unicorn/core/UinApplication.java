package im.yangqiang.android.unicorn.core;

import android.app.Activity;
import android.widget.Toast;

import im.yangqiang.android.unicorn.core.log.ULog;
import im.yangqiang.android.unicorn.core.netstate.NetChangeObserver;
import im.yangqiang.android.unicorn.core.netstate.NetWorkUtil;
import im.yangqiang.android.unicorn.core.netstate.NetworkStateReceiver;
import im.yangqiang.android.unicorn.core.toolbox.UToast;
import im.yangqiang.android.unicorn.exception.AppException;

/**
 * 提供核心功能的Application
 * Created by Carlton on 15/10/3.
 */
public class UinApplication extends UtilApplication
{
    private static final String TAG = "UtilApplication";
    /**
     * 网络改变的观察者，网络改变后会被通知
     */
    private NetChangeObserver               mNetChangeObserver;
    /**
     * 程序崩溃问题异常处理
     */
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    /**
     * 网络是否有效
     */
    private Boolean mNetworkAvailable = false;
    /**
     * 当前的Activity,在Activity栈最顶上的Activity
     */
    private IActivity  mCurrentActivity;
    /**
     * Activity管理器
     */
    private AppManager mAppManager;


    /**
     * Application的单实例
     */
    private static UinApplication mInstance;

    public UinApplication()
    {
        super();
        mInstance = this;
    }

    @Override
    public void onCreate()
    {
        getAppManager();
        super.onCreate();
        onCreating();
    }

    /**
     * 初始化数据,紧接着super.onCreate()后调用
     */
    private void init()
    {

    }

    public static UinApplication getApplication()
    {
        return mInstance;
    }

    /**
     * 正在创建Application，在Application中的onCreate（）中调用，
     */
    private void onCreating()
    {
        // 注册App异常崩溃处理器
        if (mUncaughtExceptionHandler == null)
        {
            mUncaughtExceptionHandler = getUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
        }
        // 网络改变的通知
        mNetChangeObserver = new NetChangeObserver()
        {
            @Override
            public void onConnect(NetWorkUtil.NetType type)
            {
                super.onConnect(type);
                UinApplication.this.onConnect(type);
            }

            @Override
            public void onDisConnect()
            {
                super.onDisConnect();
                UinApplication.this.onDisConnect();
            }
        };
        // 注册网络改变观察者
        NetworkStateReceiver.registerObserver(mNetChangeObserver);
    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public boolean isNetworkAvailable()
    {
        return mNetworkAvailable;
    }

    /**
     * 返回一个自定义的程序异常处理类，默认返回一个UinAppException处理
     *
     * @return
     */
    protected Thread.UncaughtExceptionHandler getUncaughtExceptionHandler()
    {
        return new AppException(this);
    }

    /**
     * 没有网络连接的时候回调
     */
    protected void onDisConnect()
    {
        mNetworkAvailable = false;
        if (mCurrentActivity != null)
        {
            mCurrentActivity.onDisConnect();
        }
    }

    /**
     * 网络连接连接时回调
     */
    protected void onConnect(NetWorkUtil.NetType type)
    {
        mNetworkAvailable = true;
        if (mCurrentActivity != null)
        {
            mCurrentActivity.onConnect(type);
        }
    }

    /**
     * 返回应用管理类，批量退出Activity或者获取当前Activity等
     *
     * @return
     */
    public AppManager getAppManager()
    {
        if (mAppManager == null)
        {
            mAppManager = AppManager.getAppManager();
        }
        return mAppManager;
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行,如果为true则为后台运行
     */
    public void exitApp(Boolean isBackground)
    {
        ULog.d(TAG, "退出程序");
        mAppManager.AppExit(this, isBackground);
    }

    /**
     * 设置当前显示的Activity
     */
    private void setCurrentActivity()
    {
        Activity currentActivity = mAppManager.currentActivity();
        if (currentActivity instanceof IActivity)
        {
            mCurrentActivity = (IActivity) currentActivity;
        }
    }

    /**
     * 返回Activity，在Activity的onBackPressed中调用，用于管理Activity
     */
    public void onBackPressed()
    {
        mAppManager.onBackPressed();
        setCurrentActivity();
    }

    /**
     * 停止一个Activity，同Activity.finish(),用于管理Activity
     */
    public void finishActivity(Activity activity)
    {
        mAppManager.finishActivity(activity);
        setCurrentActivity();
    }

    /**
     * 停止一个Activity，同Activity.finish(),用于管理Activity
     */
    public void finishActivity()
    {
        mAppManager.finishActivity();
        setCurrentActivity();
    }

    public void onActivityCreated(UinActivity activity)
    {
        if (activity == null)
        {
            throw new NullPointerException("Activity is don't allow null");
        }
        getAppManager().addActivity(activity);
        setCurrentActivity();
    }

    public void onActivityDestroy(UinActivity activity)
    {
        if (activity == null)
        {
            throw new NullPointerException("Activity is don't allow null");
        }
        getAppManager().removeActivity(activity);
        setCurrentActivity();
    }


    /**
     * 显示提示
     *
     * @param msg
     */
    public void showToast(String msg)
    {
        UToast.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    public void showToast(int resId)
    {
        UToast.showToast(this, resId, Toast.LENGTH_SHORT);
    }
}
