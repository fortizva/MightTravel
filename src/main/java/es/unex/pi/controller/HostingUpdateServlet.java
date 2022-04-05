package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
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
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.ServiceDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.Service;
import es.unex.pi.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class HostingUpdateServlet
 */
@WebServlet("/HostingUpdateServlet.do")
public class HostingUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HostingUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long hid = Long.parseLong((String) request.getParameter("hid"));
		logger.info("User \"" + request.getSession().getId() + "\" updating hosting \"" + hid + "\"");

		// Get DB connection
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		HostingDAO hostingDao = new JDBCHostingDAOImpl();
		hostingDao.setConnection(conn);

		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		logger.info("Checking user in session");

		if (session.getAttribute("user") != null) {
			logger.info("Checking hosting id");
			Hosting h = hostingDao.get(hid);
			if (h != null && h.getIdu() == user.getId()) {
				if (request.getParameter("deletehosting") != null
						&& request.getParameter("deletehosting").equals("true")) {
					hostingDao.delete(h.getId());
					response.sendRedirect("UserPanelServlet.do");
				} else {
					logger.info("Updating user's hosting");
					h.setTitle(request.getParameter("title"));
					h.setDescription(request.getParameter("description"));
					h.setTelephone(request.getParameter("telephone"));
					h.setLocation(request.getParameter("location"));
					h.setContactEmail(request.getParameter("email"));
					h.setAvailable(Integer.parseInt(request.getParameter("available")));
					h.setPrice(Integer.parseInt(request.getParameter("price")));
					h.setImage(request.getParameter("image"));
					hostingDao.save(h);

					// Multiple option parsing
					HostingCategoriesDAO hcategoriesDao = new JDBCHostingCategoriesDAOImpl();
					hcategoriesDao.setConnection(conn);
					List<Long> categoriesIds = new ArrayList<>();
					
					HostingServicesDAO hservicesDao = new JDBCHostingServicesDAOImpl();
					hservicesDao.setConnection(conn);
					List<Long> servicesIds = new ArrayList<>();
					
					Enumeration<String> parametersList = request.getParameterNames();
					Iterator<String> it = parametersList.asIterator();
					String aux;
					while(it.hasNext()) {
						aux = it.next();
						if(aux.startsWith("srv")) {
							aux = aux.replaceFirst("srv", "");
							servicesIds.add(Long.parseLong(aux));
						} else if(aux.startsWith("cat")) {
							aux = aux.replaceFirst("cat", "");
							categoriesIds.add(Long.parseLong(aux));
						}
					}
					
					// Categories
					CategoryDAO categoryDao = new JDBCCategoryDAOImpl();
					categoryDao.setConnection(conn);
					List<Category> categoriesList = categoryDao.getAll();
					HostingCategories hcategories;
					for(Category c : categoriesList) {
						if(categoriesIds.contains(c.getId()) && hcategoriesDao.get(hid, c.getId())==null) {
							hcategories = new HostingCategories();
							hcategories.setIdh(hid);
							hcategories.setIdct(c.getId());
							hcategoriesDao.add(hcategories);
						} else if(!categoriesIds.contains(c.getId()) && hcategoriesDao.get(hid, c.getId())!=null)
							hcategoriesDao.delete(hid, c.getId());
					}
					
					// Services
					ServiceDAO serviceDao = new JDBCServiceDAOImpl();
					serviceDao.setConnection(conn);
					List<Service> servicesList = serviceDao.getAll();
					HostingServices hservices;
					for(Service s : servicesList) {
						if(servicesIds.contains(s.getId()) && hservicesDao.get(hid, s.getId())==null) {
							hservices = new HostingServices();
							hservices.setIdh(hid);
							hservices.setIdsrv(s.getId());
							hservicesDao.add(hservices);
						} else if(!servicesIds.contains(s.getId()) && hservicesDao.get(hid, s.getId())!=null)
							hservicesDao.delete(hid, s.getId());
					}
					
					response.sendRedirect("HostingDetailsServlet.do?id=" + h.getId());
				}
			} else {
				response.sendRedirect("HostingDetailsServlet.do?id=" + h.getId());
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
