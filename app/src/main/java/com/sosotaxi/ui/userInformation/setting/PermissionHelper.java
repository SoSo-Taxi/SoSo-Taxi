/**
 * @Author 范承祥
 * @CreateTime 2020/7/17
 * @UpdateTime 2020/7/19
 */
package com.sosotaxi.ui.userInformation.setting;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 权限帮手
 */
public class PermissionHelper {
    /**
     * 查询是否拥有所需权限
     * @param context 上下文
     * @param authArray 所需权限列表
     * @return 是否拥有所需权限
     */
    public static boolean hasBaseAuth(Context context, String[] authArray) {
        PackageManager packageManager = context.getPackageManager();
        for (String auth : authArray) {
            if (packageManager.checkPermission(auth, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 查询是否拥有所需权限
     * @param context 上下文
     * @param auth 权限名
     * @return 是否拥有所需权限
     */
    public static boolean hasBaseAuth(Context context, String auth) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.checkPermission(auth, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}
