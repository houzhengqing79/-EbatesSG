package sg.com.ebates.weather.container;

public interface IService {
    /**
     * get container
     * @return
     */
    IContainer getContainer();

    /**
     * get service in container
     * @param key
     * @param <T>
     * @return
     */
    <T> T getService(Object key);
}
