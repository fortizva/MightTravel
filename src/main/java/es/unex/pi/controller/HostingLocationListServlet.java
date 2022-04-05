package es.unex.pi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Category;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HostingLocationListServlet
 */
@WebServlet(urlPatterns = { "/HostingLocationListServlet.do" })
public class HostingLocationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HostingLocationListServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);

		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);

		InputStream stream = this.getClass().getResourceAsStream("../config/config.properties");
	    Properties props = new Properties();
	    props.load(stream);
	    request.setAttribute("MAPS_API_KEY", props.getProperty("MAPS_API_KEY"));
		
		// Redirect search variables so they can be seen on the search bar
		request.setAttribute("q", request.getParameter("q"));
		request.setAttribute("category", request.getParameter("category"));
		request.setAttribute("availability", request.getParameter("availability"));

		boolean only_available = (request.getParameter("availability") != null
				&& request.getParameter("availability").equals("1"));

		boolean by_category = false;
		Long category_id = null;
		if (request.getParameter("category") != null && !request.getParameter("category").toString().isBlank()) {
			category_id = Long.parseLong((String) request.getParameter("category"));
			by_category = true;
		}

		boolean by_text = false;
		String search = null;
		if (request.getParameter("q") != null && !request.getParameter("q").toString().isBlank()) {
			search = (String) request.getParameter("q");
			by_text = true;
		}

		List<Hosting> hostingsList = null;
		;
		if (!by_category && !by_text) {
			logger.info("Retrieving all hostings");
			hostingsList = hostingDAO.getAll();
		} else {
			if (by_category) {
				logger.info("Searching by category: \"" + category_id + "\"");
				HostingCategoriesDAO catDAO = new JDBCHostingCategoriesDAOImpl();
				catDAO.setConnection(conn);
				List<HostingCategories> categoryList = catDAO.getAllByCategory(category_id);
				List<Long> ids = new ArrayList<>();
				hostingsList = new ArrayList<>();
				for (HostingCategories hc : categoryList) {
					if (hostingsList.isEmpty() || !ids.contains(hc.getIdh())) {
						hostingsList.add(hostingDAO.get(hc.getIdh()));
						ids.add(hc.getIdh());
					}
				}

				if (by_text) {
					logger.info("Searching by text: \"" + search + "\" and category: \"" + category_id + "\"");
					List<Hosting> aux = new ArrayList<>();
					for (Hosting h : hostingDAO.getAllBySearchAll(search)) {
						if (ids.contains(h.getId())) {
							aux.add(h);
						}
					}
					hostingsList = new ArrayList<>(aux);
				}
			} else {
				logger.info("Searching by text: \"" + search + "\"");
				hostingsList = hostingDAO.getAllBySearchAll(search);
			}
		}

		if (only_available) {
			Iterator<Hosting> it = hostingsList.iterator();
			Hosting h;
			while (it.hasNext()) {
				h = it.next();
				logger.info("Checking availability of \"" + h.getTitle() + "\"");
				if (h.getAvailable() != 1) {
					it.remove();
					logger.info("Hosting removed!");
				}
			}
		}

		request.setAttribute("HostingsList", hostingsList);
		logger.info("Establishing location for embeded map");
		Iterator<Hosting> it = hostingsList.iterator();
		boolean b_location = false;
		String location = "Cáceres";
		Hosting aux;
		while (!b_location && it.hasNext()) {
			aux = (Hosting) it.next();
			if (!aux.getLocation().isBlank()) {
				location = aux.getLocation();
				b_location = true;
			}
		}
		logger.info("Location found: " + b_location + ", setting to: \"" + location + "\"");
		request.setAttribute("Location", location);

		List<Category> categoriesList = categoryDAO.getAll();
		request.setAttribute("Categories", categoriesList);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/HostingLocationList.jsp");
		view.forward(request, response);
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
