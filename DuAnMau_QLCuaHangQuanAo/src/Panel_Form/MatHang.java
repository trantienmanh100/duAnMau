/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel_Form;

import Models.DanhMucM;
import DAO.QLMatHang;
import DAO.QL_DM;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tiến Mạnh
 */
public class MatHang extends javax.swing.JPanel {

    String SX = null;
    String tenAnh = null;
    String maDM;
    QL_DM dm = new QL_DM();
    List<DanhMucM> listDM;
    DefaultTableModel model = new DefaultTableModel();
    QLMatHang mh = new QLMatHang();
    List<Models.MatHang> listMH;

    public MatHang() {
        initComponents();
        displayDM();
        tenDM();
        fillTable(maDM, SX);
        setBackground(new Color(240, 240, 240));
    }

    public void displayDM() {
        listDM = dm.getAll_DM();
        this.cbxMaDanhMuc.removeAllItems();
        for (DanhMucM x : listDM) {
            cbxMaDanhMuc.addItem(x.getMaDM());
        }
    }

    public void tenDM() {
        for (DanhMucM x : listDM) {
            if (x.getMaDM().equals(cbxMaDanhMuc.getSelectedItem().toString())) {
                txtTenDM.setText(x.getTenDM());
            }
        }
    }

    public void SX() {  //sắp xếp theo cbxSX
        if (cbxSX.getSelectedIndex() == 0) {
            SX = "and 1=1";
        } else if (cbxSX.getSelectedIndex() == 1) {
            SX = "ORDER BY SL DESC";
        } else if (cbxSX.getSelectedIndex() == 2) {
            SX = "ORDER BY SL ASC";
        } else if (cbxSX.getSelectedIndex() == 3) {
            SX = "ORDER BY GIABAN DESC";
        } else {
            SX = "ORDER BY GIABAN ASC";
        }
        //System.out.println(SX);
    }

    public void fillTable(String maDM, String SX) {
        model = (DefaultTableModel) tblMH.getModel();
        model.setRowCount(0);
        SX();
        List<Models.MatHang> listMH = mh.getAllMH(cbxMaDanhMuc.getSelectedItem().toString(), SX);
        for (Models.MatHang x : listMH) {
            model.addRow(new Object[]{x.getMaMH(), x.getTenMH(), x.getMaVach(), x.getSoLuong(), x.getGiaNhap(),
                 x.getGiaBan(), x.getDonVi(), x.getMaDM(), x.getAnh(), x.getGhiChu()});
        }

    }

    public void display() {
        SX();
        listMH = mh.getAllMH(cbxMaDanhMuc.getSelectedItem().toString(), SX);
        Models.MatHang x = listMH.get(tblMH.getSelectedRow());
        txtDonVi.setText(x.getDonVi());
        txtGiaBan.setText("" + x.getGiaBan());
        txtGiaNhap.setText(x.getGiaNhap() + "");
        txtMaMH.setText(x.getMaMH());
        txtMaVach.setText(x.getMaVach());
        txtSL.setText(x.getSoLuong() + "");

        txtTenMH.setText(x.getTenMH());
        txtA_GhiChu.setText(x.getGhiChu());
        hinh();
    }

