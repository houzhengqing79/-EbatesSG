package sg.com.ebates.weather.container;

public interface IContainer {
    /**
     * put an object in
     * @param name as the key of object
     * @param obj
     * @param <T> objct type
     */
    <T> void addService(String name, T obj);

    /**
     * get an object by name
     * @param name
     * @param <T>
     * @return object
     */
    <T> T getService(String name);
}
