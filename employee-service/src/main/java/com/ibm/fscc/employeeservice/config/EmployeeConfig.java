package com.ibm.fscc.employeeservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class EmployeeConfig {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}
