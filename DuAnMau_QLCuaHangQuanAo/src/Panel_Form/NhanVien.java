/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel_Form;

import DAO.QlNV;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class NhanVien extends javax.swing.JPanel {

     DefaultTableModel model =new DefaultTableModel();
   
    QlNV ql =new QlNV() ;
    List<Models.NhanVien> listNV;
    String tenAnh =null;
    boolean check =true;
    Date ngaySinh =null;
    public NhanVien() {
        initComponents();
         setBackground(new Color(240,240,240));
        fillTable();
    }
    public void fillTable(){
       listNV  =ql.getAll_NV();
          model.setRowCount(0);
        model =(DefaultTableModel)tblNhanVien.getModel();
      
        for (Models.NhanVien x : listNV) {
            
            model.addRow(new Object[]{x.getMaNV(),x.getTenNV(),x.getSDT(),x.getNgaySinh(),x.getGhiChu(),x.getAnh()});       
        }
    }
    public void hinh(){
         int row =tblNhanVien.getSelectedRow();
         String hinh =tblNhanVien.getValueAt(row, 5).toString();
         ImageIcon icon =new ImageIcon("src/Images/NhanVien/"+hinh);
         Image anh =icon.getImage();
         ImageIcon anhIcon =new ImageIcon(anh.getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), 0));
            this.lbAnh.setIcon(anhIcon);
            tenAnh =hinh;
     }
    public void display(){
        Models.NhanVien nv =listNV.get(tblNhanVien.getSelectedRow());
        txtMANV.setText(nv.getMaNV());
        txtNgaySinh.setText(""+nv.getNgaySinh());
        txtA_GhiChu.setText(nv.getGhiChu());
        txtSDT.setText(nv.getSDT());
        txtTen.setText(nv.getTenNV());
        hinh();
    }
    public void addNV(){
        String maNV=txtMANV.getText();
        String ten =txtTen.getText();
        //Date ngaySinh =null;
        for (Models.NhanVien nv : listNV) {
           if(nv.getMaNV().equals(maNV)){
               JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại");
               return;
           } 
           if(nv.getSDT().equals(txtSDT.getText())){
               JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại");
               return;
           } 
        }
        
        validateTrong();
        if(check==false){
            return;
        }
        check =true;
        
         String anh;
          if(tenAnh==null){
           anh ="No Image";
        }
          else{
         anh=tenAnh;
        }
        String sdt =txtSDT.getText();
        String ghiChu =txtA_GhiChu.getText();
        
       Models.NhanVien nv = new Models.NhanVien(maNV, ten, ngaySinh, sdt, ghiChu, anh);
       ql.insert(nv);
        
    }
    public void update(){
        Models.NhanVien x =listNV.get(tblNhanVien.getSelectedRow());
        String maNV=txtMANV.getText();
        String ten =txtTen.getText();
        validateTrong();
        if(check==false){
            return;
        }
        check =true;
      
         String anh;
          if(tenAnh==null){
           anh ="No Image";
        }
          else{
         anh=tenAnh;
        }
        String sdt =txtSDT.getText();
        String ghiChu =txtA_GhiChu.getText();
        x.setMaNV(maNV);
        x.setAnh(anh);
        x.setGhiChu(ghiChu);
        x.setNgaySinh(ngaySinh);
        x.setSDT(sdt);
        x.setTenNV(ten);
        int choose =JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không?","cập nhật",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
            ql.update(x);
        }
        fillTable();
    }
    public void remove(){
        Models.NhanVien nv =listNV.get(tblNhanVien.getSelectedRow());
        String maNv =nv.getMaNV();
        int choose =JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn khóa không","Delete",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
           ql.khoaNV(maNv);
        fillTable(); 
        clearForm();
        }
        
    }
    public void clearForm(){
        txtA_GhiChu.setText("");
        txtMANV.setText("");
        txtNgaySinh.setText("");
        txtSDT.setText("");
        txtTen.setText("");
        tblNhanVien.clearSelection();
        lbAnh.setIcon(null);
        tenAnh=null;
    }
    private void validateTrong(){
        if(txtMANV.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Mã không được bỏ trống!");
            check =false;
        }
        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            ngaySinh =sdf.parse(txtNgaySinh.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày");
            check =false;
        }
        String regexStr = "^[0]{1}[0-9]{9}$";
          if(!txtSDT.getText().trim().equals("")){
          if(!txtSDT.getText().matches(regexStr)) {
           JOptionPane.showMessageDialog(this, "Sai định dạng số điện thoại");
           check =false;
          }
          }
         if(txtTen.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được bỏ trống!");
            check =false;
        }
        
       

    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbAnh = new javax.swing.JLabel();
        btnAnh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMANV = new javax.swing.JTextField();
        txtNgaySinh = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtA_GhiChu = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 204, 255));
        jLabel1.setText("Nhân Viên");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/picture.png"))); // NOI18N
        btnAnh.setText("Chọn ảnh");
        btnAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnhActionPerformed(evt);
            }
        });

        jLabel2.setText("Mã nhân viên:");

        jLabel3.setText("Tên nhân viên:");

        jLabel4.setText("SDT:");

        jLabel5.setText("Ngày sinh:");

        jLabel6.setText("Ghi chú:");

        txtA_GhiChu.setColumns(20);
        txtA_GhiChu.setRows(5);
        jScrollPane1.setViewportView(txtA_GhiChu);

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên", "SDT", "Ngày Sinh", "Ghi chú ", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNhanVien);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add.png"))); // NOI18N
        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/users.png"))); // NOI18N
        jButton3.setText("Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/user.png"))); // NOI18N
        jButton4.setText("Khóa nhân viên");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/refresh (1).png"))); // NOI18N
        jButton5.setText("Làm mới");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(424, 424, 424)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 363, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(74, 74, 74)
                                .addComponent(txtMANV, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(67, 67, 67)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(205, 205, 205)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAnh))
                        .addGap(471, 471, 471))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                        .addGap(379, 379, 379))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(649, 649, 649))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMANV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnh)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton4)
                                    .addComponent(jButton5)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnhActionPerformed
        JFileChooser fc =new JFileChooser("src/Images/NhanVien");
        FileFilter imgFilter =new FileNameExtensionFilter(
            "Image Only", ImageIO.getReaderFileSuffixes());//chỉ chọn ảnh
        fc.setFileFilter(imgFilter);
        int result =fc.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            File f =fc.getSelectedFile();
            tenAnh =f.getName();
            ImageIcon icon =new ImageIcon(f.getAbsolutePath());
            Image anh =icon.getImage();
            ImageIcon anhIcon =new ImageIcon(anh.getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), 0));
            this.lbAnh.setIcon(anhIcon);
        }

    }//GEN-LAST:event_btnAnhActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        display();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        addNV();
        fillTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        update();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        remove();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnh;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextArea txtA_GhiChu;
    private javax.swing.JTextField txtMANV;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
