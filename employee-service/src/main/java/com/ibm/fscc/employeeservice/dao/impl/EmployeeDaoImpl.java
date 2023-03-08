package com.ibm.fscc.employeeservice.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.fscc.employeeservice.dao.EmployeeDao;
import com.ibm.fscc.employeeservice.data.EmployeeEntity;
import com.ibm.fscc.employeeservice.exceptions.ResourceAlreadyExistsException;
import com.ibm.fscc.employeeservice.exceptions.ResourceNotFoundException;
import com.ibm.fscc.employeeservice.exceptions.UnauthorizedException;
import com.ibm.fscc.employeeservice.repos.EmployeeRepository;
import com.ibm.fscc.employeeservice.shared.EmployeeDto;

@Service
public class EmployeeDaoImpl implements EmployeeDao {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EmployeeDto create(EmployeeEntity employee) {
		
		System.out.println("Data received:");
		System.out.println(employee.toString());
		
		//Check if email is null
		if (employee.getEmail() == null) {
			throw new UnauthorizedException("An employee must have an email address");
		}
		
		//Check if email address is already taken
		if (emailIsTaken(employee)) {
			throw new ResourceAlreadyExistsException("Employee with this email already exists");
		} else {
			//If not, create employee
			employee.setUserId(UUID.randomUUID().toString());
			EmployeeEntity newEmployee = repository.save(employee);
			return modelMapper.map(newEmployee, EmployeeDto.class);
		}
	}

	@Override
	public EmployeeDto update(EmployeeEntity employee) {
		EmployeeEntity updatedEmployee = repository.save(employee);
		return modelMapper.map(updatedEmployee, EmployeeDto.class);	
	}

	@Override
	public String delete(EmployeeEntity employee) {
		repository.delete(employee);
		return "Employee successfully deleted";
	}

	@Override
	public EmployeeDto find(Long id) {
		try {
			EmployeeEntity foundEmployee = repository.findById(id).get();
			return modelMapper.map(foundEmployee, EmployeeDto.class);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("An employee with this ID could not be found");
		}
		
	}

	@Override
	public List<EmployeeDto> findAll() {
		List<EmployeeEntity> employees = (List<EmployeeEntity>) repository.findAll();
		List<EmployeeDto> employeesDto = new ArrayList<EmployeeDto>();
		for (EmployeeEntity e: employees) {
			employeesDto.add(modelMapper.map(e, EmployeeDto.class));
		}
		return employeesDto;
	}
	
	@Override
	public EmployeeEntity findByEmail(String email) {
		List<EmployeeEntity> employees = (List<EmployeeEntity>) repository.findAll();
		for (EmployeeEntity e: employees) {
			if (e.getEmail().equals(email)) {
				return e;
			}
		}
		throw new UnauthorizedException("No user with this email exists");
	}
	
	private boolean emailIsTaken(EmployeeEntity employee) {
		List<EmployeeDto> employees = findAll();
		for (EmployeeDto e: employees) {
			if (employee.getEmail().equals(e.getEmail())) {
				return true;
			}
		}
		return false;
	}

}
