package Server;

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
	
}
