package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import edu.wpi.energetic_easter_bunnies.database.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl extends DAO<Account>{
    static Connection connection = DBConnection.getConnection();
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

            Account account =
                    new Employee(accountID, employeeID, authorityLevel, passwordHash, firstName, lastName);

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

