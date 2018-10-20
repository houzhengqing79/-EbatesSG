package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.com.ebates.weather.common.ILogManagement;
import sg.com.ebates.weather.container.IContainer;
import sg.com.ebates.weather.container.core.Service;
import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.IWeatherManagement;
import sg.com.ebates.weather.weathermanagement.IWeatherProvider;
import sg.com.ebates.weather.weathermanagement.objs.City;

public class WeatherManagement extends Service implements IWeatherManagement {
    List<IWeather> weathers = new ArrayList<>();
    Map<City, IWeather> weatherCityMap = new HashMap<>();

    ILogManagement log;

    public WeatherManagement(IContainer container) {
        super(container);
        this.log = this.getService(ILogManagement.class);
    }

    @Override
    public List<IWeather> getWeathers() {
        return Collections.unmodifiableList(this.weathers);
    }

    @Override
    public IWeather getWeather(City city) throws JSONException {
        IWeather weather = this.weatherCityMap.get(city);
        if (null == weather || weather.isExpired()) {
            this.weatherCityMap.remove(city);
            return this.query(city);
        }
        return weather;
    }

    @Override
    public void bulkDownloading() {
        //TODO
    }

    private IWeather query(City city) throws JSONException {
        this.log.i("Start to query weather for city: " + city.toString());
        IWeatherProvider provider = this.getService(IWeatherProvider.class);
        IWeather weather = provider.query(city.getId());
        weather.setCity(city);
        this.insertWeather(weather);
        return weather;
    }

    private void insertWeather(IWeather weather){
        if (null == weather) {
            return;
        }
        IWeather localWeather = this.weatherCityMap.get(weather.getCity());
        if (null != localWeather) {
            this.weathers.remove(localWeather);
        }
        this.weathers.add(weather);
        this.weatherCityMap.put(weather.getCity(), weather);
    }
}
