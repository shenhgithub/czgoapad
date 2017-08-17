package com.lyg.czgoapad;

import android.app.Application;

/**
 * Created by Leo on 2017/6/5.
 */

public class GlobalState extends Application {
    /**
     * GlobalState 单例，给外部使用
     */
    private static GlobalState instance = null;
    /**
     *
     * 获取单例
     *
     * @Description 获取单例
     *
     * @return 单例
     * @LastModifiedDate：2013-10-18
     * @author wang_ling
     * @EditHistory：<修改内容><修改人>
     */
    public static GlobalState getInstance()
    {
        if (null == instance)
        {
            instance = new GlobalState();
        }
        return instance;
    }
}
