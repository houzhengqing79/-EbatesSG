package sg.com.ebates.weather.container.core;

import java.util.HashMap;
import java.util.Map;

import sg.com.ebates.weather.container.IContainer;

public class Container implements IContainer {

    Map<Object, Object> objMap = new HashMap<>();

    @Override
    public <T> void addService(Object key, T obj) {
        this.objMap.put(key, obj);
    }

    @Override
    public <T> T getService(Object key) {
        return (T)this.objMap.get(key);
    }

}
