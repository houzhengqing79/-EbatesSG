package sg.com.ebates.weather.weathermanagement;

import sg.com.ebates.weather.weathermanagement.objs.City;

public interface IWeather {

    /**
     * get weather id
     * @return
     */
    int getId();

    /**
     * set mapping city
     * @param city
     */
    void setCity(City city);

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
    IWeatherMain getWeatherMain();

    /**
     * get weather description object
     * @return
     */
    IWeatherDescription getWeatherDescription();

    boolean isExpired();
}
