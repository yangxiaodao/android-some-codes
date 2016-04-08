package com.xiaodao.base;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by android on 2016/4/8.
 */
public abstract class DefaultAdapter<T,H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    protected List<T> list;

    public DefaultAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}
