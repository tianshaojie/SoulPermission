package com.qw.soul.permission.request.fragment;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qw.soul.permission.bean.Permission;

import static android.os.Build.VERSION_CODES.M;

/**
 * @author cd5160866
 */
public class PermissionSupportFragment extends Fragment implements IPermissionActions {

    private static final int REQUEST_CODE = 11;

    private IPermissionListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @TargetApi(M)
    @Override
    public void requestPermissions(String[] permissions, IPermissionListener listener) {
        requestPermissions(permissions, REQUEST_CODE);
        this.listener = listener;
    }

    @TargetApi(M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permission[] permissionResults = new Permission[permissions.length];
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; ++i) {
                Permission permission = new Permission(permissions[i], grantResults[i], this.shouldShowRequestPermissionRationale(permissions[i]));
                permissionResults[i] = permission;
            }
        }
        if (listener != null && getActivity() != null && !getActivity().isDestroyed()) {
            listener.onResult(permissionResults);
        }
    }

}
