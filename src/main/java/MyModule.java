import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import io.dropwizard.hibernate.HibernateBundle;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;

import javax.ws.rs.Path;
import java.util.Set;

@RequiredArgsConstructor
public class MyModule extends DropwizardAwareModule<CrudConfiguration> {
    private static final Reflections reflections = new Reflections(MyModule.class.getName());
    private final HibernateBundle<CrudConfiguration> hibernateBundle;

    public void configure(Binder binder) {
        reflections.getTypesAnnotatedWith(Path.class).forEach(binder::bind);
        registerHealthChecks(reflections.getSubTypesOf(HealthCheck.class));
    }

    private void registerHealthChecks(Set<Class<? extends HealthCheck>> healthCheckClasses) {
        for (Class<? extends HealthCheck> healthCheckClass : healthCheckClasses) {
            try {
                getEnvironment().healthChecks().register(healthCheckClass.getSimpleName(),
                        healthCheckClass.newInstance());
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Class " + healthCheckClass + " couldn't be instantiated as it lacked an empty constructor");
            }
        }
    }


    @Provides
    @Named("message")
    public String provideMessage(CrudConfiguration configuration){
        return configuration.getMessage();
    }

    @Provides
    public SessionFactory getSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }
}
