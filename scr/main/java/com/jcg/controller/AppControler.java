package com.jcg.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jcg.model.Employee;
import com.jcg.service.EmployeeService;

@Controller
@RequestMapping("/")
@ComponentScan("com.jcg")
public class AppControler {

	@Autowired
	EmployeeService service;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/", "list" }, method = RequestMethod.GET)
	public String listEmployees(ModelMap model) {	
		List<Employee> employees = service.findAllEmployees();		
		model.addAttribute("employees", employees);	
		return "allemployees";
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		return "registration";
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "registration";
		}

		if (!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn",
					new String[] { employee.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}

		service.saveEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName() + " registrated successfully");
		return "success";
	}

	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployeeBySsn(@PathVariable String ssn) {
		service.deleteEmployeeBySsn(ssn);
		return "redirect:/list";
	}
}