package sg.com.ebates.weather.weathermanagement.providers.openweathermap;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sg.com.ebates.weather.common.ILogManagement;
import sg.com.ebates.weather.common.core.LogManagement;
import sg.com.ebates.weather.container.ContainerFactory;
import sg.com.ebates.weather.container.IContainer;
import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.UTHelper;

public class OpenWeatherMapProviderTest {

    IContainer container;
    ILogManagement logManagement;
    OpenWeatherMapProvider provider;

    @Before
    public void setUp() {
        this.container = ContainerFactory.getContainer();
        this.logManagement = new LogManagement();

        this.container.addService(ILogManagement.class, this.logManagement);

        this.provider = new OpenWeatherMapProvider(this.container);
    }

    @Test
    public void testQuery() throws JSONException {
        int id = 2172797;
        IWeather weather = this.provider.query(id);
        Assert.assertNotNull(weather);
        Assert.assertEquals(UTHelper.TEST_CITY_ID, weather.getId());
        Assert.assertEquals(UTHelper.TEST_CITY_COD, weather.getCod());
    }
}
