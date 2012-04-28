package com.noosh.nooshlite;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		SessionFactory sessionF = configureSessionFactory();
		Session session = sessionF.openSession();
		session.beginTransaction();
		List result = session.createQuery( "from City" ).list();
		for ( City c : (List<City>) result ) {
		    System.out.println( "City (" + c.getId() + ") : " + c.getName() );
		}
				
		City item = (City) session.load(City.class, new Long(2).longValue());
		System.out.println("Just this one city" + item.getName());
		
		session.getTransaction().commit();
		session.close();
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("city", item.getName());
		
		return "homer";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */                 
	@RequestMapping(value = "/tea/{userid}", method = RequestMethod.GET)
	public String findUser(@PathVariable int userId, Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println(userId);
		
		SessionFactory sessionF = configureSessionFactory();
		Session session = sessionF.openSession();
		session.beginTransaction();
		List result = session.createQuery( "from City" ).list();
		for ( City c : (List<City>) result ) {
		    System.out.println( "City (" + c.getId() + ") : " + c.getName() );
		}
				
		City item = (City) session.load(City.class, new Long(2).longValue());
		System.out.println("Just this one city" + item.getName());
		
		session.getTransaction().commit();
		session.close();
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("city", "tea");
		
		return "home";
	}
	
}
