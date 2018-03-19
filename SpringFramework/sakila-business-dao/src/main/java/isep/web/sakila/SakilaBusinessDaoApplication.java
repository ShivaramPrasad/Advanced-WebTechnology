package isep.web.sakila;

/*import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SakilaBusinessDaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SakilaBusinessDaoApplication.class, args);
	}
}*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import isep.web.sakila.dao.business.IBusiness;
import isep.web.sakila.jpa.config.PersistenceConfig;
import isep.web.sakila.jpa.entities.Actor;
import isep.web.sakila.jpa.entities.Customer;

@SpringBootApplication
public class SakilaBusinessDaoApplication
{

	public static void main(String[] args)
	{
		// We prepare the Spring Configuration
		SpringApplication app = new SpringApplication(PersistenceConfig.class);
		app.setLogStartupInfo(false);

		// We launch the Application Context
		ConfigurableApplicationContext context = app.run(args);
		// Business Layer
		IBusiness business = context.getBean(IBusiness.class);

		try
		{
			for (Actor actor : business.getAllActors())
			{
				System.out.println(actor);
			}

			Actor guiness = business.getByID(1);
			System.out.printf("Who is ID 1? %s %s %n", guiness.getLastName(), guiness.getFirstName());

		} catch (Exception ex)
		{
			System.out.println("Exception : " + ex.getCause());
		}
		
		
		try
		{
			for (Customer customer : business.getAllCustomers())
			{
				System.out.println(customer);
			}
			
			
			Customer checkname = business.getByLastName("SMITH");
			System.out.printf("LAST NAME :"+checkname.getLastName());
			
			

			Customer smith = business.getById(1);
			System.out.printf("Show username and password of CustomerID 1? %s %s %n", smith.getUsername(), smith.getPassword());

		} catch (Exception ex)
		{
			System.out.println("Exception : " + ex.getCause());
		}

		
		// Closing Spring Context
		context.close();

	}
}

