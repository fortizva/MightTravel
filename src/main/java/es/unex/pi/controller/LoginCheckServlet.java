package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginCheckServlet
 */
@WebServlet("/LoginCheckServlet.do")
public class LoginCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginCheckServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) request.getServletContext().getAttribute("dbConn");
		
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		logger.info("Trying to get user \""+request.getParameter("username")+"\" from database");
		User user = userDao.get(request.getParameter("username"));

		if (user != null && user.getPassword().equals(request.getParameter("password"))) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				logger.info("Logged in as "+user.getUsername()+" successfully!");
				response.sendRedirect("ExploreHostingsServlet.do");
			
		} else {
			logger.info("Invalid login credentials!");
			request.setAttribute("messages", "Nombre/contraseña inválidos");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
			view.forward(request, response);
		}
	}

}
