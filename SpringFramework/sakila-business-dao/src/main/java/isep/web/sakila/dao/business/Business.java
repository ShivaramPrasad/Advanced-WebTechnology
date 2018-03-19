package isep.web.sakila.dao.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import isep.web.sakila.dao.repositories.ActorRepository;
import isep.web.sakila.jpa.entities.Actor;
import isep.web.sakila.dao.repositories.CustomerRepository;
import isep.web.sakila.jpa.entities.Customer;

@Service("business")
public class Business implements IBusiness
{
	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Actor> getAllActors()
	{
		return Lists.newArrayList(actorRepository.findAll());
	}

	public Actor getByID(int actorId)
	{
		return actorRepository.findOne(actorId);
	}
	
	@Override
	public List<Customer> getAllCustomers()
	{
		return Lists.newArrayList(customerRepository.findAll());
	}
	
	public Customer getById(int customerId)
	{
		return customerRepository.findOne(customerId);
	}
	
	public Customer getByLastName(String lastName)
	{
		return customerRepository.findByLastName(lastName);
	}
	
	
}

