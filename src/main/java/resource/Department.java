package resource;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
    @Entity
    @Table(name = "department")
//    @NamedQueries({
//        @NamedQuery(name = "Department.findAll",
//                    query = "select d from Department d"),
//        @NamedQuery(name = "Department.findById",
//                query = "select dep_id, depName from Department d where d.dep_id = :dep_id")
//
//    })

@SqlResultSetMapping(
        name = "getDepartmentTable",
        classes = @ConstructorResult(
                targetClass = DepartmentDTO.class,
                columns = {
                        @ColumnResult(name="dep_id",type = long.class),
                        @ColumnResult(name="dep_name",type = String.class)
                }
        )
)
    public class Department {
        /**
         * Entity's unique identifier.
         */

        @OneToMany(mappedBy = "department")
        private List<Employee> employeeList;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "dep_id")
        private long dep_id;


        /**
         * employee first name.
         */
        @Column(name = "dep_name")
        private String depName;





}
