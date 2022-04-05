package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Service;


public interface ServiceDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets a service from the DB using id.
	 * 
	 * @param id
	 *            Service Identifier.
	 * 
	 * @return Service object with that id.
	 */
	public Service get(long id);

	/**
	 * Gets a service from the DB using name.
	 * 
	 * @param name
	 *            Service name.
	 * 
	 * @return Service object with that name.
	 */
	public Service get(String name);

	
	/**
	 * Gets all the services from the database.
	 * 
	 * @return List of all the services from the database.
	 */
	public List<Service> getAll();
	
	/**
	 * Gets all the services from the database that contain a text in the name.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the services from the database that contain a text in the name.
	 */	
	public List<Service> getAllBySearchName(String search);


	/**
	 * Adds a service to the database.
	 * 
	 * @param service
	 *            Service object with the service details.
	 * 
	 * @return Service identifier or -1 in case the operation failed.
	 */
	
	public long add(Service service);

	/**
	 * Updates an existing service in the database.
	 * 
	 * @param service
	 *            Service object with the new details of the existing service.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	
	public boolean save(Service service);

	/**
	 * Deletes a service from the database.
	 * 
	 * @param id
	 *            Service identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long id);
}
