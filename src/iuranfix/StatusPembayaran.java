/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iuranfix;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author DIANA
 */
public class StatusPembayaran implements sqlInterface{
    String jdbcDriver = "com.mysql.jdbc.Driver";
    String dbUrl = "jdbc:mysql://localhost/statusPembayaran";
    String user = "root";
    String pass = "";
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;
    
    @Override
    public ResultSet selectDb(String namadb){
        ResultSet rss = null;
        try{
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + namadb);
            rss = stmt.executeQuery();
        } catch (SQLException e){
            System.out.println("emng bau ini ma jelassss");
        }
        return rss;
    } // cuekin under construction
    
    @Override
    public void koneksi(){
        try {
            Class.forName(jdbcDriver);
            //System.out.println("kelas aman");
        } catch (ClassNotFoundException ex) {
            System.out.println("class error");
        }
        
        try {
            con = (Connection) DriverManager.getConnection(dbUrl, user, pass);
            //System.out.println("Berhasil nyambung");
        } catch (SQLException ex) {
            System.out.println("koneksi error dari si status ");
        }
    }    
    
    @Override
    public void tampilDb(Siswa siswa, AkunPengguna akun, StatusPembayaran status, Tagihan tagihan){
         try {
            ResultSet rss = status.selectDb("status");

            while(rss.next()){
                if (rss.getString("nim").equalsIgnoreCase(siswa.getNIM())) {
                    System.out.println("Semester Sekarang : " + rss.getString("status"));
                }       
            }

        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("gagal euy");
        }
    }
    
    public void insertDB(String nim, String status){
        try {
            String sql = "insert into status values (?,?)";
            pst = con.prepareStatement(sql);
            //pst.setString(1, "1");
            pst.setString(1, nim);
            pst.setString(2, status);
            pst.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("gagal masuk di insert db status");
        }
    }
    

}
