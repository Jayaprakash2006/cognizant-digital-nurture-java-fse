package com.cognizant.springlearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main Spring Boot Application class.
 *
 * Hands On 1 : SpringApplication entry point with logging
 * Hands On 2 : Load SimpleDateFormat bean from date-format.xml
 * Hands On 3 : Logging via SLF4J / Logback
 * Hands On 4 : Load Country bean from country.xml
 * Hands On 5 : Demonstrate Singleton vs Prototype scope
 * Hands On 6 : Load list of countries from country.xml
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - SpringLearnApplication main()");

        SpringApplication.run(SpringLearnApplication.class, args);

        SpringLearnApplication app = new SpringLearnApplication();
        app.displayDate();
        app.displayCountry();
        app.displayCountries();

        LOGGER.info("END - SpringLearnApplication main()");
    }

    // -------------------------------------------------------------------------
    // Hands On 2 & 3 – Load SimpleDateFormat bean and display parsed date
    // -------------------------------------------------------------------------

    /**
     * Reads a SimpleDateFormat bean from date-format.xml (Spring XML config),
     * parses the hard-coded date string and logs the result.
     */
    public void displayDate() {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

        try {
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Parsed date : {}", date);
        } catch (ParseException e) {
            LOGGER.error("Error parsing date", e);
        }

        LOGGER.info("END");
    }

    // -------------------------------------------------------------------------
    // Hands On 4 & 5 – Load Country bean; demonstrate singleton / prototype
    // -------------------------------------------------------------------------

    /**
     * Reads the 'country' bean from country.xml and logs its details.
     * Also retrieves a second reference to demonstrate Singleton vs Prototype scope:
     *   - Singleton  : constructor called once  (both references point to same object)
     *   - Prototype  : constructor called twice (each getBean() returns a new object)
     */
    public void displayCountry() {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // First bean reference
        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country        : {}", country.toString());

        // Second bean reference – demonstrates scope behaviour (Hands On 5)
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("AnotherCountry : {}", anotherCountry.toString());

        LOGGER.debug("Same instance? : {}", (country == anotherCountry));

        LOGGER.info("END");
    }

    // -------------------------------------------------------------------------
    // Hands On 6 – Load list of all four countries from country.xml
    // -------------------------------------------------------------------------

    /**
     * Reads the 'countryList' bean (ArrayList of Country) from country.xml
     * and logs each country in the list.
     */
    @SuppressWarnings("unchecked")
    public void displayCountries() {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = context.getBean("countryList", List.class);

        for (Country c : countries) {
            LOGGER.debug("Country : {}", c.toString());
        }

        LOGGER.info("END");
    }
}
