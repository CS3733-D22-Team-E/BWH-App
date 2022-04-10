package edu.wpi.energetic_easter_bunnies.entity.accounts;

public class staffAccount extends Account {
    public staffAccount(String accountID,
                        Integer authorityLevel,
                        String firstName,
                        String lastName,
                        String position,
                        String location) {
        super(accountID,
                authorityLevel,
                firstName,
                lastName,
                position,
                location);
    }
}
