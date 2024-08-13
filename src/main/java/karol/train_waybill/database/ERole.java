package karol.train_waybill.database;

public enum ERole {

	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_COMPANY("ROLE_COMPANY"),
	ROLE_RAILWAY("ROLE_RAILWAY");
	
	private final String role;

	ERole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString()
	{
		return role;
	}
}
