package controllers.follow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndex
 */
@WebServlet("/follow/index")
public class FollowIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

//        ログインしている従業員情報を元にフォローしている従業員を取得
        List<Follow> myFollows = em.createNamedQuery("getMyFollowEmployee", Follow.class)
                                          .setParameter("employee", login_employee)
                                          .getResultList();

//        フォローしている従業員が入れば処理を続行、いなければエラーメッセージを表示
        if(myFollows != null && myFollows.size() > 0) {
//            Follow型からEmployee型に入れ替える
            List<Employee> myFollowEmployees = new ArrayList<>();
            for(Follow f : myFollows) {
              myFollowEmployees.add(f.getFollow_employee());
            }

//            フォローしている従業員のレポート一覧を取得
            List<Report> follow_reports = em.createNamedQuery("getAllFollowReports", Report.class)
                                             .setParameter("emp_list", myFollowEmployees)
                                             .setFirstResult(15 * (page - 1))
                                             .setMaxResults(15)
                                             .getResultList();

//            レポートの数を取得
            long follow_reports_count = (long)em.createNamedQuery("getFollowReportsCount", Long.class)
                                                 .setParameter("emp_list", myFollowEmployees)
                                                 .getSingleResult();

            em.close();

            request.setAttribute("follow_reports", follow_reports);
            request.setAttribute("follow_reports_count", follow_reports_count);
            request.setAttribute("page", page);

        } else {

            em.close();

            request.setAttribute("follow_null", "フォローしている従業員はいません");
        }



        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/index.jsp");
        rd.forward(request, response);
    }
}
