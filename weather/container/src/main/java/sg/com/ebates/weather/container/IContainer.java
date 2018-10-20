package sg.com.ebates.weather.container;

public interface IContainer {
    /**
     * put an object in
     * @param key as the key of object
     * @param obj
     * @param <T> objct type
     */
    <T> void addService(Object key, T obj);

    /**
     * get an object by name
     * @param key
     * @param <T>
     * @return object
     */
    <T> T getService(Object key);
}
