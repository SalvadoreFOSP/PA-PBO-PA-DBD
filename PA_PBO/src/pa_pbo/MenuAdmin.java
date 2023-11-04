/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pa_pbo;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}