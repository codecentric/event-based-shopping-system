package de.codecentric.ebss.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import de.codecentric.ebss.model.Order;

public interface OrderEntryService {

	public void createOrderEntry(Order orderEntry);
	
	public void createOrderEntryFromJson(ConcurrentHashMap<String, String> orderEntryJson);

	public List<Order> findAll();

}