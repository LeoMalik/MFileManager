package com.example.leo.mfilemanager.Adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:06 2018/2/23
 * @Modified
 */
public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder{
    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder( T t,RecyclerViewAdapter adapter,int position);
}
