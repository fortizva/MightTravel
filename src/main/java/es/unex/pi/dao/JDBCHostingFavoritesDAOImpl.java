package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.HostingFavorites;

public class JDBCHostingFavoritesDAOImpl implements HostingFavoritesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingFavoritesDAOImpl.class.getName());

	@Override
	public List<HostingFavorites> getAll() {

		if (conn == null) return null;
						
		List<HostingFavorites> hostingFavoritesList = new ArrayList<HostingFavorites>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites");
						
			while ( rs.next() ) {
				HostingFavorites hostingFavorites = new HostingFavorites();
				hostingFavorites.setIdh(rs.getInt("idh"));
				hostingFavorites.setIdu(rs.getInt("idu"));
						
				hostingFavoritesList.add(hostingFavorites);
				logger.info("fetching all HostingFavorites: "+hostingFavorites.getIdh()+" "+hostingFavorites.getIdu());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostingFavoritesList;
	}

	@Override
	public List<HostingFavorites> getAllByUser(long idu) {
		
		if (conn == null) return null;
						
		ArrayList<HostingFavorites> hostingFavoritesList = new ArrayList<HostingFavorites>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE idu="+idu);

			while ( rs.next() ) {
				HostingFavorites hostingFavorites = new HostingFavorites();
				hostingFavorites.setIdh(rs.getInt("idh"));
				hostingFavorites.setIdu(rs.getInt("idu"));

				hostingFavoritesList.add(hostingFavorites);
				logger.info("fetching all HostingFavorites by idh: "+hostingFavorites.getIdh()+"->"+hostingFavorites.getIdu());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostingFavoritesList;
	}
	
	@Override
	public List<HostingFavorites> getAllByHosting(long idh) {
		
		if (conn == null) return null;
						
		ArrayList<HostingFavorites> hostingFavoritesList = new ArrayList<HostingFavorites>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE Idh="+idh);

			while ( rs.next() ) {
				HostingFavorites hostingFavorites = new HostingFavorites();
				hostingFavorites.setIdh(rs.getInt("idh"));
				hostingFavorites.setIdu(rs.getInt("idu"));
							
				hostingFavoritesList.add(hostingFavorites);
				logger.info("fetching all HostingFavorites by idu: "+hostingFavorites.getIdu()+"-> "+hostingFavorites.getIdh());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hostingFavoritesList;
	}
	
	
	@Override
	public HostingFavorites get(long idh,long idu) {
		if (conn == null) return null;
		
		HostingFavorites hostingFavorites = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE Idh="+idh+" AND idu="+idu);			 
			if (!rs.next()) return null;
			hostingFavorites= new HostingFavorites();
			hostingFavorites.setIdh(rs.getInt("idh"));
			hostingFavorites.setIdu(rs.getInt("idu"));

			logger.info("fetching HostingFavorites by idh: "+hostingFavorites.getIdh()+"  and idu: "+hostingFavorites.getIdu());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return hostingFavorites;
	}
	
	

	@Override
	public boolean add(HostingFavorites hostingFavorites) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO HostingFavorites (idh,idu) VALUES('"+
									hostingFavorites.getIdh()+"','"+
									hostingFavorites.getIdu()+"')");
						
				logger.info("creating HostingFavorites:("+hostingFavorites.getIdh()+" "+hostingFavorites.getIdu());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(HostingFavorites hostingFavorites) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE idh="+hostingFavorites.getIdh());			 
				if (!rs.next()) 
					return false;
				long idu = rs.getInt("idu");

				stmt.executeUpdate("UPDATE HostingFavorites SET idu="+hostingFavorites.getIdu()
				+" WHERE idh = "+hostingFavorites.getIdh() + " AND idu = " + idu);
				
				logger.info("updating HostingFavorites:("+hostingFavorites.getIdh()+" "+hostingFavorites.getIdu());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idh, long idu) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM HostingFavorites WHERE idh ="+idh+" AND idu="+idu);
				logger.info("deleting HostingFavorites: "+idh+" , idu="+idu);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
