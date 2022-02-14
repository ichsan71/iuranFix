/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iuranfix;
/**
 *
 * @author DIANA
 */
public class Sekolah {
    private String NPS, namaSekolah;

    public void tampilData(Sekolah sekolah){
        System.out.println("Asal Sekolah :\t" + sekolah.getNamaSekolah());
    }
    /**
     * @return the NPS
     */
    public String getNPS() {
        return NPS;
    }

    /**
     * @param NPS the NPS to set
     */
    public void setNPS(String NPS) {
        this.NPS = NPS;
    }

    /**
     * @return the namaSekolah
     */
    public String getNamaSekolah() {
        return namaSekolah;
    }

    /**
     * @param namaSekolah the namaSekolah to set
     */
    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }
   
}
