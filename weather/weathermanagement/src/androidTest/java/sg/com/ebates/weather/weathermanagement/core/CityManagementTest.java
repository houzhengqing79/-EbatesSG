package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.com.ebates.weather.weathermanagement.ICityManagement;

public class CityManagementTest {
    @Test
    public void testInitialize() throws JSONException {
        ICityManagement cityManagement = new CityManagement();
        cityManagement.initialize();
        Assert.assertTrue(true);
    }
}
