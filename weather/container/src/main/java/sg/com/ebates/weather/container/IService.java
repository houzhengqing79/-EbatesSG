package sg.com.ebates.weather.container;

public interface IService {
    /**
     * get container
     * @return
     */
    IContainer getContainer();

    /**
     * get service in container
     * @param name
     * @param <T>
     * @return
     */
    <T> T getService(String name);
}
