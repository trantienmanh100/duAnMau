package Form;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiến Mạnh
 */
public class Loaddd {
    public static void main(String[] args) throws InterruptedException {
        Loadiiiiing load =new Loadiiiiing();
        
        for (int i = 0; i <= 100; i++) {
            
            Thread.sleep(40);
            load.setVisible(true);
          load.lbLoad.setText("Loading.."+i+"%");
          //load.ProgressBar.setValue(i);
          
          if(i==100){
              Login login =new Login();
              login.setVisible(true);
              
              load.dispose();
          }
        }
        
    }
}
