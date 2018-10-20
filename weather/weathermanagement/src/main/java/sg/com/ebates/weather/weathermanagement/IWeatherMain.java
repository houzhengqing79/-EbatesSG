package sg.com.ebates.weather.weathermanagement;

public interface IWeatherMain {
    /**
     * get temp
     * @return
     */
    float getTemp();

    /**
     * get pressure
     * @return
     */
    int getPressure();

    /**
     * get humidity
     * @return
     */
    int getHumidity();

    /**
     * get min temp
     * @return
     */
    float getTempMin();

    /**
     * get max temp
     * @return
     */
    float getTempMax();
}
