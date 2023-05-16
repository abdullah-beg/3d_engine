package com.github.abdullahbeg.engine3d.object;

import com.github.abdullahbeg.engine3d.mesh.Vertex;
import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.texture.ImageLoader;
import com.github.abdullahbeg.engine3d.texture.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class OBJLoader {

    private static Scanner fileReader;
    private static ArrayList<Triangle> triangles;

    private OBJLoader() {}

    public static ArrayList<Triangle> loadObject(String filePath, Texture texture) {

        triangles = new ArrayList<>();
        loadFilePath(filePath);
        readFile(texture);
        return triangles;

    }

    private static void loadFilePath(String filePath) {

        File object = new File(filePath);

        try {
            fileReader = new Scanner(object);
            System.out.println("Object loaded successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("Error loading object! Using default object.");
            fileReader = new Scanner(ImageLoader.class.getClassLoader().getResourceAsStream("\\com\\github\\abdullahbeg\\engine3d\\object\\teapot.obj"));

        }

    }

    private static void readFile(Texture texture) {

        ArrayList<Vertex> verticies = new ArrayList<>();
        ArrayList<Vertex> uv = new ArrayList<>();

        boolean hasTextureCoords = false;

        String holder;
        String[] input;

        if (fileReader.toString().contains("vt")) {
            hasTextureCoords = true;

        }

        while (fileReader.hasNext()) {

            holder = fileReader.nextLine();

            if (holder.startsWith("v ")) {
                input = holder.split(" ", 4);
                verticies.add(new Vertex(Float.parseFloat(input[1]), Float.parseFloat(input[2]), Float.parseFloat(input[3])));

            } else if (holder.startsWith("vt ")) {
                hasTextureCoords = true;

                input = holder.split(" ", 3);
                uv.add(new Vertex(0,0,0, Float.parseFloat(input[1]), Float.parseFloat(input[2])));

            } else if (holder.startsWith("f ")) {
                input = holder.split(" ", 4);

                if (hasTextureCoords) {
                    String[] p1 = input[1].split("/");
                    String[] p2 = input[2].split("/");
                    String[] p3 = input[3].split("/");

                    Vertex v1 = verticies.get(Integer.parseInt(p1[0]) - 1);
                    Vertex v2 = verticies.get(Integer.parseInt(p2[0]) - 1);
                    Vertex v3 = verticies.get(Integer.parseInt(p3[0]) - 1);

                    triangles.add(new Triangle(
                            new Vertex(v1.getX(), v1.getY(), v1.getZ(), uv.get(Integer.parseInt(p1[1]) - 1).getU(), uv.get(Integer.parseInt(p1[1]) - 1).getV()),
                            new Vertex(v2.getX(), v2.getY(), v2.getZ(), uv.get(Integer.parseInt(p2[1]) - 1).getU(), uv.get(Integer.parseInt(p2[1]) - 1).getV()),
                            new Vertex(v3.getX(), v3.getY(), v3.getZ(), uv.get(Integer.parseInt(p3[1]) - 1).getU(), uv.get(Integer.parseInt(p3[1]) - 1).getV()),
                            texture
                    ));

                }  else {
                    String[] p1 = input[1].split("//");
                    String[] p2 = input[2].split("//");
                    String[] p3 = input[3].split("//");

                    Vertex v1 = verticies.get(Integer.parseInt(p1[0]) - 1);
                    Vertex v2 = verticies.get(Integer.parseInt(p2[0]) - 1);
                    Vertex v3 = verticies.get(Integer.parseInt(p3[0]) - 1);

                    triangles.add(new Triangle(
                            v1,
                            v2,
                            v3,
                            texture
                    ));

                }

            }

        }

    }

}