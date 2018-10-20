package sg.com.ebates.weather.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

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
import sg.com.ebates.weather.weathermanagement.core.Weather;
import sg.com.ebates.weather.weathermanagement.core.WeatherManagement;
import sg.com.ebates.weather.weathermanagement.objs.City;
import sg.com.ebates.weather.weathermanagement.providers.openweathermap.OpenWeatherMapProvider;

public class WeatherMain extends AppCompatActivity {

    private ILogManagement logManagement;
    private ICityManagement cityManagement;
    private IWeatherManagement weatherManagement;

    private HistoryManagement historyManagement;

    private AutoCompleteTextView cityAutoCompleteTextView;
    private ArrayList<City> cityArrayList;
    private ArrayAdapter<City> cityArrayAdapter;

    private ListView weatherListView;
    private ArrayList<IWeather> weathers = new ArrayList<>();
    private ArrayAdapter<IWeather> weatherArrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);

        this.initialize();
        this.initializeUI();
        this.initializeData();
    }

    private void initializeUI() {
        this.cityAutoCompleteTextView = this.findViewById(R.id.city_search_text);
        this.cityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = WeatherMain.this.cityArrayAdapter.getItem(position);
                WeatherMain.this.logManagement.i("Selected city: " + city.toString());
                WeatherMain.this.historyManagement.addCity(city.getId());
                WeatherMain.this.insertWeather(city);
            }
        });

        this.weatherListView = this.findViewById(R.id.weather_list_view);
        this.weatherArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, weathers);
        this.weatherListView.setAdapter(weatherArrayAdapter);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void insertWeather(final City city) {
        this.cityAutoCompleteTextView.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IWeather weather = WeatherMain.this.weatherManagement.getWeather(city);
                    if (!WeatherMain.this.weathers.contains(weather)) {
                        WeatherMain.this.weathers.add(0, weather);
                        WeatherMain.this.refreshWeatherListView();
                    }
                } catch (JSONException e) {
                    WeatherMain.this.logManagement.e("Add weather failed: " + e.getLocalizedMessage());
                    WeatherMain.this.showToastMessage("Weather query failed: " + e.getLocalizedMessage());
                } catch (IOException e) {
                    WeatherMain.this.logManagement.e("Add weather failed: " + e.getLocalizedMessage());
                    WeatherMain.this.showToastMessage("Weather query failed: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    private void initializeData() {
        this.cityAutoCompleteTextView.setEnabled(false);
        this.cityAutoCompleteTextView.setText("loading city and weather information...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WeatherMain.this.cityManagement.initialize();
                    List<Integer> historyIds = WeatherMain.this.historyManagement.getHistoryCityIds();
                    List<IWeather> weathers = WeatherMain.this.buildWeatherByCityIds(historyIds);
                    WeatherMain.this.weathers.clear();
                    WeatherMain.this.weathers.addAll(weathers);
                    WeatherMain.this.refreshWeatherListView();
                    WeatherMain.this.refreshCityAutoCompleteTextView();
                } catch (Exception e) {
                    WeatherMain.this.logManagement.e("Initializing history failed: " + e.getLocalizedMessage());
                    WeatherMain.this.showToastMessage("Initializing failed: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    private void refreshCityAutoCompleteTextView() {
        WeatherMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WeatherMain.this.setCityAutoCompleteTextViewAdapter();
            }
        });
    }

    private void setCityAutoCompleteTextViewAdapter() {
        this.cityArrayList = new ArrayList<>(this.cityManagement.getCities());
        this.cityArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.cityArrayList);
        this.cityAutoCompleteTextView.setAdapter(this.cityArrayAdapter);
    }

    private void refreshWeatherListView() {
        WeatherMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WeatherMain.this.cityAutoCompleteTextView.setText("");
                WeatherMain.this.cityAutoCompleteTextView.setEnabled(true);
                WeatherMain.this.weatherArrayAdapter.notifyDataSetChanged();
                WeatherMain.this.weatherListView.invalidate();
                WeatherMain.this.weatherListView.setSelection(0);
            }
        });
    }

    private List<IWeather> buildWeatherByCityIds(List<Integer> cityIds) throws JSONException, IOException {
        List<IWeather> weathers = new ArrayList<>();
        for (Integer cityId: cityIds) {
            City city = this.cityManagement.getCity(cityId);
            IWeather weather = this.weatherManagement.getWeather(city);
            weathers.add(weather);
        }
        return weathers;
    }

    private void initialize() {
        this.logManagement = new LogManagement();
        IContainer container = ContainerFactory.getContainer();
        container.addService(ILogManagement.class, this.logManagement);

        this.cityManagement = new CityManagement();
        container.addService(ICityManagement.class, this.cityManagement);

        IWeatherProvider weatherProvider = new OpenWeatherMapProvider(container);
        container.addService(IWeatherProvider.class, weatherProvider);

        this.weatherManagement = new WeatherManagement(container);
        container.addService(IWeatherManagement.class, this.weatherManagement);

        this.historyManagement = new HistoryManagement(this);
    }

    private void showToastMessage(final String message) {
        WeatherMain.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WeatherMain.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
