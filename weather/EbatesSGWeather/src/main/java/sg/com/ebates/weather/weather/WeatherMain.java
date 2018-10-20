package sg.com.ebates.weather.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sg.com.ebates.weather.common.ILogManagement;
import sg.com.ebates.weather.common.core.LogManagement;
import sg.com.ebates.weather.container.ContainerFactory;
import sg.com.ebates.weather.container.IContainer;
import sg.com.ebates.weather.weathermanagement.ICityManagement;
import sg.com.ebates.weather.weathermanagement.IWeather;
import sg.com.ebates.weather.weathermanagement.IWeatherManagement;
import sg.com.ebates.weather.weathermanagement.IWeatherProvider;
import sg.com.ebates.weather.weathermanagement.core.CityManagement;
import sg.com.ebates.weather.weathermanagement.core.WeatherManagement;
import sg.com.ebates.weather.weathermanagement.objs.City;
import sg.com.ebates.weather.weathermanagement.providers.openweathermap.OpenWeatherMapProvider;

public class WeatherMain extends AppCompatActivity {

    private ILogManagement logManagement;
    private ICityManagement cityManagement;
    private IWeatherManagement weatherManagement;
    private ArrayAdapter<City> cityArrayAdapter;
    private HistoryManagement historyManagement;

    private ListView weatherListView;
    private ArrayList<IWeather> weathers = new ArrayList<>();
    private ArrayAdapter<IWeather> weatherArrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        //initialized component
        try {
            this.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.initializeUI();
        this.initializeHistory();
    }

    private void initializeUI() {
        AutoCompleteTextView textView = this.findViewById(R.id.city_search_text);
        final List<City> cities = this.cityManagement.getCities();
        this.cityArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, cities);
        textView.setAdapter(cityArrayAdapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = WeatherMain.this.cityArrayAdapter.getItem(position);
                WeatherMain.this.logManagement.i("Selected city: " + city.toString());
            }
        });

        this.weatherListView = this.findViewById(R.id.weather_list_view);
        this.weatherArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, weathers);
        this.weatherListView.setAdapter(weatherArrayAdapter);
    }

    private void initializeHistory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Integer> historyIds = WeatherMain.this.historyManagement.getHistoryCityIds();
                    List<IWeather> weathers = WeatherMain.this.buildWeatherByCityIds(historyIds);
                    WeatherMain.this.weathers.clear();
                    WeatherMain.this.weathers.addAll(weathers);
                    WeatherMain.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WeatherMain.this.weatherListView.invalidate();
                        }
                    });
                }
                catch (Exception e) {
                    WeatherMain.this.logManagement.e("Initializing history failed: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    private List<IWeather> buildWeatherByCityIds(List<Integer> cityIds) throws JSONException {
        List<IWeather> weathers = new ArrayList<>();
        for (Integer cityId: cityIds) {
            City city = this.cityManagement.getCity(cityId);
            IWeather weather = this.weatherManagement.getWeather(city);
            weathers.add(weather);
        }
        return weathers;
    }

    private void initialize() throws IOException, JSONException {
        this.logManagement = new LogManagement();
        IContainer container = ContainerFactory.getContainer();
        container.addService(ILogManagement.class, this.logManagement);

        this.cityManagement = new CityManagement();
        this.cityManagement.initialize();
        container.addService(ICityManagement.class, this.cityManagement);

        IWeatherProvider weatherProvider = new OpenWeatherMapProvider(container);
        container.addService(IWeatherProvider.class, weatherProvider);

        this.weatherManagement = new WeatherManagement(container);
        container.addService(IWeatherManagement.class, this.weatherManagement);

        this.historyManagement = new HistoryManagement();
    }
}
