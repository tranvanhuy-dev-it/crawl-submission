package DTO;

public class AddPlatformDTO {
	private String platformName;
	private String websiteUrl;
	
	public AddPlatformDTO() {}
	
	public AddPlatformDTO(int platformId, String platformName, String websiteUrl) {
		this.platformName = platformName;
		this.websiteUrl = websiteUrl;
	}
	
	public String getPlatformName() { return platformName; }
	public void setPlatformName(String platformName) { this.platformName = platformName; }
	public String getWebsiteUrl() { return websiteUrl; }
	public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
}
