package sg.com.ebates.weather.weathermanagement.providers.openweathermap;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.com.ebates.weather.common.ILogManagement;
import sg.com.ebates.weather.container.IContainer;
import sg.com.ebates.weather.container.core.Service;
import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.IWeatherProvider;
import sg.com.ebates.weather.weathermanagement.core.Weather;

public class OpenWeatherMapProvider extends Service implements IWeatherProvider {
    private static String OPEN_WEATHER_MAP_URL_CITY = "http://api.openweathermap.org/data/2.5/weather?id=%s&APPID=c1ed0fcc4bf6bbbc7c7999e15ed2c320";
    ILogManagement log;

    public OpenWeatherMapProvider(IContainer container) {
        super(container);
        this.log = this.getService(ILogManagement.class);
    }

    @Override
    public IWeather query(int cityId) throws JSONException {
        String url = this.buildQueryUrl(cityId);
        String data = this.get(url);
        Weather.IWeatherBuilder builder = Weather.getBuilder();
        return builder.build(data);
    }

    private String buildQueryUrl(int cityId) {
        return String.format(OPEN_WEATHER_MAP_URL_CITY, String.valueOf(cityId));
    }

    private String get(String httpUrl) {
        HttpURLConnection con = null;
        try {
            this.log.i("will do query on: " + httpUrl);
            URL url = new URL(httpUrl);
            con = (HttpURLConnection) url.openConnection();
            return this.convertStreamToString(con.getInputStream());
        } catch (MalformedURLException e) {
            this.log.e(e.getMessage());
        } catch (IOException e) {
            this.log.e(e.getMessage());
        }
        return "";
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            this.log.e(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                this.log.e(e.getMessage());
            }
        }
        return sb.toString();
    }
}
