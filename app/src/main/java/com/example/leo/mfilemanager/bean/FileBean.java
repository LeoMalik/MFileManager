package com.example.leo.mfilemanager.bean;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:04 2018/2/23
 * @Modified
 */
public class FileBean {
    private String name;
    private String path;
    private FileType fileType;
    private int childCount;
    private long size;
    private int holderType;

    public String getName() {
        return name;
    }

    public int getHolderType() {
        return holderType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setHolderType(int holderType) {
        this.holderType = holderType;
    }
}
