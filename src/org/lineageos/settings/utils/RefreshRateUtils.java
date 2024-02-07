package org.lineageos.settings.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;

public class RefreshRateUtils {

    private static final String PREF_FILE = "parts_pref";
    private static final String PREF_KEY = "refresh_rate";

    private static SharedPreferences sSharedPref;

    public static int getRefreshRate(Context context) {
        if (sSharedPref == null) {
            sSharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        }
        return sSharedPref.getInt(PREF_KEY, 1);
    }

    public static void setRefreshRate(Context context, int refreshRate) {
        if (sSharedPref == null) {
            sSharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sSharedPref.edit();
        editor.putInt(PREF_KEY, refreshRate);
        editor.apply();
    }

    public static final void setFPS(int v) {
        Parcel data = Parcel.obtain();
        data.writeInterfaceToken("android.ui.ISurfaceComposer");
        data.writeInt(v);
        try {
            ServiceManager.checkService("SurfaceFlinger").transact(1035, data, (Parcel) null, 0);
        } catch (RemoteException e) {
        }
        data.recycle();
    }
}
