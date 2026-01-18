package foodstore;

import java.io.*;

public class UrunDosyaIslemleri extends BaseDosyaIslemleri {

    public UrunDosyaIslemleri(String dosyaAdi) {
        super(dosyaAdi);
    }

    // Barkod daha önce kayıtlı mı?
    public boolean barkodVarMi(String barkod) {
        barkod = barkod == null ? "" : barkod.trim();
        File file = new File(getDosyaAdi());
        
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    String kayitliBarkod = parts[0].trim();
                    
                    if (kayitliBarkod.equals(barkod)) return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Yeni ürün ekle (sadece barkod + ürün adı)
    public boolean urunEkle(String barkod, String urunAdi) {
        barkod = barkod == null ? "" : barkod.trim();
        urunAdi = urunAdi == null ? "" : urunAdi.trim();

        if (barkod.isEmpty() || urunAdi.isEmpty()) return false;
        if (barkodVarMi(barkod)) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getDosyaAdi(), true))) {
            File f = new File(getDosyaAdi());
            if (f.length() > 0) writer.newLine();
            
            writer.write(barkod + "," + urunAdi);
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //barkoddan ürün getirme fonksiyonum
    public String barkoddanUrunAdiGetir(String barkod) {
        barkod = barkod == null ? "" : barkod.trim();
        File file = new File(getDosyaAdi());
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    String kayitliBarkod = parts[0].trim();
                    String urunAdi = parts[1].trim();

                    if (kayitliBarkod.equals(barkod)) {
                    	
                        return urunAdi;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Tüm ürünleri Barkod - Ürün Adı formatında liste olarak getirir
    public java.util.List<String> getUrunListesi() {
        java.util.List<String> urunler = new java.util.ArrayList<>();
        File file = new File(getDosyaAdi());
        if (!file.exists()) return urunler;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    
                    urunler.add(parts[0] + " - " + parts[1]); 
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return urunler;
    }
}