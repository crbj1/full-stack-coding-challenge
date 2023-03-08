package com.ibm.fscc.employeeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.fscc.employeeservice.dao.EmployeeDao;
import com.ibm.fscc.employeeservice.data.EmployeeEntity;
import com.ibm.fscc.employeeservice.shared.EmployeeDto;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private Environment env;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@PostMapping("/create")
	@ResponseBody
	public EmployeeDto create(@RequestBody EmployeeEntity employee) {
		return employeeDao.create(employee);
	}
	
	@PutMapping("/update")
	@ResponseBody
	public EmployeeDto update(@RequestBody EmployeeEntity employee) {
		return employeeDao.update(employee);
	}
	
	@DeleteMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody EmployeeEntity employee) {
		return employeeDao.delete(employee);
	}
	
	@GetMapping("/find/{id}")
	@ResponseBody
	public EmployeeDto find(@PathVariable Long id) {
		return employeeDao.find(id);
	}
	
	@GetMapping("/findAll")
	@ResponseBody
	public List<EmployeeDto> findAll() {
		return employeeDao.findAll();
	}

	@GetMapping("/status/check")
	@ResponseBody
	public String status() {
		return "Working on port " + env.getProperty("server.port") + "!";
	}

}
