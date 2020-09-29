package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reports")
@NamedQueries({
    @NamedQuery(
            name = "getAllReports",
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r"
            ),
    @NamedQuery(
            name = "getMyAllReports",
            query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getMyReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
            ),
    @NamedQuery(
            name = "getAllFollowReports",
            query = "SELECT r FROM Report AS r WHERE r.employee IN :emp_list ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getFollowReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee IN :emp_list"
            ),
    @NamedQuery(
            name = "getApprovalReports",
            query = "SELECT r FROM Report AS r WHERE r.approval_employee IS NOT NULL ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getApprovalReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.approval_employee IS NOT NULL"
            )
})
@Entity
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    1対多は外部キー、多対多は中間テーブルを使う。今回は前者。
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

//    Date は年月日のみ
    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

//    テキストエリアの指定を行うのが@Lob、これを指定することで、改行もデータベースに保存される
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

//    Timestamp は年月日の他に時分秒（ミリ秒）まで
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

//    承認した従業員
    @ManyToOne
    @JoinColumn(name = "approval_employee_id", nullable = false)
    private Employee approval_employee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Employee getApproval_employee() {
        return approval_employee;
    }

    public void setApproval_employee(Employee approval_employee) {
        this.approval_employee = approval_employee;
    }
}