/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iuranfix;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
/**
 *
 * @author DIANA
 */
public class Main {
        public static void main(String[] args) {
        AkunPengguna akun = new AkunPengguna();
        Siswa siswas = new Siswa();
        Tagihan tagihan = new Tagihan();
        StatusPembayaran status = new StatusPembayaran();
        Sekolah sekolah1 = new Sekolah();
        sekolah1.setNPS("123");
        sekolah1.setNamaSekolah("UNIVERSITAS TEAM 7");
        
        String pil;
        do {            
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("**************************************************************************************************");
        System.out.println("\t\t\t\tWELLCOME TO IURAN APP SEKOLAH TEAM 7\n");
        System.out.println("**************************************************************************************************");
        System.out.println("1. Buat Akun\n");
        System.out.println("2. Masuk\n");
        System.out.println("3. Keluar\n");
        System.out.println("**************************************************************************************************");
        System.out.print("Masukan pilihan : ");
        pil = scanner.next();
        cls();
        switch(pil) {
            case "1": 
                // insert data akun
                System.out.println("=========CREATE AKUN PAGE==========");
                System.out.print(" Masukan NIM        : ");
                String nim = scanner.next();
                scanner.nextLine(); // This line you have to add (It consumes the \n character)
                System.out.print(" Masukan Username   : ");
                String nama = scanner.nextLine();
                System.out.print(" Masukan Password   : ");
                String pass = scanner.nextLine();
                
                // sambungin ke DB
                akun.koneksi();
                siswas.koneksi();
                tagihan.koneksi();
                status.koneksi();
                
                siswas.cekDbInsert(siswas, akun, nim, nama, pass); // cek db inisialisasi akun dan insert data akun ke db

                break;
            case "2": 
                cls();
                 // Masuk data akun
                System.out.println("=========LOGIN PAGE==========");
                System.out.println("");
                System.out.print("Masukan NIM         : ");
                nim = scanner.next();
                scanner.nextLine(); // This line you have to add (It consumes the \n character)
                System.out.print("Masukan Username    : ");
                nama = scanner.nextLine();
                System.out.print("Masukan Password    : ");
                pass = scanner.nextLine();

                // sambungin ke DB
                akun.koneksi();
                siswas.koneksi();
                status.koneksi();
                tagihan.koneksi();

                //cls
                cls();
            
                // method ambil data dari db Akun isi ke var
                akun.ambilDataDariDb(akun, nim, nama, pass);

                // method ambil data dari Siswa db isi ke var
                siswas.ambilDataDariDb(siswas,akun);
            
                // method kondisi pengecekan login   
                siswas.cekInfoLogin(siswas, akun, status, tagihan, nim, nama, pass);
            
                // test kondisi pengecekan apakah akun terdaftar atau tidak
                if (!(siswas.getNIM() == null) || siswas.getNIM() == null) {
                    if (!(siswas.getNIM().equals(nim))) {
                        System.out.println("AKUN TIDAK TERDAFTAR! SILAHKAN BUAT AKUN "); // U SHOULD FIX THIS
                        break;
                    }
                }
                
            case "3":
                System.out.println("bayyy<3");
                break;
                
            default:
                System.out.println("Pilihan salah!");
                break;
        }
        
       } while (!pil.equals("3")); 
        
    }

    public static void cls(){
	for (int i = 0; i < 50; i++) {
            System.out.println(" ");
        }
        try{	
		new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
	}catch(IOException | InterruptedException E)
		{
			System.out.println(E);
		}
    }
    
    public static void delay(){
        for(int i = 1 ; i < 4 ; i++){
            System.out.println("PLEASE WAIT " + (4-i) ); 
            try{
                Thread.sleep(2000);
            }
            catch(InterruptedException e){    

            }
        }
    }
	


    }
    

