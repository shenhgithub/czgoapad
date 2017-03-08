package com.lyg.czgoapad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DetailActivity.this.finish();
            }
        });

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

        webView.setOnTouchListener(this);

        webView.setWebViewClient(new MyWebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(DetailActivity.this);
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
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        webView.loadUrl(url);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if(mimetype.startsWith("image"))
                intent.setType("image/*");
            else if(mimetype.startsWith("video"))
                intent.setType("video/*");
            else if(mimetype.startsWith("audio"))
                intent.setType("audio/*");
            else if(mimetype.equals("application/pdf"))
                intent.setType("application/pdf");
            else
                intent.setType("*/*");
            startActivity(intent);
        }
    }

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArray;
    private final static int FILECHOOSER_RESULTCODE=1;
}
