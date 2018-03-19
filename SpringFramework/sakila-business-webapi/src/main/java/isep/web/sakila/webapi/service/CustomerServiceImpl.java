package isep.web.sakila.webapi.service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isep.web.sakila.dao.repositories.CustomerRepository;
import isep.web.sakila.jpa.entities.Customer;
import isep.web.sakila.webapi.model.CustomerWO;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerRepository		customerRepository;

	private static final Log	log	= LogFactory.getLog(CustomerServiceImpl.class);

	public List<CustomerWO> findAllCustomers()
	{
		List<CustomerWO> customers = new LinkedList<CustomerWO>();

		for (Customer customer : customerRepository.findAll())
		{
			customers.add(new CustomerWO(customer));
			log.debug("Adding " + customer);
		}

		return customers;
	}

	public CustomerWO findById(int id)
	{
		log.debug(String.format("Looking for user by Id %s", id));
		Customer customer = customerRepository.findOne(id);

		if (customer != null)
		{
			return new CustomerWO(customer);
		}
		return null;
	}
	
	public CustomerWO findLastName(String lastName) {
		// TODO Auto-generated method stub
		log.debug(String.format("Looking for user by lastname %s", lastName));
		Customer customer = customerRepository.findByLastName(lastName);

		if (customer != null)
		{
			return new CustomerWO(customer);
		}
		return null;
	}


	public void saveCustomer(CustomerWO customerWO)
	{
		Customer customer = new Customer();
		customer.setLastName(customerWO.getLastName());
		customer.setPassword(customerWO.getPassword());
		customer.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		customerRepository.save(customer);
	}

	public void updateCustomer(CustomerWO customerWO)
	{
		Customer customer2update = customerRepository.findOne(customerWO.getId());
		customer2update.setLastName(customerWO.getLastName());
		customer2update.setPassword(customerWO.getPassword());
		customer2update.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		customerRepository.save(customer2update);
	}
	
	public void loginCustomer(CustomerWO customerWO) 
	{
		Customer checkcredentials = customerRepository.findByLastName(customerWO.getLastName());
		if(checkcredentials.getPassword()==customerWO.getPassword()){
			System.out.println("Password is "+customerWO.getPassword());
			System.out.println("User Exist");
			
		}
		else{
			System.out.println("User doesn't exist");
		}
		/*String usernameWO = userWO.getLastName();
		String passwordWO = userWO.getPassword();
		System.out.println("jdgjfgjfdhgg: "+usernameWO);*/
		
		/*List<CustomerWO> customers = new LinkedList<CustomerWO>();

		for (Customer customer : customerRepository.findAll())
		{
			
		}
		
		
		
		String usernameDB = customer.getUsername();
		String passwordDB = customer.getPassword();
		
		//We find the costumer
		customer = customerRepository.findOne(userWO.getId());
		
		//We check the logging
		if(usernameWO.equals(usernameDB) && passwordDB.equals(passwordWO))
		{
			System.out.println("\nUsername: "+usernameDB);
		}
		else
		{
			System.out.println("\n USER DOES NOT EXIST");
		}*/

	}

	@Override
	public void deleteCustomerById(int id)
	{
		customerRepository.delete(id);
	}

	




}
