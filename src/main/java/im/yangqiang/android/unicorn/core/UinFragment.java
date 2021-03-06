package im.yangqiang.android.unicorn.core;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Fragment
 */
public abstract class UinFragment<T extends ViewDataBinding> extends Fragment
{
    Fragment mStartActivityForResultFragment;
    private UinActivity mActivity;
    private T           dataBinding;
    private Bundle  mStateBundle           = new Bundle();
    /**
     * 是否恢复了状态
     *
     * @return
     */
    private boolean isRestoreInstanceState = false;

    /**
     * 如果Fragment 没有使用泛型使用这个方法来获取模版
     *
     * @param fragment 当前Fragment
     * @return 返回泛型Model
     */
    public static <M extends UinModel> M model(Fragment fragment, Class<M> cls)
    {
        return UinActivity.model(fragment.getContext(), cls);
    }

    /**
     * 是否恢复了Fragment的状态
     *
     * @return
     */
    protected boolean isRestoreInstanceState()
    {
        return isRestoreInstanceState;
    }

    public T getDataBinding()
    {
        return dataBinding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = (UinActivity) getActivity();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        saveInstanceState();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        isRestoreInstanceState = restoreInstanceState();
    }

    public Bundle getSaveInstanceState()
    {
        return mStateBundle;
    }

    private void saveInstanceState()
    {
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            onSaveState(mStateBundle);
            arguments.putBundle("saveInstanceSate17608005921", mStateBundle);
        }
    }

    private boolean restoreInstanceState()
    {
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            Bundle bundle = arguments.getBundle("saveInstanceSate17608005921");
            if (bundle != null)
            {
                onRestoreState(bundle);
                return true;
            }
        }
        return false;
    }

    /**
     * 保存状态回调
     *
     * @param outState
     */
    protected void onSaveState(Bundle outState)
    {

    }

    /**
     * 恢复状态回调
     *
     * @param instanceState
     */
    protected void onRestoreState(Bundle instanceState)
    {

    }

    /**
     * 点击事件
     *
     * @param view
     */
    public void onClick(View view)
    {
        hideKeyboard();
    }

    /**
     * 显示提示
     *
     * @param msg
     */
    public void showToast(String msg)
    {
        mActivity.showToast(msg);
    }

    public void showToast(int resId)
    {
        mActivity.showToast(resId);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard()
    {
        mActivity.hideKeyboard();
    }

    /**
     * 隐藏键盘，使用hideSoftInputFromWindow中的flags
     *
     * @param flags InputMethodManager.hideSoftInputFromWindow（IBinder windowToken, int flags）中的flags
     */
    public void hideKeyboard(int flags)
    {
        mActivity.hideKeyboard(flags);
    }

    /**
     * 显示键盘
     */
    public void showKeyboard()
    {
        mActivity.showKeyboard();
    }

    public void showKeyboard(int flags)
    {
        mActivity.showKeyboard(flags);
    }

    public void toggleSoftInputFromWindow()
    {
        mActivity.toggleSoftInputFromWindow();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (!(activity instanceof UinActivity))
        {
            throw new RuntimeException("activity must be implement UinActivity");
        }
        mActivity = (UinActivity) activity;
    }

    public View onCreateView(LayoutInflater inflater, int layoutId, ViewGroup container, boolean attachToParent)
    {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, container, attachToParent);
        return dataBinding.getRoot();
    }

    public View onCreateView(boolean isBinding, LayoutInflater inflater, int layoutId, ViewGroup container, boolean attachToParent)
    {
        if (isBinding)
        {
            return onCreateView(inflater, layoutId, container, attachToParent);
        }
        else
        {
            return inflater.inflate(layoutId, container, attachToParent);
        }
    }

    /**
     * 如果Fragment的子类调用这个方法去代替startActivityFroResult
     *
     * @param fragment
     * @param intent
     * @param requestCode
     */
    public void startActivityForResultByFragment(Fragment fragment, Intent intent, int requestCode)
    {
        mStartActivityForResultFragment = fragment;
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        final FragmentManager childFragmentManager = getChildFragmentManager();
        if (childFragmentManager != null)
        {
            final List<Fragment> nestedFragments = childFragmentManager.getFragments();
            if (nestedFragments == null || nestedFragments.size() == 0)
                return;
            for (Fragment childFragment : nestedFragments)
            {
                if (childFragment != null && !childFragment.isDetached() && !childFragment.isRemoving())
                {
                    if (childFragment == mStartActivityForResultFragment)
                    {
                        childFragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        }
    }

//    /**
//     * 修复系统中Fragment嵌套onActivityResult不调用的BUG,在最内Fragment使用：getParentFragment().startActivityForResult()
//     * 方式启动Activity，然后这个方法会代替onActivityResult被回调
//     *
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     * @see #onActivityResult(int, int, Intent)
//     */
//    public void onUinActivityResult(int requestCode, int resultCode, Intent data)
//    {
//
//    }

    public <M extends UinModel> M model(Class<M> cls)
    {
        return (M) mActivity.model(cls);
    }

    public UinApplication getApp()
    {
        return (UinApplication) mActivity.getApplication();
    }
}