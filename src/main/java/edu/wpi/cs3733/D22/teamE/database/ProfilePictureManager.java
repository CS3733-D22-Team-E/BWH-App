package edu.wpi.cs3733.D22.teamE.database;

import edu.wpi.cs3733.D22.teamE.Main;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

public class ProfilePictureManager {

  public static byte[] toByte() throws IOException {
    BufferedImage image = ImageIO.read(Main.class.getResource("view/images/employees/wwong2.jpeg"));
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(image, "jpeg", bos);
    byte[] arr = bos.toByteArray();
    return arr;
  }

  public static void toPng(byte[] byteArr) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
    BufferedImage bImage2 = ImageIO.read(bis);
    ImageIO.write(
        bImage2,
        "jpeg",
        new File("src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/employees/wong.jpeg"));
  }

  public static boolean getPersonalPicture(Employee employee) throws SQLException, IOException {
    Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    Statement s = connection.createStatement();
    try {
      ResultSet rs =
          s.executeQuery(
              "select PROFILEPIC from EMPLOYEES where EMPLOYEEID = '"
                  + employee.getEmployeeID()
                  + "'");
      if (rs.next()) {
        Blob blob = rs.getBlob(1);
        byte[] bytes = blob.getBytes(1L, (int) blob.length());
        toPng(bytes);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    s.close();
    return true;
  }

  public static void savePersonalPicture(Employee employee, byte[] byteArr) throws SQLException {
    Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    String sql =
        "UPDATE EMPLOYEES SET PROFILEPIC = (?) where EMPLOYEEID = '"
            + employee.getEmployeeID()
            + "'";
    int rowsaffected = 0;
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setBinaryStream(1, new ByteArrayInputStream(byteArr), byteArr.length);
    rowsaffected = statement.executeUpdate();
  }
}
