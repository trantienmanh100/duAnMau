/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel_Form;

import DAO.QL_KhachHang;
import Form.LichSuMuaHangKhach;
import Util.XImage;
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
public class KhachHang extends javax.swing.JPanel {

    private javax.swing.JFileChooser fileChooser;
    private boolean check = true;
   // String tenAnh = null;
    DefaultTableModel model = new DefaultTableModel();
    QL_KhachHang kh = new QL_KhachHang();
    List<Models.KhachHang> listKH = kh.getAll_KH();

    public KhachHang() {
        initComponents();
        fillTable();
        setBackground(new Color(240, 240, 240));
    }

    public void fillTable() {
        model = (DefaultTableModel) tblKH.getModel();
        model.setRowCount(0);
        listKH = kh.getAll_KH();
        for (Models.KhachHang x : listKH) {
            model.addRow(new Object[]{x.getMaKH(), x.getTenKH(), x.getAnh(), x.getSDT(), x.getDiaChi(), x.getEmail(), x.getGhiChu()});
        }
    }

    public void addKH() {
        String ma = txtMa.getText();
        for (Models.KhachHang khachHang : listKH) {
            if (khachHang.getMaKH().equals(ma)) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại");
                check = false;
            }
        }

        String tenKH = txtTen.getText();
        String anh =lblAnh.getToolTipText();
        
        String SDT = txtSDT.getText();

        String diaChi = txtDiaCHi.getText();
        String email = txtEmail.getText();

        String ghiChu = txtA_GhiChu.getText();
        validate(ma, "Mã khách hàng");
        validate(tenKH, "Tên khách hàng");
        validateSDT_Email(SDT, email);
        if (check == false) {
            check = true;
            return;
        }

        Models.KhachHang k = new Models.KhachHang(ma, tenKH, SDT, anh, diaChi, email, ghiChu);
        kh.insert(k);
        fillTable();
    }

