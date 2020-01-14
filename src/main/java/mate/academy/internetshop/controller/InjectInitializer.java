package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Injector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InjectInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            System.out.println("Dependency injection started");
            Injector.InjectDependency();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