    public void hinh() {
        int row = tblMH.getSelectedRow();
        String hinh = tblMH.getValueAt(row, 8).toString();
        ImageIcon icon = new ImageIcon("src/Images/HangHoa/" + hinh);
        Image anh = icon.getImage();
        ImageIcon anhIcon = new ImageIcon(anh.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), 0));
        this.lblAnh.setIcon(anhIcon);
        tenAnh = hinh;
    }

    public void addMH() {
        String maMH = txtMaMH.getText();
        String tenMH = txtTenMH.getText();
        String donVi = txtDonVi.getText();
        int soLuong = Integer.parseInt(txtSL.getText());
        String maDM = cbxMaDanhMuc.getSelectedItem().toString();
        float giaNhap = Float.parseFloat(txtGiaNhap.getText());
        float giaBan = Float.parseFloat(txtGiaBan.getText());
        String anh;

        if (tenAnh == null) {
            anh = "No Image";
        } else {
            anh = tenAnh;
        }
        String ghiChu = txtA_GhiChu.getText();
        String maVach = txtMaVach.getText();
        Models.MatHang m = new Models.MatHang(maMH, tenMH, donVi, soLuong, maDM, giaNhap, giaBan, anh, ghiChu, maVach);
        mh.insert(m);
        SX();
        fillTable(maDM, SX);
    }

    public void update() {
        // listMH =mh.getAllMH(maDM);
        Models.MatHang x = listMH.get(tblMH.getSelectedRow());
        String maMH = txtMaMH.getText();
        String tenMH = txtTenMH.getText();
        String donVi = txtDonVi.getText();
        int soLuong = Integer.parseInt(txtSL.getText());
        String maDM = cbxMaDanhMuc.getSelectedItem().toString();
        float giaNhap = Float.parseFloat(txtGiaNhap.getText());
        float giaBan = Float.parseFloat(txtGiaBan.getText());
        String anh;

        if (tenAnh == null) {
            anh = "No Image";
        } else {
            anh = tenAnh;
        }
        String ghiChu = txtA_GhiChu.getText();
        String maVach = txtMaVach.getText();
        x.setAnh(anh);
        x.setDonVi(donVi);
        x.setGhiChu(ghiChu);
        x.setGiaBan(giaBan);
        x.setGiaNhap(giaNhap);
        x.setMaDM(maDM);
        x.setMaMH(maMH);
        x.setMaVach(maVach);
        x.setSoLuong(soLuong);
        x.setTenMH(tenMH);
        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không?", "cập nhật", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_OPTION) {
            mh.update(x);
        }
        SX();
        fillTable(maDM, SX);
    }

    public void delete() {
        //listMH =mh.getAllMH(maDM);
        Models.MatHang x = listMH.get(tblMH.getSelectedRow());
        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "DELETE", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_OPTION) {
            mh.delete(x.getMaMH());
            clearForm();
        }
        SX();
        fillTable(maDM, SX);
    }

    public void clearForm() {
        txtA_GhiChu.setText("");
        txtDonVi.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        txtMaMH.setText("");
        txtMaVach.setText("");
        txtSL.setText("");
        txtSL.setText("");
        txtTenDM.setText("");
        txtTenMH.setText("");
        tblMH.clearSelection();
        lblAnh.setIcon(null);
        tenAnh = null;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaMH = new javax.swing.JTextField();
        txtTenDM = new javax.swing.JTextField();
        txtDonVi = new javax.swing.JTextField();
        txtGiaNhap = new javax.swing.JTextField();
        txtTenMH = new javax.swing.JTextField();
        txtSL = new javax.swing.JTextField();
        cbxMaDanhMuc = new javax.swing.JComboBox<>();
        txtGiaBan = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtA_GhiChu = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMH = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtMaVach = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbxSX = new javax.swing.JComboBox<>();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Mã danh mục");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 72, -1, -1));

        jLabel2.setText("Mã mặt hàng");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 121, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Mặt hàng");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        jLabel4.setText("Tên mặt hàng");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 180, -1, -1));

        jLabel5.setText("Số lượng");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, -1, -1));

        jLabel6.setText("Tên Danh mục");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, -1, -1));

        jLabel7.setText("Đơn vị ");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 121, -1, -1));

        jLabel8.setText("Giá nhập");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 180, -1, -1));

        jLabel9.setText("Giá bán");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, -1, -1));
        add(txtMaMH, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 118, 157, -1));

        txtTenDM.setEditable(false);
        add(txtTenDM, new org.netbeans.lib.awtextra.AbsoluteConstraints(673, 69, 157, -1));
        add(txtDonVi, new org.netbeans.lib.awtextra.AbsoluteConstraints(673, 118, 157, -1));
        add(txtGiaNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(673, 177, 157, -1));
        add(txtTenMH, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 177, 157, -1));
        add(txtSL, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, 157, -1));

        cbxMaDanhMuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMaDanhMucItemStateChanged(evt);
            }
        });
        add(cbxMaDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 69, 157, -1));

        txtGiaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaBanActionPerformed(evt);
            }
        });
        add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 230, 160, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, 100, 130));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add-image.png"))); // NOI18N
        jButton1.setText("Chọn ảnh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 160, -1, -1));

        txtA_GhiChu.setColumns(20);
        txtA_GhiChu.setRows(5);
        jScrollPane1.setViewportView(txtA_GhiChu);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 220, 250, 130));

        jLabel10.setText("Ghi chú");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 210, -1, -1));

        tblMH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã mặt hàng", "Tên mặt hàng", "Mã vạch", "Số lượng", "Giá nhập", "Giá bán", "Đơn vị tính", "Mã DM", "Anh", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMH);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 880, 361));

        jLabel11.setText("Mã vạch");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, -1, -1));
        add(txtMaVach, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 157, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/plus.png"))); // NOI18N
        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/update (1).png"))); // NOI18N
        jButton3.setText("Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/delete (1).png"))); // NOI18N
        jButton4.setText("Xóa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/refresh (2).png"))); // NOI18N
        jButton5.setText("Làm mới");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, -1, -1));

        jLabel12.setText("Sắp xếp");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 370, -1, -1));

        cbxSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định", "Số lượng giảm dần", "Số lượng tăng dần", "Giá bán cao->thấp ", "Giá bán thấp đến cao" }));
        cbxSX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSXItemStateChanged(evt);
            }
        });
        cbxSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSXActionPerformed(evt);
            }
        });
        add(cbxSX, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 370, 147, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMaDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMaDanhMucItemStateChanged
        tenDM();
        SX();
        fillTable(maDM, SX);
    }//GEN-LAST:event_cbxMaDanhMucItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fc = new JFileChooser("src/Images/HangHoa");
        FileFilter imgFilter = new FileNameExtensionFilter(
                "Image Only", ImageIO.getReaderFileSuffixes());//chỉ chọn ảnh
        fc.setFileFilter(imgFilter);
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            tenAnh = f.getName();
            ImageIcon icon = new ImageIcon(f.getAbsolutePath());
            Image anh = icon.getImage();
            ImageIcon anhIcon = new ImageIcon(anh.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), 0));
            this.lblAnh.setIcon(anhIcon);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblMHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMHMouseClicked
        display();
    }//GEN-LAST:event_tblMHMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        addMH();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        update();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        delete();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cbxSXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSXItemStateChanged
        SX();
        fillTable(maDM, SX);
    }//GEN-LAST:event_cbxSXItemStateChanged

    private void txtGiaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaBanActionPerformed

    private void cbxSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSXActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxMaDanhMuc;
    private javax.swing.JComboBox<String> cbxSX;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTable tblMH;
    private javax.swing.JTextArea txtA_GhiChu;
    private javax.swing.JTextField txtDonVi;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaMH;
    private javax.swing.JTextField txtMaVach;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTenDM;
    private javax.swing.JTextField txtTenMH;
    // End of variables declaration//GEN-END:variables
}
