package com.github.abdullahbeg.engine3d.texture;

import java.awt.image.BufferedImage;

public class Texture {

    private int textureWidth;
    private int textureHeight;

    private int[][] colorArray;

    public Texture() { this(""); }

    public Texture(String filePath) {

        BufferedImage texture = ImageLoader.loadFilePath(filePath);
        
        if (texture != null) {
            textureWidth = texture.getWidth();
            textureHeight = texture.getHeight();

            generateArray(texture);
            texture.flush();
        }

    }

    private void generateArray(BufferedImage texture) {

        colorArray = new int[textureHeight][textureWidth];

        for (int y = 0; y < textureHeight; y++) {
            for (int x = 0; x < textureWidth; x++) {

                colorArray[y][x] = texture.getRGB(x,y);

            }

        }

    }

    public int getTextureWidth() { return textureWidth; }
    public int getTextureHeight() { return textureHeight; }

    public int getTexturePixel(double x, double y) {

        if (x >= textureWidth) { x = textureWidth - 1; }
        if (y >= textureHeight) { y = textureHeight - 1; }

        if (x < 0) { x = 0; }
        if (y < 0) { y = 0; }

        return colorArray[(int)y][(int)x];

    }

}
