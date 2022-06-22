/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 *
 * @author Tiến Mạnh
 */
public class Model_Menu {
    private String icon;
     private String name;
     MenuType type ;
     
     
     public static enum MenuType{
         MENU ,EMPTY
     }
     public Icon toIcon() {
        return new ImageIcon(getClass().getResource("/Images/Icon_Logo/" + icon + ".png"));
    }

    public Icon toIconSelected() {
        return new ImageIcon(getClass().getResource("/Images/Icon_Logo/" + icon + "_selected.png"));
    } 

    public Model_Menu(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public Model_Menu() {
    }
    

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }
     
}
