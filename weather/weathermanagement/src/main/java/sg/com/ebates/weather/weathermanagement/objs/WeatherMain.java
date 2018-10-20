package sg.com.ebates.weather.weathermanagement.objs;

public class WeatherMain implements sg.com.ebates.weather.weathermanagement.IWeatherMain {
    private float temp;
    private int pressure;
    private int humidity;
    private float tempMin;
    private float tempMax;

    public WeatherMain(float temp, int pressure, int humidity, float tempMin, float tempMax) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    @Override
    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    @Override
    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @Override
    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    @Override
    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }
}
