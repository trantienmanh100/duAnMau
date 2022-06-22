/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel_Form;

import DAO.QLMatHang;
import Models.DanhMucM;
import DAO.QL_DM;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tiến Mạnh
 */
public class DanhMuc extends javax.swing.JPanel {

    /**
     * Creates new form DanhMuc
     */
    DefaultTableModel model =new DefaultTableModel();
    QL_DM dm =new QL_DM();
    List<DanhMucM> listDM;
    List<Models.MatHang> listMH;
    QLMatHang mh =new QLMatHang();
    public DanhMuc() {
        initComponents();
        setBackground(new Color(240,240,240));
        fillTable();
        
    }
    public void fillTable(){
       listDM  =dm.getAll_DM();
          model.setRowCount(0);
        model =(DefaultTableModel)tblDanhMuc.getModel();
      
        for (DanhMucM x : listDM) {
            
            model.addRow(new Object[]{x.getMaDM(),x.getTenDM()});       
        }
    }
    public void dissplay(){
        DanhMucM d =listDM.get(tblDanhMuc.getSelectedRow());
        txtMaDM.setText(d.getMaDM());
        txtTenDM.setText(d.getTenDM());
    }
    public void addDM(){
       
        String ma =txtMaDM.getText();
        for (DanhMucM danhMuc : listDM) {
            if(ma.equals(danhMuc.getMaDM())){
                JOptionPane.showMessageDialog(this, "Mã không được trùng");
                return;
            }
        }
         if(ma.trim().equals("")){
            JOptionPane.showMessageDialog(this, "Mã danh mục không được để trống!");
            return;
        }
        String ten =txtTenDM.getText();
        if(ten.trim().equals("")){
            JOptionPane.showMessageDialog(this, "Tên danh mục không được để trống!");
            return;
        }
        DanhMucM d =new DanhMucM(ma, ten);
        dm.insert(d);
        fillTable();
        
    }
    public void update(){
        DanhMucM d =listDM.get(tblDanhMuc.getSelectedRow());
        if(txtMaDM.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Mã danh mục không được để trống!");
            return;
        }
        String ten =txtTenDM.getText();
        if(txtTenDM.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Tên danh mục không được để trống!");
            return;
        }
        d.setMaDM(txtMaDM.getText());
        d.setTenDM(txtTenDM.getText());
        int choose =JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không?","cập nhật",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
            dm.update(d);
        }
        fillTable();
    }
    public void delete(){
        listMH =mh.getAllMH();
        for (Models.MatHang x : listMH) {
            if(txtMaDM.getText().trim().equals(x.getMaDM())){
                JOptionPane.showMessageDialog(this, "Danh mục này đã có trong bảng mặt hàng nếu muốn hãy xóa mặt hàng trước!");
                return;
            }
        }
        if(tblDanhMuc.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this,"Chọn một dòng rồi xóa");
            return;
        }
        DanhMucM d =listDM.get(tblDanhMuc.getSelectedRow());
        int choose =JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không","Delete",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
           dm.delete(txtMaDM.getText());
        fillTable(); 
    }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaDM = new javax.swing.JTextField();
        txtTenDM = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhMuc = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Danh Mục");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Mã danh mục ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("Tên danh mục ");

        txtMaDM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtTenDM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add-button.png"))); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/update.png"))); // NOI18N
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/delete.png"))); // NOI18N
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã danh mục", "Tên Danh mục"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhMucMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhMuc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenDM, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(txtMaDM))
                        .addGap(534, 534, 534))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(651, 651, 651))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 498, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(61, 61, 61)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(491, 491, 491))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(471, 471, 471))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel1)
                .addGap(122, 122, 122)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(61, 61, 61)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addDM();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        delete();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhMucMouseClicked
        dissplay();
    }//GEN-LAST:event_tblDanhMucMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDanhMuc;
    private javax.swing.JTextField txtMaDM;
    private javax.swing.JTextField txtTenDM;
    // End of variables declaration//GEN-END:variables
}
