package sg.com.ebates.weather.weathermanagement;

import org.json.JSONException;

import java.util.List;

import sg.com.ebates.weather.weathermanagement.objs.City;

public interface IWeatherManagement {
    /**
     * get all of cache weathers
     * @return
     */
    List<IWeather> getWeathers();

    /**
     * get weather by city
     * first check cache, if no cache or expired, will try query from server
     * @param city
     * @return
     */
    IWeather getWeather(City city) throws JSONException;

    /**
     * bulkDownloading
     */
    void bulkDownloading();
}
