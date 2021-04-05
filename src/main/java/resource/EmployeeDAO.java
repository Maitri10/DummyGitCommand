package resource;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeDAO extends AbstractDAO<Employee> {
    DepartmentDAO departmentDAO;
    public EmployeeDAO(SessionFactory sessionFactory,DepartmentDAO departmentDAO) {
        super(sessionFactory);
        Session session = sessionFactory.openSession();
        this.departmentDAO=departmentDAO;

    }


    public List<Employee> findAll(){
        return list(namedQuery("Employee.findAll"));
    }

    public List<Employee> findByFirstName(String name1){
//        StringBuilder builder = new StringBuilder("%");
//        builder.append(name).append("%");
        return list(namedQuery("Employee.findByFirstName")
                .setParameter("name",name1));

    }

    public Employee findByIdEmp(long id){
        Query<Employee> query = currentSession().getNamedQuery("Employee.findById")
                .setParameter("id",id);
        return query.getSingleResult();
    }

    public void insertData(String firstName, String lastName, String position,long dep_id){
//        currentSession().beginTransaction();
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPosition(position);

        Department department = departmentDAO.findByIdDep(dep_id);
        employee.setDepartment(department);
        currentSession().save(employee);
//        Query<Employee> query = currentSession().getNamedQuery("Employee.insertData")
//                .setParameter("id",id)
//                .setParameter("first_name",firstName)
//                .setParameter("last_name",lastName)
//                .setParameter("e_position",position);
        currentSession().getTransaction().commit();
    }

    public void updateData(Employee employee){

        currentSession().update(employee);
        currentSession().getTransaction().commit();

    }

    public void delete(Employee employee) {
        currentSession().delete(employee);
        currentSession().getTransaction().commit();
    }

    public List groupByDep_Id(){
        Query query = currentSession().createNativeQuery("SELECT d.dep_id,d.dep_name,COUNT(e.first_name) as No_of_emp FROM DEPARTMENT d LEFT JOIN EMPLOYEES e ON d.DEP_ID = e.DEP_ID GROUP BY e.DEP_ID","GroupByDepIdResult");
        return query.getResultList();
    }

    public List<EmpJoinDepDTO> joinEmpDep(){
        Query query = currentSession().createNativeQuery("select e.id,e.e_mail,e.first_name,e.last_name,e.e_position,e.phone,d.dep_name from EMPLOYEES e LEFT JOIN Department d ON e.dep_id = d.dep_id","joinEmpDep");
        return query.getResultList();
    }
}
