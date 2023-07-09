package Client;

public class DataMessage {
	private String UserSend;
	private String type;
	private String content;
	public DataMessage(String userSend, String type, String content) {
		super();
		UserSend = userSend;
		this.type = type;
		this.content = content;
	}
	public String getUserSend() {
		return UserSend;
	}
	public void setUserSend(String userSend) {
		UserSend = userSend;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
