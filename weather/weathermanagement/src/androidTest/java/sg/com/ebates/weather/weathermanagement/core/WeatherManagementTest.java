package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import sg.com.ebates.weather.common.ILogManagement;
import sg.com.ebates.weather.common.core.LogManagement;
import sg.com.ebates.weather.container.ContainerFactory;
import sg.com.ebates.weather.container.IContainer;
import sg.com.ebates.weather.weathermanagement.ICityManagement;
import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.IWeatherManagement;
import sg.com.ebates.weather.weathermanagement.IWeatherProvider;
import sg.com.ebates.weather.weathermanagement.UTHelper;
import sg.com.ebates.weather.weathermanagement.objs.City;
import sg.com.ebates.weather.weathermanagement.providers.openweathermap.OpenWeatherMapProvider;

public class WeatherManagementTest {
    IContainer container;
    ILogManagement logManagement;
    ICityManagement cityManagement;
    IWeatherProvider weatherProvider;
    IWeatherManagement weatherManagement;

    @Before
    public void setUp() throws IOException, JSONException {
        this.container = ContainerFactory.getContainer();
        this.logManagement = new LogManagement();
        this.container.addService(ILogManagement.class, this.logManagement);

        this.cityManagement = new CityManagement();
        this.cityManagement.initialize();
        this.container.addService(ICityManagement.class, this.cityManagement);

        this.weatherProvider = new OpenWeatherMapProvider(this.container);
        this.container.addService(IWeatherProvider.class,this.weatherProvider);

        this.weatherManagement = new WeatherManagement(container);
    }

    @Test
    public void testGetWeather() throws JSONException {
        City city = this.cityManagement.getCity(UTHelper.TEST_CITY_ID);
        Assert.assertNotNull(city);
        IWeather weather = this.weatherManagement.getWeather(city);
        Assert.assertNotNull(weather);
        Assert.assertEquals(UTHelper.TEST_CITY_ID, weather.getId());
        Assert.assertEquals(UTHelper.TEST_CITY_COD, weather.getCod());
    }
}
