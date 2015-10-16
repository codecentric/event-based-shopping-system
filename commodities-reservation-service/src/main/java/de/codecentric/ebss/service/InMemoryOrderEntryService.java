package de.codecentric.ebss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.Assert;

import de.codecentric.ebss.model.Order;

public class InMemoryOrderEntryService implements OrderEntryService {

	private Log log = LogFactory.getLog(getClass());
	
//	private ObjectMapper mapper;
	private Map<UUID, Order> orders = new ConcurrentHashMap<UUID, Order>();
	
	public InMemoryOrderEntryService(ObjectMapper mapper) {
		// this.mapper = mapper;
	}

	@Override
	public void createOrderEntry(Order orderEntry) {
		Assert.notNull(orderEntry);
		orders.put(orderEntry.getUuid(), orderEntry);
	}

	@Override
	public List<Order> findAll() {
		return new ArrayList<Order>(orders.values());
	}

	@Override
	public void createOrderEntryFromJson(ConcurrentHashMap<String, String> orderEntryJson) {
		log.info("orderEntryJson :: " + orderEntryJson);
	}
}
