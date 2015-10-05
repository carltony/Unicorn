package im.yangqiang.android.unicorn.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * List列表项点击事件
 * Created by Carlton on 2014/12/22.
 */
public interface OnItemClickListener
{
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position, long id);
}
