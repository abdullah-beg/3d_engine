package com.github.abdullahbeg.engine3d.texture;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    
    private ImageLoader() {}

    public static BufferedImage loadFilePath(String filePath) {

        BufferedImage image;

        try {
            image = ImageIO.read(new File(filePath));
            System.out.println("Texture loaded successfully!");
            return image;

        } catch (IOException e) {
            System.out.println("File not found! Using default texture.");

            try {
                image = ImageIO.read(ImageLoader.class.getClassLoader().getResourceAsStream("\\com\\github\\abdullahbeg\\engine3d\\texture\\default.png"));
                // image = ImageIO.read(ImageLoader.class.getClassLoader().getResourceAsStream("\\com\\github\\abdullahbeg\\engine3d\\texture\\logo.png"));
                return image;

            } catch (IOException x) {
                System.out.println("An error has occured.");
                System.out.println("Printing stack trace:");
                x.printStackTrace();

            }

        }

        return null;

    }

}
