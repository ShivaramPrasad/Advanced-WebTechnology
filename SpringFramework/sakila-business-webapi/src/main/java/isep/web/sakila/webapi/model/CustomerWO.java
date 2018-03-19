package isep.web.sakila.webapi.model;


import isep.web.sakila.jpa.entities.Address;
import isep.web.sakila.jpa.entities.Customer;

public class CustomerWO extends WebObject 
{
	private static final long	serialVersionUID	= -1377067679473844279L;
	
	protected int						id;
	protected String 					firstName;
	protected String 					lastName;
	//protected String					username;
	protected String					password;
	protected Address 					addressId;
	//protected String customerAddress;

	public CustomerWO()
	{
		super();
	}

	public CustomerWO(int id, String firstName, String lastName, String password)
	{
		super();
		this.id = id;
		this.firstName=firstName;
		this.lastName=lastName;
		//this.username = username;
		this.password = password;
		//this.addressId=addressId;
		//this.customerAddress=customerAddress;
	}

	//public CustomerWO(final Customer customer, final Address address)
	public CustomerWO(final Customer customer)
	{
		super();
		this.id = customer.getCustomerId();
		this.firstName=customer.getFirstName();
		this.lastName=customer.getLastName();
		//this.username = customer.getUsername();
		this.password = customer.getPassword();
		//this.addressId=customer.getAddress();
		//this.customerAddress=address.getAddress();
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*public Address getAddressId() {
		return addressId;
	}

	public void setAddressId(Address addressId) {
		this.addressId = addressId;
	}*/

	/*public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}*/

	public int getId()
	{
		return id;
	}
	

	public String getPassword()
	{
		return password;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "Customer [ID=" + this.id + ", FirstName=" + this.firstName +", LastName=" + this.lastName +", Password=" + this.password +"]\n";
	}
	
}
