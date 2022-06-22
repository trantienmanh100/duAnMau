/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author Tiến Mạnh
 */
public class XImage {
    public static void save(File src){
        File dst =new File("Icon",src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        try {
            Path from =Paths.get(src.getAbsolutePath());
            Path to= Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new  RuntimeException(e);
        }
    }
      public static ImageIcon read(String fileName){
    File path = new File("Icon", fileName);
    return new ImageIcon(path.getAbsolutePath());
 }
}
