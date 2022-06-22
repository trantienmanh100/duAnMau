/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form.TestMenu;

import Models.Model_Menu;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Tiến Mạnh
 */
public class Menu extends javax.swing.JPanel {
    
    public void addEventMenu(EventMenu event){
        this.event =event;
    }
    
    private int selectedIndex =-1;
    private Timer timer;
    private boolean toUp;//hiệu ứng khi đi lên
    private int menuYTaget;
    private int menuY;
    private int speed =2;
    private EventMenuCallBack callBack;
    private EventMenu event;
    public Menu() {
        initComponents();
        setOpaque(false);
        listMenu.setOpaque(false);
        listMenu.addEventSelectedMenu(new EventMenuSelected() {
            @Override
            public void menuSelected(int index, EventMenuCallBack callBack) {
                if(index!=selectedIndex){
                    Menu.this.callBack=callBack;
                    toUp =selectedIndex> index;
                    if(selectedIndex==-1){
                        speed=20;
                    }
                    else{
                        speed =3;
                        //speed=selectedIndex-1;
                        if(speed<0){
                            speed*=-1;
                        }
                    }
                    speed++;
                    selectedIndex=index;
                    menuYTaget =selectedIndex*50 +listMenu.getY();
                    if(!timer.isRunning()){
                        timer.start();
                    }
                }
            }
        });
        timer =new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(toUp){
                    if(menuY<=menuYTaget-5){
                       menuY =menuYTaget ;
                       repaint();
                       timer.stop();
                       callBack.call(selectedIndex);
                       if(event !=null){
                          event.menuIndexChange(selectedIndex);
                       }
                    }
                    else{
                        menuY -=speed;
                        repaint();
                    }
                }
                else{
                   if(menuY>=menuYTaget+5){
                       menuY =menuYTaget;
                       repaint();
                       timer.stop();
                       callBack.call(selectedIndex);
                        if(event !=null){
                          event.menuIndexChange(selectedIndex);
                       }
                   } 
                   else{
                      menuY +=speed;
                      repaint();
                   }
                }
            }
        });
        init();
    }
    private void init(){
        listMenu.addItem(new Model_Menu("1", "Danh mục sản phẩm", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("2", "Quản lý nhân viên", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("3", "Quản lý khách hàng", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("4", "Quản lý hàng hóa", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("5", "Quản lý hóa đơn", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("6", "Báo cáo thống kê", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("7", "Đăng xuất", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("8", "Quản lý tài khoản", Model_Menu.MenuType.MENU));
        //listMenu.addItem(new Model_Menu("9", "Thông tin", Model_Menu.MenuType.MENU));
       // listMenu.addItem(new Model_Menu("10", "Phản hồi", Model_Menu.MenuType.MENU));
//        listMenu.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        listMenu = new Form.TestMenu.ListMenu<>();

        panelMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("management application");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Clothing store ");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icon_Logo/Storey_Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovingLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovingLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(38, 38, 38))))
            .addGroup(panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMovingLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jLabel2)
                    .addContainerGap(158, Short.MAX_VALUE)))
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovingLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMovingLayout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(59, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //GradientPaint g = new GradientPaint(0, 0, Color.decode("#a8c0ff"), 0, getHeight(), Color.decode("#3f2b96"));
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#00d2ff"), 0, getHeight(), Color.decode("#928DAB"));
        //GradientPaint g = new GradientPaint(0, 0, Color.decode("#1c92d2"), 0, getHeight(), Color.decode("#f2fcfe"));
        super.paintComponent(grphcs); 
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        if(selectedIndex>=0){
            int menuX=10;
            int height =50;
            int width =getWidth();
            g2.setColor(new Color(240,240,240));
            g2.fillRoundRect(menuX, menuY,width, height, 35,35);
            Path2D.Float f =new Path2D.Float();
            f.moveTo(width-30, menuY);
            f.curveTo(width-10, menuY, width, menuY, width, menuY-30);
            f.lineTo(width, menuY+height+30);
            f.curveTo(width, menuY+height, width-10, menuY+height, width-30, menuY+height);
            g2.fill(f);
        }
        super.paintComponent(grphcs);
    }
  private int x;
  private int y;
  public void initMoving(JFrame frame){
      panelMoving.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent me) {
            x =me.getX();
            y =me.getY();
          }
        
      });
      panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent me) {
             frame.setLocation(me.getXOnScreen()-x, me.getYOnScreen()-y);
          }
        
      });
  }
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private Form.TestMenu.ListMenu<String> listMenu;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
}
