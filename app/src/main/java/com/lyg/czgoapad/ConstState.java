package com.lyg.czgoapad;

/**
 * Created by Leo on 2017/6/5.
 */

public class ConstState {
    /**
     * 存储文件的根路径
     */
    public static String DIR_NAME = OsUtils.getApplicationName(GlobalState.getInstance(), "/CZGOAPAD/");

    // 文件根目录
    public static final String MIP_ROOT_DIR = FileUtil.getSDPath() + DIR_NAME;
}
