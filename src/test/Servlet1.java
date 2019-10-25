package test;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class Servlet1 extends javax.servlet.http.HttpServlet {

    public static Random random = new Random();

    @Resource(name="about")
    String name2;
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    public static Color getRandomColor() {

        Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(25));

        return c;
    }

    public static Color getReversedColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();

        BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Color color = getRandomColor();
        g.setColor(color);
        g.fillRect(0, 0, 200, 50);

        String username = getInitParameter("username");



        try {

            Context ctx = new InitialContext();

            Context envCtx = (Context)ctx.lookup("java:comp/env");

            username = (String)envCtx.lookup("about");
        }catch(Exception e) {
            System.out.println(e.toString());
       }


        g.setColor(getReversedColor(color));
        g.drawString(name2 + "123", 15, 15);


        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image);
        out.flush();
    }
}
