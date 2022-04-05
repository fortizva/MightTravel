package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingServicesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingServicesDAOImpl;
import es.unex.pi.dao.JDBCServiceDAOImpl;
import es.unex.pi.dao.ServiceDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.Service;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditHostingServlet
 */
@WebServlet("/EditHostingServlet.do")
public class EditHostingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditHostingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		request.setAttribute("user", user);

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);
		List<Category> categoriesList = categoryDAO.getAll();
		request.setAttribute("Categories", categoriesList);

		HostingDAO hostingDao = new JDBCHostingDAOImpl();
		hostingDao.setConnection(conn);

		ServiceDAO serviceDao = new JDBCServiceDAOImpl();
		serviceDao.setConnection(conn);

		if (user != null) {
			List<Service> serviceList = serviceDao.getAll();
			request.setAttribute("ServicesList", serviceList);
			logger.info("Getting all services");

			Long hid = Long.parseLong(request.getParameter("hid"));

			if (hid != null && hostingDao.get(hid).getIdu() == user.getId()) {
				logger.info("Getting user's posted hosting");
				Hosting hosting = hostingDao.get(hid);
				request.setAttribute("Hosting", hosting);

				HostingCategoriesDAO hcategoriesDao = new JDBCHostingCategoriesDAOImpl();
				hcategoriesDao.setConnection(conn);
				List<HostingCategories> hcategoriesList = hcategoriesDao.getAllByHosting(hid);
				List<Long> checkedCategoriesList = new ArrayList<>();
				for(HostingCategories hc : hcategoriesList) {
					checkedCategoriesList.add(hc.getIdct());
				}
				request.setAttribute("CheckedCategoriesList", checkedCategoriesList);
				
				HostingServicesDAO hservicesDao = new JDBCHostingServicesDAOImpl();
				hservicesDao.setConnection(conn);
				List<HostingServices> hservicesList = hservicesDao.getAllByHosting(hid);
				List<Long> checkedServicesList = new ArrayList<>();
				for(HostingServices hs : hservicesList) {
					checkedServicesList.add(hs.getIdsrv());
				}
				request.setAttribute("CheckedServicesList", checkedServicesList);
				
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/EditHosting.jsp");
				view.forward(request, response);
			} else
				response.sendRedirect("UserPanelServlet.do");

		} else {
			response.sendRedirect("LoginServlet.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
