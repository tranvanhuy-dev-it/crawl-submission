package Entities;

public class Platform {
	private int platformId;
	private String platformName;
	private String websiteUrl;
	
	public Platform() {}
	
	public Platform(int platformId, String platformName, String websiteUrl) {
		this.platformId = platformId;
		this.platformName = platformName;
		this.websiteUrl = websiteUrl;
	}
	
	public int getPlatformId() { return platformId; }
	public void setPlatformId(int platformId) { this.platformId = platformId; }
	
	public String getPlatformName() { return platformName; }
	public void setPlatformName(String platformName) { this.platformName = platformName; }
	
	public String getWebsiteUrl() { return websiteUrl; }
	public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
	
	@Override
	public String toString() {
		return platformName;
	}
}
