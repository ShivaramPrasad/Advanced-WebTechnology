package isep.web.sakila.webapi.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import isep.web.sakila.webapi.model.CustomerWO;
import isep.web.sakila.webapi.service.CustomerService;

@RestController
public class CustomerRestController
{

	@Autowired
	CustomerService							customerService;

	private static final Log	log	= LogFactory.getLog(CustomerRestController.class);

	@RequestMapping(value = "/customer/", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerWO>> listAllCustomers()
	{
		List<CustomerWO> customers = customerService.findAllCustomers();
		if (customers.isEmpty())
		{
			return new ResponseEntity<List<CustomerWO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<CustomerWO>>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerWO> getCustomer(@PathVariable("id") int id)
	{
		System.out.println("Fetching Customer with id " + id);
		CustomerWO staffWO = customerService.findById(id);
		if (staffWO == null)
		{
			System.out.println("Customer with id " + id + " not found");
			return new ResponseEntity<CustomerWO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerWO>(staffWO, HttpStatus.OK);
	}
	
	/*
	 * Call customername Example localhost:8080/customername/SMITH/
	 */
	@RequestMapping(value = "/customername/{lastName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerWO> getCustomerLastName(@PathVariable("lastName") String lastName)
	{
		System.out.println("Fetching Customer with lastname " + lastName);
		CustomerWO staffWO = customerService.findLastName(lastName);
		if (staffWO == null)
		{
			System.out.println("Customer with lastname " + lastName + " not found");
			return new ResponseEntity<CustomerWO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerWO>(staffWO, HttpStatus.OK);
	}

	// -------------------Create a Customer----------------------------------

	@RequestMapping(value = "/customer/", method = RequestMethod.POST)
	public ResponseEntity<Void> createCustomer(@RequestBody CustomerWO customerWO, UriComponentsBuilder ucBuilder)
	{
		System.out.println("Creating Customer " + customerWO.getLastName());

		customerService.saveCustomer(customerWO);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(customerWO.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/customerUpdate/", method = RequestMethod.POST)
	public ResponseEntity<CustomerWO> updateCustomer(@RequestBody CustomerWO customerWO, UriComponentsBuilder ucBuilder)
	{
		log.error(String.format("Updating Customer %s ", customerWO.getId()));
		CustomerWO currentCustomer = customerService.findById(customerWO.getId());

		if (currentCustomer == null)
		{
			System.out.println("Customer with id " + customerWO.getId() + " not found");
			return new ResponseEntity<CustomerWO>(HttpStatus.NOT_FOUND);
		}

		currentCustomer.setLastName(customerWO.getLastName());
		currentCustomer.setPassword(customerWO.getPassword());
		customerService.updateCustomer(currentCustomer);

		return new ResponseEntity<CustomerWO>(currentCustomer, HttpStatus.OK);
	}
	
	/*
	 * Function loginCustomer
	 */

	@RequestMapping(value = "/customerLogin/", method = RequestMethod.GET)
	public ResponseEntity<CustomerWO> loginCustomer(@RequestBody CustomerWO customerWO, UriComponentsBuilder ucBuilder)
	{
		log.error(String.format("Login Customer ID %s ", customerWO.getId()));
		log.error(String.format("Login Customer LastName %s ", customerWO.getLastName()));
		CustomerWO currentCustomer = customerService.findLastName(customerWO.getLastName());

		if (currentCustomer == null)
		{
			System.out.println("Customer with lastname " + customerWO.getLastName() + " not found");
			return new ResponseEntity<CustomerWO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<CustomerWO>(currentCustomer, HttpStatus.OK);
	}
	@RequestMapping(value = "/customerDelete/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomerWO> deleteCustomer(@PathVariable("id") int id)
	{

		System.out.println("Fetching & Deleting Customer with id " + id);

		CustomerWO staffWO = customerService.findById(id);
		if (staffWO == null)
		{
			System.out.println("Unable to delete. Customer with id " + id + " not found");
			return new ResponseEntity<CustomerWO>(HttpStatus.NOT_FOUND);
		}

		customerService.deleteCustomerById(id);
		return new ResponseEntity<CustomerWO>(HttpStatus.NO_CONTENT);
	}
}
