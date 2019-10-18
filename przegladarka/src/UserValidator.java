public class UserValidator {
	private static final String name = "tajniak";
	private static final String password = "1234";

	public static boolean authenticate(String name, String password) {
		if(UserValidator.name.equals(name) & UserValidator.password.equals(password))
			return true;
		else
			return false;
	}
}