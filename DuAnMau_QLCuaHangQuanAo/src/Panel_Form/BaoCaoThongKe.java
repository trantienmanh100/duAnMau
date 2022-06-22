/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel_Form;

import Models.DanhMucM;
import DAO.QL_BCTT;
import DAO.QL_DM;
import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Tiến Mạnh
 */
public class BaoCaoThongKe extends javax.swing.JPanel {

    /**
     * Creates new form BaoCaoThongKe
     */
    String where;
    QL_BCTT bctt = new QL_BCTT();
    QL_DM dm = new QL_DM();
    List<DanhMucM> listDM;

    public BaoCaoThongKe() {
        initComponents();
        showPieChart();
        showTS();
        showBieuDoCot();
        
        setBackground(new Color(240, 240, 240));
        jPanel1.setBackground(new Color(240, 240, 240));
    }

    public void showPieChart() {

        //Tạo dữ liệu
        DefaultPieDataset barDataset = new DefaultPieDataset();
        barDataset.setValue("Tiền mặt", bctt.HTTT("N'Tiền mặt'"));
        barDataset.setValue("Thẻ tín dụng", bctt.HTTT("N'Thẻ tín dụng'"));
        //barDataset.setValue( "Không Mua " , new Double( 111 ) ); 

        //tạo biểu đồ
        JFreeChart piechart = ChartFactory.createPieChart("Hình thức thanh toán", barDataset, false, true, false);//explain

        PiePlot piePlot = (PiePlot) piechart.getPlot();

       
        piePlot.setBackgroundPaint(Color.white);

        //tạo panel để hiển thị biểu đồ
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelBarChart.removeAll();
        panelBarChart.add(barChartPanel, BorderLayout.CENTER);
        panelBarChart.validate();
    }

    public void showBieuDoCot() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        listDM = dm.getAll_DM();

        for (int i = 0; i < listDM.size(); i++) {
            DanhMucM danhMuc = listDM.get(i);
            String maDM = danhMuc.getMaDM();
            dataset.setValue(duLieucot(maDM), "Số sản phẩm", maDM);
        }
        JFreeChart chart = ChartFactory.createBarChart("Biểu đồ số sản phẩm theo danh mục", "Tên danh mục", "Số sản phẩm",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        //tạo biểu đồ 
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.white);

