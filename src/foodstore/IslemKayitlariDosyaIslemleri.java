package foodstore;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IslemKayitlariDosyaIslemleri extends BaseDosyaIslemleri {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public IslemKayitlariDosyaIslemleri(String dosyaAdi) {
        super(dosyaAdi);
    }
    
    
    // kayıt ekleme
    public boolean kayitEkle(String islemTipi, String kullaniciAdi,
                             String barkod, String urunAdi, String skt, int adet) {

        String zaman = LocalDateTime.now().format(formatter);
        
        String satir = zaman + "," + islemTipi + "," + kullaniciAdi + "," +
                barkod + "," + urunAdi + "," + skt + "," + adet;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getDosyaAdi(), true))) {
            File f = new File(getDosyaAdi());
            if (f.length() > 0) writer.newLine();
            
            writer.write(satir);
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}