package com.qw.soul.permission.request.fragment;

import android.annotation.TargetApi;

import static android.os.Build.VERSION_CODES.M;

/**
 * @author cd5160866
 */
public interface IPermissionActions {

    /**
     * 请求权限
     *
     * @param permissions 权限
     * @param listener    回调
     */
    @TargetApi(M)
    void requestPermissions(String[] permissions, IPermissionListener listener);

}
