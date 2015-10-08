package im.yangqiang.android.unicorn.exception;

import android.content.Context;

import im.yangqiang.android.unicorn.core.UinApplication;

/**
 * App异常捕捉
 * Created by Carlton on 15/10/8.
 */
public class AppException implements Thread.UncaughtExceptionHandler
{
    private Context                         mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public AppException(Context context)
    {
        init(context);
    }

    protected Context getContext()
    {
        return mContext;
    }

    private void init(Context context)
    {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex)
    {
        if (handlerException(thread, ex) || mDefaultHandler == null)
        {
            try
            {
                Thread.sleep(1500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            UinApplication.getApplication().exitApp(false);
        }
        else
        {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    protected boolean handlerException(Thread thread, Throwable ex)
    {
        return false;
    }
}
