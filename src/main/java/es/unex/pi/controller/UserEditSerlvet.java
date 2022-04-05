package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
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
 * Servlet implementation class UserEditSerlvet
 */
@WebServlet("/UserEditServlet.do")
public class UserEditSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserEditSerlvet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (user != null) {
			logger.info("User is logged in for some account edits");

			if (request.getParameter("deleteaccount") != null && request.getParameter("deleteaccount").equals("true")) {
				logger.info("Deleting user \"" + user.getUsername() + "\"");
				userDao.delete(user.getId());
				if (session != null)
					session.invalidate();

				response.sendRedirect("LoginServlet.do");
			} else {
				boolean b_taken = false;
				if (username != null && !username.isEmpty()) {
					if (userDao.get(username) == null) {
						user.setUsername(username);
						logger.info("Username changed to \"" + username + "\"");
					} else {
						b_taken = true;
						logger.info("Username has been previously taken!");
					}
				}
				if (!b_taken) {
					if (email != null && !email.isEmpty()) {
						user.setEmail(email);
					}
					if (password != null && !password.isEmpty()) {
						user.setPassword(password);
					}
					logger.info("Changes have been successfully applied to user \"" + user.getUsername() + "\"");
					userDao.save(user);
					request.setAttribute("messages",
							"Los cambios han sido aplicados correctamente.");
					RequestDispatcher view = request.getRequestDispatcher("UserPanelServlet.do");
					view.forward(request, response);
				} else {
					request.setAttribute("messages",
							"Los cambios no han sido aplicados correctamente. El nombre de usuario ya está en uso.");
					RequestDispatcher view = request.getRequestDispatcher("UserPanelServlet.do");
					view.forward(request, response);
				}
			}
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
