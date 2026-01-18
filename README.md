# Catering Stok Yönetim Sistemi

## Proje Hakkında
Bu proje, bir catering firmasının stok ve ürün yönetimini gerçekleştirmek amacıyla geliştirilmiş bir masaüstü uygulamasıdır.  
Uygulama Java programlama dili ile geliştirilmiş olup, grafiksel kullanıcı arayüzü için **Java Swing** kullanılmıştır.  
Veriler, proje içerisinde yer alan **data/** klasöründeki `.txt` dosyaları üzerinden dosya okuma ve yazma yöntemleri ile tutulmaktadır.

## Kullanılan Teknolojiler
- Java
- Java Swing
- Dosya İşlemleri (TXT)
- Eclipse IDE

## Proje Yapısı
- `src/` : Java kaynak kodları
- `data/` : Uygulama verilerinin tutulduğu dosyalar
  - `kullanicilar.txt`
  - `stoklar.txt`
  - `urunler.txt`
  - `islem_kayitlari.txt`

## Program Nasıl Çalıştırılır?
1. Proje Eclipse IDE içerisine import edilir.
2. `src` klasörü altında bulunan başlangıç (main) sınıfı açılır.
3. İlgili sınıfa sağ tıklanır → **Run As → Java Application**.
4. Uygulama çalıştığında giriş ekranı açılır.

> **Not:** Uygulamanın düzgün çalışabilmesi için `.txt` dosyalarının **data/** klasörü içerisinde bulunması gerekmektedir.

## Test Kullanıcısı
Giriş ekranında aşağıdaki bilgiler kullanılarak sisteme giriş yapılabilir:

- **Admin:** `admin::1234`
- **Personel:** `personel::1234`