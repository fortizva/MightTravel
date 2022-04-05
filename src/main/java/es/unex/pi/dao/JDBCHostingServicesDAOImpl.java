package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.HostingServices;

public class JDBCHostingServicesDAOImpl implements HostingServicesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingServicesDAOImpl.class.getName());

	@Override
	public List<HostingServices> getAll() {

		if (conn == null) return null;
						
		ArrayList<HostingServices> hostingServicesList = new ArrayList<HostingServices>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices");
						
			while ( rs.next() ) {
				HostingServices hostingServices = new HostingServices();
				hostingServices.setIdh(rs.getInt("idh"));
				hostingServices.setIdsrv(rs.getInt("idsrv"));
						
				hostingServicesList.add(hostingServices);
				logger.info("fetching all HostingServices: "+hostingServices.getIdh()+" "+hostingServices.getIdsrv());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostingServicesList;
	}

	@Override
	public List<HostingServices> getAllByService(long idsrv) {
		
		if (conn == null) return null;
						
		ArrayList<HostingServices> hostingServicesList = new ArrayList<HostingServices>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE idsrv="+idsrv);

			while ( rs.next() ) {
				HostingServices hostingServices = new HostingServices();
				hostingServices.setIdh(rs.getInt("idh"));
				hostingServices.setIdsrv(rs.getInt("idsrv"));

				hostingServicesList.add(hostingServices);
				logger.info("fetching all HostingServices by idh: "+hostingServices.getIdh()+"->"+hostingServices.getIdsrv());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostingServicesList;
	}
	
	@Override
	public List<HostingServices> getAllByHosting(long idh) {
		
		if (conn == null) return null;
						
		ArrayList<HostingServices> hostingServicesList = new ArrayList<HostingServices>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE Idh="+idh);

			while ( rs.next() ) {
				HostingServices HostingServices = new HostingServices();
				HostingServices.setIdh(rs.getInt("idh"));
				HostingServices.setIdsrv(rs.getInt("idsrv"));
							
				hostingServicesList.add(HostingServices);
				logger.info("fetching all HostingServices by idsrv: "+HostingServices.getIdsrv()+"-> "+HostingServices.getIdh());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hostingServicesList;
	}
	
	
	@Override
	public HostingServices get(long idh,long idsrv) {
		if (conn == null) return null;
		
		HostingServices HostingServices = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE Idh="+idh+" AND idsrv="+idsrv);			 
			if (!rs.next()) return null;
			HostingServices= new HostingServices();
			HostingServices.setIdh(rs.getInt("idh"));
			HostingServices.setIdsrv(rs.getInt("idsrv"));

			logger.info("fetching HostingServices by idh: "+HostingServices.getIdh()+"  and idsrv: "+HostingServices.getIdsrv());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return HostingServices;
	}
	
	

	@Override
	public boolean add(HostingServices HostingServices) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO HostingServices (idh,idsrv) VALUES('"+
									HostingServices.getIdh()+"','"+
									HostingServices.getIdsrv()+"')");
						
				logger.info("creating HostingServices:("+HostingServices.getIdh()+" "+HostingServices.getIdsrv());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(HostingServices HostingServices) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE idh="+HostingServices.getIdh());			 
				if (!rs.next()) 
					return false;
				long idsrv = rs.getInt("idsrv");

				stmt.executeUpdate("UPDATE HostingServices SET idsrv="+HostingServices.getIdsrv()
				+" WHERE idh = "+HostingServices.getIdh() + " AND idsrv = " + idsrv);
				
				logger.info("updating HostingServices:("+HostingServices.getIdh()+" "+HostingServices.getIdsrv());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idh, long idsrv) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM HostingServices WHERE idh ="+idh+" AND idsrv="+idsrv);
				logger.info("deleting HostingServices: "+idh+" , idsrv="+idsrv);
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
