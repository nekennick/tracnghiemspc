package app.pcdongthap.tracnghiemspc;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context mContext;

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putBooleanValue(String key, boolean value) {
        SharedPreferences msharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,0);
        SharedPreferences.Editor editor= msharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean putBooleanValue(String key){
        SharedPreferences msharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,0);
        return msharedPreferences.getBoolean(key, false);
    }
}
