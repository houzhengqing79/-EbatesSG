package sg.com.ebates.weather.container.core;

import java.util.HashMap;
import java.util.Map;

import sg.com.ebates.weather.container.IContainer;

public class Container implements IContainer {

    Map<String, Object> objMap = new HashMap<>();

    @Override
    public <T> void addService(String name, T obj) {
        this.objMap.put(name, obj);
    }

    @Override
    public <T> T getService(String name) {
        return (T)this.objMap.get(name);
    }
}
