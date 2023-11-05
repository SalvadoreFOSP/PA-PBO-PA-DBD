# Source Code

1. **Menu Awal**
   
package pa_pbo;
  
    public class MenuAwal extends javax.swing.JFrame {
    
        /**
         * Creates new form MenuAwal
         */
        public MenuAwal() {
            initComponents();
        }

        private void wargabtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
            MenuLogin menuLogin = new MenuLogin();
            menuLogin.setVisible(true);
            this.dispose();
        }                                        
    
        private void regisbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
            MenuRegis menuRegis = new MenuRegis();
            menuRegis.setVisible(true);
            this.dispose();
        }                                        
    
        private void adminbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
            MenuLogin menuLogin = new MenuLogin();
            menuLogin.setVisible(true);
            this.dispose();
        }
    }
    
2. **Menu Regis**

package pa_pbo;

    public class MenuRegis extends javax.swing.JFrame {
      
          DBCon con;
          public MenuRegis() {
              initComponents();
              this.con = new DBCon();
          }
    
        private void regisbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
            
            String nama = namatxt.getText();
            String alamat = alamattxt.getText();
            String nik = niktxt.getText();
            String no_telp = telptxt.getText();
            String username = usertxt.getText();
            String password = pwfield.getText();
            
            try (Connection conn = con.create_connection();
                 PreparedStatement ps =  conn.prepareStatement("INSERT INTO warga (nama, alamat, nik, no_telp, username, password) VALUES (?, ?, ?, ?, ?, ?)");
                    )
            {
                ps.setString(1, nama);
                ps.setString(2, alamat);
                ps.setString(3, nik);
                ps.setString(4, no_telp);
                ps.setString(5, username);
                ps.setString(6, password);
                
                ps.executeUpdate();
                System.out.println("Register Berhasil");
                
                JOptionPane.showMessageDialog(null, "Akun Anda Berhasil Dibuat", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                MenuAwal menuAwal = new MenuAwal();
                menuAwal.setVisible(true);
                this.dispose();
                
            } catch (SQLException ex) {
                System.out.println(ex);
            }
    
        }                                       
    
        private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
            MenuAwal menuAwal = new MenuAwal();
            menuAwal.setVisible(true);
            this.dispose();
        }
        }
    
3. **Menu Login**

package pa_pbo;

    public class MenuLogin extends javax.swing.JFrame {
        
        private boolean cek(String username, String password) {
            try {
                Connection conn = con.create_connection();
                String query = "SELECT * FROM warga WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
    
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                return false;
            }
        }
    
        DBCon con;
        public MenuLogin() {
            initComponents();
            this.con = new DBCon();
        }
    
        private void kembalibtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
            MenuAwal menuAwal = new MenuAwal();
            menuAwal.setVisible(true);
            this.dispose();
        }                                          
    
        private void loginbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
            
            String username = userfield.getText();
            String password = new String(pwfield.getPassword());
            
            if (username.equals("admin") && password.equals("admin")) {
                        
                        JOptionPane.showMessageDialog(this, "Berhasil login", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                        
                        MenuAdmin menuAdmin = new MenuAdmin();
                        menuAdmin.setVisible(true);
                        this.dispose();
                        
            } else if (username.isEmpty() || password.isEmpty()){
                System.out.println("Gagal Login!");
                    JOptionPane.showMessageDialog(null, "Gagal login", "Gagal", JOptionPane.ERROR_MESSAGE);
                    
            } else{
    
                if (cek(username, password)) {
                    System.out.println("Berhasil Login!");
                    
                    JOptionPane.showMessageDialog(this, "Berhasil login", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                    MenuUser menuUser = new MenuUser();
                    menuUser.setVisible(true);
                    this.dispose();
                } else {
                    System.out.println("Gagal Login!");
                    JOptionPane.showMessageDialog(null, "Gagal login", "Gagal", JOptionPane.ERROR_MESSAGE);
                    
                }
            }
        
        }
        }

4. **Menu Admin**

