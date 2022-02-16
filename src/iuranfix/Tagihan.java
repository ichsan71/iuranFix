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
public class Tagihan implements sqlInterface{
    private int hargaTotal;        

    String dbUrlTagihan = "jdbc:mysql://localhost/tagihandb";
    String jdbcDriver = "com.mysql.jdbc.Driver";
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
        System.out.println("ERROR SELECT DB IN TAGIHAN");
    }
    return rss;
    } // cuekin under construction
    
    @Override
    public void tampilDb(Siswa siswa, AkunPengguna akun, StatusPembayaran status, Tagihan tagihan){
         try {
            ResultSet rss = tagihan.selectDb("tagihan");

            while(rss.next()){
                if (rss.getString("id_jurusan").equalsIgnoreCase(siswa.getId_jurusan()) && siswa.getStatusPembayaran().equalsIgnoreCase("")) {
                    siswa.setId_jurusan(rss.getString("id_jurusan"));
                    tagihan.setHargaTotal(rss.getInt("totalTagihan"));

                    System.out.println("Tagihan\t : " + rss.getString("totalTagihan"));
                }             
            }
            if (siswa.getStatusPembayaran().equalsIgnoreCase("DIBAYAR")) {
                System.out.println("Tagihan\t : 0");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("gagal tampil di kelas tagihan");
        }
    }
        
    @Override
    public void koneksi(){
        try {
            Class.forName(jdbcDriver);
            //System.out.println("kelas aman");
        } catch (ClassNotFoundException ex) {
            System.out.println("class error");
        }
        
        try {
            con = (Connection) DriverManager.getConnection(dbUrlTagihan, user, pass);
            //System.out.println("Berhasil nyambung");
        } catch (SQLException ex) {
            System.out.println("koneksi error dari si tagihan ");
        }
    }

    /**
     * @return the hargaTotal
     */
    public int getHargaTotal() {
        return hargaTotal;
    }

    /**
     * @param hargaTotal the hargaTotal to set
     */
    public void setHargaTotal(int hargaTotal) {
        this.hargaTotal = hargaTotal;
    }   
        
}
