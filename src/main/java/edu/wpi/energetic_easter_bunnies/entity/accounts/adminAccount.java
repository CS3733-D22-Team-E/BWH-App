package edu.wpi.energetic_easter_bunnies.entity.accounts;

public class adminAccount extends Account {

    public adminAccount(String accountID, String passwordHash, String firstName, String lastName, String position, String location) {
        super(accountID, passwordHash, firstName, lastName, position, location);
        this.authorityLevel=2;
    }
}