package pa_pbo;

    public class MenuAdmin extends javax.swing.JFrame {
        
        public static void main(String args[]) {
            
            JFrame frame = new JFrame("Menu Admin");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
    
            DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "Tanggal", "Status", "Kategori" }, 0);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
    
            JButton approveButton = new JButton("Setuju");
            JButton deleteButton = new JButton("Hapus");
            JButton logoutButton = new JButton("Log Out");
    
            frame.add(scrollPane);
            frame.add(approveButton);
            frame.add(deleteButton);
            frame.add(logoutButton);
    
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lurah", "root", "");
                Statement statement = connection.createStatement();
    
                String query = "SELECT id, tanggal, status, kategori FROM laporan_surat";
                ResultSet resultSet = statement.executeQuery(query);
    
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String tanggal = resultSet.getString("tanggal");
                    String status = resultSet.getString("status");
                    String kategori = resultSet.getString("kategori");
    
                    model.addRow(new String[] { id, tanggal, status, kategori });
                }
    
                approveButton.addActionListener((ActionEvent e) -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String idToApprove = (String) model.getValueAt(selectedRow, 0);
                        model.setValueAt("Disetujui", selectedRow, 2);
                        
                        try {
                            String updateQuery = "UPDATE laporan_surat SET status = 'Disetujui' WHERE id = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                            preparedStatement.setString(1, idToApprove);
                            preparedStatement.executeUpdate();
                        } catch (SQLException ex) {
                        }
                    }
                });
    
                deleteButton.addActionListener((ActionEvent e) -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String idToDelete = (String) model.getValueAt(selectedRow, 0);
                        model.removeRow(selectedRow);
                        
                        try {
                            String deleteQuery = "DELETE FROM laporan_surat WHERE id = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                            preparedStatement.setString(1, idToDelete);
                            preparedStatement.executeUpdate();
                        } catch (SQLException ex) {
                        }
                    }
                });
                
                logoutButton.addActionListener((ActionEvent e) -> {
                    MenuAwal menuAwal = new MenuAwal();
                    menuAwal.setVisible(true);
                    frame.dispose();
                });
    
            } catch (ClassNotFoundException | SQLException ex) {
            }
    
            frame.setLayout(null);
            scrollPane.setBounds(20, 20, 550, 300);
            approveButton.setBounds(20, 330, 100, 30);
            deleteButton.setBounds(130, 330, 100, 30);
            logoutButton.setBounds(240, 330, 100, 30);
            frame.setVisible(true);
        
            
            
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MenuAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            //</editor-fold>
    
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                new MenuAdmin().setVisible(true);
            });
        }
    
        public MenuAdmin() {
            
            initComponents();
        }
        }

5. **Menu User**

package pa_pbo;

    public class MenuUser extends javax.swing.JFrame {
    
        public MenuUser() {
            initComponents();
        }
    
        private void buatbtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
            BuatSurat buatSurat = new BuatSurat();
            buatSurat.setVisible(true);
            this.dispose();
        }                                       
    
        private void lihatbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            
            LihatSurat lihatSurat = new LihatSurat();
            lihatSurat.setVisible(true);
            this.dispose();
        }                                        
    
        private void keluarbtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
            MenuAwal menuAwal = new MenuAwal();
            menuAwal.setVisible(true);
            this.dispose();
        }
        }

6. **Menu Buat Surat**

package pa_pbo;

    public class BuatSurat extends javax.swing.JFrame {
       
        public BuatSurat() {
            initComponents();
        }
    
        private void ktpbtnActionPerformed(java.awt.event.ActionEvent evt) {                                       
            BuatKTP buatKTP = new BuatKTP();
            buatKTP.setVisible(true);
            this.dispose();
        }                                      
    
        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            BuatAkta buatAkta = new BuatAkta();
            buatAkta.setVisible(true);
            this.dispose();
        }                                        
    
        private void kembalibtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
            MenuUser menuUser = new MenuUser();
            menuUser.setVisible(true);
            this.dispose();
        }
        }

8. **Menu Buat KTP**

package pa_pbo;

    public class BuatKTP extends javax.swing.JFrame {
        
        DBCon con;
    
        /**
         * Creates new form BuatKTP
         */
        public BuatKTP() {
            initComponents();
            this.con = new DBCon();
        }
        
        private static String generateRandomID() {
            Random random = new Random();
            int randomNik = 100 + random.nextInt(900); // ID 3 digit acak
            return String.valueOf(randomNik);
        }
        
        private String getCurrentDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            return dateFormat.format(today);
        }

        private void kembalibtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        BuatSurat buatSurat = new BuatSurat();
        buatSurat.setVisible(true);
        this.dispose();
    }                                          

    private void kirimbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         

        String id = generateRandomID();
        String date = getCurrentDate();
        String kategori = "KTP";
        String status = "Belum Disetujui";
        
        String nik = niktxt.getText();
        String nama = namatxt.getText();
        String tanggal = tanggaltxt.getText();
        String tempat = tempattxt.getText();
        String jenis = jenistxt.getText();
        String goldar = goldartxt.getText();
        
        try (Connection conn = con.create_connection();
                PreparedStatement ps =  conn.prepareStatement("INSERT INTO surat (nik, nama, tanggal_lahir, tempat_lahir, jenis_kelamin) VALUES (?, ?, ?, ?, ?)");
                PreparedStatement stm = conn.prepareStatement("INSERT INTO ktp (nik, goldar) VALUES (?, ?)");
                PreparedStatement stmn = conn.prepareStatement("INSERT INTO laporan_surat (id, tanggal, status, kategori) VALUES (?, ?, ?, ?)")
                )
        {
            
            ps.setString(1, nik);
            ps.setString(2, nama);
            ps.setString(3, tanggal);
            ps.setString(4, tempat);
            ps.setString(5, jenis);
            
            stm.setString(1, nik);
            stm.setString(2, goldar);
            
            stmn.setString(1, id);
            stmn.setString(2, date);
            stmn.setString(3, status);
            stmn.setString(4, kategori);
            
            ps.executeUpdate();
            stm.executeUpdate();
            stmn.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "KTP Berhasil Dibuat, Mohon Menunggu Verifikasi", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            MenuUser menuUser = new MenuUser();
            menuUser.setVisible(true);
            this.dispose();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        }

