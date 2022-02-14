/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iuranfix;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import static iuranfix.Main.cls;
import static iuranfix.Main.delay;
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
public class Siswa extends Sekolah{
    private String NIM, namaSiswa, kelas, jurusan, semester, id_jurusan;
    String statusPembayaran;
    
    String jdbcDriver = "com.mysql.jdbc.Driver";
    String dbUrl = "jdbc:mysql://localhost/mahasiswadb";
    String user = "root";
    String pass = "";
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    Statement st;

    /**
     *
     * @param namadb
     * @return
     */

    public ResultSet selectDb(String namadb){
        ResultSet rss = null;
    try{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + namadb);
        rss = stmt.executeQuery();
    } catch (SQLException e){
        System.out.println("emng bau ini ma");
    }
    return rss;
    } // cuekin under construction
    
    public void cekInfoLogin(Siswa siswa, AkunPengguna akun, StatusPembayaran status, Tagihan tagihan, String nim, String nama, String pass){
        try {
                    ResultSet rs = akun.selectDb("akun");
                    while (rs.next()) {     
                       if (rs.getString("nim").equals(nim)){

                           if (rs.getString("username").equalsIgnoreCase(nama) && rs.getString("password").equalsIgnoreCase(pass)) {
                                // show menus
                                akun.menuAkun(akun, siswa, tagihan, status);
                                break;
                           } else{
                               System.out.println("USERNAME ATAU PASSWORD SALAH!");
                               break;
                           }
                        } 
                    }
                    //System.out.println("NIM tidak terdaftar");
                } catch (SQLException ex) {
                    //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("ERROR SELECT DB IN MAIN");
                }

    }
    
    public void ambilDataDariDb(Siswa siswa, AkunPengguna akun){
        try {
            ResultSet rs = siswa.selectDb("mahasiswa");
            
            while(rs.next()){
                if (rs.getString("nim").equalsIgnoreCase(akun.getNim())) {
                    // inisialisasi                    
                    siswa.setNIM(rs.getString("nim"));
                    siswa.setNamaSiswa(rs.getString("nama"));
                    siswa.setSemester(rs.getString("semester"));
                    siswa.setJurusan(rs.getString("jurusan"));     
                    siswa.setKelas(rs.getString("kelas"));     
                    siswa.setStatusPembayaran(rs.getString("status")); 
                    siswa.setId_jurusan(rs.getString("id_jurusan")); 
                }
                        
            }  
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("gagal euy");
        }
    }
    
    public void cekDbInsert(Siswa siswa, AkunPengguna akun, String nim, String nama, String pass){
         try {
            ResultSet rs = siswa.selectDb("mahasiswa");
            
            while(rs.next()){
                if (rs.getString("nim").equals(nim)){

                    // insert data ke DB
                    akun.insertDB(nim, nama, pass);

                    System.out.println("Akun berhasil dibuat");
                    delay();
                    cls();
                    break;
                }           
            }
             if (akun.getNim() == null) {
                 System.out.println("NIM TIDAK TERDAFTAR! SILAHKAN HUBUNGI AKADEMISI");
             }
             
             
        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("gagal euy");
        }
    }
    
    public void tampilDb(Siswa siswa, AkunPengguna akun){
         try {
            ResultSet rs = siswa.selectDb("mahasiswa");
            
            while(rs.next()){
                if (rs.getString("nim").equals(akun.getNim())) {
                    System.out.println("NIM      : " + rs.getString("nim"));
                    System.out.println("Nama\t : " + rs.getString("nama"));
                    System.out.println("Kelas\t : " + rs.getString("kelas"));
                    System.out.println("Jurusan\t : " + rs.getString("jurusan"));
                    System.out.println("Semester : " + rs.getInt("semester"));
                }             
            }

        } catch (SQLException ex) {
            Logger.getLogger(AkunPengguna.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("gagal euy");
        }
    }
    public void bayar(Siswa siswa, StatusPembayaran status){
        if (siswa.getStatusPembayaran().equalsIgnoreCase("")) {
            status.insertDB(siswa.getNIM(), "DIBAYAR");
            System.out.println("TERIMAKASIH TELAH MAMBAYAR :)");
            try {
                String sql = "update mahasiswa set status = ? where nim = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, "DIBAYAR");
                pst.setString(2, siswa.getNIM());
                pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Siswa.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            System.out.println("TIDAK ADA TAGIHAN");
        }
        
    }
    
    public void statusPembayaran(Siswa siswa, StatusPembayaran status){
        System.out.println("\t==STATUS PEMBAYARAN==\n");
        
        status.tampilDb(status, siswa);

        for (int i = Integer.parseInt(siswa.semester)-1 ; i > 0; i--) {
            System.out.println("Semester " + i + " \t: " + "DIBAYAR");
        }
        
    }

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
            System.out.println("koneksi error");
        }
        
    }

    /**
     * @return the NIM
     */
    public String getNIM() {
        return NIM;
    }

    /**
     * @param NIM the NIM to set
     */
    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    /**
     * @return the namaSiswa
     */
    public String getNamaSiswa() {
        return namaSiswa;
    }

    /**
     * @param namaSiswa the namaSiswa to set
     */
    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }

    /**
     * @return the kelas
     */
    public String getKelas() {
        return kelas;
    }

    /**
     * @param kelas the kelas to set
     */
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    /**
     * @return the jurusan
     */
    public String getJurusan() {
        return jurusan;
    }

    /**
     * @param jurusan the jurusan to set
     */
    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    /**
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * @return the id_jurusan
     */
    public String getId_jurusan() {
        return id_jurusan;
    }

    /**
     * @param id_jurusan the id_jurusan to set
     */
    public void setId_jurusan(String id_jurusan) {
        this.id_jurusan = id_jurusan;
    }

    /**
     * @return the statusPembayaran
     */
    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    /**
     * @param statusPembayaran the statusPembayaran to set
     */
    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }
    
    
    
    
}
