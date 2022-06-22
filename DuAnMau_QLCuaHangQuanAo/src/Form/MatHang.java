/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Models.DanhMucM;
import DAO.QLMatHang;
import DAO.QL_DM;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tiến Mạnh
 */
public class MatHang extends javax.swing.JFrame {

    /**
     * Creates new form MatHang
     */
    String SX =null;
    String tenAnh =null;
    String maDM ;
    QL_DM dm =new QL_DM();
    List<DanhMucM> listDM;
    DefaultTableModel model =new DefaultTableModel();
    QLMatHang mh =new QLMatHang();
     List<Models.MatHang> listMH;
    public MatHang() {
        initComponents();
        setLocationRelativeTo(null);
        displayDM();
        tenDM();
        fillTable(maDM,SX);
    }
    public void displayDM(){
        listDM =dm.getAll_DM();
        this.cbxMaDanhMuc.removeAllItems();
        for (DanhMucM x : listDM) {
            cbxMaDanhMuc.addItem(x.getMaDM());
        }
    }
    public void tenDM(){
        for (DanhMucM x : listDM) {
            if(x.getMaDM().equals(cbxMaDanhMuc.getSelectedItem().toString())){
                txtTenDM.setText(x.getTenDM());
            }
        }
    }
    public void SX(){  //sắp xếp theo cbxSX
        if(cbxSX.getSelectedIndex()==0){
            SX ="and 1=1";
        }
        else if(cbxSX.getSelectedIndex()==1){
            SX ="ORDER BY SL DESC";
        }
        else if(cbxSX.getSelectedIndex()==2){
             SX ="ORDER BY SL ASC";
        }
        else if(cbxSX.getSelectedIndex()==3){
             SX ="ORDER BY GIABAN DESC";
        }
        else{
            SX ="ORDER BY GIABAN ASC";
        }
        //System.out.println(SX);
    }
    public void fillTable(String maDM,String SX){
        model =(DefaultTableModel)tblMH.getModel();
        model.setRowCount(0);
        SX();
       List<Models.MatHang> listMH = mh.getAllMH(cbxMaDanhMuc.getSelectedItem().toString(),SX);
        for (Models.MatHang x : listMH) {
            model.addRow(new Object[]{x.getMaMH(),x.getTenMH(),x.getMaVach(),x.getSoLuong(),x.getGiaNhap()
            ,x.getGiaBan(),x.getDonVi(),x.getMaDM(),x.getAnh(),x.getGhiChu()});
        }
        
        
    }
    public void display(){
        SX();
        listMH = mh.getAllMH(cbxMaDanhMuc.getSelectedItem().toString(),SX);
        Models.MatHang x =listMH.get(tblMH.getSelectedRow());
        txtDonVi.setText(x.getDonVi());
        txtGiaBan.setText(""+x.getGiaBan());
        txtGiaNhap.setText(x.getGiaNhap()+"");
        txtMaMH.setText(x.getMaMH());
        txtMaVach.setText(x.getMaVach());
        txtSL.setText(x.getSoLuong()+"");
      
        txtTenMH.setText(x.getTenMH());
        txtA_GhiChu.setText(x.getGhiChu());
        hinh();
    }
    public void hinh(){
         int row =tblMH.getSelectedRow();
         String hinh =tblMH.getValueAt(row, 8).toString();
         ImageIcon icon =new ImageIcon("src/Images/KhachHang/"+hinh);
         Image anh =icon.getImage();
         ImageIcon anhIcon =new ImageIcon(anh.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), 0));
            this.lblAnh.setIcon(anhIcon);
            tenAnh =hinh;
     }
    public void addMH(){
           String maMH =txtMaMH.getText();
    String tenMH =txtTenMH.getText();
    String donVi =txtDonVi.getText();
    int soLuong=Integer.parseInt(txtSL.getText());
    String maDM =cbxMaDanhMuc.getSelectedItem().toString();
    float giaNhap =Float.parseFloat(txtGiaNhap.getText());
    float giaBan=Float.parseFloat(txtGiaBan.getText());
    String anh;
    
          if(tenAnh==null){
           anh ="Không Ảnh";
        }
          else{
         anh=tenAnh;
        } 
    String ghiChu =txtA_GhiChu.getText();
    String maVach=txtMaVach.getText();
        Models.MatHang m =new Models.MatHang(maMH, tenMH, donVi, soLuong, maDM, giaNhap, giaBan, anh, ghiChu, maVach);
        mh.insert(m);
        SX();
        fillTable(maDM,SX);
    }
    public void update(){
       // listMH =mh.getAllMH(maDM);
        Models.MatHang x =listMH.get(tblMH.getSelectedRow());
        String maMH =txtMaMH.getText();
    String tenMH =txtTenMH.getText();
    String donVi =txtDonVi.getText();
    int soLuong=Integer.parseInt(txtSL.getText());
    String maDM =cbxMaDanhMuc.getSelectedItem().toString();
    float giaNhap =Float.parseFloat(txtGiaNhap.getText());
    float giaBan=Float.parseFloat(txtGiaBan.getText());
    String anh;
    
          if(tenAnh==null){
           anh ="Không Ảnh";
        }
          else{
         anh=tenAnh;
        } 
    String ghiChu =txtA_GhiChu.getText();
    String maVach=txtMaVach.getText();
    x.setAnh(anh); x.setDonVi(donVi); x.setGhiChu(ghiChu); x.setGiaBan(giaBan);
    x.setGiaNhap(giaNhap); x.setMaDM(maDM); x.setMaMH(maMH); x.setMaVach(maVach);
    x.setSoLuong(soLuong); x.setTenMH(tenMH);
     int choose =JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không?","cập nhật",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
            mh.update(x);
        }
        SX();
        fillTable(maDM,SX);
    }
    public void delete(){
        //listMH =mh.getAllMH(maDM);
        Models.MatHang x =listMH.get(tblMH.getSelectedRow());
        int choose =JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?","DELETE",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
            mh.delete(x.getMaMH());
            clearForm();
        }
        SX();
        fillTable(maDM,SX);
    }
    public void clearForm(){
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
               tenAnh=null;
              
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Mã danh mục");

        jLabel2.setText("Mã mặt hàng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Mặt hàng");

        jLabel4.setText("Tên mặt hàng");

        jLabel5.setText("Số lượng");

        jLabel6.setText("Tên Danh mục");

        jLabel7.setText("Đơn vị ");

        jLabel8.setText("Giá nhập");

        jLabel9.setText("Giá bán");

        txtTenDM.setEditable(false);

        cbxMaDanhMuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMaDanhMucItemStateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton1.setText("Chọn ảnh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtA_GhiChu.setColumns(20);
        txtA_GhiChu.setRows(5);
        jScrollPane1.setViewportView(txtA_GhiChu);

        jLabel10.setText("Ghi chú");

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

        jLabel11.setText("Mã vạch");

        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Xóa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Làm mới");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel12.setText("Sắp xếp");

        cbxSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định", "Số lượng giảm dần", "Số lượng tăng dần", "Giá bán cao->thấp ", "Giá bán thấp đến cao" }));
        cbxSX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSXItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(391, 391, 391)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSL, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(txtTenMH, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(txtMaMH, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(cbxMaDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(32, 32, 32)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jButton5)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel12)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel11)
                        .addGap(30, 30, 30)
                        .addComponent(txtMaVach, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxSX, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGap(124, 124, 124)
                                        .addComponent(jLabel10)))
                                .addGap(87, 87, 87))))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jButton2)
                        .addGap(43, 43, 43)
                        .addComponent(jButton3)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(txtTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(txtMaMH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenMH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jButton1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtMaVach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jLabel12)
                    .addComponent(cbxSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMaDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMaDanhMucItemStateChanged
        tenDM();
        SX();
        fillTable(maDM,SX);
    }//GEN-LAST:event_cbxMaDanhMucItemStateChanged

    private void tblMHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMHMouseClicked
        display();
    }//GEN-LAST:event_tblMHMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       addMH();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        update();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        delete();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cbxSXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSXItemStateChanged
        SX();
        fillTable(maDM, SX);
    }//GEN-LAST:event_cbxSXItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(MatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MatHang().setVisible(true);
            }
        });
    }

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
