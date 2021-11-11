/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view;

import com.koneksi.koneksi;
import static com.koneksi.koneksi.*;
import com.model.model_inventory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Harits F
 */
public class FrameSales extends javax.swing.JFrame {

    /**
     * Creates new form FrameSales
     */
    public FrameSales() {
        initComponents();
        dataTableOrder();
        dataTableCust();
        NoUrutOrder();
        NoUrutCustomer();
        NoUrutOrderInvoiced();
        dataTableOrderInvoiced();
        NoUrutOrderReady();
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
            }catch(SQLException | ArrayIndexOutOfBoundsException e){
                JOptionPane.showMessageDialog(null, e);
                
            }
        } 
        
            public void NoUrutCustomer(){
            String no;
            int dc = dataCustomer.getRowCount();
            for(int a=0;a<dc;a++){
                no =String.valueOf(a+1);
                dataCustomer.setValueAt(no,a, 0);
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
            public static void NoUrutOrder() {
        String no;
        int dos = dataOrderSalesNew.getRowCount();
        for (int j = 0; j < dos; j++) {
            no = String.valueOf(j+1);
            //readData.setValueAt(no+".",j,0);
            dataOrderSalesNew.setValueAt(no,j,0);
        }
    }
            public static void dataTableOrder() {
        int jumBaris =0;
        int i=0;
        conn = koneksi.koneksi();
        String query="SELECT * FROM header_order as a LEFT JOIN customer as b\n" +
                        "ON a.id_customer=b. id_customer\n" +
                        "LEFT JOIN users as c\n" +
                        "on a.id_user_sales = c.id_user\n" +
                        "LEFT JOIN detail_order as d\n" +
                        "on a.no_order = d.no_order\n" +
                        "LEFT JOIN barang as e\n" +       
                        "on d.kd_barang = e.kd_barang\n" +
                        "WHERE status_order='New' \n" +
                        "GROUP BY a.no_order\n" +
                        "ORDER by a.status_order DESC, d.tanggal_order DESC";
        try{
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(query);
            
            while(res.next()){
                jumBaris++; 
            }
        }catch(SQLException ex){  
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan");
        } 
        
        String isi[][] = new String[jumBaris][7];
        try{
            Statement st = conn.createStatement();
            ResultSet data = st.executeQuery(query);
            //int ad = 0;
            while(data.next()){   
                isi[i][1] = data.getString("no_order");
                isi[i][2] = data.getString("nama_customer");
                isi[i][3] = data.getString("total_penjualan"); 
                isi[i][4] = data.getString("tanggal_order");              
                isi[i][5] = data.getString("status_order"); 
                isi[i][6] = data.getString("nama_users"); 
                i++; 
            }
        }catch(SQLException ex){  
            JOptionPane.showMessageDialog(null, ex); 
        } 
        
        String NamaKolom[] = {"No","Nomor Order","Nama Customer","Total Harga Barang","Tanggal Order","Status Order","Nama Sales"}; 
        //DefaultTableModel model = new DefaultTableModel(){}; 
        DefaultTableModel dtms = new DefaultTableModel(isi,NamaKolom){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        dataOrderSalesNew.setModel(dtms);
        
    }public void NoUrutOrderInvoiced(){
        String no;
        int dos = dataOrderSalesInvoiced.getRowCount();
	for(int j=0;j<dos;j++){
            no = String.valueOf(j+1);
            //readData.setValueAt(no+".",j,0);
            dataOrderSalesInvoiced.setValueAt(no,j,0); 
        }
    }
    public static void dataTableOrderInvoiced(){
        int jumBaris =0;
        int i=0;
        conn = koneksi.koneksi();
        String query="SELECT * FROM header_order as a LEFT JOIN customer as b\n" +
                        "ON a.id_customer=b. id_customer\n" +
                        "LEFT JOIN users as c\n" +
                        "on a.id_user_sales = c.id_user\n" +
                        "LEFT JOIN detail_order as d\n" +
                        "on a.no_order = d.no_order\n" +
                        "LEFT JOIN barang as e\n" +       
                        "on d.kd_barang = e.kd_barang\n" +
                        "WHERE status_order='Invoice' \n" +
                        "GROUP BY a.no_order\n" +
                        "ORDER by a.status_order DESC, d.tanggal_order DESC";
        try{
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(query);
            
            while(res.next()){
                jumBaris++; 
            }
        }catch(SQLException ex){  
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan");
        } 
        
        String isi[][] = new String[jumBaris][7];
        try{
            Statement st = conn.createStatement();
            ResultSet data = st.executeQuery(query);
            //int ad = 0;
            while(data.next()){   
                isi[i][1] = data.getString("no_order");
                isi[i][2] = data.getString("nama_customer");
                isi[i][3] = data.getString("total_penjualan"); 
                isi[i][4] = data.getString("tanggal_order");              
                isi[i][5] = data.getString("status_order"); 
                isi[i][6] = data.getString("nama_users"); 
                i++; 
            }
        }catch(SQLException ex){  
            JOptionPane.showMessageDialog(null, ex); 
        } 
        
        String NamaKolom[] = {"No","Nomor Order","Nama Customer","Total Harga Barang","Tanggal Order","Status Order","Nama Sales"}; 
        //DefaultTableModel model = new DefaultTableModel(){}; 
        DefaultTableModel dtms = new DefaultTableModel(isi,NamaKolom){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        dataOrderSalesInvoiced.setModel(dtms);
    }
    public void NoUrutOrderReady(){
        String no;
        int dos = dataOrderSalesReady.getRowCount();
	for(int j=0;j<dos;j++){
            no = String.valueOf(j+1);
            //readData.setValueAt(no+".",j,0);
            dataOrderSalesReady.setValueAt(no,j,0); 
        }
    }
    public static void dataTableOrderReady(){
        int jumBaris =0;
        int i=0;
        conn = koneksi.koneksi();
        String query="SELECT * FROM header_order as a LEFT JOIN customer as b\n" +
                        "ON a.id_customer=b. id_customer\n" +
                        "LEFT JOIN users as c\n" +
                        "on a.id_user_gudang = c.id_user\n" +
                        "LEFT JOIN detail_order as d\n" +
                        "on a.no_order = d.no_order\n" +
                        "LEFT JOIN barang as e\n" +       
                        "on d.kd_barang = e.kd_barang\n" +
                        "WHERE status_order='Shipped' \n" +
                        "GROUP BY a.no_order\n" +
                        "ORDER by a.status_order DESC, d.tanggal_order DESC";
        try{
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(query);
            
            while(res.next()){
                jumBaris++; 
            }
        }catch(SQLException ex){  
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan");
        } 
        
        String isi[][] = new String[jumBaris][7];
        try{
            Statement st = conn.createStatement();
            ResultSet data = st.executeQuery(query);
            //int ad = 0;
            while(data.next()){   
                isi[i][1] = data.getString("no_order");
                isi[i][2] = data.getString("nama_customer");
                isi[i][3] = data.getString("total_penjualan"); 
                isi[i][4] = data.getString("tanggal_bayar");              
                isi[i][5] = data.getString("status_order"); 
                isi[i][6] = data.getString("nama_users"); 
                i++; 
            }
        }catch(SQLException ex){  
            JOptionPane.showMessageDialog(null, ex); 
        } 
        
        String NamaKolom[] = {"No","Nomor Order","Nama Customer","Total Harga Barang","Tanggal Bayar","Status Order","Admin Gudang"}; 
        //DefaultTableModel model = new DefaultTableModel(){}; 
        DefaultTableModel dtms = new DefaultTableModel(isi,NamaKolom){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        dataOrderSalesReady.setModel(dtms);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btn_tambah_cust = new javax.swing.JButton();
        btn_refresh_customer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataCustomer = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_buat_order = new javax.swing.JButton();
        btn_kirim_invoice = new javax.swing.JButton();
        b_cek_detail_order = new javax.swing.JButton();
        btn_refresh_order = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataOrderSalesNew = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        b_cek_detail_order1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        dataOrderSalesInvoiced = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        cek_completed_order1 = new javax.swing.JButton();
        b_cek_detail_order2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        dataOrderSalesReady = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTabbedPane1KeyReleased(evt);
            }
        });

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

        dataCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "ID Customer", "Nama Customer", "Alamat", "Telepon"
            }
        ));
        dataCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dataCustomerKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(dataCustomer);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btn_tambah_cust)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refresh_customer))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah_cust)
                    .addComponent(btn_refresh_customer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Customer", jPanel1);

        btn_buat_order.setText("Buat Order");

        btn_kirim_invoice.setText("Kirim Invoice");
        btn_kirim_invoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kirim_invoiceActionPerformed(evt);
            }
        });

        b_cek_detail_order.setText("Cek Detail Order");
        b_cek_detail_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_cek_detail_orderActionPerformed(evt);
            }
        });

        btn_refresh_order.setText("Refresh");
        btn_refresh_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_orderActionPerformed(evt);
            }
        });

        dataOrderSalesNew.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nomor Order", "Nama Customer", "Total Harga Barang", "Tanggal Order", "Tanggal Bayar", "Status Order", "Nama Sales"
            }
        ));
        jScrollPane2.setViewportView(dataOrderSalesNew);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_buat_order)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_kirim_invoice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_cek_detail_order)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refresh_order))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_buat_order)
                    .addComponent(btn_kirim_invoice)
                    .addComponent(b_cek_detail_order)
                    .addComponent(btn_refresh_order))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Order", jPanel2);

        b_cek_detail_order1.setText("Cek Detail Order");
        b_cek_detail_order1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_cek_detail_order1ActionPerformed(evt);
            }
        });

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        dataOrderSalesInvoiced.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nomor Order", "Nama Customer", "Total Harga Barang", "Tanggal Order", "Tanggal Bayar", "Status Order", "Admin Gudang"
            }
        ));
        jScrollPane3.setViewportView(dataOrderSalesInvoiced);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(b_cek_detail_order1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_cek_detail_order1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Invoiced Order", jPanel3);

        cek_completed_order1.setText("Bayar");
        cek_completed_order1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cek_completed_order1ActionPerformed(evt);
            }
        });

        b_cek_detail_order2.setText("Cek Detail Order");
        b_cek_detail_order2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_cek_detail_order2ActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        dataOrderSalesReady.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nomor Order", "Nama Customer", "Total Harga Bayar", "Tanggal Order", "Tanggal Bayar", "Status Order", "Admin Gudang"
            }
        ));
        jScrollPane4.setViewportView(dataOrderSalesReady);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cek_completed_order1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b_cek_detail_order2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cek_completed_order1)
                    .addComponent(b_cek_detail_order2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ready To Pay", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1KeyReleased

    private void dataCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dataCustomerKeyReleased
        // TODO add your handling code here:
        UbahCustomer();
        dataTableCust();
        NoUrutCustomer();
    }//GEN-LAST:event_dataCustomerKeyReleased

    private void btn_refresh_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refresh_customerActionPerformed
        // TODO add your handling code here:
        dataTableCust();
        NoUrutCustomer();
    }//GEN-LAST:event_btn_refresh_customerActionPerformed

    private void btn_tambah_custActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_custActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_tambah_custActionPerformed

    private void btn_kirim_invoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kirim_invoiceActionPerformed
        // TODO add your handling code here:
         int tabelData = dataOrderSalesNew.getSelectedRow();
        if(dataOrderSalesNew.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih datanya pada tabel !!!");
        }else{
            ambil_no_order = dataOrderSalesNew.getValueAt(tabelData, 1).toString();
            try{
                model_inventory mi = new model_inventory();
                mi.KirimInvoice(this);
                NoUrutOrder();
            }catch(SQLException ex){
                Logger.getLogger(FrameSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_kirim_invoiceActionPerformed

    private void b_cek_detail_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_cek_detail_orderActionPerformed
        // TODO add your handling code here:
        int tabelData = dataOrderSalesNew.getSelectedRow();
        if(dataOrderSalesNew.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih datanya pada tabel !!!");
        }else{
            ambil_no_order_detail = dataOrderSalesNew.getValueAt(tabelData, 1).toString();
            try{
                DetailOrderSales detailor = new DetailOrderSales();
                detailor.setVisible(true);
            }catch(Exception ex){
                Logger.getLogger(FrameSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_b_cek_detail_orderActionPerformed

    private void btn_refresh_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refresh_orderActionPerformed
        // TODO add your handling code here:
        dataTableOrder();
        NoUrutOrder();
    }//GEN-LAST:event_btn_refresh_orderActionPerformed

    private void b_cek_detail_order1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_cek_detail_order1ActionPerformed
        // TODO add your handling code here:
        int tabelData = dataOrderSalesInvoiced.getSelectedRow();
        if(dataOrderSalesInvoiced.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih datanya pada tabel !!!");
        }else{
            ambil_no_order_detail = dataOrderSalesInvoiced.getValueAt(tabelData, 1).toString();
            try{
                DetailOrderSales detailor = new DetailOrderSales();
                detailor.setVisible(true);
            }catch(Exception ex){
                Logger.getLogger(FrameSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_b_cek_detail_order1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dataTableOrderInvoiced();
        NoUrutOrderInvoiced();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cek_completed_order1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cek_completed_order1ActionPerformed
        // TODO add your handling code here:
        int tabelData = dataOrderSalesReady.getSelectedRow();
        if(dataOrderSalesReady.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih datanya pada tabel !!!");
        }else{
            ambil_no_order = dataOrderSalesReady.getValueAt(tabelData, 1).toString();
            try{
                model_inventory mi = new model_inventory();
                mi.BayarOrder(this);
                dataTableOrderReady();
                //dataTableOrderCompleted();
            }catch(SQLException ex){
                Logger.getLogger(FrameSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cek_completed_order1ActionPerformed

    private void b_cek_detail_order2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_cek_detail_order2ActionPerformed
        // TODO add your handling code here:
        int tabelData = dataOrderSalesReady.getSelectedRow();
        if(dataOrderSalesReady.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih datanya pada tabel !!!");
        }else{
            ambil_no_order_detail = dataOrderSalesReady.getValueAt(tabelData, 1).toString();
            try{
                DetailOrderSales detailor = new DetailOrderSales();
                detailor.setVisible(true);
            }catch(Exception ex){
                Logger.getLogger(FrameSales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_b_cek_detail_order2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dataTableOrderReady();
        NoUrutOrderReady();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton b_cek_detail_order;
    private javax.swing.JButton b_cek_detail_order1;
    private javax.swing.JButton b_cek_detail_order2;
    private javax.swing.JButton btn_buat_order;
    private javax.swing.JButton btn_kirim_invoice;
    private javax.swing.JButton btn_refresh_customer;
    private javax.swing.JButton btn_refresh_order;
    private javax.swing.JButton btn_tambah_cust;
    private javax.swing.JButton cek_completed_order1;
    public static javax.swing.JTable dataCustomer;
    public static javax.swing.JTable dataOrderSalesInvoiced;
    public static javax.swing.JTable dataOrderSalesNew;
    public static javax.swing.JTable dataOrderSalesReady;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
