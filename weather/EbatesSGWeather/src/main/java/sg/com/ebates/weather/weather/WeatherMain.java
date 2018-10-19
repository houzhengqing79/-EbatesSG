package sg.com.ebates.weather.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

import sg.com.ebates.weather.weathermanagement.ICityManagement;
import sg.com.ebates.weather.weathermanagement.core.CityManagement;

public class WeatherMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);

        ICityManagement cityManagement = new CityManagement();
        try {
            cityManagement.initialize();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
