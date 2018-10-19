package sg.com.ebates.weather.weathermanagement;

import sg.com.ebates.weather.weathermanagement.objs.City;
import sg.com.ebates.weather.weathermanagement.objs.WeatherDescription;
import sg.com.ebates.weather.weathermanagement.objs.WeatherMain;

public interface IWeather {

    /**
     * get weather id
     * @return
     */
    int getId();

    /**
     * city of current weather for
     * @return
     */
    City getCity();

    /**
     * get cod
     * @return
     */
    int getCod();

    /**
     * get Weather Main object
     * @return
     */
    WeatherMain getWeatherMain();

    /**
     * get weather description object
     * @return
     */
    WeatherDescription getWeatherDescription();
}
