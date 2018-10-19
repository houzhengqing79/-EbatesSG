package sg.com.ebates.weather.weathermanagement;

public interface IWeatherProvider {
    String NAME = "WEATHER_PROVIDER";
    /**
     * query weather by city id
     * @param cityId
     * @return
     */
    IWeather query(int cityId);
}
