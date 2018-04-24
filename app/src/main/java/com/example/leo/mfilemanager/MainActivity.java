package com.example.leo.mfilemanager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.leo.mfilemanager.Adapter.FileAdapter;
import com.example.leo.mfilemanager.Adapter.TitleAdapter;
import com.example.leo.mfilemanager.Adapter.base.RecyclerViewAdapter;
import com.example.leo.mfilemanager.bean.FileBean;

import com.example.leo.mfilemanager.bean.FileType;
import com.example.leo.mfilemanager.bean.PopClickEvent;
import com.example.leo.mfilemanager.bean.TitlePath;
import com.example.leo.mfilemanager.encrypt.encrypt;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView title_recycler_view ;
    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;
    private TitleAdapter titleAdapter ;
    private LinearLayout empty_rel ;

    private List<FileBean> beanList = new ArrayList<>();
    private File rootFile ;
    private int PERMISSION_CODE_WRITE_EXTERNAL_STORAGE = 100 ;
    private String rootPath ;
    private PopOptionUtil mPop;//菜单弹框
    private ProgressDialog pd1 = null;

//    private Button button;
//    private EditText result;
//
//    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Context mcontext = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd1 = new ProgressDialog(MainActivity.this);
        //依次设置标题,内容,是否用取消按钮关闭,是否显示进度

        pd1.setCancelable(false);
        //这里是设置进度条的风格,HORIZONTAL是水平进度条,SPINNER是圆形进度条
        pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd1.setIndeterminate(true);
        mPop = new PopOptionUtil(this);

        mPop.setOnPopClickEvent(new PopClickEvent() {
            @Override
            //加密函数
            public void onPreClick(final FileBean fileBean) {
                mPop.hide();
                pd1.setTitle("正在加密中");
                pd1.setMessage("正在加密中,请耐心等待...");
                //调用show()方法将ProgressDialog显示出来
                pd1.show();




                //创建一个新的文件
                final File srcFile=new File(fileBean.getPath());
                File encFile=new File(srcFile.getAbsolutePath()+".hid");
                if (encFile.exists()){
                    encFile.delete();
                }
                try {
                    encFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                InputStream fis = null;
                try {
                    fis = new FileInputStream(srcFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                OutputStream fos = null;
                try {
                    fos = new FileOutputStream(encFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                //创建文件流
                final InputStream finalFis = fis;
                final OutputStream finalFos = fos;

                int a= (int) srcFile.length();
                final byte[] buffer=new byte[a];
                new Thread()
                {
                    public void run()
                    {
                        try {
                            finalFis.read(buffer);
                            finalFos.write(encrypt.jiami(buffer,true));
                            pd1.dismiss();
                            TitlePath titlePath= (TitlePath) titleAdapter.getItem(titleAdapter.getItemCount()-1);
                            getFile(titlePath.getPath());
                            srcFile.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
//                // get prompts.xml view
//                LayoutInflater li = LayoutInflater.from(context);
//                View promptsView = li.inflate(R.layout.prompts, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder.setView(promptsView);
//
//                final EditText userInput = (EditText) promptsView
//                        .findViewById(R.id.editTextDialogUserInput);
//
//                // set dialog message
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("确认",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        // get user input and set it to result
//                                        // edit text
//                                        key=userInput.getText().toString();
//                                        new Thread()
//                                        {
//                                            public void run()
//                                            {
//                                                try {
//                                                    finalFis.read(buffer);
//                                                    mtext[0] =new encrypt(key);
//                                                    finalFos.write(encrypt.jiami(buffer,true,key));
//
//                                                    //消除加载框,刷新ui
//                                                    pd1.dismiss();
//                                                    TitlePath titlePath= (TitlePath) titleAdapter.getItem(titleAdapter.getItemCount()-1);
//                                                    getFile(titlePath.getPath());
//                                                    srcFile.delete();
//                                                    //要在ui线程下调用
//                                                    Toast.makeText(MainActivity.this,fileBean.getName()+"加密成功",Toast.LENGTH_SHORT).show();
//
//                                                } catch (Exception e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }.mstart();
//                                    }
//                                })
//                        .setNegativeButton("取消",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();
                //创建一个新的线程,运行加密算法(读取文件流进行逐位加密)

//                mPop.hide();
//                try {
//                    FileUtil.EncFile(new File(fileBean.getPath()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            //解密函数
            public void onNextClick(final FileBean fileBean) throws FileNotFoundException {
                mPop.hide();
                final File encFile=new File(fileBean.getPath());

                String path=encFile.getAbsolutePath();
                if (!path.endsWith(".hid")){
                    Toast.makeText(MainActivity.this,fileBean.getName()+"无需解密",Toast.LENGTH_SHORT).show();
                    return;
                }
                pd1.setTitle("正在解密中");
                pd1.setMessage("正在解密中,请耐心等待...");

                pd1.show();
                File decFile=new File(path.substring(0,path.length()-4));
                if (decFile.exists()){
                    decFile.delete();
                }
                try {
                    decFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                InputStream fis = null;
                try {
                    fis = new FileInputStream(encFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                OutputStream fos = null;
                try {
                    fos = new FileOutputStream(decFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final InputStream finalFis = fis;
                final OutputStream finalFos = fos;
                int a= (int) encFile.length();
                final byte[] buffer=new byte[a];
                new Thread()
                {
                    public void run()
                    {
                        try {
                            finalFis.read(buffer);
                            finalFos.write(encrypt.jiami(buffer,false));
                            pd1.dismiss();
                            TitlePath titlePath= (TitlePath) titleAdapter.getItem(titleAdapter.getItemCount()-1);
                            getFile(titlePath.getPath());
                            encFile.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            }

            @Override
            public void onDeleteClick(FileBean fileBean) {
                FileUtil.DeleteFile(new File(fileBean.getPath()));
                Toast.makeText(MainActivity.this,fileBean.getName()+"删除成功",Toast.LENGTH_SHORT).show();
                TitlePath titlePath= (TitlePath) titleAdapter.getItem(titleAdapter.getItemCount()-1);
                getFile(titlePath.getPath());
                mPop.hide();
            }

            @Override
            public void onShareClick(FileBean fileBean) {
                FileUtil.sendFile(MainActivity.this,new File(fileBean.getPath()));
                mPop.hide();
            }



        });


        //设置title
        title_recycler_view= (RecyclerView) findViewById(R.id.title_recycler_view);
        title_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        titleAdapter=new TitleAdapter(MainActivity.this,new ArrayList<TitlePath>());
        title_recycler_view.setAdapter(titleAdapter);

        //设置fileitem
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter=new FileAdapter(MainActivity.this,beanList);
        recyclerView.setAdapter(fileAdapter);

        //设置空白视图
        empty_rel= (LinearLayout) findViewById(R.id.empty_rel);

        //设置监听
        fileAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FileBean file=beanList.get(position);
                FileType fileType=file.getFileType();
                if (fileType==FileType.directory){
                    //刷新文件列表
                    getFile(file.getPath());
                    refreshTitleState(file.getName(),file.getPath());
                }
                else if (fileType==FileType.apk){
                    FileUtil.openAppIntent(MainActivity.this,new File(file.getPath()));
                }else if ( fileType == FileType.apk ){
                    //安装app
                    FileUtil.openAppIntent( MainActivity.this , new File( file.getPath() ) );
                }else if ( fileType == FileType.image ){
                    FileUtil.openImageIntent( MainActivity.this , new File( file.getPath() ));
                }else if ( fileType == FileType.txt ){
                    FileUtil.openTextIntent( MainActivity.this , new File( file.getPath() ) );
                }else if ( fileType == FileType.music ){
                    FileUtil.openMusicIntent( MainActivity.this ,  new File( file.getPath() ) );
                }else if ( fileType == FileType.video ){
                    FileUtil.openVideoIntent( MainActivity.this ,  new File( file.getPath() ) );
                }else {
                    FileUtil.openApplicationIntent( MainActivity.this , new File( file.getPath() ) );
                }
            }
        });

        fileAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                FileBean fileBean= (FileBean) fileAdapter.getItem(position);
                //或者fileBean=beanList.get(position);
                FileType fileType=fileBean.getFileType();
                if (fileType!=null&&fileType!=FileType.directory)
                {

                    mPop.show(view,fileBean);

//                    FileUtil.sendFile(MainActivity.this,new File(fileBean.getPath()));
                }
                return true;
            }
        });

        titleAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TitlePath titlePath= (TitlePath) titleAdapter.getItem(position);
                getFile(titlePath.getPath());

                int count = titleAdapter.getItemCount() ;
                for (int i=0;i<count-position-1;i++){
                    titleAdapter.removeLast();
                }
            }
        });


        //主函数部分
        //获取绝对路径
        rootPath=Environment.getExternalStorageDirectory().getAbsolutePath();
        refreshTitleState("内部存储",rootPath);
//        Toast.makeText(MainActivity.this,rootPath,Toast.LENGTH_LONG).show();



        if(AndPermission.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE )) {
            // 有权限，直接do anything.
            getFile(rootPath);
        } else {
            //申请权限。
            AndPermission.with(this)
                    .requestCode(PERMISSION_CODE_WRITE_EXTERNAL_STORAGE)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    .send();
        }
    }



    public void getFile(String path){
        rootFile=new File(path+File.separator);
        new MyTask(rootFile).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR , "") ;
    }

    class MyTask extends AsyncTask{

        File file;

        MyTask(File file){
            this.file=file;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            List<FileBean> fileBeanList=new ArrayList<>();
            if (file.isDirectory()){
                File[] filesArray=file.listFiles();
                //数组转list
                if (filesArray!=null){
                    List<File> fileList=new ArrayList<>();
                    Collections.addAll(fileList,filesArray);
                    Collections.sort(fileList,FileUtil.comparator);

                    for (File f:fileList){
                        if (f.isHidden()) continue;

                        FileBean fileBean = new FileBean();
                        fileBean.setName(f.getName());
                        fileBean.setPath(f.getAbsolutePath());
                        fileBean.setFileType( FileUtil.getFileType( f ));
                        fileBean.setChildCount( FileUtil.getFileChildCount( f ));
                        fileBean.setSize( f.length() );
                        fileBean.setHolderType( 0 );

                        fileBeanList.add(fileBean);

                        FileBean lineBean = new FileBean();
                        lineBean.setHolderType( 1 );
                        fileBeanList.add( lineBean );
                    }
                }
            }
            beanList=fileBeanList;
            return beanList;
        }

        @Override
        protected void onPostExecute(Object o) {
            if ( beanList.size() > 0  ){
                empty_rel.setVisibility( View.GONE );
            }else {
                empty_rel.setVisibility( View.VISIBLE );
            }
            fileAdapter.refresh(beanList);
        }
    }


    void refreshTitleState( String title , String path ){
        TitlePath filePath = new TitlePath() ;
        filePath.setNameState( title + " > " );
        filePath.setPath( path );
        titleAdapter.addItem( filePath );
        title_recycler_view.smoothScrollToPosition( titleAdapter.getItemCount());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            List<TitlePath> titlePathList = (List<TitlePath>) titleAdapter.getAdapterData();
            if ( titlePathList.size() == 1 ){
                finish();
            }else {
                titleAdapter.removeItem( titlePathList.size() - 1 );
                getFile( titlePathList.get(titlePathList.size() - 1 ).getPath() );
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if(requestCode == PERMISSION_CODE_WRITE_EXTERNAL_STORAGE ) {
                getFile(rootPath);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            AndPermission.defaultSettingDialog( MainActivity.this, PERMISSION_CODE_WRITE_EXTERNAL_STORAGE )
                    .setTitle("权限申请失败")
                    .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                    .setPositiveButton("好，去设置")
                    .show();
        }
    };


}
