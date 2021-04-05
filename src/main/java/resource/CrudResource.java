package resource;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employees")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Produces(MediaType.APPLICATION_JSON)
public class CrudResource {
    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;

//    public CrudResource(EmployeeDAO employeeDAO, DepartmentDAO departmentDAO) {
//        this.employeeDAO = employeeDAO;
//        this.departmentDAO = departmentDAO;
//    }

    @GET
    @Path("/first_name")
    @UnitOfWork
    public List<Employee> findByFirstName(@QueryParam("first_name") String name3){
      return employeeDAO.findByFirstName(name3);
    }

    @GET
    @Path("/findByidEmp")
    @UnitOfWork
    public Employee findByidEmp(@QueryParam("id") long id){
        return employeeDAO.findByIdEmp(id);
    }

    @GET
    @Path("/groupByDepId")
    @UnitOfWork
    public List groupByDep_ID(){
        return employeeDAO.groupByDep_Id();
    }

    @GET
    @Path("/findByidDep")
    @UnitOfWork
    public Department findByidDep(@QueryParam("dep_id") long id){
        return departmentDAO.findByIdDep(id);
    }

    @GET
    @UnitOfWork
    @Path("/findAllEmp")
    public List<Employee> findAllEmp() {
        return employeeDAO.findAll();	    }

    @GET
    @UnitOfWork
    @Path("/findAllDep")
    public List<DepartmentDTO> findAllDep() {
        //System.out.println("All dep  : "+departmentDAO.findAll());
        return departmentDAO.findAll();	    }

    @GET
    @UnitOfWork
    @Path("/joinEmpDep")
    public List<EmpJoinDepDTO> join(){
        return employeeDAO.joinEmpDep();
    }


    @POST
    @UnitOfWork
    @Path("/insertEmp")
    public void insertEmp(@QueryParam("first_name") String firstName
            ,@QueryParam("last_name") String lastName
            ,@QueryParam("e_position") String position
            ,@QueryParam("dep_id") long dep_id) {
         employeeDAO.insertData(firstName, lastName, position, dep_id);	    }

    @POST
    @UnitOfWork
    @Path("/insertDep")
    public void insertDep(@QueryParam("dep_name") String dep_name){
        departmentDAO.insertData(dep_name);	    }

    @PUT
    @Path("/updateEmp")
    @UnitOfWork
    public Employee update(@QueryParam("id") long id
            ,@QueryParam("e_mail") String email
            ,@QueryParam("first_name") String first_name
            ,@QueryParam("last_name") String last_name
            ,@QueryParam("phone") String phone
            ,@QueryParam("e_position") String e_position) {
        Employee employee = employeeDAO.findByIdEmp(id);
        employee.setFirstName(first_name);
        employee.setE_mail(email);
        employee.setLastName(last_name);
        employee.setPosition(e_position);
        employee.setPhone(phone);

        employeeDAO.updateData(employee);
        return employee;
    }

    @PUT
    @Path("/updateDep")
    @UnitOfWork
    public Department update(@QueryParam("id") long id
            ,@QueryParam("dep_name") String dep_name
            ,@QueryParam("dep_id") long dep_id) {
        Department department = departmentDAO.findByIdDep(id);
        department.setDepName(dep_name);

        departmentDAO.updateData(department);
        return department;
    }

    @DELETE
    @Path("/deleteEmp")
    @UnitOfWork
    public void delete(@QueryParam("id") long id){
        Employee employee = employeeDAO.findByIdEmp(id);
        employeeDAO.delete(employee);
        //return employee;
    }

}
