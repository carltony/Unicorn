package im.yangqiang.android.unicorn.core;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import im.yangqiang.android.unicorn.core.netstate.NetWorkUtil;

/**
 * 框架Activity，支持v4的FragmentActivity
 */
public class UinActivity<T extends ViewDataBinding> extends AppCompatActivity implements IActivity
{
    private T dataBinding;

    /**
     * 显示提示
     *
     * @param msg
     */
    public void showToast(String msg)
    {
        getApp().showToast(msg);
    }

    public void showToast(int resId)
    {
        getApp().showToast(resId);
    }

    /**
     * 第一次点击返回的系统时间
     */
    private long mFirstClickTime = 0;

    /**
     * 双击退出
     */
    public boolean onDoubleClickExit(long timeSpace)
    {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > timeSpace)
        {
            doubleExitCallBack();
            mFirstClickTime = currentTimeMillis;
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 双击退出，间隔时间为2000ms
     *
     * @return
     */
    public boolean onDoubleClickExit()
    {
        return onDoubleClickExit(2000);
    }

    /**
     * 双击退出不成功的回调。 第一次点击后回调，直到第二次点击的时间超过了给定时间，每一个回合回调一次
     */
    public void doubleExitCallBack()
    {
        Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard()
    {
        hideKeyboard(InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘，使用hideSoftInputFromWindow中的flags
     *
     * @param flags InputMethodManager.hideSoftInputFromWindow（IBinder windowToken, int flags）中的flags
     */
    public void hideKeyboard(int flags)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View peekDecorView = getWindow().peekDecorView();
        inputMethodManager.hideSoftInputFromWindow(peekDecorView.getWindowToken(), flags);
    }

    /**
     * 显示键盘
     */
    public void showKeyboard()
    {
        showKeyboard(InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示键盘
     *
     * @param flags
     */
    public void showKeyboard(int flags)
    {
        InputMethodManager inputMethodManager = getInputMethodManager();
        View peekDecorView = getWindow().peekDecorView();
        inputMethodManager.showSoftInputFromInputMethod(peekDecorView.getWindowToken(), flags);
    }

    public void toggleSoftInputFromWindow(int hideFlags, int showFlags)
    {
        View peekDecorView = getWindow().peekDecorView();
        getInputMethodManager().toggleSoftInputFromWindow(peekDecorView.getWindowToken(), showFlags, hideFlags);
    }

    public void toggleSoftInputFromWindow()
    {
        View peekDecorView = getWindow().peekDecorView();
        getInputMethodManager().toggleSoftInputFromWindow(peekDecorView.getWindowToken(), InputMethodManager.SHOW_IMPLICIT, InputMethodManager
                .HIDE_IMPLICIT_ONLY);
    }

    public InputMethodManager getInputMethodManager()
    {
        return (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    /**
     * 点击事件,所有子类必须重写这个方法，在XML中配置onClick属性名称一定要是onClick
     *
     * @param view
     */
    public void onClick(View view)
    {
        hideKeyboard();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        notifyApplicationActivityCreated();
    }

    @Override
    protected void onDestroy()
    {
        notifyApplicationActivityDestroy();
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutId)
    {
        super.setContentView(layoutId);
        dataBinding = DataBindingUtil.setContentView(this, layoutId);
    }

    public void setContentView(int layoutId, boolean isBinding)
    {
        if(isBinding)
        {
            setContentView(layoutId);
        }
        else
        {
            super.setContentView(layoutId);
        }
    }

    public T getDataBinding()
    {
        return dataBinding;
    }

    @Override
    public void onBackPressed()
    {
        getApp().onBackPressed();
        super.onBackPressed();
    }

    public void finishActivity(Activity activity)
    {
        getApp().finishActivity(activity);
    }

    public void finishActivity()
    {
        getApp().finishActivity(this);
    }

    public UinApplication getApp()
    {
        return (UinApplication) getApplication();
    }

    private void notifyApplicationActivityCreated()
    {
        getApp().onActivityCreated(this);
    }

    private void notifyApplicationActivityDestroy()
    {
        getApp().onActivityDestroy(this);
    }

    @Override
    public void onConnect(NetWorkUtil.NetType type)
    {

    }

    @Override
    public void onDisConnect()
    {

    }

    public <M extends UinModel> M model(Class<M> cls)
    {
        return ModelUtils.instance(this, cls);
    }

    /**
     * 如果Activity没有使用泛型，使用这个方法获取model
     */
    public static <M extends UinModel> M model(Context context, Class<M> cls)
    {
        return ModelUtils.instance(context, cls);
    }
}