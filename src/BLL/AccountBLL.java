package BLL;

import java.util.List;

import DAL.AccountDAL;
import DTO.AddAccountDTO;
import Entities.Account;

public class AccountBLL {
	private AccountDAL dal = new AccountDAL();
	
	public List<Account> GetAllAccounts() {
		return dal.GetAllAccounts();
	}
	
	public Account GetAccountById(int accountId) {
		return dal.GetAccountById(accountId);
	}
	
	public List<Account> GetAccountByPlatform(int plfId) {
		return dal.GetAccountByPlatform(plfId);
	}
	
	public void DeleteAccount(int accountId) {
		dal.DeleteAccount(accountId);
	}
	
	public boolean AddAccount(AddAccountDTO dto) {
		if (CheckExistsAccount(dto)) {
			return false;
		} else {
			dal.AddAccount(dto);
			return true;
		}
	}
	
	public boolean UpdateAccount(Account acc) {
		if (CheckValidAccount(acc)) {
			dal.UpdateAccount(acc);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean CheckExistsAccount(AddAccountDTO dto) {
	    List<Account> lists = dal.GetAllAccounts(); 
	    
	    for (Account a : lists) {       
	        if (a.getHandle().equalsIgnoreCase(dto.getHandle()) && a.getPlatformId() == dto.getPlatformId()) {
	            return true; 
	        }
	    }
	    return false;
	}
	
	private boolean CheckValidAccount(Account acc) {
	    if (acc == null) return false;

	    String handle = acc.getHandle();
	    int platformId = acc.getPlatformId();
	    int accountId = acc.getAccountId();

	    if (handle == null || handle.trim().isEmpty()) {
	        return false;
	    }
	    if (platformId <= 0 || accountId <= 0) {
	        return false;
	    }
	    return true;
	}
}
