package sg.com.ebates.weather.weathermanagement;

import org.json.JSONException;

public interface IWeatherProvider {
    /**
     * query weather by city id
     * @param cityId
     * @return
     */
    IWeather query(int cityId) throws JSONException;
}
