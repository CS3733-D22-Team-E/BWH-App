package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.entity.accounts.Account;
import edu.wpi.energetic_easter_bunnies.entity.accounts.staffAccount;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements DAO<Account> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<Account> accounts;

  public AccountDAOImpl() throws SQLException {
    accounts = new ArrayList<Account>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM ACCOUNTS ORDER BY ACCOUNTID DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String accountID = rs.getString("ACCOUNTID");
      String employeeID = rs.getString("EMPLOYEEID");
      int authorityLevel = rs.getInt("AUTHORITYLEVEL");
      String passwordHash = rs.getString("PASSWORDHASH");
      String firstName = rs.getString("FIRSTNAME");
      String lastName = rs.getString("LASTNAME");
      String position = rs.getString("POSITION");

      Account account =
          new staffAccount(
              accountID, employeeID, authorityLevel, passwordHash, firstName, lastName, position);

      accounts.add(account);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<Account> getAll() {
    return accounts;
  }

  @Override
  public Account get(String id) {
    for (Account account : accounts) {
      if (account.getAccountID().equals(id)) return account;
    }
    System.out.println("Account with AccountID " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(Account account) {
    accounts.add(account);
  }

  @Override
  public void delete(Account account) {
    accounts.remove(account);
  }
}
