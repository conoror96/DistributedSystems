package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class UserAPIApplication extends Application<UserAPIConfig> {

    public static void main(String[] args) throws Exception {
        new UserAPIApplication().run(args);
    }

    public void run(UserAPIConfig artistApiConfig, Environment environment) throws Exception {

        final UserAPIResource resource = new UserAPIResource();

        environment.jersey().register(resource);

        // Register health check
        final UserHealthCheck healthCheck = new UserHealthCheck();
        environment.healthChecks().register("example", healthCheck);
    }
}
