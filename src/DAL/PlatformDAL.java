package DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.AddPlatformDTO;
import Database.DBHelper;
import Entities.Platform;

public class PlatformDAL {
	public List<Platform>  GetAllPlatforms() {
		List<Platform> list = new ArrayList<Platform>();
		
		String sql = "SELECT * FROM platforms";
		ResultSet rs = DBHelper.getInstance().GetRecords(sql);
		
		try {
			while (rs != null && rs.next()) {
				Platform plf = new Platform();
				
				plf.setPlatformId(rs.getInt("platformId"));
				plf.setPlatformName(rs.getString("platformName"));
				plf.setWebsiteUrl(rs.getString("websiteUrl"));
				
				list.add(plf);
			}
		} catch(Exception e) {
			
		}
		return list;
	}
	
	public Platform GetPlatformById(int platformId) {
	    String sql = "SELECT * FROM platforms WHERE platformId = ?";
	    ResultSet rs = DBHelper.getInstance().GetRecords(sql, platformId);
	    
	    Platform plf = null; 
	    try {
	        if (rs != null && rs.next()) {
	            plf = new Platform(); 
	            
	            plf.setPlatformId(rs.getInt("platformId"));
	            plf.setPlatformName(rs.getString("platformName"));
	            plf.setWebsiteUrl(rs.getString("websiteUrl"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return plf; 
	}
	
	public void AddPlatform(AddPlatformDTO plf) {
		String sql = "INSERT INTO platforms (platformName, websiteUrl) VALUES(?, ?)";
		DBHelper.getInstance().ExecuteDB(sql, plf.getPlatformName(), plf.getWebsiteUrl());
	}
	
	public void UpdatePlatform(Platform plf) {
	    String sql = "UPDATE platforms SET platformName = ?, websiteUrl = ? WHERE platformId = ?";
	    DBHelper.getInstance().ExecuteDB(sql, plf.getPlatformName(), plf.getWebsiteUrl(), plf.getPlatformId());
	}
	
	public void DeletePlatform(int platformId) {
	    String sql = "DELETE FROM platform WHERE platformId = ?";
	    DBHelper.getInstance().ExecuteDB(sql, platformId);
	}
}
