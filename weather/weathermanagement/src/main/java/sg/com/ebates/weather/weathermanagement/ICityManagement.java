package sg.com.ebates.weather.weathermanagement;

import org.json.JSONException;

import java.util.List;

import sg.com.ebates.weather.weathermanagement.objs.City;

public interface ICityManagement {
    /**
     * initialize city list from city list file
     */
    void initialize() throws JSONException;

    /**
     * get all of supported cities list
     * cache from server cities list file
     * TODO: update for cities from server
     * @return
     */
    List<City> getCities();

    /**
     * get city by city id
     * @param id
     * @return City Object
     */
    City getCity(int id);

    /**
     * get cities list by city name
     * @param name
     * @return list of city which match city name
     */
    List<City> getCities(String name);
}
