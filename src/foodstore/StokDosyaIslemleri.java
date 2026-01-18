package foodstore;

import java.io.*;

public class StokDosyaIslemleri extends BaseDosyaIslemleri {

    public StokDosyaIslemleri(String dosyaAdi) {
        super(dosyaAdi);
    }

    //STOK EKLEME
    public boolean stokEkle(String barkod, String urunAdi, String skt, int eklenenAdet) {
        if (barkod == null) barkod = "";
        if (urunAdi == null) urunAdi = "";
        if (skt == null) skt = "";

        barkod = barkod.trim();
        urunAdi = urunAdi.trim();
        skt = skt.trim();

        if (barkod.isEmpty() || urunAdi.isEmpty() || skt.isEmpty() || eklenenAdet <= 0)
            return false;

        File file = new File(getDosyaAdi());
        File tempFile = new File("temp_" + getDosyaAdi());

        boolean kayitBulundu = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                
                if (parts.length >= 4) {
                    String mevcutBarkod = parts[0].trim();
                    String mevcutSkt = parts[2].trim();
                    String mevcutAdetStr = parts[3].trim();
                    String mevcutUrunAdi = parts[1].trim(); 

                    // EĞER AYNI BARKOD VE AYNI SKT VARSA ADETİ TOPLAR
                    if (mevcutBarkod.equals(barkod) && mevcutSkt.equals(skt)) {
                        int mevcutAdet = 0;
                        try {
                            mevcutAdet = Integer.parseInt(mevcutAdetStr);
                        } catch (NumberFormatException e) {
                            mevcutAdet = 0;
                        }
                        //STOK ARTTIRMA
                        int yeniToplamAdet = mevcutAdet + eklenenAdet;
                        
                        
                        writer.write(mevcutBarkod + "," + mevcutUrunAdi + "," + mevcutSkt + "," + yeniToplamAdet);
                        writer.newLine();
                        kayitBulundu = true;
                    } 
                    else {
                        
                        writer.write(line);
                        writer.newLine();
                    }
                } else {
                    
                    writer.write(line);
                    writer.newLine();
                }
            }

            
            if (!kayitBulundu) {
                writer.write(barkod + "," + urunAdi + "," + skt + "," + eklenenAdet);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Dosya değişimi (Eski sil, yeninin adını değiştir)
        if (file.exists() && !file.delete()) return false;
        return tempFile.renameTo(file);
    }
    
    /*
     * Stok Çıkış:
     * - Aynı barkod + aynı SKT satırını bulur
     * - Adet düşer
     * - Kalan 0 ise satırı siler
     *
     * Sonuç kodları:
     *  1  -> başarılı
     *  0  -> stok kaydı bulunamadı
     * -1  -> yeterli stok yok
     * -2  -> dosya hatası
     */

    public int stokCikar(String barkod, String skt, int cikisAdet) {
        if (barkod == null) barkod = "";
        if (skt == null) skt = "";

        barkod = barkod.trim();
        skt = skt.trim();

        if (barkod.isEmpty() || skt.isEmpty() || cikisAdet <= 0) return -2;

        File file = new File(getDosyaAdi());
        if (!file.exists()) return -2;

        
        File tempFile = new File("temp_" + getDosyaAdi());
        
        boolean bulundu = false;
        boolean basarili = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts.length >= 4) {
                    String b = parts[0].trim();
                    String urunAdi = parts[1].trim();
                    String kayitSkt = parts[2].trim();
                    String adetStr = parts[3].trim();

                    if (b.equals(barkod) && kayitSkt.equals(skt)) {
                        bulundu = true;
                        int mevcutAdet;
                        try {
                        	
                            mevcutAdet = Integer.parseInt(adetStr);
                        } catch (NumberFormatException ex) {
                        	
                            writer.write(line);
                            writer.newLine();
                            continue;
                        }

                        if (mevcutAdet < cikisAdet) {
                            writer.write(line); 
                            writer.newLine();
                        } else {
                        	
                        	//STOK DÜŞÜRME
                            int kalan = mevcutAdet - cikisAdet;
                            
                            basarili = true;
                            if (kalan > 0) {
                                writer.write(barkod + "," + urunAdi + "," + skt + "," + kalan);
                                writer.newLine();
                            }
                        }
                        continue;
                    }
                }
                
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        // Dosyaları değiştir
        if (!file.delete()) return -2;
        if (!tempFile.renameTo(file)) return -2;

        if (!bulundu) return 0;
        if (!basarili) return -1;
        return 1;
    }
}