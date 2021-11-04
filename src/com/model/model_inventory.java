/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_inventory;
import com.koneksi.koneksi;
import com.view.TambahCustomer;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author GB
 */
public class model_inventory implements controller_inventory {

    @Override
    public void TambahCustomer(TambahCustomer tc) throws SQLException {
        try {
            Connection con = koneksi.koneksi();
            String sql = "insert into customer values(?,?,?,?)";
            try (PreparedStatement prepare = con.prepareStatement(sql)) {
                prepare.setString(1, tc.auto_num);
                prepare.setString(2, tc.nama_customer.getText());
                prepare.setString(3, tc.alamat.getText());
                prepare.setString(4, tc.telp.getText());
                prepare.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
