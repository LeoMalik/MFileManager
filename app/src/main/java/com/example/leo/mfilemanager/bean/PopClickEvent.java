package com.example.leo.mfilemanager.bean;

import java.io.FileNotFoundException;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 22:30 2018/2/28
 * @Modified
 */
public interface PopClickEvent {
    public void onPreClick(FileBean fileBean);
    public void onNextClick(FileBean fileBean) throws FileNotFoundException;
    public void onDeleteClick(FileBean fileBean);
    public void onShareClick(FileBean fileBean);
}