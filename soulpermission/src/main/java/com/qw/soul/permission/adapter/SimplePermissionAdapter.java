package com.qw.soul.permission.adapter;

import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;


/**
 * @author cd5160866
 */
public abstract class SimplePermissionAdapter implements CheckRequestPermissionListener {

    @Override
    public void onGranted(Permission permission) {

    }

    @Override
    public void onDenied(Permission permission) {

    }
}
