package com.qw.soul.permission.adapter;

import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;


/**
 * @author cd5160866
 */
public abstract class SimplePermissionsAdapter implements CheckRequestPermissionsListener {
    @Override
    public void onGranted(Permission[] allPermissions) {

    }

    @Override
    public void onDenied(Permission[] deniedPermissions) {

    }
}
