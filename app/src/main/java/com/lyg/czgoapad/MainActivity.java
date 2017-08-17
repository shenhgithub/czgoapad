package com.lyg.czgoapad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {

    private String              dirPath;
    private boolean             isDownloading                        = false;
    private String  fileName             = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dirPath = ConstState.MIP_ROOT_DIR + "/tmp/";
        File file = new File(dirPath);
        if (file != null && !file.exists())
        {
            file.mkdirs();
        }

        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


        webView.setWebViewClient(new MyWebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("url", url);
//                intent.putExtras(bundle);
//                startActivity(intent);
                //MainActivity.this.finish();
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                //CLog.i("UPFILE", "in openFile Uri Callback");
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                //CLog.i("UPFILE", "in openFile Uri Callback has accept Type" + acceptType);
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                String type = TextUtils.isEmpty(acceptType) ? "application/*" : acceptType;
                i.setType(type);
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);
            }

            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                //CLog.i("UPFILE", "in openFile Uri Callback has accept Type" + acceptType + "has capture" + capture);
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                String type = TextUtils.isEmpty(acceptType) ? "application/*" : acceptType;
                i.setType(type);
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            //Android 5.0+
            @Override
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                //CLog.i("UPFILE", "file chooser params：" + fileChooserParams.toString());
                mUploadMessageArray = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                        && fileChooserParams.getAcceptTypes().length > 0) {
                    i.setType(fileChooserParams.getAcceptTypes()[0]);
                } else {
                    i.setType("*/*");
                }
                //i.setData(Uri.parse("file://"));
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                return true;
            }
        });
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        //webView.loadUrl("http://221.195.199.172:8088/portal/index_blue.jsp");
        webView.loadUrl("http://221.195.199.172:8088/portal/t");
        //webView.loadUrl("http://218.207.83.171:8088/portal/t/");
        //webView.loadUrl("http://218.92.115.51/portal/t/");
//        webView.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILECHOOSER_RESULTCODE) {
            if (null != mUploadMessage) {
                Uri result = data == null || resultCode != RESULT_OK ? null
                        : data.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
            else if(null != mUploadMessageArray){
                Uri result = data == null || resultCode != RESULT_OK ? null
                        : data.getData();
                mUploadMessageArray.onReceiveValue(new Uri[] {result});
                mUploadMessageArray = null;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            //intent.setType("*/*");
//            startActivity(intent);

            if (isDownloading)
            {
                Toast.makeText(MainActivity.this, "正在下载文件，请稍后！", Toast.LENGTH_SHORT).show();
                return;
            }




            fileName = getFileName(url);

            File filePath = new File(dirPath + fileName);
            if (filePath != null && filePath.exists())
            {
                // startActivity(OpenFilesTool.getPdfFileIntent(filePath));
                // openFile(dirPath + fileName);
                filePath.delete();
            }
            // else
            // {
            Thread th = new Thread()
            {
                /**
                 * 重载方法
                 *
                 * @author rqj
                 */
                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                    downloadFileWithUrl(url, dirPath + fileName);
                    super.run();
                }

            };
            th.start();
            isDownloading = true;
            //waitDialog.show(getSupportFragmentManager());
            // }
        }
    }

    public String getFileName(String url)
    {
        String filename = "";

        String[] arrs = url.split("&");
        String fileNameArr = "";
        if (arrs != null)
        {
            for (int i = 0; i < arrs.length; i++)
            {
                if (arrs[i].startsWith("filename="))
                {

                    fileNameArr = arrs[i];
                    break;
                }
            }
        }

        int index_1 = fileNameArr.indexOf("=");
        filename = fileNameArr.substring(index_1 + 1, fileNameArr.length());

        try
        {
            filename = URLDecoder.decode(filename, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return filename;
    }

    public void createPath(File file) throws IOException {
        if (file.exists())
            file.delete();
        File localFile = file.getParentFile();
        if ((localFile != null) && (!localFile.exists()))
            localFile.mkdirs();
        //file.createNewFile();
    }

    Handler mDowanlodFileHandler = new Handler()
    {

        /**
         * 重载方法
         *
         * @param msg
         * @author rqj
         */
        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            //waitDialog.dismiss();
            File file = new File(dirPath + fileName);
            if (file != null && file.exists())
            {
                OpenFilesTool.openFileFun(MainActivity.this, file.getAbsolutePath()
                        .trim()
                        .toLowerCase());
            }
            super.handleMessage(msg);
        }

    };

    private void downloadFileWithUrl(String paramString, String filePath)
    {
        try
        {
            URL localURL = new URL(paramString);
            HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
            localHttpURLConnection.setConnectTimeout(30000);
            localHttpURLConnection.setReadTimeout(30000);
            InputStream localInputStream = localHttpURLConnection.getInputStream();
            FileOutputStream localFileOutputStream = null;

            File localFile1 = new File(filePath);
            if (localFile1 != null)
            {
                createPath(localFile1);
                localFileOutputStream = new FileOutputStream(localFile1);
            }
            byte[] arrayOfByte = new byte[1024];
            while (true)
            {
                int i = localInputStream.read(arrayOfByte);
                if (i == -1)
                {
                    localFileOutputStream.flush();
                    localFileOutputStream.close();
                    localInputStream.close();
                    break;
                }
                else
                {
                    localFileOutputStream.write(arrayOfByte, 0, i);
                }

            }
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
        finally
        {
            isDownloading = false;
            Message localMessage = new Message();
            localMessage.what = 0;
            mDowanlodFileHandler.sendMessage(localMessage);
        }

    }

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArray;
    private final static int FILECHOOSER_RESULTCODE=1;
}
