package resource;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Queue;

public class DepartmentDAO extends
        AbstractDAO<Department>{

    public DepartmentDAO(SessionFactory sessionFactory) {
            super(sessionFactory);
            Session session = sessionFactory.openSession();

            }

    public Department findByIdDep(long id){
        Query<Department> query1 = currentSession().createNativeQuery("select dep_id, depName from Department d where d.dep_id = :dep_id","getDepartmentTable")
                .setParameter("dep_id",id);
        return query1.getSingleResult();
    }

    public List<DepartmentDTO> findAll(){
        Query query = currentSession().createNativeQuery("select dep_id, dep_name from Department d","getDepartmentTable");
            return list(query);
            }

    public void insertData(String dep_name){
//        currentSession().beginTransaction();
        Department department = new Department();
        department.setDepName(dep_name);
        currentSession().save(department);
        currentSession().getTransaction().commit();
    }
    public void updateData(Department department){

        currentSession().update(department);
        currentSession().getTransaction().commit();

    }


}
