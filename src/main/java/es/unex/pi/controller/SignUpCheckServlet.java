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
@WebServlet("/SignUpCheckServlet.do")
public class SignUpCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpCheckServlet() {
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

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();

		if (username != null && email != null && password != null) {
			if (password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}")) {
				if (email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
					user.setUsername(username);
					user.setEmail(email);
					user.setPassword(password);
					userDao.add(user);
					response.sendRedirect("LoginServlet.do");
				} else {
					logger.info("Invalid email! \""+email+"\"");
					request.setAttribute("messages", "Email inválido");
					RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SignUp.jsp");
					view.forward(request, response);
				}
			} else {
				logger.info("Invalid password! \""+password+"\"");
				request.setAttribute("messages", "Contraseña inválida");
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SignUp.jsp");
				view.forward(request, response);
			}
		} else {
			logger.info("Missing parameters!");
			request.setAttribute("messages", "Debe completar todos los campos");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SignUp.jsp");
			view.forward(request, response);
		}
	}

}
