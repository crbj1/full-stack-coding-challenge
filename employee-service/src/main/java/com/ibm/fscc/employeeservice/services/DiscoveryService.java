package com.ibm.fscc.employeeservice.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryService {
	
	@Autowired
	private DiscoveryClient client;
	
	private URI getLoginServiceUrl() {
		List<ServiceInstance> list = client.getInstances("login-ws");
		if (list != null && list.size() > 0) {
			return list.get(0).getUri();
		}
		return null;
	}
	
	public URI getValidateUrl() {
		String baseUrlString = getLoginServiceUrl().toString();
		if (baseUrlString != null) {
			String hashUrlString = baseUrlString + "/api/valid/token";
			return URI.create(hashUrlString);
		}
		return null;
	}

}
