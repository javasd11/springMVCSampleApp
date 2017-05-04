package com.jcg.dao;

import java.util.List;

import com.jcg.model.Employee;

public interface EmployeeDAO {

	Employee findById(int id);
	 
    void saveEmployee(Employee employee);
     
    void deleteEmployeeBySsn(String ssn);
     
    List findAllEmployees();
 
    Employee findEmployeeBySsn(String ssn);
}
