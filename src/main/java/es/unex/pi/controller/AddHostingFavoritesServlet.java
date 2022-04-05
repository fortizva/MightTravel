package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingFavoritesDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingFavoritesDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
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
 * Servlet implementation class AddHostingFavoritesServlet
 */
@WebServlet("/AddHostingFavoritesServlet.do")
public class AddHostingFavoritesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddHostingFavoritesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long hid = Long.parseLong((String) request.getParameter("hid"));
		logger.info("User \""+ request.getSession().getId() +"\" adding hosting \""+ hid +"\" to favorites");
		
		// Get DB connection
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		HostingDAO hostingDao = new JDBCHostingDAOImpl();
		hostingDao.setConnection(conn);
		
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		HttpSession session = request.getSession(true);
		logger.info("Checking user in session");
		
		if(session.getAttribute("user")!=null) {
			logger.info("Checking hosting id");
			Hosting h = hostingDao.get(hid);
			if(h!=null) {
				logger.info("Adding hosting to user's favorites");
				User user = (User) session.getAttribute("user");
				HostingFavoritesDAO favoritesDao = new JDBCHostingFavoritesDAOImpl();
				favoritesDao.setConnection(conn);
				HostingFavorites favorites = favoritesDao.get(hid, user.getId());
				if(favorites == null) {
					logger.info("Favorite not found! Adding hosting to favorites");
					favorites = new HostingFavorites();
					favorites.setIdh(h.getId());
					favorites.setIdu(user.getId());
					favoritesDao.add(favorites);
					h.setLikes(h.getLikes()+1);
					hostingDao.save(h);
				} else {
					logger.info("Favorite found! Deleting database entry");
					favoritesDao.delete(h.getId(), user.getId());
					h.setLikes(h.getLikes()-1);
					hostingDao.save(h);
				}
			}
			response.sendRedirect("HostingDetailsServlet.do?id="+h.getId());
		}else {
			
			response.sendRedirect("LoginServlet.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
