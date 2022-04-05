package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingFavoritesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingFavoritesDAOImpl;
import es.unex.pi.model.Category;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingFavorites;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UserPanelServlet
 */
@WebServlet("/UserPanelServlet.do")
public class UserPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPanelServlet() {
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
		if (user != null) {
			request.setAttribute("user", user);

			Connection conn = (Connection) getServletContext().getAttribute("dbConn");

			HostingDAO hostingDao = new JDBCHostingDAOImpl();
			hostingDao.setConnection(conn);

			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
			categoryDAO.setConnection(conn);
			List<Category> categoriesList = categoryDAO.getAll();
			request.setAttribute("Categories", categoriesList);

			logger.info("Getting user's favorites");
			HostingFavoritesDAO favoritesDao = new JDBCHostingFavoritesDAOImpl();
			favoritesDao.setConnection(conn);
			List<HostingFavorites> favoritesList = favoritesDao.getAllByUser(user.getId());
			List<Hosting> favHostingsList = new ArrayList<>();

			for (HostingFavorites f : favoritesList) {
				favHostingsList.add(hostingDao.get(f.getIdh()));
			}

			request.setAttribute("FavoritesList", favHostingsList);

			logger.info("Getting user's "+user.getUsername()+" posted hostings");
			List<Hosting> hostingsList = hostingDao.getAllByUser(user.getId());
			for(Hosting h : hostingsList) {
				logger.info("PUBLISHED HOSTING: "+h.getTitle());
			}
			request.setAttribute("HostingsList", hostingsList);
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/UserPanel.jsp");
			view.forward(request, response);
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
