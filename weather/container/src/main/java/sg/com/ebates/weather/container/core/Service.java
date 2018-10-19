package sg.com.ebates.weather.container.core;

import sg.com.ebates.weather.container.IContainer;
import sg.com.ebates.weather.container.IService;

public class Service implements IService {
    private IContainer container;

    public Service(IContainer container) {
        this.container = container;
    }

    @Override
    public IContainer getContainer() {
        return this.container;
    }

    @Override
    public <T> T getService(String name) {
        return this.getContainer().getService(name);
    }
}
