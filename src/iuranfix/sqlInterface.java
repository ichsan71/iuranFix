/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iuranfix;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author San
 */
public interface sqlInterface {   
    
    public ResultSet selectDb(String namadb);
    
    public void koneksi();
    
    public void tampilDb(Siswa siswa, AkunPengguna akun, StatusPembayaran status, Tagihan tagihan);
}
