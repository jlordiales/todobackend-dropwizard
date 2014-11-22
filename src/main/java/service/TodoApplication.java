package service;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resource.TodoResource;

public class TodoApplication extends Application<Configuration> {
    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new Java8Bundle());
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(new TodoResource());
    }

    public static void main(String[] args) throws Exception {
        new TodoApplication().run(new String[]{"server","src/main/resources/config.yaml"});
    }
}
