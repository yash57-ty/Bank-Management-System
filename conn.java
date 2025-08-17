
package bank;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Date;
public class conn{
    Connection con;
    conn(){
        try{
            Class.forName("org.apache.derby.client.ClientAutoloadedDriver");
            con=DriverManager.getConnection("jdbc:derby://localhost:1527/banksystem","yash","yash2006");
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    public void insertSignup(String formno, String name, String fname, String dob,
                             String gender, String email, String marital,
                             String address, String city, String pincode, String state) {
        try {
            String sql = "INSERT INTO signup " +
                         "(FORM_NO, NAME, FATHER_NAME, DOB, GENDER, EMAIL, MARITAL_STATUS, ADDRESS, CITY, PINCODE, STATE) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, formno);
                pstmt.setString(2, name);
                pstmt.setString(3, fname);
                pstmt.setString(4, dob);
                pstmt.setString(5, gender);
                pstmt.setString(6, email);
                pstmt.setString(7, marital);
                pstmt.setString(8, address);
                pstmt.setString(9, city);
                pstmt.setString(10, pincode);
                pstmt.setString(11, state);
                
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertSignup2(String formno, String rel,String  cate,String  inc,String edu,String occ,String pan ,String addhar,
            String scitizen,String eAccount) {
        try {
            String sql = "INSERT INTO signup2 " +
                         "(FORM_NO,RELIGION, CATEGORY, INCOME, EDUCATION, OCCUPTION, PAN, ADDHAR, SENIORCITIZEN,EXISTING_ACCOUT) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, formno);
                pstmt.setString(2, rel);
                pstmt.setString(3, cate);
                pstmt.setString(4, inc);
                pstmt.setString(5, edu);
                pstmt.setString(6, occ);
                pstmt.setString(7, pan);
                pstmt.setString(8, addhar);
                pstmt.setString(9, scitizen);
                pstmt.setString(10,eAccount);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertSignup3(String formno, String atype,String cardno,String pin,String fac) {
        try {
            String sql = "INSERT INTO signup3 " +
                         "(FORM_NO,SACCOUNT_TYPE, CARD_NUMBER, PIN, FACILITY) " +
                         "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, formno);
                pstmt.setString(2, atype);
                pstmt.setString(3,cardno);
                pstmt.setString(4, pin);
                pstmt.setString(5, fac);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public void insertLogin(String formno, String cardno,String pin) {
        try {
            String sql = "INSERT INTO login " +
                         "(FORM_NO, CARD_NO, PIN) " +
                         "VALUES (?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, formno);
                pstmt.setString(2,cardno);
                pstmt.setString(3, pin);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public void insertdeposit(String pin,Date date,String type,String amount) {
        try {
            String sql = "INSERT INTO bank " +
                         "(PIN,DATE,TYPE,AMOUNT) " +
                         "VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, pin);
                pstmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
                pstmt.setString(3, type);
                pstmt.setString(4,amount);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public boolean checkLogin(String pin,String cardno){
         try{
            String sql = "SELECT * FROM login WHERE CARD_NO = ? AND PIN = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cardno);
            pstmt.setString(2, pin);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();         
         }catch(Exception e){
             e.printStackTrace();
             return false;
         }
     }
     public int getBalance(String pin) {
        int balance = 0;
        try {
            String sql = "SELECT * FROM bank WHERE PIN = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();
        
            while (rs.next()) {
                String type = rs.getString("TYPE");
                int amt = Integer.parseInt(rs.getString("AMOUNT"));
                if (type.equals("deposit")) {
                    balance += amt;
                }else if (type.equals("Withdraw")) {
                    balance -= amt;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
     public void changepin(String oldPin,String newPin){
        try {
        String q1 = "UPDATE bank SET pin = ? WHERE pin = ?";
        String q2 = "UPDATE login SET pin = ? WHERE pin = ?";
        String q3 = "UPDATE signup3 SET pin = ? WHERE pin = ?";

        PreparedStatement pstmt1 = con.prepareStatement(q1);
        pstmt1.setString(1, newPin);
        pstmt1.setString(2, oldPin);
        pstmt1.executeUpdate();

        PreparedStatement pstmt2 = con.prepareStatement(q2);
        pstmt2.setString(1, newPin);
        pstmt2.setString(2, oldPin);
        pstmt2.executeUpdate();

        PreparedStatement pstmt3 = con.prepareStatement(q3);
        pstmt3.setString(1, newPin);
        pstmt3.setString(2, oldPin);
        pstmt3.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
     }
    public static void main(String args[]){
        new conn();
    }
}
