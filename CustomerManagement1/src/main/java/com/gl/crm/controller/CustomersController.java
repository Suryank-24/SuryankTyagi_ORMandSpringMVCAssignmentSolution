package com.gl.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.crm.entity.Customer;
import com.gl.crm.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomersController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/list")
	public String listCustomers(Model theModel) {
		// get Customer from db
		List<Customer> theCustomers = customerService.findAll();
		// add to the spring model
		theModel.addAttribute("Customers", theCustomers);
		return "customer-list";		
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data
		Customer thecustomer = new Customer();
		theModel.addAttribute("Customer", thecustomer);
		return "customer-form";
}
	

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		// get the Customer from the service
		Customer thecustomer = customerService.findById(theId);
		// set Customer as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", thecustomer);
		// send over to our form
		return "customer-form";
	}

	
	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname, @RequestParam("email") String email) {

		System.out.println(id);
		Customer theCustomer;
		if (id != 0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirstName(firstname);
			theCustomer.setLastname(lastname);
			theCustomer.setEmail(email);
		} else
			theCustomer = new Customer(firstname, lastname, email);
		// save the Book
		customerService.save(theCustomer);
		// use a redirect to prevent duplicate submissions
		return "redirect:/customers/list";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {
		// delete the Book
		customerService.deleteById(theId);
		// redirect to /Books/list
		return "redirect:/customers/list";
	}
}