9. **Menu Buat Akta**

package pa_pbo;

    public class BuatAkta extends javax.swing.JFrame {
    
    
    DBCon con;
    
    /**
     * Creates new form BuatAkta
     */
    public BuatAkta() {
        initComponents();
        this.con = new DBCon();
    }
    
    private static String generateRandomNIK() {
        Random random = new Random();
        int randomNik = 100000000 + random.nextInt(900000000); // NIK 9 digit acak
        return String.valueOf(randomNik);
    }
    
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return dateFormat.format(today);
    }
    
    private static String generateRandomID() {
        Random random = new Random();
        int randomNik = 100 + random.nextInt(900); // ID 3 digit acak
        return String.valueOf(randomNik);
    }

    private void kirimbtnActionPerformed(java.awt.event.ActionEvent evt) {                                         

        String id = generateRandomID();
        String date = getCurrentDate();
        String kategori = "Akta";
        String status = "Belum Disetujui";
        
        String nik = generateRandomNIK();
        String nama = namatxt.getText();
        String tanggal = tanggaltxt.getText();
        String tempat = tempattxt.getText();
        String jenis = jenistxt.getText();
        String anak = anaktxt.getText();
        
        try (Connection conn = con.create_connection();  
                PreparedStatement ps =  conn.prepareStatement("INSERT INTO surat (nik, nama, tanggal_lahir, tempat_lahir, jenis_kelamin) VALUES (?, ?, ?, ?, ?)");
                PreparedStatement stm = conn.prepareStatement("INSERT INTO akte_kelahiran (nik, anakke) VALUES (?, ?)");
                PreparedStatement stmn = conn.prepareStatement("INSERT INTO laporan_surat (id, tanggal, status, kategori) VALUES (?, ?, ?, ?)")
                
            )
        {
            ps.setString(1, nik);
            ps.setString(2, nama);
            ps.setString(3, tanggal);
            ps.setString(4, tempat);
            ps.setString(5, jenis);
            
            stm.setString(1, nik);
            stm.setString(2, anak);
            
            stmn.setString(1, id);
            stmn.setString(2, date);
            stmn.setString(3, status);
            stmn.setString(4, kategori);
            
            ps.executeUpdate();
            stm.executeUpdate();
            stmn.executeUpdate();
            
            System.out.println("Register Berhasil");
            
            JOptionPane.showMessageDialog(null, "Akta Berhasil Dibuat, Mohon Menunggu Verifikasi", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            MenuUser menuUser = new MenuUser();
            menuUser.setVisible(true);
            this.dispose();
            
 
        } catch (SQLException ex) {
            System.out.println(ex);    
        }
    }                                        

    private void namatxtActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void kembalibtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        BuatSurat buatSurat = new BuatSurat();
        buatSurat.setVisible(true);
        this.dispose();
    }
    }

10. **Menu Lihat Surat**

package pa_pbo;

public class LihatSurat extends javax.swing.JFrame {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplikasi Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "Tanggal", "Status", "Kategori" }, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton kembaliButton = new JButton("Kembali");

        frame.add(scrollPane);
        frame.add(kembaliButton);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lurah", "root", "");
            Statement statement = connection.createStatement();

            String query = "SELECT id, tanggal, status, kategori FROM laporan_surat";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String tanggal = resultSet.getString("tanggal");
                String status = resultSet.getString("status");
                String kategori = resultSet.getString("kategori");

                model.addRow(new String[] { id, tanggal, status, kategori });
            }

            kembaliButton.addActionListener((ActionEvent e) -> {
                BuatSurat buatSurat = new BuatSurat();
                buatSurat.setVisible(true);
                frame.dispose();
            });

        } catch (ClassNotFoundException | SQLException ex) {
        }

        frame.setLayout(null);
        scrollPane.setBounds(20, 20, 550, 300);
        kembaliButton.setBounds(20, 330, 100, 30);
        frame.setVisible(true);
    }
    
    public LihatSurat() {
        initComponents();
    }
    }
