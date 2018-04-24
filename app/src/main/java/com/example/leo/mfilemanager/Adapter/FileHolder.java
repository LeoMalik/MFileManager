package com.example.leo.mfilemanager.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.leo.mfilemanager.Adapter.base.RecyclerViewAdapter;
import com.example.leo.mfilemanager.Adapter.base.RecyclerViewHolder;
import com.example.leo.mfilemanager.FileUtil;
import com.example.leo.mfilemanager.R;
import com.example.leo.mfilemanager.bean.FileBean;
import com.example.leo.mfilemanager.bean.FileType;

import java.io.File;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:06 2018/2/23
 * @Modified
 */
public class FileHolder extends RecyclerViewHolder<FileHolder> {

    ImageView fileIcon;
    TextView fileName;
    TextView fileChildCount;
    TextView fileSize;
    ImageView dir_enter_image;

    public FileHolder(View itemView) {
        super(itemView);
        fileIcon = (ImageView) itemView.findViewById(R.id.fileIcon);
        fileName = (TextView) itemView.findViewById(R.id.fileName);
        fileChildCount = (TextView) itemView.findViewById(R.id.fileChildCount);
        fileSize = (TextView) itemView.findViewById(R.id.fileSize);
        dir_enter_image = (ImageView) itemView.findViewById(R.id.dir_enter_image);
    }

    @Override
    public void onBindViewHolder(FileHolder fileHolder, RecyclerViewAdapter adapter, int position) {
        FileBean fileBean= (FileBean) adapter.getItem(position);
        fileHolder.fileName.setText(fileBean.getName());

        FileType fileType=fileBean.getFileType();
        if (fileType==FileType.directory){
            fileHolder.fileChildCount.setVisibility(View.VISIBLE);
            fileHolder.fileChildCount.setText(fileBean.getChildCount()+"项");

            fileHolder.fileSize.setVisibility(View.GONE);
            fileHolder.dir_enter_image.setVisibility(View.VISIBLE);
        }
        else {
            fileHolder.fileChildCount.setVisibility(View.GONE);
            fileHolder.fileSize.setVisibility(View.VISIBLE);
            fileHolder.fileSize.setText(FileUtil.sizeToChange(fileBean.getSize()));

            fileHolder.dir_enter_image.setVisibility(View.GONE);
        }
            if (fileType == FileType.directory) {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_dir);
            } else if (fileType == FileType.music) {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_music);
            } else if (fileType == FileType.video) {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_video);
            } else if (fileType == FileType.txt) {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_txt);
            } else if (fileType == FileType.zip) {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_zip);
            } else if (fileType == FileType.image) {
                Glide.with(fileHolder.itemView.getContext()).load(new File(fileBean.getPath())).into(fileHolder.fileIcon);
            } else if (fileType == FileType.apk) {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_apk);
            } else {
                fileHolder.fileIcon.setImageResource(R.mipmap.file_icon_other);
            }
        }
}

