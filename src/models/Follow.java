package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follow_employees")
@NamedQueries({
    @NamedQuery(
            name = "getMyFollowEmployees",
            query = "SELECT f FROM Follow AS f WHERE f.my_employee = :employee ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "getFollowEmployeesCount",
            query = "SELECT COUNT(f) FROM Follow AS f"
            ),
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "my_employee", nullable = false)
    private Employee my_employee;

    @ManyToOne
    @JoinColumn(name = "follow_employee", nullable = false)
    private Employee follow_employee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getMy_employee() {
        return my_employee;
    }

    public void setMy_employee(Employee my_employee) {
        this.my_employee = my_employee;
    }

    public Employee getFollow_employee() {
        return follow_employee;
    }

    public void setFollow_employee(Employee follow_employee) {
        this.follow_employee = follow_employee;
    }
}
