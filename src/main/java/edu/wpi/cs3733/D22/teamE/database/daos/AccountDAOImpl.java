package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.accounts.adminAccount;
import edu.wpi.cs3733.D22.teamE.entity.accounts.staffAccount;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements DAO<Account> {
  static Connection connection = AccountsManager.getInstance().getConnection();
  List<Account> accounts;

  public AccountDAOImpl() throws SQLException {
    accounts = new ArrayList<Account>();
    String query = "SELECT * FROM ACCOUNTS ORDER BY ACCOUNTID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.execute();
    ResultSet rs = statement.executeQuery();
    int numID = 0;
    while (rs.next()) {
      String accountID = rs.getString("ACCOUNTID");
      String employeeID = rs.getString("EMPLOYEEID");
      int authorityLevel = rs.getInt("AUTHORITYLEVEL");
      String passwordHash = rs.getString("PASSWORDHASH");
      String firstName = rs.getString("FIRSTNAME");
      String lastName = rs.getString("LASTNAME");
      String position = rs.getString("POSITION");

      Account account = null;
      if (authorityLevel < Account.adminPerm) {
        account =
            new staffAccount(
                accountID, employeeID, authorityLevel, passwordHash, firstName, lastName, position);
      } else {
        account =
            new adminAccount(
                accountID, employeeID, authorityLevel, passwordHash, firstName, lastName, position);
      }

      accounts.add(account);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<Account> getAll() {
    accounts = new ArrayList<>();

    try {
      String query = "SELECT * FROM ACCOUNTS ORDER BY ACCOUNTID DESC";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.execute();
      ResultSet rs = statement.executeQuery();
      int numID = 0;
      while (rs.next()) {
        String accountID = rs.getString("ACCOUNTID");
        String employeeID = rs.getString("EMPLOYEEID");
        int authorityLevel = rs.getInt("AUTHORITYLEVEL");
        String passwordHash = rs.getString("PASSWORDHASH");
        String firstName = rs.getString("FIRSTNAME");
        String lastName = rs.getString("LASTNAME");
        String position = rs.getString("POSITION");

        Account account = null;
        if (authorityLevel < Account.adminPerm) {
          account =
              new staffAccount(
                  accountID,
                  employeeID,
                  authorityLevel,
                  passwordHash,
                  firstName,
                  lastName,
                  position);
        } else {
          account =
              new adminAccount(
                  accountID,
                  employeeID,
                  authorityLevel,
                  passwordHash,
                  firstName,
                  lastName,
                  position);
        }

        accounts.add(account);
        numID++;
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("Get All Failed!");
      e.printStackTrace();
    }

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
    delete(account);
    accounts.add(account);
    try {
      String query =
          "INSERT INTO ACCOUNTS (ACCOUNTID, EMPLOYEEID , AUTHORITYLEVEL, PASSWORDHASH, FIRSTNAME, LASTNAME, POSITION) VALUES ('"
              + account.getAccountID()
              + "','"
              + account.getEmployeeID()
              + "',"
              + account.getAuthorityLevel()
              + ",'"
              + account.getPasswordHash()
              + "','"
              + account.getFirstName()
              + "','"
              + account.getLastName()
              + "','"
              + account.getPosition()
              + "')"; // Insert into database; does not check if the employeeID already exists
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(Account account) {
    if (get(account.getAccountID()) != null) {
      accounts.remove(account);
      try {
        String query = "DELETE FROM ACCOUNTS WHERE ACCOUNTID = ('" + account.getAccountID() + "')";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
