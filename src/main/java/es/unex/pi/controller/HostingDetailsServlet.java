package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingFavoritesDAO;
import es.unex.pi.dao.HostingServicesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingFavoritesDAOImpl;
import es.unex.pi.dao.JDBCHostingServicesDAOImpl;
import es.unex.pi.dao.JDBCServiceDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.ServiceDAO;
import es.unex.pi.dao.UserDAO;
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
 * Servlet implementation class HostingDetailsServlet
 */
@WebServlet(urlPatterns = { "/HostingDetailsServlet.do" })
public class HostingDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HostingDetailsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtain the connection to the database from the ServletContext
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		HostingDAO hostingDao = new JDBCHostingDAOImpl();
		// Set the connection to the database in the HostingDAO object
		hostingDao.setConnection(conn);

		CategoryDAO categoryDao = new JDBCCategoryDAOImpl();
		categoryDao.setConnection(conn);
		List<Category> categoryList = categoryDao.getAll();
		request.setAttribute("Categories", categoryList);
		
		try {
			String id = request.getParameter("id");
			logger.info("get parameter id (" + id + ")");
			long hid = 0;
			hid = Long.parseLong(id);
			logger.info("get parameter id (" + id + ") and casting " + hid);
			Hosting hosting = hostingDao.get(hid);
			if (hosting != null) {
				request.setAttribute("hosting", hosting);

				HostingCategoriesDAO hcategoriesDao = new JDBCHostingCategoriesDAOImpl();
				hcategoriesDao.setConnection(conn);
				List<Category> checkedCategoriesList = new ArrayList<>();
				for(HostingCategories hc : hcategoriesDao.getAllByHosting(hosting.getId())) {
					checkedCategoriesList.add(categoryDao.get(hc.getIdct()));
				}
				request.setAttribute("CheckedCategoriesList", checkedCategoriesList);
				
				// Get current host services
				HostingServicesDAO servicesDao = new JDBCHostingServicesDAOImpl();
				servicesDao.setConnection(conn);
				ServiceDAO serviceDao = new JDBCServiceDAOImpl();
				serviceDao.setConnection(conn);
				List<HostingServices> hostingServicesList = servicesDao.getAllByHosting(hid);
				List<Service> servicesList = new ArrayList<>();
				for(HostingServices hs : hostingServicesList) {
					servicesList.add(serviceDao.get(hs.getIdsrv()));
				}
				
				request.setAttribute("ServicesList", servicesList);
				
				
				// Get if current host is one of user's favorites
				HostingFavoritesDAO favoritesDao = new JDBCHostingFavoritesDAOImpl();
				favoritesDao.setConnection(conn);
				UserDAO userDao = new JDBCUserDAOImpl();
				userDao.setConnection(conn);
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");

				request.setAttribute("favorite",
						(user != null && favoritesDao.get(hosting.getId(), user.getId()) != null));

				// TODO: Dispatch the request to the HostingDetails.jsp
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/HostingDetails.jsp");
				view.forward(request, response);
			} else {
				// TODO: Redirect to ExploreHostingsServlet.do
				response.sendRedirect("ExploreHostingsServlet.do");
			}
		} catch (NumberFormatException e) {
			logger.info("parameter id is not a number");

			// TODO: Redirect to ExploreHostingsServlet.do
			response.sendRedirect("ExploreHostingsServlet.do");
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
