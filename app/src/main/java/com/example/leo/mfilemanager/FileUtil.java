package com.example.leo.mfilemanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import com.example.leo.mfilemanager.bean.FileType;

import java.io.*;
import java.util.Comparator;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:07 2018/2/23
 * @Modified
 */
public class FileUtil {


    /**
     * 获取文件类型
     * @param file
     * @return
     */
    public static FileType getFileType(File file ){
        if (file.isDirectory()) {
            return FileType.directory ;
        }
        String fileName = file.getName().toLowerCase() ;

        if ( fileName.endsWith(".mp3")) {
            return FileType.music ;
        }

        if ( fileName.endsWith(".mp4") || fileName.endsWith( ".avi")
                || fileName.endsWith( ".3gp") || fileName.endsWith( ".mov")
                || fileName.endsWith( ".rmvb") || fileName.endsWith( ".mkv")
                || fileName.endsWith( ".flv") || fileName.endsWith( ".rm")) {
            return FileType.video ;
        }

        if ( fileName.endsWith(".txt") || fileName.endsWith(".log") || fileName.endsWith(".xml")) {
            return FileType.txt ;
        }

        if ( fileName.endsWith(".zip") || fileName.endsWith( ".rar")) {
            return FileType.zip ;
        }

        if ( fileName.endsWith(".png") || fileName.endsWith( ".gif")
                || fileName.endsWith( ".jpeg") || fileName.endsWith( ".jpg")   ) {
            return FileType.image ;
        }

        if ( fileName.endsWith(".apk") ) {
            return FileType.apk ;
        }

        return FileType.other ;
    }

    /**
     * 文件按照名字排序
     */
    public static Comparator comparator = new Comparator<File>() {
        @Override
        public int compare(File file1 , File file2 ) {
            if ( file1.isDirectory() && file2.isFile() ){
                return -1 ;
            }else if ( file1.isFile() && file2.isDirectory() ){
                return 1 ;
            }else {
                return file1.getName().toLowerCase().compareTo( file2.getName().toLowerCase() ) ;
            }
        }
    } ;

    /**
     * 获取文件的子文件个数
     * @param file
     * @return
     */
    public static int getFileChildCount(File file) {
        int count = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isHidden()) continue;
                count ++ ;
            }
        }
        return count;
    }

    /**
     * 文件大小转换
     * @param size
     * @return
     */
    public static String sizeToChange( long size ){
        java.text.DecimalFormat df   =new   java.text.DecimalFormat("#.00");  //字符格式化，为保留小数做准备

        double G = size * 1.0 / 1024 / 1024 /1024 ;
        if ( G >= 1 ){
            return df.format( G ) + " GB";
        }

        double M = size * 1.0 / 1024 / 1024  ;
        if ( M >= 1 ){
            return df.format( M ) + " MB";
        }

        double K = size  * 1.0 / 1024   ;
        if ( K >= 1 ){
            return df.format( K ) + " KB";
        }

        return size + " B" ;
    }

    /**
     * 安装apk
     * @param context
     * @param file
     */
    public static void openAppIntent(Context context , File file ){
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.leo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 打开图片资源
     * @param context
     * @param file
     */
    public static void openImageIntent( Context context , File file ) {
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.leo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(uri, "image/*");
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * 打开文本资源
     * @param context
     * @param file
     */
    public static void openTextIntent( Context context , File file ) {
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.leo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(uri, "text/*");
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * 打开音频资源
     * @param context
     * @param file
     */
    public static void openMusicIntent( Context context , File file ){
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.leo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "audio/*");
        context.startActivity(intent);
    }

    /**
     * 打开视频资源
     * @param context
     * @param file
     */
    public static void openVideoIntent( Context context , File file ){
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.leo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "video/*");
        context.startActivity(intent);
    }

    /**
     * 打开所有能打开应用资源
     * @param context
     * @param file
     */
    public static void openApplicationIntent( Context context , File file ){
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.leo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/*");
        context.startActivity(intent);
    }

    /**
     * 发送文件给第三方app
     * @param context
     * @param file
     */
    public static void sendFile( Context context , File file ){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM,
                Uri.fromFile(file));
        share.setType("*/*");//此处可发送多种文件
        context.startActivity(Intent.createChooser(share, "发送"));
    }

    private static int dataOfFile = 0; //文件字节内容
    /**
     * 加密文件函数
     * @param srcFile
     */
    public static void EncFile(File srcFile) throws Exception{
        File encFile=new File(srcFile.getAbsolutePath()+".hid");
        if (encFile.exists()){
            encFile.delete();
        }
        try {
            encFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
            InputStream fis=new FileInputStream(srcFile);
            OutputStream fos=new FileOutputStream(encFile);

            while ((dataOfFile = fis.read()) > -1) {
                fos.write(dataOfFile+1);
            }

            fis.close();
            fos.flush();
            fos.close();
            srcFile.delete();
    }

    /**
     * 解密函数
     * @param encFile
     * @throws Exception
     */
    public static void DecFile(File encFile) throws Exception{
        String path=encFile.getAbsolutePath();
        File decFile=new File(path.substring(0,path.length()-4));
        try {
            decFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        InputStream fis=new FileInputStream(encFile);
        OutputStream fos=new FileOutputStream(decFile);

        while ((dataOfFile = fis.read()) > -1) {
            fos.write(dataOfFile-1);
        }
        fis.close();
        fos.flush();
        fos.close();
        encFile.delete();
    }

    public static void DeleteFile(File file){
        file.delete();
    }
}
