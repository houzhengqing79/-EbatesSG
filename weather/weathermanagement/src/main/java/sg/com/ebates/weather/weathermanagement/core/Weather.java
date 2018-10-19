package sg.com.ebates.weather.weathermanagement.core;

import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.objs.City;
import sg.com.ebates.weather.weathermanagement.objs.WeatherDescription;
import sg.com.ebates.weather.weathermanagement.objs.WeatherMain;

class Weather implements IWeather {
    private int id;
    private int cod;
    private City city;
    private WeatherMain weatherMain;
    private WeatherDescription weatherDescription;

    public Weather(int id, int cod, City city, WeatherMain weatherMain, WeatherDescription weatherDescription) {
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
    public City getCity() {
        return this.city;
    }

    @Override
    public int getCod() {
        return this.cod;
    }

    @Override
    public WeatherMain getWeatherMain() {
        return this.weatherMain;
    }

    @Override
    public WeatherDescription getWeatherDescription() {
        return this.weatherDescription;
    }
}
