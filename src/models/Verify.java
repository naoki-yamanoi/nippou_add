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

@Table(name = "toVerify")
@NamedQueries({
    @NamedQuery(
            name = "checkSameReport",
            query = "SELECT v FROM Verify AS v WHERE v.report = :report"
            )
})
@Entity
public class Verify {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

//    0なら未認証、1なら認証済み
    @Column(name = "verify_flag", nullable = false)
    private Integer verify_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Integer getVerify_flag() {
        return verify_flag;
    }

    public void setVerify_flag(Integer verify_flag) {
        this.verify_flag = verify_flag;
    }
}
