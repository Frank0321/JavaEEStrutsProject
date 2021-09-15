package drink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import drink.model.Account;

public class AccountDao {
	
	private static AccountDao accountDao = new AccountDao();
	private AccountDao(){}
	public static AccountDao getInstance(){
		return accountDao;
	}
	
	public Account queryAccountById(String id){
		Account account = new Account();
		String SQLcommand = "SELECT * FROM BANK_ACCOUNT WHERE ID = ?";
		try(Connection conn = DBConnectionFactory.getOracleDBConnection();
			PreparedStatement stmt = conn.prepareStatement(SQLcommand)){
			stmt.setString(1, id);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()){
					account.setId(id);
					account.setName(rs.getString("NAME"));
					account.setPwd(rs.getString("PWD"));
					account.setBlance(rs.getInt("BALANCE"));
				}
			}catch (SQLException e){
				e.printStackTrace();
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return account;
	}
	
}
