package edu.wpi.cs3733.D22.teamE.database;

import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.controllers.HeaderController;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

public class ProfilePictureManager {

  public static HeaderController header;

  public static byte[] toByte(String employeeID) throws IOException {
    BufferedImage image =
        ImageIO.read(Main.class.getResource("view/images/employees/" + employeeID + ".jpeg"));
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(image, "jpeg", bos);
    byte[] arr = bos.toByteArray();
    return arr;
  }

  public static void toJpeg(byte[] byteArr, String employeeID) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
    BufferedImage bImage2 = ImageIO.read(bis);
    ImageIO.write(
        bImage2,
        "jpeg",
        new File(
            "src/main/resources/edu/wpi/cs3733/D22/teamE/view/images/employees/"
                + employeeID
                + ".jpeg"));
  }

  public static ByteArrayInputStream getPersonalPicture(Employee employee)
      throws SQLException, IOException {
    Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    Statement s = connection.createStatement();
    ResultSet rs =
        s.executeQuery(
            "select PROFILEPIC from EMPLOYEES where EMPLOYEEID = '"
                + employee.getEmployeeID()
                + "'");
    if (rs.next()) {
      Blob blob = rs.getBlob(1);
      byte[] bytes = blob.getBytes(1L, (int) blob.length());
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      return bis;
    }
    s.close();
    return null;
  }

  public static void savePersonalPicture(Employee employee, byte[] byteArr) throws SQLException {
    Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
    String sql =
        "UPDATE EMPLOYEES SET PROFILEPIC = (?) where EMPLOYEEID = '"
            + employee.getEmployeeID()
            + "'";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setBinaryStream(1, new ByteArrayInputStream(byteArr), byteArr.length);
    statement.executeUpdate();
    header.updatePFP();
  }

  public static void setHeaderReference(HeaderController headerController) {
    header = headerController;
  }
}
