# Catering Stok Yönetim Sistemi

> Java Swing kullanılarak geliştirilmiş, catering firmaları için stok ve ürün takibini sağlayan uygulama.

## Proje Hakkında
Bu proje, bir catering firmasının stok, ürün ve işlem kayıtlarını yönetebilmesini sağlamak amacıyla geliştirilmiş bir uygulamadır.  
Uygulama **Java programlama dili** ile geliştirilmiş olup, grafiksel kullanıcı arayüzü için **Java Swing** kullanılmıştır.

Veriler, proje içerisinde yer alan **`data/` klasöründeki `.txt` dosyaları** üzerinden dosya okuma ve yazma yöntemleri ile tutulmaktadır.  
Bu sayede kullanıcı, stok ve işlem bilgileri kalıcı olarak saklanabilmektedir.

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
2. `src` klasörü altında bulunan main sınıfı açılır.
3. İlgili sınıfa sağ tıklanır → **Run As → Java Application**.
4. Uygulama çalıştığında giriş ekranı açılır.

> **Not:** Uygulamanın düzgün çalışabilmesi için `.txt` dosyalarının **`data/` klasörü içerisinde** bulunması gerekmektedir.

## Test Kullanıcısı
Giriş ekranında aşağıdaki bilgiler kullanılarak sisteme giriş yapılabilir:

- **Admin:** `admin::1234`
- **Personel:** `personel::1234`

## Amaç ve Kazanımlar
Bu proje, Java uygulamaları geliştirme, dosya tabanlı veri yönetimi ve **nesne yönelimli programlama (OOP)** prensiplerini uygulamalı olarak pekiştirmek amacıyla geliştirilmiştir.
