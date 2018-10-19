package sg.com.ebates.weather.container.core;

import org.junit.Assert;
import org.junit.Test;

import sg.com.ebates.weather.container.IContainer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ContainerTest {
    @Test
    public void common() {
        IContainer container = new Container();

        container.addService("a", this);
        Assert.assertEquals(this, container.getService("a"));

        container.addService("a", "b");
        Assert.assertEquals("b", container.getService("a"));
    }
}