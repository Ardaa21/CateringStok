package foodstore;

import java.io.*;

public class UserFileRepository extends BaseDosyaIslemleri {

    public UserFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    public void dosyaYoksaOlustur() {
        super.dosyaYoksaOlustur(); 
        
        File file = new File(getDosyaAdi());
        if (file.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("YONETICI,admin,1234,En sevdiğin renk nedir?,mavi");
                writer.newLine();
                writer.write("PERSONEL,personel,1234");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Giriş doğrulama
    public boolean validateCredentials(String username, String password, UserType userType) {
        File file = new File(getDosyaAdi());
        if (!file.exists()) return false;

        username = username == null ? "" : username.trim();
        password = password == null ? "" : password.trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
            
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String type = parts[0].trim();
                    String u = parts[1].trim();
                    String p = parts[2].trim();

                    if (type.equals(userType.name()) && u.equals(username) && p.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    // Yönetici kullanıcı adı var mı?
    public boolean managerExists(String username) {
        File file = new File(getDosyaAdi());
        if (!file.exists()) return false;
        username = username == null ? "" : username.trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String type = parts[0].trim();
                    String u = parts[1].trim();
                    if (type.equals(UserType.YONETICI.name()) && u.equals(username)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Yeni yönetici KAYIT
    public boolean addManager(String username, String password, String securityQuestion, String securityAnswer) {
        username = username == null ? "" : username.trim();
        password = password == null ? "" : password.trim();
        securityQuestion = securityQuestion == null ? "" : securityQuestion.trim();
        securityAnswer = securityAnswer == null ? "" : securityAnswer.trim();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getDosyaAdi(), true))) {
            writer.newLine();
            writer.write(UserType.YONETICI.name() + "," + username + "," + password + "," + securityQuestion + "," + securityAnswer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    // Yönetici güvenlik sorusunu getir
    public String getManagerSecurityQuestion(String username) {
        File file = new File(getDosyaAdi());
        if (!file.exists()) return null;
        username = username == null ? "" : username.trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String type = parts[0].trim();
                    String u = parts[1].trim();
                    String question = parts[3].trim();
                    if (type.equals(UserType.YONETICI.name()) && u.equals(username)) {
                        return question;
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }
    
    // Yönetici güvenlik cevabı doğru mu?
    public boolean validateManagerSecurityAnswer(String username, String answer) {
        File file = new File(getDosyaAdi());
        if (!file.exists()) return false;
        username = username == null ? "" : username.trim();
        answer = answer == null ? "" : answer.trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String type = parts[0].trim();
                    String u = parts[1].trim();
                    String savedAnswer = parts[4].trim();
                    if (type.equals(UserType.YONETICI.name()) && u.equals(username)) {
                    	
                        return savedAnswer.equalsIgnoreCase(answer);
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    // Yönetici şifresini güncelle
    public boolean updateManagerPassword(String username, String newPassword) {
        File file = new File(getDosyaAdi());
        if (!file.exists()) return false;
        
        username = username == null ? "" : username.trim();
        newPassword = newPassword == null ? "" : newPassword.trim();
        
        File tempFile = new File("temp_" + getDosyaAdi());
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String type = parts[0].trim();
                    String u = parts[1].trim();
                    
                    if (type.equals(UserType.YONETICI.name()) && u.equals(username)) {
                        if (parts.length >= 5) {
                            String question = parts[3].trim();
                            String answer = parts[4].trim();
                            
                            writer.write(UserType.YONETICI.name() + "," + username + "," + newPassword + "," + question + "," + answer);
                            writer.newLine();
                            updated = true;
                            continue;
                        }
                    }
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) { return false; }

        if (updated) {
            if (!file.delete()) return false;
            return tempFile.renameTo(file);
        } else {
            tempFile.delete();
            return false;
        }
    }

    //personel ekleme fonksiyonu
    public boolean addPersonel(String username, String password) {
    	username = username == null ? "" : username.trim();
        password = password == null ? "" : password.trim();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getDosyaAdi(), true))) {
            File f = new File(getDosyaAdi());
            if (f.length() > 0) writer.newLine();
            
            writer.write(UserType.PERSONEL.name() + "," + username + "," + password);
            
            return true;
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }
    
    // PERSONEL kullanıcı adı var mı?
    public boolean personelExists(String username) {
        File file = new File(getDosyaAdi());
        if (!file.exists()) return false;
        username = username == null ? "" : username.trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String type = parts[0].trim();
                    String u = parts[1].trim();
                    if (type.equals(UserType.PERSONEL.name()) && u.equals(username)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    	
       // Personel İsimlerini Getir
       public java.util.List<String> getPersonelList() {
           java.util.List<String> personels = new java.util.ArrayList<>();
           File file = new File(getDosyaAdi());
           if (!file.exists()) return personels;

           try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
               String line;
               while ((line = reader.readLine()) != null) {
                   String[] parts = line.split(",");
                   
                   if (parts.length >= 2) {
                       String type = parts[0].trim();
                       String u = parts[1].trim();
                       
                       
                       if (type.equals(UserType.PERSONEL.name())) {
                           personels.add(u);
                           
                       }
                   }
                   
               }
           } catch (IOException e) { e.printStackTrace(); }
           return personels;
       }

       // Personel Sil 
       public boolean deletePersonel(String username) {
           File file = new File(getDosyaAdi());
           if (!file.exists()) return false;
           
           File tempFile = new File("temp_" + getDosyaAdi());
           boolean silindi = false;

           try (BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
               
               String line;
               while ((line = reader.readLine()) != null) {
                   String[] parts = line.split(",");
                   
                   
                   if (parts.length >= 2) {
                       String type = parts[0].trim();
                       String u = parts[1].trim();
                       
                       if (type.equals(UserType.PERSONEL.name()) && u.equals(username)) {
                           silindi = true; 
                           continue; 
                       }
                   }
                   
                   
                   writer.write(line);
                   writer.newLine();
               }
           } catch (IOException e) { return false; }

           // Dosya değişim işlemi
           if (silindi) {
               if (!file.delete()) {
                   tempFile.delete(); // Eski silinemezse temp'i temizle
                   return false;
               }
               return tempFile.renameTo(file);
           } else {
               tempFile.delete(); // Kimse bulunamadıysa temp'i sil
               return false;
           }
       }
}