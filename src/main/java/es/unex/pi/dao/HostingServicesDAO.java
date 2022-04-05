package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.HostingServices;


public interface HostingServicesDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the hosting and the services related to them from the database.
	 * 
	 * @return List of all the hosting and the services related to them from the database.
	 */
	
	public List<HostingServices> getAll();

	/**
	 *Gets all the HostingServices that are related to a service.
	 * 
	 * @param idsrv
	 *            Service identifier
	 * 
	 * @return List of all the HostingServices related to a service.
	 */
	public List<HostingServices> getAllByService(long idsrv);
	
	/**
	 * Gets all the HostingServices that contains an specific hosting.
	 * 
	 * @param idh
	 *            Hosting Identifier
	 * 
	 * @return List of all the HostingServices that contains an specific hosting
	 */
	public List<HostingServices> getAllByHosting(long idh);

	/**
	 * Gets a HostingServices from the DB using idh and idsrv.
	 * 
	 * @param idr 
	 *            hosting identifier.
	 *            
	 * @param idsrv
	 *            Service Identifier
	 * 
	 * @return HostingServices with that idh and idsrv.
	 */
	
	public HostingServices get(long idh,long idsrv);

	/**
	 * Adds an HostingServices to the database.
	 * 
	 * @param hostingServices
	 *            HostingServices object with the details of the relation between the hosting and the service.
	 * 
	 * @return hosting identifier or -1 in case the operation failed.
	 */
	
	public boolean add(HostingServices hostingServices);

	/**
	 * Updates an existing HostingServices in the database.
	 * 
	 * @param hostingServices
	 *            HostingServices object with the new details of the existing relation between the hosting and the service. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save(HostingServices hostingServices);

	/**
	 * Deletes an HostingServices from the database.
	 * 
	 * @param idh
	 *            Hosting identifier.
	 *            
	 * @param idsrv
	 *            Service Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idh, long idsrv);
}