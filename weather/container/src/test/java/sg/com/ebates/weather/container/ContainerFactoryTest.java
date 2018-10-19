package sg.com.ebates.weather.container;

import org.junit.Assert;
import org.junit.Test;

public class ContainerFactoryTest {
    @Test
    public void common() {
        IContainer container = ContainerFactory.getContainer();
        Assert.assertNotNull(container);
        Assert.assertEquals(container, ContainerFactory.getContainer());
    }
}
