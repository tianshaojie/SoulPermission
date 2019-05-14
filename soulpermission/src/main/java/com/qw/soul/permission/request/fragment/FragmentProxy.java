package com.qw.soul.permission.request.fragment;

import android.support.annotation.NonNull;

import com.qw.soul.permission.debug.PermissionDebug;


/**
 * @author cd5160866
 */
public class FragmentProxy implements IPermissionActions {

    private static final String TAG = FragmentProxy.class.getSimpleName();

    private IPermissionActions fragmentImp;

    public FragmentProxy(IPermissionActions fragmentImp) {
        this.fragmentImp = fragmentImp;
    }

    @Override
    public void requestPermissions(@NonNull String[] permissions, IPermissionListener listener) {
        this.fragmentImp.requestPermissions(permissions, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " request:" + hashCode());
    }

}
