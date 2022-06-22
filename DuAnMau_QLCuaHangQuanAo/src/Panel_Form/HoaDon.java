/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel_Form;

import Models.HoaDonCT;
import Models.HoaDon_M;
import DAO.QLMatHang;
import DAO.QL_HoaDon;
import DAO.QL_KhachHang;
import DAO.QlNV;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Tiến Mạnh
 */
public class HoaDon extends javax.swing.JPanel {

    private String tenHD =null;
    static String kq=null ;
    QL_KhachHang kh= new QL_KhachHang();
    QlNV nv =new QlNV();
    QL_HoaDon hd =new QL_HoaDon();
    QLMatHang mh =new QLMatHang();
    DefaultTableModel model =new DefaultTableModel();
     List<Models.KhachHang> listKH ;
     List<Models.NhanVien> listNV;
     List<HoaDon_M> listHD ;
     List<Models.MatHang> listMH;
     List<HoaDonCT> listHDCT;
    public HoaDon() {
        initComponents();
        fillcbx();
        fillTable();
       
        setBackground(new Color(240,240,240));
        txtTimKiem.setBackground(new Color(0,0,0,0));
        
    }
    public void fillcbx(){
        listNV =nv.getAll_NV();
        listKH =kh.getAll_KH();
        listMH =mh.getAllMH();
       cbxMaKH.removeAllItems();
       cbxMANV.removeAllItems();
       cbxMaHang.removeAllItems();
       for (Models.MatHang x : listMH) {
          cbxMaHang.addItem(x.getMaMH());
        }
        for (Models.KhachHang x : listKH) {
            cbxMaKH.addItem(x.getMaKH());
        }
        for (Models.NhanVien x : listNV) {
            cbxMANV.addItem(x.getMaNV());
        }
        
        
        ngayBan();//set ngày bán hiện tại
        thongTinNV();//hiển thị thông tin KH,NV,HH
        thongTinKH();
        TTHD();
        
    }
    public void thongTinNV(){
        for (Models.NhanVien x : listNV) {
          if(cbxMANV.getSelectedItem().toString().equals(x.getMaNV())){
              txtTenNV.setText(x.getTenNV());
          }
        }
        
    }
    public void thongTinKH(){
        for (Models.KhachHang y : listKH) {
            if(cbxMaKH.getSelectedItem().toString().equals(y.getMaKH())){
              txtTenKH.setText(y.getTenKH());
              txtSDT.setText(y.getSDT());
          }
        }
    }
    public void TTHD(){
        for (Models.MatHang x : listMH) {
           if(cbxMaHang.getSelectedItem().toString().equals(x.getMaMH())){
               txtTenHang.setText(x.getTenMH());
               txtDonGia.setText(""+x.getGiaBan());
               txtSoLuong.setText("");
               txtGiamGia.setText("");
               txtThanhTien.setText("");
           }
        }
    }
    public void ngayBan(){
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.parse(""+ java.time.LocalDate.now()); // java.time.LocalDate.now() lấy ngày hiện tại
        txtNgayBan.setText(""+ java.time.LocalDate.now());
        } catch (Exception e) {
         JOptionPane.showMessageDialog(this, "Sai định dạng ngày");
            return;
        }
    }
    public void fillTable(){
        model =(DefaultTableModel) tblHDon.getModel();
        model.setRowCount(0);
        listHD =hd.getAllHD();
        for (HoaDon_M x :listHD) {
            model.addRow(new Object[]{x.getMaHD(),x.getMaNV(),x.getMaKH(),x.getNgayBan(),x.getHinhThucThanhToan(),new BigDecimal(x.getTongTien())});
        }
    }
    public void fillTableHDCT(){
       
        HoaDon_M xxx =listHD.get(tblHDon.getSelectedRow());
        model =(DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0);
        listHDCT=hd.getAllHDCT(xxx.getMaHD());
        for (HoaDonCT x : listHDCT) {
           model.addRow(new Object[]{x.getMAHD(),x.getMAMH(),x.getSoLuong(),x.getDONGIA(),x.getGIAMGIA(),x.getTHANHTIEN()});
        }

    }
    public void addHD(){
        String maHD =txtMaHD.getText();
        for (HoaDon_M hoaDon : listHD) {
            if(maHD.equals(hoaDon.getMaHD())){
                JOptionPane.showMessageDialog(this, "Hóa đơn này đã tồn tại");
                return;
            }
        }
        String maNV =cbxMANV.getSelectedItem().toString();
        String maKH =cbxMaKH.getSelectedItem().toString();
        Date ngay;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ngay =  sdf.parse(txtNgayBan.getText());

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày");
            return;
        }
        float tongTien =0;

        String HTTT =cbxHTTT.getSelectedItem().toString() ;
        HoaDon_M h=new HoaDon_M(maHD, maNV, maKH, HTTT, ngay, tongTien);
        hd.insertHD(h);
    }
    public void addHDCT(){
        
       String maHD =txtMaHD.getText();
       String maMH =cbxMaHang.getSelectedItem().toString();
       if(hd.checkHDCT(maHD, maMH)!=null){
           //System.out.println(""+hd.checkHDCT(maHD, maMH));
           if(hd.checkHDCT(maHD, maMH).equals("co")){
           JOptionPane.showMessageDialog(this, "Hóa đơn chi tiết này đã tồn tại");
           return;
       }

       }
        
                   

       if(txtSoLuong.getText().trim().equals("")){
           JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
           return;
       }
       try {
           int sl =Integer.parseInt(txtSoLuong.getText()); 
        } catch (NumberFormatException  e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên ");
            return;
        }
       
       int sl =Integer.parseInt(txtSoLuong.getText()); 
       String maMH1 =cbxMaHang.getSelectedItem().toString();
           if(sl>mh.getSL(maMH1)){
               JOptionPane.showMessageDialog(this, "Số lượng lớn hơn số hiện hàng hiện có");
               return;
           }
           
       float giamGia;
       if(!txtGiamGia.getText().trim().equals("")){
        try {
             giamGia =Float.parseFloat(txtGiamGia.getText());
     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giảm giá không đúng định dạng");
            return;
        }
       }
        float dongia =Float.parseFloat(txtDonGia.getText());
         
       if(txtGiamGia.getText().trim().equals("")){
           giamGia=0;
       }
       else{       
        giamGia =Float.parseFloat(txtGiamGia.getText()); 
       }
       float thanhTien =Float.parseFloat(txtThanhTien.getText());
       HoaDonCT ct =new HoaDonCT(maHD, maMH, sl, dongia, giamGia, thanhTien);
       
       hd.insertHDCT(ct);
         
       mh.updateSL(maMH, sl);
       tongTien();
       fillTableHDCT();
    }
    
    public void updateHD(){
        HoaDon_M x =listHD.get(tblHDon.getSelectedRow());
        String maHD =txtMaHD.getText();
        String maNV =cbxMANV.getSelectedItem().toString();
        String maKH =cbxMaKH.getSelectedItem().toString();
        Date ngay;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ngay =  sdf.parse(txtNgayBan.getText());

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày");
            return;
        }
        float tongTien =0;

        String HTTT =cbxHTTT.getSelectedItem().toString() ;
        
        x.setMaKH(maKH);
        x.setMaNV(maNV);
        //x.setNgayBan(ngay);
        x.setHinhThucThanhToan(HTTT);
        int choose =JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không?","cập nhật",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
        hd.updateHD(x);
        }
    }
    public void deleteHD(){
        HoaDon_M x =listHD.get(tblHDon.getSelectedRow());
         int choose =JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không (sẽ xóa cả hóa đơn chi tiết )?","DELETE",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
        hd.delete(x.getMaHD());
        fillTable();
        //fillTableHDCT();
        DefaultTableModel model =(DefaultTableModel)tblHDCT.getModel();
        model.setRowCount(0);
        }
    }
    public void display(){
        HoaDon_M x =listHD.get(tblHDon.getSelectedRow());
        txtMaHD.setText(x.getMaHD());
        setSelectedCBX(x.getMaNV(), cbxMANV);
        setSelectedCBX(x.getMaKH(), cbxMaKH);
        setSelectedCBX(x.getHinhThucThanhToan(), cbxHTTT);
        
        txtNgayBan.setText(""+x.getNgayBan());
        txtTongTien.setText(""+x.getTongTien());
        
//        String b =ChuyenSangChu(3000000+"");
//        lbTien.setText(b);
        }
    
       
    
    public void setSelectedCBX(String a,JComboBox cbx){//hiển thị cbx theo click
        for (int i = 0; i < cbx.getItemCount(); i++) {
            String b =(String) cbx.getItemAt(i);
            if(a.trim().equals(b)){
                cbx.setSelectedItem(b);
            }
        }
    }
    public void tinhTien(){
       float donGia = Float.parseFloat(txtDonGia.getText());
        int sl ;
        float thanhTien = 0 ;
        if(!txtSoLuong.getText().trim().equals("")){
       sl=Integer.parseInt(txtSoLuong.getText());
        }
       float giamGia =0;
       if(!txtGiamGia.getText().trim().equals("")){
       giamGia =Float.parseFloat(txtGiamGia.getText());
       }
       sl=Integer.parseInt(txtSoLuong.getText());
       thanhTien=donGia*sl-giamGia;
       txtThanhTien.setText(""+thanhTien);
       
    }
    public void tongTien(){
        try {
        HoaDon_M x =listHD.get(tblHDon.getSelectedRow());
        String maHD =x.getMaHD();
        float tongTien=0;
        for (int i = 0; i < tblHDCT.getRowCount(); i++) {
         tongTien = Float.parseFloat(tblHDCT.getValueAt(i, 5).toString()) + tongTien;
        }
        hd.updatetongTien(tongTien, maHD);
        } catch (Exception e) {
        }
    }
    public void displayCTHD(){
        HoaDonCT x =listHDCT.get(tblHDCT.getSelectedRow());
        setSelectedCBX(x.getMAMH(), cbxMaHang);
        txtSoLuong.setText(""+x.getSoLuong());
        txtDonGia.setText(""+x.getDONGIA());
        txtGiamGia.setText(""+x.getGIAMGIA());
        txtThanhTien.setText(""+x.getTHANHTIEN());
        
    }
    public void updateCTHD(){
        HoaDonCT x =listHDCT.get(tblHDCT.getSelectedRow());
        x.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        x.setGIAMGIA(Float.parseFloat(txtGiamGia.getText()));
        x.setTHANHTIEN(Float.parseFloat(txtThanhTien.getText()));
        x.setMAMH(cbxMaHang.getSelectedItem().toString());
        x.setMAHD(txtMaHD.getText());
        int choose=JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật?","Cập nhật",JOptionPane.YES_NO_OPTION);
        if(choose ==JOptionPane.YES_OPTION){
        hd.updateHDCT(x);
        fillTableHDCT();
        }
    }
    public void deleteHDCT(){
       HoaDonCT x =listHDCT.get(tblHDCT.getSelectedRow()); 
       int choose=JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?","Xóa",JOptionPane.YES_NO_OPTION);
        if(choose ==JOptionPane.YES_OPTION){
        hd.deleteHDCT(x.getMAHD(),x.getMAMH());
        fillTableHDCT();
            tongTien();
            fillTable();
        }
    }
   
      
    
      public static HashMap<String, String> hm_tien = new HashMap<String, String>() {
        {
            put("0", "không");
            put("1", "một");
            put("2", "hai");
            put("3", "ba");
            put("4", "bốn");
            put("5", "năm");
            put("6", "sáu");
            put("7", "bảy");
            put("8", "tám");
            put("9", "chín");
        }
    };
      public static HashMap<String, String> hm_hanh = new HashMap<String, String>() {
        {
            put("1", "đồng");
            put("2", "mươi");
            put("3", "trăm");
            put("4", "nghìn");
            put("5", "mươi");
            put("6", "trăm");
            put("7", "triệu");
            put("8", "mươi");
            put("9", "trăm");
            put("10", "tỷ");
            put("11", "mươi");
            put("12", "trăm");
            put("13", "nghìn");
            put("14", "mươi");
            put("15", "trăm");

        }
    };  
      public static String ChuyenSangChu(String x) {
         kq = "";
        x = x.replace(".", "");
        String arr_temp[] = x.split(",");
//        if (!NumberUtils.isNumber(arr_temp[0])) {
//            return "";
//        }
        String m = arr_temp[0];
        int dem = m.length();
        String dau = "";
        int flag10 = 1;
        while (!m.equals("")) {
            if (m.length() <= 3 && m.length() > 1 && Long.parseLong(m) == 0) {

            } else {
                dau = m.substring(0, 1);
                if (dem % 3 == 1 && m.startsWith("1") && flag10 == 0) {
                    kq += "mốt ";
                    flag10 = 0;
                } else if (dem % 3 == 2 && m.startsWith("1")) {
                    kq += "mười ";
                    flag10 = 1;
                } else if (dem % 3 == 2 && m.startsWith("0") && m.length() >= 2 && !m.substring(1, 2).equals("0")) {
                    //System.out.println("a  "+m.substring(1, 2));
                    kq += "lẻ ";
                    flag10 = 1;
                } else {
                    if (!m.startsWith("0")) {
                        kq += hm_tien.get(dau) + " ";
                        flag10 = 0;
                    }
                }
                if (dem%3!=1 &&m.startsWith("0") && m.length()>1) {
                } else {
                    if (dem % 3 == 2 && (m.startsWith("1") || m.startsWith("0"))) {//mười
                    } else {
                        kq += hm_hanh.get(dem + "") + " ";
                    }
                }
            }
            m = m.substring(1);
            dem = m.length();
        }
        kq=kq.substring(0, kq.length() - 1);
        return kq;
        
    }
 
      
      
      public void inHoaDon() throws Exception{
          XSSFWorkbook workbook =new XSSFWorkbook();
          XSSFSheet sheet =workbook.createSheet("Hóa đơn");
          
          XSSFRow row =null;
          Cell cell =null;
          
          row = sheet.createRow( 2);
           // row.setHeight((short) 500);
           XSSFFont font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.RED.getIndex()); // text color
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Cửa hàng quần áo Hẻm Store");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Hóa đơn bán hàng");
            
          row =sheet.createRow(3);
            cell =row.createCell(4, CellType.STRING);
            cell.setCellValue("Mã hóa đơn :"+txtMaHD.getText());
            row =sheet.createRow(4);
            cell =row.createCell(4, CellType.STRING);
            cell.setCellValue("Tên nhân viên:"+txtTenNV.getText());
            row =sheet.createRow(5);
            cell =row.createCell(4, CellType.STRING);
            cell.setCellValue("Tên khách hàng :"+txtTenKH.getText());
            row =sheet.createRow(6);
            cell =row.createCell(4, CellType.STRING);
            cell.setCellValue("Ngày bán :"+txtNgayBan.getText());
            row =sheet.createRow(7);
            cell =row.createCell(4, CellType.STRING);
            cell.setCellValue("Tổng tiền :"+txtTongTien.getText());
            
          row = sheet.createRow(9); 
          cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã hóa đơn");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Mã mặt hàng");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Số lượng");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Đơn giá");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Giảm giá");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Thành tiền");
            
            HoaDon_M xxx =listHD.get(tblHDon.getSelectedRow());
            listHDCT=hd.getAllHDCT(xxx.getMaHD());
            
            for (int i = 0; i < listHDCT.size(); i++) {
                HoaDonCT x =listHDCT.get(i);
               row = sheet.createRow(10+i);
               cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(i+1);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(x.getMAHD());
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(x.getMAMH());
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(x.getSoLuong());
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(x.getDONGIA());
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(x.getGIAMGIA());
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(x.getTHANHTIEN());
            tenHD ="HoaDon_"+x.getMAHD();
               
          }
            File file =new File("C:/Users/vuong/Desktop/"+tenHD+".xlsx");
            FileOutputStream fos =new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
      }
      public void findMaHD(){
          int checkFind =-1;
          for (int i = 0; i < listHD.size(); i++) {
              HoaDon_M hoadon =listHD.get(i);
               if(txtTimKiem.getText().trim().equals(hoadon.getMaHD())){
                   tblHDon.setRowSelectionInterval(i, i);
                   display();
                   fillTableHDCT();
                   checkFind =i;
               } 
               
          }
          if(checkFind==-1){
              JOptionPane.showMessageDialog(this, "Không có hóa đơn này");
              return;
          }

      }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        cbxMANV = new javax.swing.JComboBox<>();
        txtTenNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxMaKH = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtTenKH = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbxMaHang = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        txtTenHang = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNgayBan = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        cbxHTTT = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHDon = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        lbTienBangChu = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        separatorTK = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Hóa đơn");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 11, -1, -1));

        jLabel1.setText("Mã hóa đơn");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 106, -1, -1));

        jLabel2.setText("Mã nhân viên");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 166, -1, -1));

        jLabel4.setText("Tên nhân viên");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 219, -1, -1));
        add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 103, 143, -1));

        cbxMANV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMANVItemStateChanged(evt);
            }
        });
        add(cbxMANV, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 163, 143, -1));

        txtTenNV.setEditable(false);
        add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 216, 143, -1));

        jLabel5.setText("Mã khách hàng");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, -1, -1));

        jLabel6.setText("Tên  khách hàng");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        cbxMaKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMaKHItemStateChanged(evt);
            }
        });
        add(cbxMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(481, 103, 144, -1));

        jLabel8.setText("Số diện thoại");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, -1, -1));

        txtSDT.setEditable(false);
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(481, 216, 142, -1));

        jLabel9.setText("Thông tin chung");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 46, -1, -1));

        jLabel10.setText("Thông tin mặt hàng");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 444, -1, -1));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 424, 1379, -1));

        txtTenKH.setEditable(false);
        add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 163, 143, -1));

        jLabel11.setText("Mã hàng");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 485, -1, -1));

        cbxMaHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMaHangItemStateChanged(evt);
            }
        });
        add(cbxMaHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 482, 140, -1));

        jLabel12.setText("Đơn giá");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 567, -1, -1));

        jLabel13.setText("Tên hàng");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 485, -1, -1));

        txtDonGia.setEditable(false);
        add(txtDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 564, 142, -1));

        txtTenHang.setEditable(false);
        add(txtTenHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 482, 142, -1));

        jLabel14.setText("Ngày bán");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 269, -1, -1));

        txtNgayBan.setEditable(false);
        txtNgayBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayBanActionPerformed(evt);
            }
        });
        add(txtNgayBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 266, 143, -1));

        jLabel15.setText("Số lượng");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 567, -1, -1));

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });
        add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 564, 140, -1));

        jLabel16.setText("Giảm giá");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 643, -1, -1));

        txtGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiamGiaKeyReleased(evt);
            }
        });
        add(txtGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 640, 142, -1));

        jLabel17.setText("Thành Tiền");
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(287, 643, -1, -1));

        txtThanhTien.setEditable(false);
        add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 640, 142, -1));

        tblHDCT.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã mặt hàng", "Số lượng", "Đơn giá", "Giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHDCT);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(681, 482, 696, 208));

        jLabel18.setText("Hình thức thanh toán");
        add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, -1, -1));

        cbxHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Thẻ tín dụng" }));
        add(cbxHTTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 266, 147, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add-to-cart.png"))); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 367, 99, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/update.png"))); // NOI18N
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 367, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/remove-from-cart.png"))); // NOI18N
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 367, -1, -1));

        jLabel7.setText("Tổng tiền:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 361, -1, -1));

        txtTongTien.setEditable(false);
        add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 358, 147, -1));

        tblHDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày bán", "Hình thức thanh toán", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDon.setGridColor(new java.awt.Color(255, 255, 255));
        tblHDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHDon);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(676, 135, 695, 240));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/add-to-cart.png"))); // NOI18N
        jButton4.setText("Thêm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 720, -1, -1));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/update.png"))); // NOI18N
        btnSua.setText("Sửa ");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 720, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/remove-from-cart.png"))); // NOI18N
        jButton6.setText("Xóa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 720, -1, -1));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/floppy-disk.png"))); // NOI18N
        jButton7.setText("In hóa đơn");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 720, -1, -1));
        add(lbTienBangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(687, 404, -1, -1));

        jLabel19.setText("Bằng chữ:");
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 404, -1, -1));

        txtTimKiem.setForeground(new java.awt.Color(153, 153, 153));
        txtTimKiem.setText("Search...");
        txtTimKiem.setBorder(null);
        txtTimKiem.setOpaque(false);
        txtTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiemMouseClicked(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 163, -1));

        separatorTK.setForeground(new java.awt.Color(0, 153, 255));
        add(separatorTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(747, 91, 163, 10));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/searching.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 60, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMANVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMANVItemStateChanged
        thongTinNV();
    }//GEN-LAST:event_cbxMANVItemStateChanged

    private void cbxMaKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMaKHItemStateChanged
        thongTinKH();
    }//GEN-LAST:event_cbxMaKHItemStateChanged

    private void cbxMaHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMaHangItemStateChanged
        TTHD();
    }//GEN-LAST:event_cbxMaHangItemStateChanged

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
       try {
           int sl =Integer.parseInt(txtSoLuong.getText()); 
           String maMH =cbxMaHang.getSelectedItem().toString();
           if(sl>mh.getSL(maMH)){
               JOptionPane.showMessageDialog(this, "Số lượng lớn hơn số hiện hàng hiện có");
               return;
           }
        } catch (NumberFormatException  e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên ");
            return;
        }
        tinhTien();
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtGiamGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiamGiaKeyReleased
         
        try {
           float  giamGia =Float.parseFloat(txtGiamGia.getText());
     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giảm giá không đúng định dạng");
            return;
        }
        tinhTien();
    }//GEN-LAST:event_txtGiamGiaKeyReleased

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        displayCTHD();
        //System.out.println(""+tblHDCT.getSelectedRow());
    }//GEN-LAST:event_tblHDCTMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addHD();
        fillTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateHD();
        fillTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deleteHD();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblHDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDonMouseClicked
        display();
        fillTableHDCT();
    }//GEN-LAST:event_tblHDonMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
        addHDCT();
        tongTien();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillTable();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateCTHD();
        tongTien();
        //fillTableHDCT();
        fillTable();

    }//GEN-LAST:event_btnSuaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       
        try {
            deleteHDCT();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            inHoaDon();
            JOptionPane.showMessageDialog(this,"In hóa đơn thành công");
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        findMaHD();
        
    }//GEN-LAST:event_jLabel20MouseClicked

    private void txtTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseClicked
        txtTimKiem.setText("");
        txtTimKiem.setForeground(Color.blue);
    }//GEN-LAST:event_txtTimKiemMouseClicked

    private void txtNgayBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JComboBox<String> cbxHTTT;
    private javax.swing.JComboBox<String> cbxMANV;
    private javax.swing.JComboBox<String> cbxMaHang;
    private javax.swing.JComboBox<String> cbxMaKH;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbTienBangChu;
    private javax.swing.JSeparator separatorTK;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHDon;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtNgayBan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenHang;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
