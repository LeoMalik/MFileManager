package com.example.leo.mfilemanager.Adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.leo.mfilemanager.Adapter.FileAdapter;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:05 2018/2/23
 * @Modified
 */
public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view, int position);
    }

    public FileAdapter.OnItemClickListener onItemClickListener;
    public FileAdapter.OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(FileAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public void setOnItemLongClickListener(FileAdapter.OnItemLongClickListener  listener){ onItemLongClickListener = listener;}

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    int pos=holder.getLayoutPosition();
                    onItemClickListener.onItemClick(view,pos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onItemLongClickListener!=null){
                    int pos=holder.getLayoutPosition();
                    onItemLongClickListener.onItemLongClick(view,pos);
                }
                return false;
            }
        });

        onBindViewHolders(holder,position);
    }

    public abstract void onBindViewHolders(RecyclerView.ViewHolder holder, int position);

    public abstract Object getItem(int postion);

    public abstract Object getAdapterData() ;

}
