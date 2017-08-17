package com.lyg.czgoapad;

import android.os.Environment;

import java.io.File;

/**
 * Created by Leo on 2017/6/5.
 */

public class FileUtil {
    /**
     * 获取根目录
     *
     * @return
     * @LastModifiedDate：2013-9-27
     * @author shen_feng
     * @EditHistory：<修改内容><修改人>
     */
    public static String getSDPath()
    {
        File sdDir = null;
        if (checkSDCard())
        {
            sdDir = Environment.getExternalStorageDirectory();// 获取根目录
        }
        else
        {

            sdDir = new File("");

        }
        return sdDir.toString();

    }
    /**
     *
     * 检查sd卡是否能读写
     *
     * @return
     * @LastModifiedDate：2013-9-27
     * @author shen_feng
     * @EditHistory：<修改内容><修改人>
     */
    public static boolean checkSDCard()
    {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            File f = Environment.getExternalStorageDirectory();
            if (f.canRead() && f.canWrite())
            {
                return true;
            }
            return false;

        }
        else
        {
            return false;
        }
    }
}
