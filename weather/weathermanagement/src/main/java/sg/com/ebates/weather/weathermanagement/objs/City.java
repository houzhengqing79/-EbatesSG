package sg.com.ebates.weather.weathermanagement.objs;

public class City {
    private int id;
    private String name;
    private String country;
    private Coordinate coordinate;

    private City(int id, String name, String country, Coordinate coordinate) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.coordinate = coordinate;
    }
    public City(int id, String name, String country, double lon, double lat) {
        this(id, name, country, new Coordinate(lon, lat));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString(){
        return "City, id: " + id + ", name: " + name + ", country: " + this.country;
    }
}
