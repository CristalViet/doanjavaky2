package Client;

public class Account_Client_side {
		String userName;
		String Avatar;
		String Description;
		public Account_Client_side(String userName, String avatar, String description) {
			super();
			this.userName = userName;
			Avatar = avatar;
			Description = description;
		}
		@Override
		public String toString() {
			return "Account_Client_side [userName=" + userName + ", Avatar=" + Avatar + ", Description=" + Description
					+ "]";
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getAvatar() {
			return Avatar;
		}
		public void setAvatar(String avatar) {
			Avatar = avatar;
		}
		public String getDescription() {
			return Description;
		}
		public void setDescription(String description) {
			Description = description;
		}
		
		
}
