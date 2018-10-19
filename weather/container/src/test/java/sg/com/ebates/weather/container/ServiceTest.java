package sg.com.ebates.weather.container;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sg.com.ebates.weather.container.core.Container;
import sg.com.ebates.weather.container.core.Service;

public class ServiceTest {
    private IContainer container;
    private IService service;

    @Before
    public void setUp() {
        this.container = new Container();
        this.service = new Service(this.container);
    }

    @Test
    public void testGetContainer() {
        Assert.assertEquals(this.container, this.service.getContainer());
    }

    @Test
    public void testGetService() {
        this.container.addService("a", this);
        Assert.assertEquals(this, this.service.getService("a"));
    }
}
