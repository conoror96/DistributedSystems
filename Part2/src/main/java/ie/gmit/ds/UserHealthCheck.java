package ie.gmit.ds;
// Adapted from https://github.com/john-french/artistAPI-dropwizard
import com.codahale.metrics.health.HealthCheck;

public class UserHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}