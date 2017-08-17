package com.lyg.czgoapad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Leo on 2017/6/5.
 */

public class OpenFilesTool {
    // android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(File file)
    {
        Uri uri =
                Uri.parse(file.toString())
                        .buildUpon()
                        .encodedAuthority("com.android.htmlfileprovider")
                        .scheme("content")
                        .encodedPath(file.toString())
                        .build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    // android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    // android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    // android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(File file)
    {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    //定义用于检查要打开的文件的后缀是否在遍历后缀数组中
    public static boolean checkEndsWithInStringArray(String checkItsEnd,
                                                     String[] fileEndings){
        for(String aEnd : fileEndings){
            if(checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    public static void openFileFun(Context activity , String filepath)
    {
        File currentPath = new File(filepath);
        if(currentPath!=null&&currentPath.isFile())
        {
            String fileName = currentPath.toString();
            Intent intent;
            if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingImage))){
                intent = OpenFilesTool.getImageFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingWebText))){
                intent = OpenFilesTool.getHtmlFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingPackage))){
                intent = OpenFilesTool.getApkFileIntent(currentPath);
                activity.startActivity(intent);

            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingAudio))){
                intent = OpenFilesTool.getAudioFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingVideo))){
                intent = OpenFilesTool.getVideoFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingText))){
                intent = OpenFilesTool.getTextFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingPdf))){
                intent = OpenFilesTool.getPdfFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingWord))){
                intent = OpenFilesTool.getWordFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingExcel))){
                intent = OpenFilesTool.getExcelFileIntent(currentPath);
                activity.startActivity(intent);
            }else if(checkEndsWithInStringArray(fileName, activity.getResources().
                    getStringArray(R.array.fileEndingPPT))){
                intent = OpenFilesTool.getPPTFileIntent(currentPath);
                activity.startActivity(intent);
            }else
            {
                Toast.makeText(activity, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(activity, "请检查文件是否存在！", Toast.LENGTH_SHORT).show();
        }
    }
}
