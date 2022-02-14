/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iuranfix;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author DIANA
 */
public class AkunPengguna extends Siswa{
private String username, password, nim;
    
    String dbUrlAkun = "jdbc:mysql://localhost/akundb";
    
    Scanner scanner = new Scanner(System.in);
    
    public void menuAkun(AkunPengguna akun, Siswa siswas, Tagihan tagihan, StatusPembayaran status){
        String pil = "";
        do {            
            ResultSet rst = null;
            
            System.out.println("\t\t\t\t\t\tMAIN MENU\n");    
            System.out.println("**************************************************************************************************");
            System.out.println("1. Tampil Data Akun");
            System.out.println("2. Edit Data Akun");
            System.out.println("3. Hapus Data Akun");
            System.out.println("4. Bayar Tagihan");
            System.out.println("5. Status Pembayaran");
            System.out.println("6. Keluar");
            System.out.println("**************************************************************************************************");
            System.out.print("Masukan pilihan : ");
            pil = scanner.next();
            

            switch(pil){
                case "1":
                    siswas.tampilDb(siswas, akun); // ini method tampil data siswa dari kelas siswa
                    akun.tampilAkun(akun); // ini method tampil data akun dari kelas akunPengguna
                    Main.delay(); // ini untuk delay loading
                    Main.cls(); // ini untuk bersihin layar
                    break;
                case "2":
                    akun.editData(akun); // ini method untuk edit data akun dari kelas akunPengguna
                    System.out.println("Data berhasil di ubah");
                    Main.delay(); // ini untuk delay loading
                    Main.cls(); // ini untuk bersihin layar
                    break;
                case "3":
                    System.out.print("Apakah kamu yakin untuk menghapus akun [Y/T] ? ");
                    String del = scanner.next(); // input user
                    if (del.equalsIgnoreCase("y")) {
                        akun.deleteData(akun); // ini method delete data akun dari kelas akunPengguna
                        pil = "6";
                    } else{        
                        break;
                    }
                    Main.delay(); // ini untuk delay loading
                    Main.cls(); // ini untuk bersihin layar
                    break;
                case "4":
                    System.out.println("\t========INFO TAGIHAN========");
                    siswas.tampilDb(siswas, akun); // ini method tampil data siswa dari kelas siswa
                    tagihan.tampilDb(siswas, akun, tagihan); // ini method tampil data tagihan dari kelas tagihan
                    
                    System.out.print("Apakah kamu yakin untuk membayar Tagihan [Y/T] ? ");
                    String pay = scanner.next(); // input user
                    
                    if (pay.equalsIgnoreCase("y")) {
                        siswas.bayar(siswas, status); // ini method bayar dari kelas siswa
                    }
                    Main.delay(); // ini untuk delay loading
                    Main.cls(); // ini untuk bersihin layar
                    break;
                case "5":
                    siswas.statusPembayaran(siswas, status); // ini method tampil status pembayaran dari kelas Status
                    Main.delay(); // ini untuk delay loading
                    Main.cls(); // ini untuk bersihin layar
                    break;           
                    }
            }while (!pil.equals("6")); 
        
    }

    public void ambilDataDariDb(AkunPengguna akun, String nim, String nama, String pass){
        try {
            ResultSet rs = akun.selectDb("akun");
            while(rs.next()){
                // inisialisasi
                akun.setNim(nim);
                akun.setUsername(nama);
                akun.setPassword(pass);    
            }  
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("gagal euy");
        }
    }
        
    public void deleteData(AkunPengguna akun){
        try {
            String sql = "delete from akun where nim = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, akun.getNim());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("update gagal");
        }
        System.out.println("Akun telah dihapus");        
    }
    
    public void editData(AkunPengguna akun){
        
        System.out.print("Masukan username baru : ");
        String userNew = scanner.next();
        scanner.nextLine();
        System.out.print("Masukan Password baru : ");
        String passNew = scanner.nextLine();
        
        akun.setUsername(userNew);
        akun.setPassword(passNew);
        
        try {
            String sql = "update akun set username = ?, password = ? where nim = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userNew);
            pst.setString(2, passNew);
            pst.setString(3, akun.getNim());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("update gagal");
        }
    }
    
    public void insertDB(String nim, String username, String password){
        setUsername(username);
        setNim(nim);
        setPassword(password);
        try {
            String sql = "insert into akun values (?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, nim);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("gagal masuk");
        }
    }
    
    @Override
    public ResultSet selectDb(String namaDb){
        ResultSet rss = null;
    try{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + namaDb);
        rss = stmt.executeQuery();
    } catch (SQLException e){
        System.out.println("emng bau ini ma");
    }
    return rss;
    } // cuekin under construction
    
    public void tampilAkun(AkunPengguna akun){
        try {
            ResultSet rs = akun.selectDb("akun");
            
            while(rs.next()){
                if (rs.getString("nim").equals(akun.getNim())) {
                    //System.out.println("NIM      : " + rs.getString("nim"));
                    System.out.println("Username : " + rs.getString("username"));
                    System.out.println("Password : " + rs.getString("password"));
                }             
            }

        } catch (SQLException ex) {
            System.out.println("gagal manggil aing si tampil akun");
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
            con = (Connection) DriverManager.getConnection(dbUrlAkun, user, pass);
            //System.out.println("Berhasil nyambung");
        } catch (SQLException ex) {
            System.out.println("koneksi error");
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nim
     */
    public String getNim() {
        return nim;
    }

    /**
     * @param nim the nim to set
     */
    public void setNim(String nim) {
        this.nim = nim;
    }

    
        
}
