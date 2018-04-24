package com.example.leo.mfilemanager.Adapter;

import android.view.View;
import android.widget.TextView;
import com.example.leo.mfilemanager.Adapter.base.RecyclerViewAdapter;
import com.example.leo.mfilemanager.Adapter.base.RecyclerViewHolder;
import com.example.leo.mfilemanager.R;
import com.example.leo.mfilemanager.bean.TitlePath;


/**
 * Created by ${zhaoyanjun} on 2017/1/12.
 */

public class TitleHolder extends RecyclerViewHolder<TitleHolder> {

    TextView textView ;

    public TitleHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.title_Name );
    }

    @Override
    public void onBindViewHolder(TitleHolder lineHolder, RecyclerViewAdapter adapter, int position) {
        TitlePath titlePath = (TitlePath) adapter.getItem( position );
        lineHolder.textView.setText( titlePath.getNameState() );
    }
}
