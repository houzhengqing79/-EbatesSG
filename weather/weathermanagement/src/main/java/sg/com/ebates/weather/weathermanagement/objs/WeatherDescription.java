package sg.com.ebates.weather.weathermanagement.objs;

public class WeatherDescription implements sg.com.ebates.weather.weathermanagement.IWeatherDescription {
    private int id;
    private String mainDescription;
    private String description;
    private String icon;

    public WeatherDescription(int id, String mainDescription, String description, String icon) {
        this.id = id;
        this.mainDescription = mainDescription;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
