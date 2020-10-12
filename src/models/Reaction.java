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

@Table(name = "reaction")
@NamedQueries({
    @NamedQuery(
            name = "getReactions",
            query = "SELECT reac FROM Reaction AS reac WHERE reac.reaction_employee = :login_employee"
            )
})
@Entity
public class Reaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //  リアクションした従業員
    @ManyToOne
    @JoinColumn(name = "reaction_employee", nullable = false)
    private Employee reaction_employee;

    //  リアクションされたレポート
    @ManyToOne
    @JoinColumn(name = "reaction_report", nullable = false)
    private Report reaction_report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getReaction_employee() {
        return reaction_employee;
    }

    public void setReaction_employee(Employee reaction_employee) {
        this.reaction_employee = reaction_employee;
    }

    public Report getReaction_report() {
        return reaction_report;
    }

    public void setReaction_report(Report reaction_report) {
        this.reaction_report = reaction_report;
    }
}
