package ie.gmit.ds;
// https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
// Adapted from https://github.com/john-french/artistAPI-dropwizard

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// runtime environment of the application
public class UserAPIApplication extends Application<UserAPIConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPIApplication.class);

    public static void main(String[] args) throws Exception {
        new UserAPIApplication().run(args);
    }

    @Override
    public void run(UserAPIConfig clientConfig, Environment e) throws Exception {
        LOGGER.info("Registering REST resources");
        // Inject Environment.getValidator() in REST resource
        e.jersey().register(new UserAPIResource(e.getValidator()));

        e.healthChecks().register("UserHealthCheck", new UserHealthCheck());
    }
}
