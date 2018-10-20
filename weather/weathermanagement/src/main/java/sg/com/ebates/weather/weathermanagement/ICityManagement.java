package sg.com.ebates.weather.weathermanagement;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import sg.com.ebates.weather.weathermanagement.objs.City;

public interface ICityManagement {
    /**
     * initialize city list from city list file
     */
    void initialize() throws JSONException, IOException;

    /**
     * get all of supported cities list
     * cache from server cities list file
     * TODO: update for cities from server
     * @return
     */
    List<City> getCities();

    /**
     * return city full name list
     * @return
     */
    List<String> getCityFullnames();

    /**
     * get city by city id
     * @param id
     * @return City Object
     */
    City getCity(int id);

    /**
     * get cities list by city name
     * @param fullname city fullname
     * @return list of city which match city name
     */
    City getCity(String fullname);
}
