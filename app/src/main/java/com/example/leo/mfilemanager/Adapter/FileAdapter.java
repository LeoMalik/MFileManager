package com.example.leo.mfilemanager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.leo.mfilemanager.Adapter.base.RecyclerViewAdapter;
import com.example.leo.mfilemanager.R;
import com.example.leo.mfilemanager.bean.FileBean;

import java.util.List;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:06 2018/2/23
 * @Modified
 */
public class FileAdapter extends RecyclerViewAdapter{

    private Context context;
    private List<FileBean> mData;
    private LayoutInflater mLayoutInflater;

    public FileAdapter(Context context, List<FileBean> mData) {
        this.context = context;
        this.mData = mData;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==0){
            view=mLayoutInflater.inflate(R.layout.list_item_file,parent,false);
            return new FileHolder(view);
        }

        else {
            view=mLayoutInflater.inflate(R.layout.list_item_line,parent,false);
            return new LineHolder(view);
        }
    }

    @Override
    public void onBindViewHolders(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FileHolder){
            FileHolder fileHolder=(FileHolder) holder;
            fileHolder.onBindViewHolder(fileHolder,this,position);
        }
        else if (holder instanceof LineHolder){
            LineHolder lineHolder=(LineHolder) holder;
            lineHolder.onBindViewHolder(lineHolder,this,position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mData.get( position).getHolderType() ;
    }

    @Override
    public Object getItem(int postion) {
        return mData.get(postion);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }


    public void refresh(List<FileBean> mData){
        this.mData=mData;
        notifyDataSetChanged();
    }
    @Override
    public Object getAdapterData() {
        return mData ;
    }
}
