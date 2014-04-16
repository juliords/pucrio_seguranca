import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User 
{
	private Integer group;
	private String name;
	private String login;
	private String passwdHash;
	private String passwdSalt;
	private PublicKey publicKey;
	
	private User()
	{
		// nao instanciavel
	}
	
	public static User getUser(String login) 
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException
	{
		Connection conn = Database.getConnection();
		
		
		String sql = "SELECT pk_login, fk_grupo, nome, senha_hash, senha_salt, chave_publica "
						+ "FROM usuario "
						+ "WHERE pk_login = ?;";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, login);
		
		ResultSet rs = stm.executeQuery();
		
		User user = null;
		if(rs.next())
		{
			user = new User();
			user.group = rs.getInt("fk_grupo");
			user.login = rs.getString("pk_login");
			user.name = rs.getString("nome");
			user.passwdHash = rs.getString("senha_hash");
			user.passwdSalt = rs.getString("senha_salt");
			try {
				user.publicKey = Security.loadPublicKey(Common.hexToBin(rs.getString("chave_publica")));
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				return null; // never gonna happen
			}
		}
		return user;
	}

	public Integer getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	public String getPasswdHash() {
		return passwdHash;
	}

	public String getPasswdSalt() {
		return passwdSalt;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}
