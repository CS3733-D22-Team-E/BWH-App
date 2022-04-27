package edu.wpi.cs3733.D22.teamE;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Texting {
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "ACad7285cd2fdbd178b10b88f3c336b90d";
  public static final String AUTH_TOKEN =
      "655417373ed32489bb442e5970cfadd9"; // "0bb060eaa1fec2901389a120f849e1f3";

  public static void sendSMS(String phoneNumber, String sms) {
    if (!phoneNumber.startsWith("+1")) phoneNumber += "+1";
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message =
        Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                "MG1ac5310d1cb7fa5bd6b3cc678d597e01",
                sms)
            .create();

    System.out.println(message.getSid());
  }
}
