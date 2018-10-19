package sg.com.ebates.weather.container;

import sg.com.ebates.weather.container.core.Container;

public class ContainerFactory {
    private static final IContainer container = new Container();

    /**
     * Singleton Container
     * @return
     */
    public static IContainer getContainer() {
        return container;
    }
}
