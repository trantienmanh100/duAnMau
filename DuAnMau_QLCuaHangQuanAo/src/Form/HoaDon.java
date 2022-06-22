/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Models.HoaDonCT;
import Models.HoaDon_M;
import Models.NhanVien;
import DAO.QLMatHang;
import DAO.QL_HoaDon;
import DAO.QL_KhachHang;
import DAO.QlNV;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRangeCopier;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author Tiến Mạnh
 */
public class HoaDon extends javax.swing.JFrame {

    /**
     * Creates new form HoaDon
     */
    private String tenHD =null;
    static String kq=null ;
    QL_KhachHang kh= new QL_KhachHang();
    QlNV nv =new QlNV();
    QL_HoaDon hd =new QL_HoaDon();
    QLMatHang mh =new QLMatHang();
    DefaultTableModel model =new DefaultTableModel();
     List<Models.KhachHang> listKH ;
     List<NhanVien> listNV;
     List<HoaDon_M> listHD ;
     List<Models.MatHang> listMH;
     List<HoaDonCT> listHDCT;
    public HoaDon() {
        initComponents();
        setLocationRelativeTo(null);
        fillcbx();
        fillTable();
        //TTHD();
        //fillTableHDCT();
        
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
        for (NhanVien x : listNV) {
            cbxMANV.addItem(x.getMaNV());
        }
        
        
        ngayBan();//set ngày bán hiện tại
        thongTinNV();//hiển thị thông tin KH,NV,HH
        thongTinKH();
        TTHD();
        
    }
    public void thongTinNV(){
        for (NhanVien x : listNV) {
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
            model.addRow(new Object[]{x.getMaHD(),x.getMaNV(),x.getMaKH(),x.getNgayBan(),x.getHinhThucThanhToan(),x.getTongTien()});
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
       int sl =Integer.parseInt(txtSoLuong.getText());
       float dongia =Float.parseFloat(txtDonGia.getText());
       float giamGia;
       if(txtGiamGia.getText().trim().equals("")){
           giamGia=0;
       }
       else{
        giamGia =Float.parseFloat(txtGiamGia.getText());
       }
       float thanhTien =Float.parseFloat(txtThanhTien.getText());
       HoaDonCT ct =new HoaDonCT(maHD, maMH, sl, dongia, giamGia, thanhTien);
       hd.insertHDCT(ct);
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
         int choose =JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?","DELETE",JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
        hd.delete(x.getMaHD());
        fillTable();
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
        int sl = 0;
        if(!txtSoLuong.getText().trim().equals("")){
       sl=Integer.parseInt(txtSoLuong.getText());
        }
       float giamGia =0;
       if(!txtGiamGia.getText().trim().equals("")){
       giamGia =Float.parseFloat(txtGiamGia.getText());
       }
       
       float thanhTien =donGia*sl-giamGia;
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
       int choose=JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?","Cập nhật",JOptionPane.YES_NO_OPTION);
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
      public static String currencyFormat(String curr) {
        try {
            double vaelue = Double.parseDouble(curr);
            String pattern = "###,###";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            String output = myFormatter.format(vaelue);
            return output;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }
      
      
      public void inHoaDon() throws Exception{
          XSSFWorkbook workbook =new XSSFWorkbook();
          XSSFSheet sheet =workbook.createSheet("Hóa đơn");
          
          XSSFRow row =null;
          Cell cell =null;
          
          row = sheet.createRow( 2);
           // row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Cửa hàng quần áo Hẻm Store");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Hóa đơn bán hàng");
            
            
          row = sheet.createRow(5); 
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
               row = sheet.createRow(6+i);
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

 
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jComboBox4 = new javax.swing.JComboBox<>();
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
        jButton8 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        lbTien = new javax.swing.JLabel();
        lbla = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Hóa đơn");

        jLabel1.setText("Mã hóa đơn");

        jLabel2.setText("Mã nhân viên");

        jLabel4.setText("Tên nhân viên");

        cbxMANV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMANVItemStateChanged(evt);
            }
        });

        txtTenNV.setEditable(false);

        jLabel5.setText("Mã khách hàng");

        jLabel6.setText("Tên  khách hàng");

        cbxMaKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMaKHItemStateChanged(evt);
            }
        });

        jLabel8.setText("Số diện thoại");

        txtSDT.setEditable(false);

        jLabel9.setText("Thông tin chung");

        jLabel10.setText("Thông tin mặt hàng");

        txtTenKH.setEditable(false);

        jLabel11.setText("Mã hàng");

        cbxMaHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMaHangItemStateChanged(evt);
            }
        });

        jLabel12.setText("Đơn giá");

        jLabel13.setText("Tên hàng");

        txtDonGia.setEditable(false);

        txtTenHang.setEditable(false);

        jLabel14.setText("Ngày bán");

        txtNgayBan.setEditable(false);

        jLabel15.setText("Số lượng");

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        jLabel16.setText("Giảm giá");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền", "Phần trăm %" }));

        txtGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiamGiaKeyReleased(evt);
            }
        });

        jLabel17.setText("Thành Tiền");

        txtThanhTien.setEditable(false);

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

        jLabel18.setText("Hình thức thanh toán");

        cbxHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Thẻ tín dụng" }));

        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setText("Tổng tiền:");

        txtTongTien.setEditable(false);

        tblHDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày bán", "Hình thức thanh toán", "Tổng tiền"
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

        jButton4.setText("Thêm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa ");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jButton6.setText("Xóa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("In hóa đơn");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("jButton8");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        lbla.setText("jLabel19");

        jLabel19.setText("Bằng chữ:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1379, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(26, 26, 26)
                                        .addComponent(cbxMaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(56, 56, 56))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenHang, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton7)
                                        .addGap(43, 43, 43)
                                        .addComponent(jButton8)
                                        .addGap(40, 40, 40)
                                        .addComponent(jButton5)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbla)
                                .addGap(619, 619, 619))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(111, 111, 111))))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaHD)
                            .addComponent(cbxMANV, 0, 143, Short.MAX_VALUE)
                            .addComponent(txtTenNV)
                            .addComponent(txtNgayBan))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxHTTT, 0, 147, Short.MAX_VALUE)
                                    .addComponent(txtTongTien)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(33, 33, 33)
                        .addComponent(jButton6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(628, 628, 628)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbTien))
                            .addComponent(jLabel3))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(cbxMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cbxMANV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtNgayBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18)
                                    .addComponent(cbxHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2)
                                    .addComponent(jButton3)
                                    .addComponent(jLabel7)
                                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(lbTien))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cbxMaHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jLabel17)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(btnSua)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton5)
                    .addComponent(lbla))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMANVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMANVItemStateChanged
        thongTinNV();
    }//GEN-LAST:event_cbxMANVItemStateChanged

    private void cbxMaKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMaKHItemStateChanged
        thongTinKH();
    }//GEN-LAST:event_cbxMaKHItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateHD();
        fillTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       deleteHD();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addHD();
        fillTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbxMaHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMaHangItemStateChanged
       TTHD();
    }//GEN-LAST:event_cbxMaHangItemStateChanged

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
       displayCTHD();
        //System.out.println(""+tblHDCT.getSelectedRow());
    }//GEN-LAST:event_tblHDCTMouseClicked

    private void tblHDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDonMouseClicked
       display();
       fillTableHDCT();
    }//GEN-LAST:event_tblHDonMouseClicked

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
       tinhTien();
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtGiamGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiamGiaKeyReleased
        tinhTien();
    }//GEN-LAST:event_txtGiamGiaKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        addHDCT();
        fillTableHDCT();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        tongTien();
        fillTableHDCT();
        fillTable();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
       updateCTHD();
       tongTien();
       //fillTableHDCT();
       fillTable();
       
    }//GEN-LAST:event_btnSuaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ChuyenSangChu(6200000+"");
        //currencyFormat(62000000+"");
        lbla.setText(currencyFormat(62000000+""));
        lbTien.setText(kq);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       deleteHDCT();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            inHoaDon();
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HoaDon().setVisible(true);
            }
        });
    }

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
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox4;
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
    private javax.swing.JLabel lbTien;
    private javax.swing.JLabel lbla;
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
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