//    public void hinh() {
//        int row = tblKH.getSelectedRow();
//        String hinh = tblKH.getValueAt(row, 2).toString();
//        ImageIcon icon = new ImageIcon("src/Images/KhachHang/" + hinh);
//        Image anh = icon.getImage();
//        ImageIcon anhIcon = new ImageIcon(anh.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), 0));
//        this.lblAnh.setIcon(anhIcon);
//        tenAnh = hinh;
//    }

    public void display() {
        Models.KhachHang k = listKH.get(tblKH.getSelectedRow());
        txtA_GhiChu.setText(k.getGhiChu());
        txtDiaCHi.setText(k.getDiaChi());
        txtEmail.setText(k.getEmail());
        txtMa.setText(k.getMaKH());
        txtSDT.setText(k.getSDT());
        txtTen.setText(k.getTenKH());
        if (k.getAnh() != null) {
            lblAnh.setToolTipText(k.getAnh());
            lblAnh.setIcon(XImage.read(k.getAnh()));
        }

    }

    public void update() {
        Models.KhachHang k = listKH.get(tblKH.getSelectedRow());
        String ma = txtMa.getText();
        String tenKH = txtTen.getText();
       
       String anh =lblAnh.getToolTipText();
        String SDT = txtSDT.getText();

        String diaChi = txtDiaCHi.getText();
        String email = txtEmail.getText();
        String ghiChu = txtA_GhiChu.getText();
        k.setAnh(anh);
        k.setDiaChi(diaChi);
        k.setEmail(email);
        k.setGhiChu(ghiChu);
        k.setMaKH(ma);
        k.setSDT(SDT);
        k.setTenKH(tenKH);
        validate(ma, "Mã khách hàng");
        validate(tenKH, "Tên khách hàng");
        validateSDT_Email(SDT, email);
        if (check == false) {
            check = true;
            return;
        }
        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không?", "cập nhật", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_OPTION) {
            kh.update(k);
        }
        fillTable();
    }

    public void delete() {
        Models.KhachHang x = listKH.get(tblKH.getSelectedRow());
        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "DELETE", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_OPTION) {
            kh.delete(x.getMaKH());
            clearForm();
        }
        fillTable();
    }

    public void clearForm() {
        txtA_GhiChu.setText("");
        txtDiaCHi.setText("");
        txtEmail.setText("");
        txtMa.setText("");
        txtSDT.setText("");
        txtTen.setText("");
        lblAnh.setIcon(null);
        tblKH.clearSelection();
        //tenAnh = null;
    }

    private void validate(String txt, String ten) {

        if (txt.trim().equals("")) {
            JOptionPane.showMessageDialog(this, ten + " không được để trống");
            check = false;

        }
    }

    private void validateSDT_Email(String SDT, String email) {
        String regexStr = "^[0]{1}[0-9]{9}$";
        if (!SDT.trim().equals("")) {
            if (!SDT.matches(regexStr)) {
                JOptionPane.showMessageDialog(this, "Sai định dạng số điện thoại");
                check = false;
            }
        }
        String regexEmail = "^[A-Za-z0-9_.]{5,32}@([a-zA-Z0-9]{2,12})(.[a-zA-Z]{2,12})+$";
        if (!email.trim().equals("")) {
            if (!email.matches(regexEmail)) {
                JOptionPane.showMessageDialog(this, "Sai định dạng email");
                check = false;
            }
        }
    }

    public void getData() {//xem ls mua hàng khách
        if (tblKH.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng rồi xem lịch sử");
            return;
        }
        String maKH = txtMa.getText();
        String ten = txtTen.getText();
        LichSuMuaHangKhach ls = new LichSuMuaHangKhach(maKH, ten);
        ls.setVisible(true);
    }

    public void chonAnh() {
        JFileChooser fc = new JFileChooser("src/Images/KhachHang");
        FileFilter imgFilter = new FileNameExtensionFilter(
                "Image Only", ImageIO.getReaderFileSuffixes());//chỉ chọn ảnh
        fc.setFileFilter(imgFilter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            Image anh = icon.getImage();
            ImageIcon anhIcon = new ImageIcon(anh.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), 0));
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName());
        }
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
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtDiaCHi = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKH = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtA_GhiChu = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Khách Hàng");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, -1, -1));

        jLabel2.setText("Mã khách hàng");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, -1, -1));

        jLabel3.setText("Tên khách hàng");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, -1, -1));

        jLabel4.setText("Địa chỉ");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, -1, -1));

        jLabel5.setText("SDT");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, -1));
        add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 158, -1));
        add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, 158, -1));
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, 158, -1));
        add(txtDiaCHi, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 158, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 70, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add-image.png"))); // NOI18N
        jButton1.setText("Chọn ảnh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 230, -1, -1));

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên khách hàng", "Anh", "SDT", "Địa chỉ", "Email", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKH);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 460, 780, 250));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/users.png"))); // NOI18N
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add.png"))); // NOI18N
        jButton3.setText("Thêm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/user.png"))); // NOI18N
        jButton4.setText("Xóa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/refresh (1).png"))); // NOI18N
        jButton5.setText("Làm mới");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, -1, -1));

        jLabel6.setText("Email");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 150, -1, -1));

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, 158, -1));

        txtA_GhiChu.setColumns(20);
        txtA_GhiChu.setRows(5);
        jScrollPane2.setViewportView(txtA_GhiChu);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 220, 286, -1));

        jLabel7.setText("Ghi chú");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 210, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/medical-history.png"))); // NOI18N
        jButton6.setText("Lịch sử mua hàng");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, -1, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        JFileChooser fc =new JFileChooser("src/Images/KhachHang");
//        FileFilter imgFilter =new FileNameExtensionFilter(
//            "Image Only", ImageIO.getReaderFileSuffixes());//chỉ chọn ảnh
//        fc.setFileFilter(imgFilter);
//        int result =fc.showOpenDialog(this);
//        if(result==JFileChooser.APPROVE_OPTION){
//            File f =fc.getSelectedFile();
//            tenAnh =f.getName();
//            ImageIcon icon =new ImageIcon(f.getAbsolutePath());
//            Image anh =icon.getImage();
//            ImageIcon anhIcon =new ImageIcon(anh.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), 0));
//            this.lblAnh.setIcon(anhIcon);
//        }
        chonAnh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKHMouseClicked
        display();
    }//GEN-LAST:event_tblKHMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        addKH();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        delete();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        getData();
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTable tblKH;
    private javax.swing.JTextArea txtA_GhiChu;
    private javax.swing.JTextField txtDiaCHi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
