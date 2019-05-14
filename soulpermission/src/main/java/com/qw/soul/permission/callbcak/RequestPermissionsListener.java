package com.qw.soul.permission.callbcak;


import com.qw.soul.permission.bean.Permission;

/**
 * @author cd5160866
 */
public interface RequestPermissionsListener {

    /**
     * 所有权限ok，可做后续的事情
     *
     * @param allPermissions 权限实体类
     */
    void onGranted(Permission[] allPermissions);

    /**
     * 不ok的权限，被拒绝或者未授予
     *
     * @param deniedPermissions 权限实体类
     */
    void onDenied(Permission[] deniedPermissions);
}
