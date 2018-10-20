package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import sg.com.ebates.weather.weathermanagement.ICityManagement;

public class CityManagementTest {
    @Test
    public void testInitialize() throws JSONException, IOException {
        ICityManagement cityManagement = new CityManagement();
        cityManagement.initialize();
        Assert.assertTrue(cityManagement.getCities().size() > 0);
    }
}
