package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.com.ebates.weather.common.core.ResourceHelper;
import sg.com.ebates.weather.weathermanagement.ICityManagement;
import sg.com.ebates.weather.weathermanagement.objs.City;

public class CityManagement implements ICityManagement {
    private String cityFilePath = "/META-INF/city/city.list.json";
    List<City> cities = new ArrayList<>();
    Map<Integer, City> cityIdMap = new HashMap<>();
    Map<String, List<City>> cityNameMap = new HashMap<>();

    @Override
    public void initialize() throws JSONException {
        String cityData = ResourceHelper.readResource(CityManagement.class, cityFilePath);
        JSONArray cityList = new JSONArray(cityData);

        for(int i = 0; i < cityList.length(); ++i) {
            JSONObject cityObj = cityList.getJSONObject(i);
            int id = cityObj.getInt("id");
            String name = cityObj.getString("name");
            String country = cityObj.getString("country");
            JSONObject coord = cityObj.getJSONObject("coord");
            double lon = coord.getDouble("lon");
            double lat = coord.getDouble("lat");
            City city = new City(id, name, country, lon, lat);
            this.insertCity(city);
        }
    }

    @Override
    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    @Override
    public City getCity(int id) {
        return this.cityIdMap.get(id);
    }

    @Override
    public List<City> getCities(String name) {
        return Collections.unmodifiableList(this.cityNameMap.get(name));
    }

    private void insertCity(City city) {
        if (this.cityIdMap.containsKey(city.getId())) {
            return;
        }
        List<City> nameCities = this.cityNameMap.get(city.getName());
        if (null == nameCities) {
            nameCities = new ArrayList<City>();
            this.cityNameMap.put(city.getName(), nameCities);
        }
        nameCities.add(city);

        this.cities.add(city);
        this.cityIdMap.put(city.getId(), city);
    }
}
