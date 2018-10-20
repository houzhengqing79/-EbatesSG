package sg.com.ebates.weather.weathermanagement.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    Map<String, City> cityNameMap = new HashMap<>();

    @Override
    public void initialize() throws JSONException, IOException {
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
    public List<String> getCityFullnames() {
        return new ArrayList<>(this.cityNameMap.keySet());
    }

    @Override
    public City getCity(String fullname) {
        return this.cityNameMap.get(fullname);
    }

    @Override
    public City getCity(int id) {
        return this.cityIdMap.get(id);
    }

    private void insertCity(City city) {
        if (this.cityIdMap.containsKey(city.getId())) {
            return;
        }

        this.cityNameMap.put(city.getFullname(), city);
        this.cities.add(city);
        this.cityIdMap.put(city.getId(), city);
    }
}
