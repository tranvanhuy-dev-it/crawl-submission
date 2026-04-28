package DAL;
import java.util.List;

import DTO.AddAccountDTO;
import Database.DBHelper;
import Entities.Account;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AccountDAL {
	
	public List<Account>  GetAllAccounts() {
		List<Account> list = new ArrayList<Account>();
		
		String sql =  	"SELECT a.*, p.platformName FROM accounts a " +
		                "JOIN platforms p ON a.platformId = p.platformId where a.isActive = 1";
		ResultSet rs = DBHelper.getInstance().GetRecords(sql);
		
		try {
			while (rs != null && rs.next()) {
				Account acc = new Account();
				
				acc.setAccountId(rs.getInt("accountId"));
				acc.setAddedDate(rs.getTimestamp("addedDate"));
				acc.setHandle(rs.getString("handle"));
				acc.setLastCrawlDate(rs.getTimestamp("lastCrawlDate"));
				acc.setPlatformId(rs.getInt("platformId"));
				acc.setPlatformName(rs.getString("platformName"));
				acc.setIsActive(rs.getBoolean("isActive"));
				
				list.add(acc);
			}
		} catch(Exception e) {
			
		}
		return list;
	}
	
	public Account GetAccountById(int accountId) {
		String sql = "SELECT a.*, p.platformName FROM accounts a " +
	             "JOIN platforms p ON a.platformId = p.platformId " +
	             "WHERE a.accountId = ? AND a.isActive = 1";

	    ResultSet rs = DBHelper.getInstance().GetRecords(sql, accountId);
	    
	    Account acc = null; 
	    try {
	        if (rs != null && rs.next()) {
	        	acc = new Account();
				
				acc.setAccountId(rs.getInt("accountId"));
				acc.setAddedDate(rs.getTimestamp("addedDate"));
				acc.setHandle(rs.getString("handle"));
				acc.setLastCrawlDate(rs.getTimestamp("lastCrawlDate"));
				acc.setPlatformId(rs.getInt("platformId"));
				acc.setPlatformName(rs.getString("platformName"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return acc; 
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
	
	public List<Account> GetAccountByPlatform(int platformId) {
		List<Account> list = new ArrayList<Account>();
		
		String sql = "SELECT a.*, p.platformName FROM accounts a " +
                "JOIN platforms p ON a.platformId = p.platformId " +
                "WHERE a.platformId = ? AND a.isActive = 1";
		ResultSet rs = DBHelper.getInstance().GetRecords(sql, platformId);
		
		try {
			while (rs != null && rs.next()) {
				Account acc = new Account();
				
				acc.setAccountId(rs.getInt("accountId"));
				acc.setAddedDate(rs.getTimestamp("addedDate"));
				acc.setHandle(rs.getString("handle"));
				acc.setLastCrawlDate(rs.getTimestamp("lastCrawlDate"));
				acc.setPlatformId(rs.getInt("platformId"));
				acc.setPlatformName(rs.getString("platformName"));
				acc.setIsActive(rs.getBoolean("isActive"));
				
				list.add(acc);
			}
		} catch(Exception e) {
			
		}
		return list;
	}
}
