package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.HostingFavorites;


public interface HostingFavoritesDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the hosting and the users related to them from the database.
	 * 
	 * @return List of all the hosting and the users related to them from the database.
	 */
	
	public List<HostingFavorites> getAll();

	/**
	 *Gets all the HostingFavorites that are related to a user.
	 * 
	 * @param idu
	 *            User identifier
	 * 
	 * @return List of all the HostingFavorites related to a user.
	 */
	public List<HostingFavorites> getAllByUser(long idu);
	
	/**
	 * Gets all the HostingFavorites that contains an specific hosting.
	 * 
	 * @param idh
	 *            Hosting Identifier
	 * 
	 * @return List of all the HostingFavorites that contains an specific hosting
	 */
	public List<HostingFavorites> getAllByHosting(long idh);

	/**
	 * Gets a HostingFavorites from the DB using idh and idu.
	 * 
	 * @param idr 
	 *            hosting identifier.
	 *            
	 * @param idu
	 *            User Identifier
	 * 
	 * @return HostingFavorites with that idh and idu.
	 */
	
	public HostingFavorites get(long idh,long idu);

	/**
	 * Adds an HostingFavorites to the database.
	 * 
	 * @param HostingFavorites
	 *            HostingFavorites object with the details of the relation between the hosting and the user.
	 * 
	 * @return hosting identifier or -1 in case the operation failed.
	 */
	
	public boolean add(HostingFavorites HostingFavorites);

	/**
	 * Updates an existing HostingFavorites in the database.
	 * 
	 * @param hostingFavorites
	 *            HostingFavorites object with the new details of the existing relation between the hosting and the user. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save(HostingFavorites hostingFavorites);

	/**
	 * Deletes an HostingFavorites from the database.
	 * 
	 * @param idh
	 *            Hosting identifier.
	 *            
	 * @param idu
	 *            User Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idh, long idu);
}