package BLL;

import java.util.List;

import DAL.PlatformDAL;
import DTO.AddPlatformDTO;
import Entities.Platform;

public class PlatformBLL {
	private PlatformDAL dal = new PlatformDAL();
	
	public List<Platform> GetAllPlatform() {
		return dal.GetAllPlatforms();
	}
	
	public Platform GetPlatformById(int platformId) {
		return dal.GetPlatformById(platformId);
	}
	
	public boolean AddPlatform(AddPlatformDTO dto) {
		if (CheckExistsPlatform(dto)) {
			return false;
		} else {
			dal.AddPlatform(dto);
			return true;
		}
	}
	
	public void DeletePlatform(int platformId) {
		dal.DeletePlatform(platformId);
	}
	
	public boolean UpdatePlatform(Platform plf) {
		if (CheckValidPlatform(plf)) {
			dal.UpdatePlatform(plf);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean CheckExistsPlatform(AddPlatformDTO dto) {
		List<Platform> lists = dal.GetAllPlatforms();
		
		for (Platform p : lists) {
			if (p.getPlatformName().equals(dto.getPlatformName()) || p.getWebsiteUrl().equals(dto.getWebsiteUrl())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean CheckValidPlatform(Platform plf) {
	    if (plf == null) return false;
	    
	    String name = plf.getPlatformName();
	    String url = plf.getWebsiteUrl();
	    int id = plf.getPlatformId();	    
	    if (name == null || name.trim().isEmpty() || url == null || url.trim().isEmpty() || id < 0) {
	        return false; 
	    }
	    return true;
	}
}
