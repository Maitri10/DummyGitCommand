import com.google.inject.Stage;
import com.hubspot.dropwizard.guicier.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.session.SessionHandler;
import org.apache.poi.ss.formula.functions.T;
import resource.Department;
import resource.Employee;

public class CrudApplication extends Application<CrudConfiguration> {
    private GuiceBundle<CrudConfiguration> guiceBundle;

    public static void main(String args[]) throws Exception {
        new CrudApplication().run(args);
    }


    public void run(CrudConfiguration crudConfiguration, Environment environment) throws Exception {
//        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateBundle.getSessionFactory());
//        EmployeeDAO employeeDAO = new EmployeeDAO(hibernateBundle.getSessionFactory(),departmentDAO);
//        environment.servlets().setSessionHandler(new SessionHandler());
//        Class<T> classes[] = new Class[]{DepartmentDAO.class, EmployeeDAO.class};
//        Object objects[] = new Object[]{new DepartmentDAO(hibernateBundle.getSessionFactory()),
//                new EmployeeDAO(hibernateBundle.getSessionFactory(),new DepartmentDAO(hibernateBundle.getSessionFactory()))};
        //final CrudResource resource = new CrudResource(employeeDAO,departmentDAO);
        //environment.jersey().register(resource);
//        environment.jersey().register(new UnitOfWorkAwareProxyFactory(hibernateBundle)
//                .create(classes, objects));
    }
    private final HibernateBundle<CrudConfiguration> hibernateBundle
            = new HibernateBundle<CrudConfiguration>(Employee.class, Department.class) {

        public DataSourceFactory getDataSourceFactory(CrudConfiguration configuration){
            return configuration.getDatabase();
        }
    };

    @Override
    public String getName() {
        return "Hello ";
    }


    public void initialize(
            final Bootstrap<CrudConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        final MyModule myModule = new MyModule(hibernateBundle );
            guiceBundle = GuiceBundle.defaultBuilder(CrudConfiguration.class)
                .modules(myModule)
                .enableGuiceEnforcer(false)
                .stage(Stage.PRODUCTION)
                .build();
            bootstrap.addBundle(guiceBundle);

    }

}
