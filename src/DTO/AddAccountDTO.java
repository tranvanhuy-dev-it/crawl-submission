package DTO;

import java.sql.Timestamp;

public class AddAccountDTO {
	private String handle;
	private int platformId;
	private Timestamp addedDate;
	private Timestamp lastCrawlDate;
	
	public AddAccountDTO() {}
	
	public AddAccountDTO(String handle, int platformId, Timestamp addedDate, Timestamp lastCrawlDate) {
        this.handle = handle;
        this.platformId = platformId;
        this.addedDate = addedDate;
        this.lastCrawlDate = lastCrawlDate;
    }
	
    public String getHandle() { return handle; }
    public void setHandle(String handle) { this.handle = handle; }
    public int getPlatformId() { return platformId; }
    public void setPlatformId(int platformId) { this.platformId = platformId; }
    public Timestamp getAddedDate() { return addedDate; }
    public void setAddedDate(Timestamp addedDate) { this.addedDate = addedDate; }
    public Timestamp getLastCrawlDate() { return lastCrawlDate; }
    public void setLastCrawlDate(Timestamp lastCrawlDate) { this.lastCrawlDate = lastCrawlDate; }
}
