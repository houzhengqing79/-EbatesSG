package sg.com.ebates.weather.weather;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryManagement {
    private static final int HISTORY_SIZE_LIMIT = 10;
    private static final int SINGAPORE_CITY_ID = 1880252;
    private static final String WeatherHistoryName = "WeatherHistory";
    private static final String CityIdKey = "CityIdKey";

    private Context context;
    private List<Integer> historyCityIds;

    public HistoryManagement(Context context) {
        this.context = context;
    }

    public List<Integer> getHistoryCityIds() {
        SharedPreferences pref = this.context.getSharedPreferences(WeatherHistoryName, Context.MODE_PRIVATE);
        String cityIdsValue = pref.getString(CityIdKey, "");
        List<Integer> cityIds = this.parseCityIds(cityIdsValue);
        if (!cityIds.contains(SINGAPORE_CITY_ID)) {
            cityIds.add(0, SINGAPORE_CITY_ID);
        }
        if (cityIds.size() > HISTORY_SIZE_LIMIT) {
            cityIds = cityIds.subList(0, HISTORY_SIZE_LIMIT);
        }
        this.historyCityIds = cityIds;
        return Collections.unmodifiableList(cityIds);
    }

    public void addCity(Integer cityId) {
        // support the first one is singapore
        this.historyCityIds.add(1, cityId);
        StringBuilder sb = new StringBuilder();
        for (Integer i: this.historyCityIds) {
            sb.append(String.valueOf(i));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String cityIdsValue = sb.toString();
        SharedPreferences pref = this.context.getSharedPreferences(WeatherHistoryName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(CityIdKey, cityIdsValue);
        editor.commit();
    }

    private List<Integer> parseCityIds(String value) {
        ArrayList<Integer> cityIds = new ArrayList<>();
        String[] values = value.split(",");
        for (String val: values) {
            Integer intVal = this.parseInt(val);
            if (null != intVal) {
                if (!cityIds.contains(intVal)) {
                    cityIds.add(intVal);
                }
            }
        }
        return cityIds;
    }

    private Integer parseInt(String val) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
