package sg.com.ebates.weather.weather;

import java.util.ArrayList;
import java.util.List;

public class HistoryManagement {
    private static final int SINGAPORE_CITY_ID = 1880252;
    public List<Integer> getHistoryCityIds() {
        List<Integer> cityIds = new ArrayList<>();
        cityIds.add(SINGAPORE_CITY_ID);
        return cityIds;
    }
}
