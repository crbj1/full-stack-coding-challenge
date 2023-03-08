package com.ibm.fscc.employeeservice.dao;

import java.util.List;

import com.ibm.fscc.employeeservice.data.EmployeeEntity;
import com.ibm.fscc.employeeservice.shared.EmployeeDto;

public interface EmployeeDao {
	
	EmployeeDto create(EmployeeEntity employee);
	EmployeeDto update(EmployeeEntity employee);
	String delete(EmployeeEntity employee);
	
	EmployeeDto find(Long id);
	List<EmployeeDto> findAll();
	
	EmployeeEntity findByEmail(String email);

}
