package sg.com.ebates.weather.common.core;

import android.util.Log;

import sg.com.ebates.weather.common.ILogManagement;

public class LogManagement implements ILogManagement {
    @Override
    public void d(String message) {
        Log.d("Weather", message);
    }

    @Override
    public void i(String message) {
        Log.i("Weather", message);
    }
}
