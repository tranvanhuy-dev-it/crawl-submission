package Entities;

import java.sql.Timestamp;

public class Account {
	private int accountId;
	private String handle;
	private int platformId;
	private Timestamp addedDate;
	private Timestamp lastCrawlDate;
	private boolean isActive;
	
	public Account() {}
	
	public Account(int accountId, String handle, int platformId, Timestamp addedDate, Timestamp lastCrawlDate, boolean isActive) {
        this.accountId = accountId;
        this.handle = handle;
        this.platformId = platformId;
        this.addedDate = addedDate;
        this.lastCrawlDate = lastCrawlDate;
        this.isActive = isActive;
    }
	
	public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    
    public String getHandle() { return handle; }
    public void setHandle(String handle) { this.handle = handle; }
    
    public int getPlatformId() { return platformId; }
    public void setPlatformId(int platformId) { this.platformId = platformId; }
    
    public Timestamp getAddedDate() { return addedDate; }
    public void setAddedDate(Timestamp addedDate) { this.addedDate = addedDate; }
    
    public Timestamp getLastCrawlDate() { return lastCrawlDate; }
    public void setLastCrawlDate(Timestamp lastCrawlDate) { this.lastCrawlDate = lastCrawlDate; }
    
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
