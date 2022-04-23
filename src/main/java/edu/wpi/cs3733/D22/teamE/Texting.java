package edu.wpi.cs3733.D22.teamE;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class Texting {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACad7285cd2fdbd178b10b88f3c336b90d";
    public static final String AUTH_TOKEN = "0bb060eaa1fec2901389a120f849e1f3";

    public static void sendSMS(String sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+19788317440"),
                        "MG1ac5310d1cb7fa5bd6b3cc678d597e01",
                        sms)
                .create();

        System.out.println(message.getSid());
    }
}