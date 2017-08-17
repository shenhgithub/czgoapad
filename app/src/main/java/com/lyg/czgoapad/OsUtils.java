package com.lyg.czgoapad;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Leo on 2017/6/5.
 */

public class OsUtils {
    /**
     *
     * 获取存储文件的根路径
     *
     * @Description 获取存储文件的根路径
     *
     * @param context 应用上下文
     * @param dStr 文件名
     * @return 文件的根路径
     * @LastModifiedDate：2013-10-25
     * @author wang_ling
     * @EditHistory：<修改内容><修改人>
     */
    public static String getApplicationName(Context context, String dStr)
    {
        String mAppName = "";

        try
        {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getApplicationInfo().loadLabel(packageManager).toString();

            mAppName = "/" + packageName + "/";
        }
        catch (Exception e)
        {
            // TODO: handle exception
            mAppName = dStr;
        }

        return mAppName;
    }
}
