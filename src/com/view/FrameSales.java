/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;
import com.koneksi.koneksi;
import static com.koneksi.koneksi.conn;
import static com.koneksi.koneksi.pst;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author GB
 */
public class FrameSales extends javax.swing.JFrame {

    /**
     * Creates new form FrameSales
     */
    public FrameSales() {
        initComponents();
        dataTableCust();
        NoUrutCustomer();
    }
        private void UbahCustomer(){
        int input = JOptionPane.showOptionDialog(null, "Apakah ingin mengubah data customer ?", "Ubah Data Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        
        if(input== JOptionPane.OK_OPTION)
            try {
                int barisCellCekCust = dataCustomer.getSelectedRow();
                int kolomCellCekCust = dataCustomer.getSelectedColumn();
                String id_cust = (String)dataCustomer.getValueAt(barisCellCekCust, 1);
                String nama_cust = (String)dataCustomer.getValueAt(barisCellCekCust, 2);
                String alamat_cust = (String)dataCustomer.getValueAt(barisCellCekCust, 3);
                String telp_cust = (String)dataCustomer.getValueAt(barisCellCekCust, 4);
                
                String query_ubah_cust="UPDATE customer SET nama_customer=?, alamat=?, telp=? WHERE id_customer=?";
                
                pst = conn.prepareStatement(query_ubah_cust);
                
                pst.setString(1, nama_cust);
                pst.setString(2, alamat_cust);
                pst.setString(3, telp_cust);
                pst.setString(4, id_cust);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data berhasil di update");
                dataTableCust();
                NoUrutCustomer();
            } catch (SQLException | ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, e);

            }
    }
         public void NoUrutCustomer() {
        String no;
        int dc = dataCustomer.getRowCount();
        for (int a = 0; a < dc; a++) {
            no = String.valueOf(a + 1);
            dataCustomer.setValueAt(no, a, 0);
        }
    }
         
                 public static void dataTableCust(){
            int jumBaris= 0;
            int i=0;
            conn = koneksi.koneksi();
            String query="SELECT * from customer";
            try{
                Statement stt = conn.createStatement();
                ResultSet res = stt.executeQuery(query);
                
                while(res.next()){
                    jumBaris++;
                }
            }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan");
            }
            
            String isi[][] = new String[jumBaris][5];
            try{
                Statement st = conn.createStatement();
                ResultSet data = st.executeQuery(query);
                while(data.next()){
                    isi[i][1] = data.getString("id_customer");
                    isi[i][2] = data.getString("nama_customer");
                    isi[i][3] = data.getString("alamat");
                    isi[i][4] = data.getString("telp");
                    i++;
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
                
                }
            String NamaKolom[] = {"No","ID customer","Nama customer","Alamat","Telepon cutomer"};
            DefaultTableModel dtms = new DefaultTableModel(isi,NamaKolom){
               //@override
                public boolean isCellEditable(int row, int column){
                switch (column) {
                    case 0:
                        return false;
                    case 1:
                        return false;
                    default:
                        return true;
                        
                        
                }
  
                
            }
                
            };
            dataCustomer.setModel(dtms); 
                    
            }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        customer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataCustomer = new javax.swing.JTable();
        btn_tambah_cust = new javax.swing.JButton();
        btn_refresh_customer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "ID_Customer", "Nama_Customer", "Alamat", "Telepon"
            }
        ));
        dataCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dataCustomerKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(dataCustomer);

        btn_tambah_cust.setText("(+) Customer");
        btn_tambah_cust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_custActionPerformed(evt);
            }
        });

        btn_refresh_customer.setText("Refresh");
        btn_refresh_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_customerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customerLayout = new javax.swing.GroupLayout(customer);
        customer.setLayout(customerLayout);
        customerLayout.setHorizontalGroup(
            customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_tambah_cust)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refresh_customer)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );
        customerLayout.setVerticalGroup(
            customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerLayout.createSequentialGroup()
                .addGroup(customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah_cust)
                    .addComponent(btn_refresh_customer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Customer", customer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambah_custActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_custActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_tambah_custActionPerformed

    private void dataCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dataCustomerKeyReleased
UbahCustomer();
dataTableCust();
NoUrutCustomer();
    }//GEN-LAST:event_dataCustomerKeyReleased

    private void btn_refresh_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refresh_customerActionPerformed
dataTableCust();
NoUrutCustomer();
    }//GEN-LAST:event_btn_refresh_customerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(FrameSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameSales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_refresh_customer;
    private javax.swing.JButton btn_tambah_cust;
    private javax.swing.JPanel customer;
    public static javax.swing.JTable dataCustomer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