        //create render object to change the moficy the line properties like color
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, clr3);

        //tạo panel để hiển thị biểu đồ
        ChartPanel barchartPanel = new ChartPanel(chart);
        panelBDCot.removeAll();
        panelBDCot.add(barchartPanel, BorderLayout.CENTER);
        panelBDCot.validate();

    }

    private int duLieucot(String tenDM) { //lấy số lượng theo danh mục
        String query = "	SELECT SUM(CT.SL) FROM CHITIETHD CT INNER JOIN  MATHANG MH ON CT.MAMH = MH.MAMH \n"
                + "	INNER JOIN DANHMUC DM ON MH.MADANHMUC =DM.MADANHMUC \n"
                + "	WHERE DM.MADANHMUC =N'" + tenDM + "'";
        int SLSP = (int) bctt.thongSo(query);
        return SLSP;
    }


    public void showTS() {
        loc();
        String query = "SELECT SUM(TONGTIEN)  FROM HD ";
        double dt = bctt.thongSo(query + where);
        BigDecimal doanhThhu = new BigDecimal(bctt.thongSo(query + where));
        lbDoanhTHu.setText("" + doanhThhu);

        query = "DECLARE @TT float \n"
                + "	SELECT @TT = Sum(tongtien) from hd \n" + where
                + "	SELECT @TT -SUM(CT.SL *MH.GIANHAP)  FROM CHITIETHD CT INNER JOIN MATHANG MH ON MH.MAMH =CT.MAMH"
                + " INNER JOIN HD ON HD.MAHD =CT.MAHD";
        double ln = bctt.thongSo(query + where);
        BigDecimal loiNhuan = new BigDecimal(bctt.thongSo(query + where));
        lblLoiNHuan.setText("" + loiNhuan);
        query = "SELECT COUNT(MAHD) FROM HD ";

        lblSHD.setText("" + bctt.thongSo(query + where));
        query = "SELECT SUM(GIAMGIA) FROM CHITIETHD CT INNER JOIN HD ON HD.MAHD =CT.MAHD";
        BigDecimal giamGia = new BigDecimal(bctt.thongSo(query + where));
        lblGiamGia.setText("" + giamGia);
        BigDecimal tienVon = new BigDecimal(dt - ln);

        lblTienVon.setText("" + tienVon);
    }

    public void loc() {
        int chon = cbxChoose.getSelectedIndex();
        switch (chon) {
            case -1:
                break;
            case 0:
                where = " where DateDiff(dd,HD.THOIGIAN,getdate())=0";
                break;
            case 1:
                where = " where DateDiff(dd,HD.THOIGIAN,getdate())=1";
                break;
            case 2:
                where = " where DateDiff(wk,HD.THOIGIAN,getdate())=0";
                break;
            case 3:
                where = " where DateDiff(wk,HD.THOIGIAN,getdate()) = 1";
                break;
            case 4:
                where = " where DateDiff(mm,HD.THOIGIAN,getdate()) = 0";
                break;
            case 5:
                where = " where DateDiff(mm,HD.THOIGIAN,getdate()) = 1";
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelBDCot = new javax.swing.JPanel();
        panelBarChart = new javax.swing.JPanel();
        cbxChoose = new javax.swing.JComboBox<>();
        lblLoiNHuan = new javax.swing.JLabel();
        lbDoanhTHu = new javax.swing.JLabel();
        lblTienVon = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        lblSHD = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMau1 = new javax.swing.JLabel();
        lblMau3 = new javax.swing.JLabel();
        lblMau2 = new javax.swing.JLabel();
        lblMau4 = new javax.swing.JLabel();
        lblMau5 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBDCot.setBackground(new java.awt.Color(255, 255, 255));
        panelBDCot.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panelBDCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 600, 309));

        panelBarChart.setBackground(new java.awt.Color(255, 255, 255));
        panelBarChart.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panelBarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 430, 420, 309));

        cbxChoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Hôm qua", "Tuần hiện tại", "Tuần trước", "Tháng này", "Tháng trước" }));
        cbxChoose.setToolTipText("");
        cbxChoose.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxChooseItemStateChanged(evt);
            }
        });
        jPanel1.add(cbxChoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 200, 169, -1));

        lblLoiNHuan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLoiNHuan.setForeground(new java.awt.Color(255, 255, 255));
        lblLoiNHuan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoiNHuan.setText("jLabel2");
        jPanel1.add(lblLoiNHuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 180, -1));

        lbDoanhTHu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbDoanhTHu.setForeground(new java.awt.Color(255, 255, 255));
        lbDoanhTHu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDoanhTHu.setText("jLabel2");
        jPanel1.add(lbDoanhTHu, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 180, -1));

        lblTienVon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTienVon.setForeground(new java.awt.Color(255, 255, 255));
        lblTienVon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTienVon.setText("1");
        jPanel1.add(lblTienVon, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 180, -1));

        lblGiamGia.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGiamGia.setForeground(new java.awt.Color(255, 255, 255));
        lblGiamGia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGiamGia.setText("1");
        jPanel1.add(lblGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 180, -1));

        lblSHD.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSHD.setForeground(new java.awt.Color(255, 255, 255));
        lblSHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSHD.setText("1");
        jPanel1.add(lblSHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 180, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Số hóa Đơn ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 180, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Doanh Thu ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Lợi nhuận ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tiền vốn");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 60, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Giảm giá ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 60, -1));

        lblMau1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AnhMau2.png"))); // NOI18N
        lblMau1.setText("jLabel3");
        jPanel1.add(lblMau1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 190, 110));

        lblMau3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Layer 1.png"))); // NOI18N
        lblMau3.setText("jLabel3");
        jPanel1.add(lblMau3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 190, 110));

        lblMau2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AnhMau1.png"))); // NOI18N
        lblMau2.setText("jLabel3");
        jPanel1.add(lblMau2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 190, 110));

        lblMau4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Kyoto.png"))); // NOI18N
        lblMau4.setText("jLabel3");
        jPanel1.add(lblMau4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 190, 110));

        lblMau5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Instagram.png"))); // NOI18N
        lblMau5.setText("jLabel3");
        jPanel1.add(lblMau5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 190, 110));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1430, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private void cbxChooseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxChooseItemStateChanged
        loc();
        showTS();
    }//GEN-LAST:event_cbxChooseItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxChoose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbDoanhTHu;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblLoiNHuan;
    private javax.swing.JLabel lblMau1;
    private javax.swing.JLabel lblMau2;
    private javax.swing.JLabel lblMau3;
    private javax.swing.JLabel lblMau4;
    private javax.swing.JLabel lblMau5;
    private javax.swing.JLabel lblSHD;
    private javax.swing.JLabel lblTienVon;
    private javax.swing.JPanel panelBDCot;
    private javax.swing.JPanel panelBarChart;
    // End of variables declaration//GEN-END:variables
}
