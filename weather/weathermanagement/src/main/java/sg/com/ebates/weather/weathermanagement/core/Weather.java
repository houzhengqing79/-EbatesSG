package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.IWeatherDescription;
import sg.com.ebates.weather.weathermanagement.IWeatherMain;
import sg.com.ebates.weather.weathermanagement.objs.City;
import sg.com.ebates.weather.weathermanagement.objs.WeatherDescription;
import sg.com.ebates.weather.weathermanagement.objs.WeatherMain;

public class Weather implements IWeather {
    private static final long WEATHER_CACHE_TIME_MILLIS = 5 * 60 * 1000; // 5 minutes
    long creatingTime = System.currentTimeMillis();
    private int id;
    private int cod;
    private City city;
    private IWeatherMain weatherMain;
    private IWeatherDescription weatherDescription;

    public Weather(int id, int cod, IWeatherMain weatherMain, IWeatherDescription weatherDescription) {
        this.id = id;
        this.cod = cod;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public City getCity() {
        return this.city;
    }

    @Override
    public int getCod() {
        return this.cod;
    }

    @Override
    public IWeatherMain getWeatherMain() {
        return this.weatherMain;
    }

    @Override
    public IWeatherDescription getWeatherDescription() {
        return this.weatherDescription;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() + WEATHER_CACHE_TIME_MILLIS >= this.creatingTime;
    }

    @Override
    public String toString() {
        return String.format("City: %s. Temp: %s. Weather: %s, %s.", this.city.toString(), this.weatherMain.getTemp(),
                this.weatherDescription.getMainDescription(), this.weatherDescription.getDescription());
    }

    public static interface IWeatherBuilder {
        IWeather build(String json) throws JSONException;
        IWeather build(JSONObject obj) throws JSONException;
    }

    private static class WeatherJsonBuilder implements IWeatherBuilder {

        @Override
        public IWeather build(String json) throws JSONException {
            JSONObject obj = new JSONObject(json);
            return this.build(obj);
        }

        @Override
        public IWeather build(JSONObject obj) throws JSONException {
            int id = obj.getInt("id");
            int cod = obj.getInt("cod");
            IWeatherMain main = this.buildWeatherMain(obj);
            IWeatherDescription description = this.buildWeatherDescription(obj);
            IWeather weather = new Weather(id, cod, main, description);
            return weather;
        }

        private IWeatherDescription buildWeatherDescription(JSONObject obj) throws JSONException {
            JSONArray weather = obj.getJSONArray("weather");
            if (null != weather && weather.length() > 0) {
                JSONObject weather0 = weather.getJSONObject(0);
                int id = weather0.getInt("id");
                String main = weather0.getString("main");
                String description = weather0.getString("description");
                String icon = weather0.getString("icon");
                IWeatherDescription weatherDescription = new WeatherDescription(id, main, description, icon);
                return weatherDescription;
            }
            return null;
        }

        private IWeatherMain buildWeatherMain(JSONObject obj) throws JSONException {
            JSONObject main = obj.getJSONObject("main");
            float temp = (float)main.getDouble("temp");
            int pressure = main.getInt("pressure");
            int humidity = main.getInt("humidity");
            float tempMin = main.has("temp_min")? (float)main.getDouble("temp_min"):temp; //default same with temp
            float tempMax = main.has("temp_max")? (float)main.getDouble("temp_max"):temp; //default same with temp
            IWeatherMain weatherMain = new WeatherMain(temp, pressure, humidity, tempMin, tempMax);
            return weatherMain;
        }
    }

    public static IWeatherBuilder getBuilder() {
        return new WeatherJsonBuilder();
    }
}
