/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.ui;
import com.mycompany.cleano.service.LogDao;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 *
 * @author abdurraihan
 */

public class LogPanel extends JPanel {

    private JTable tblLogs;
    private JScrollPane jScrollPane1;

    public LogPanel() {
        initComponents();
        loadLogData();
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        tblLogs = new JTable();

        tblLogs.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Username", "Waktu Login"}
        ));
        jScrollPane1.setViewportView(tblLogs);

        setLayout(new BorderLayout());
        add(jScrollPane1, BorderLayout.CENTER);
    }

    private void loadLogData() {
        LogDao dao = new LogDao();
        List<String[]> logs = dao.getAllLogs();

        DefaultTableModel model = (DefaultTableModel) tblLogs.getModel();
        model.setRowCount(0); // kosongkan tabel

        for (String[] row : logs) {
            model.addRow(row);
        }
    }

    // Tambahkan method ini kalau mau bisa refresh manual dari luar
    public void refreshLogs() {
        loadLogData();
    }
}
