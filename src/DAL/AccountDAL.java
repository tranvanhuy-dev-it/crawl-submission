package DAL;
import java.util.List;

import DTO.AccountResponse;
import DTO.AddAccountDTO;
import Database.DBHelper;
import Entities.Account;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AccountDAL {
	
	public List<AccountResponse>  GetAllAccounts() {
		List<AccountResponse> list = new ArrayList<AccountResponse>();
		
		String sql =  	"SELECT a.*, p.platformName FROM accounts a " +
		                "JOIN platforms p ON a.platformId = p.platformId where a.isActive = 1";
		
		ResultSet rs = DBHelper.getInstance().GetRecords(sql);
		
		try {
			while (rs != null && rs.next()) {
				AccountResponse acc = new AccountResponse(rs.getInt("accountId"), 
					      rs.getString("handle"), 
						  rs.getInt("platformId"), 
						  rs.getString("platformName"),
						  rs.getTimestamp("addedDate"), 
						  rs.getTimestamp("lastCrawlDate"), 
						  rs.getBoolean("isActive")
						  );
				
				list.add(acc);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public AccountResponse GetAccountById(int accountId) {
		String sql = "SELECT a.*, p.platformName FROM accounts a " +
	             "JOIN platforms p ON a.platformId = p.platformId " +
	             "WHERE a.accountId = ? AND a.isActive = 1";

	    ResultSet rs = DBHelper.getInstance().GetRecords(sql, accountId);
	    
	    AccountResponse acc = null; 
	    try {
	        if (rs != null && rs.next()) {
	        	acc = new AccountResponse(rs.getInt("accountId"), 
					      rs.getString("handle"), 
						  rs.getInt("platformId"), 
						  rs.getString("platformName"),
						  rs.getTimestamp("addedDate"), 
						  rs.getTimestamp("lastCrawlDate"), 
						  rs.getBoolean("isActive")
						  );
	        }
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return acc; 
	}
	
	public List<AccountResponse> GetAccountByPlatform(int platformId) {
		List<AccountResponse> list = new ArrayList<AccountResponse>();
		
		String sql = "SELECT a.*, p.platformName FROM accounts a " +
                "JOIN platforms p ON a.platformId = p.platformId " +
                "WHERE a.platformId = ? AND a.isActive = 1";
		
		ResultSet rs = DBHelper.getInstance().GetRecords(sql, platformId);
		
		try {
			while (rs != null && rs.next()) {
				AccountResponse acc = new AccountResponse(rs.getInt("accountId"), 
					      rs.getString("handle"), 
						  rs.getInt("platformId"), 
						  rs.getString("platformName"),
						  rs.getTimestamp("addedDate"), 
						  rs.getTimestamp("lastCrawlDate"), 
						  rs.getBoolean("isActive")
						  );
				
				list.add(acc);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void AddAccount(AddAccountDTO acc) {
		String sql = "INSERT INTO accounts (handle, platformId, addedDate, lastCrawlDate, isActive) VALUES(?, ?, ?, ?, ?)";
		DBHelper.getInstance().ExecuteDB(sql, acc.getHandle(), acc.getPlatformId(), acc.getAddedDate(), acc.getLastCrawlDate(), 1);
	}
	
	public void UpdateAccount(Account acc) {
	    String sql = "UPDATE accounts SET handle = ?, platformId = ? WHERE accountId = ?";
	    DBHelper.getInstance().ExecuteDB(sql, acc.getHandle(), acc.getPlatformId(), acc.getAccountId());
	}
	
	public void DeleteAccount(int accountId) {
		System.out.println(accountId);
	    String sql = "Update accounts SET isActive = 0 WHERE accountId = ?";
	    DBHelper.getInstance().ExecuteDB(sql, accountId);
	}
	
	public void UpdateLastCrawlDate(int accountId) {
	    String sql = "UPDATE accounts SET lastCrawlDate = NOW() WHERE accountId = ?";
	    DBHelper.getInstance().ExecuteDB(sql, accountId);
	}

}
