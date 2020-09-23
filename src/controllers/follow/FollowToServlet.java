package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class controllersFollowupServlet
 */
@WebServlet("/follow/to")
public class FollowToServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowToServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

//        フォローする従業員、フォローされる従業員を取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));


//        フォローする従業員、フォローされる従業員をセット
        Follow f = new Follow();
        f.setMy_employee(login_employee);
        f.setFollow_employee(e);

//        これでデータベースに保管できる
        em.persist(f);
        em.getTransaction().commit();

        em.close();

        request.getSession().setAttribute("follow_message", "フォローしました。");

        response.sendRedirect(request.getContextPath() + "/employees/index");
    }

}
