package foodstore;

import java.io.File;
import java.io.IOException;

public abstract class BaseDosyaIslemleri implements IDosyaIslemleri {

    // Encapsulation
    private String dosyaAdi;

    public BaseDosyaIslemleri(String dosyaAdi) {
        this.dosyaAdi = dosyaAdi;
        dosyaYoksaOlustur();
    }

    // Alt sınıflar dosya adına bu metodla ulaşacak
    @Override
    public String getDosyaAdi() {
        return this.dosyaAdi;
    }

    // Ortak dosya oluşturma mantığı
    @Override
    public void dosyaYoksaOlustur() {
        File file = new File(getDosyaAdi());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}