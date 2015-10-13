package de.codecentric.ebss;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.codecentric.ebss.OrderEntryApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OrderEntryApplication.class)
@WebAppConfiguration
public class OrderApplicationTest {

	@Test
	public void contextLoads() {
	}

}