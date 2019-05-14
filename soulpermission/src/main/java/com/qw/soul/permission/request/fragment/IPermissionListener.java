package com.qw.soul.permission.request.fragment;


import com.qw.soul.permission.bean.Permission;

/**
 * @author cd5160866
 */
public interface IPermissionListener {

    /**
     * 得到权限检查结果
     *
     * @param permissions 封装权限的数组
     */
    void onResult(Permission[] permissions);

}
