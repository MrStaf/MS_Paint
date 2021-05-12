/*
 * The MIT License
 *
 * Copyright 2021 Mr_Staf.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package Main;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Color;

/**
 *
 * @author Mr_Staf
 */

public interface GraphicsShape {
    public abstract void drawIt(Graphics g);
    public abstract void drawExt(Graphics g);
}

class Shape implements GraphicsShape{
    public Integer x1, x2, y1, y2;
    public Integer bordersize;
    public Color c1;
    public boolean visible;

    Shape(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color c1) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.bordersize = bordersize;
        this.c1 = c1;
        this.visible = true;
    }
    public void drawIt(Graphics g) {
       
    }
    public void drawExt(Graphics g) {
        
    }
   
}

class Line extends Shape {
    Line(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color c1) {
        super(x1, x2, y1, y2, bordersize, c1);
    }
    @Override
    public void drawIt(Graphics g) {
        if (this.visible) {
            // corr
            int Points[] = Utils.getLineBorder(x1, x2, y1, y2, bordersize);

            // border size
            g.setColor(this.c1);
            if (this.bordersize == 1) {
                g.drawLine(x1, y1, x2, y2);
            } else {
                int []xPoints = {Points[0], Points[1], Points[2], Points[3]};
                int []yPoints = {Points[4], Points[5], Points[6], Points[7]};
                g.fillPolygon(xPoints, yPoints, 4);
            }
        }
    }
    @Override
    public void drawExt(Graphics g) {
        // corr
        int Points[] = Utils.getLineBorder(x1, x2, y1, y2, bordersize);
        int []xPoints = {Points[0], Points[1], Points[2], Points[3]};
        int []yPoints = {Points[4], Points[5], Points[6], Points[7]};
        int x1min = Utils.minList(xPoints);
        int x2max = Utils.maxList(xPoints);
        int y1min = Utils.minList(yPoints);
        int y2max = Utils.maxList(yPoints);
        // border size
        g.setColor(Color.black );
        g.drawRect(x1min, y1min, (x2max-x1min), (y2max - y1min));
    }
}

class Rect extends Shape {
    public Color c2;
    Rect(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color cb, Color cbg) {
        super(x1, x2, y1, y2, bordersize, cb);
        this.c2 = cbg;
    }
    @Override
    public void drawIt(Graphics g) {
        if (this.visible) {
            // corr
            if (x1 > x2) {
                int x3 = x1;
                x1 = x2;
                x2 = x3;
            }
            if (y1 > y2){
                int y3 = y1;
                y1 = y2;
                y2 = y3;
            }
            Integer add = 0;
            if (this.bordersize % 2 == 1) {
                add = 1;
            }
            Integer halfPositiveBorderSize = + ((this.bordersize/2)+add);

            // border size
            g.setColor(this.c1);
            if (this.bordersize == 1) {
                g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
            } else {
                g.fillRect(x1, y1, (x2 - x1), (y2 - y1));
            }
            // fill
            g.setColor(this.c2);
            g.fillRect(x1+halfPositiveBorderSize, y1+halfPositiveBorderSize, (x2 - x1)-this.bordersize, (y2 - y1)-this.bordersize);
        }
    }
}

class Oval extends Shape {
    public Color c2;
    Oval(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color cb, Color cbg) {
        super(x1, x2, y1, y2, bordersize, cb);
        this.c2 = cbg;
    }
    @Override
    public void drawIt(Graphics g) {
        if (this.visible){
            // cor
            g.setColor(c1);
            if (x1 > x2) {
                int x3 = x1;
                x1 = x2;
                x2 = x3;
            }
            if (y1 > y2){
                int y3 = y1;
                y1 = y2;
                y2 = y3;
            }
            // border size
            if (this.bordersize == 1) {
                g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
            } else {
                g.fillOval(x1, y1, (x2 - x1), (y2 - y1));
            }
            // fill
            g.setColor(this.c2);
            g.fillOval(x1+this.bordersize, y1+this.bordersize, (x2 - x1)-2*this.bordersize, (y2 - y1)-2*this.bordersize);
        }
    }
}

class DrawFree extends Shape {
    public Integer Xi[], Yi[];
    DrawFree(Integer x1, Integer x2, Integer y1, Integer y2, Integer bordersize, Color c1, Integer Xi[], Integer Yi[]) {
        super(x1, x2, y1, y2, bordersize, c1);
        this.Xi = Xi;
        this.Yi = Yi;
    }
    @Override
    public void drawIt(Graphics g) {
        g.setColor(this.c1);
        if (this.visible) {
            for (int i = 0; i < Xi.length; i++) {
                g.fillOval(Xi[i]-this.bordersize/2, Yi[i]-this.bordersize/2, this.bordersize, this.bordersize);
            }
        }
    }
    public void updateIt(Integer x, Integer y) {
        this.Xi = Utils.addLast(this.Xi.length, this.Xi, x);
        this.Yi = Utils.addLast(this.Yi.length, this.Yi, y);
    }
}

class GraphShape {
    LinkedList<Shape> Current;
    LinkedList<Shape> Deleted;
    Integer maxDeleted;
    @SuppressWarnings("unchecked")
    GraphShape() {
        this.Current = new LinkedList();
        this.Deleted = new LinkedList();
        this.maxDeleted = 20;
    }

    public void createRect(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color c1, Color c2) {
        this.Current.addLast(new Rect(x1, x2, y1, y2, bordersize, c1, c2));
    }
    public void createLine(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color c1) {
        this.Current.addLast(new Line(x1, x2, y1, y2, bordersize, c1));
    }
    public void createOval(Integer x1,Integer x2,Integer y1, Integer y2, Integer bordersize, Color c1, Color c2) {
        this.Current.addLast(new Oval(x1, x2, y1, y2, bordersize, c1, c2));
    }
    public void createDrawFree(Integer Xi[], Integer Yi[], Integer bordersize, Color cb) {
        this.Current.addLast(new DrawFree(0,0,0,0, bordersize, cb, Xi, Yi));
    }
    public void deleteLast() {
        if (this.Current.size() > 0) {
            this.Deleted.addLast(this.Current.removeLast());
        }
        if (this.Deleted.size() == this.maxDeleted) {
            this.Deleted.pop();
        }
    }
    public void deletePreview() {
        if (this.Current.size() > 0) {
            this.Current.removeLast();
        }
    }
    public void undo() {
        if (this.Deleted.size() > 0){
            this.Current.addLast(this.Deleted.removeLast());
        }
    }
    public Shape getLast() {
        return this.Current.getLast();
    }
    public LinkedList<Shape> getAll() {
        return this.Current;
    }
    public void drawAll(Graphics g) {
        Iterator<Shape> itr = this.Current.iterator();
        while(itr.hasNext()) {
            itr.next().drawIt(g);
        }
    }
}

class Utils {
    public static final int MAX_RGB_VALUE = 255;
    public static Color invert(Color c) {
        int a = c.getAlpha();
        int r = MAX_RGB_VALUE - c.getRed();
        int g = MAX_RGB_VALUE - c.getGreen();
        int b = MAX_RGB_VALUE - c.getBlue();

        // if the resulting color is to light (e.g. initial color is black, resulting color is white...)
        if ((r + g + b > 740) || (r + g + b < 20)) {
            // return a standard yellow
            return new Color(MAX_RGB_VALUE, MAX_RGB_VALUE, 40, a);
        } else {
            return new Color(r, g, b, a);
        }
    }
    public static int[] getLineBorder(int x1, int x2, int y1, int y2, int bordersize) {
        Integer nx = (y1-y2);
        Integer ny = (x2-x1);
        double distance = Math.sqrt(nx*nx+ny*ny);
        nx = (int)((nx / distance)*bordersize);
        ny = (int)((ny / distance)*bordersize);
        int x11 = x1+nx*(-1);
        int x22 = x1+nx;
        int x3 = x2+nx;
        int x4 = x2+nx*(-1);
        int y11 = y1+ny*(-1);
        int y22 = y1+ny;
        int y3 = y2+ny;
        int y4 = y2+ny*(-1);
        int ans[] = {x11, x22, x3, x4, y11, y22, y3, y4};
        return ans;
    }
    public static int maxList(int list[]) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.length; i++) {
            max = Math.max(max, list[i]);
        }
        return max;
    }
    public static int minList(int list[]) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < list.length; i++) {
            min = Math.min(min, list[i]);
        }
        return min;
    }   
    /**
     *
     * @param n length of the array
     * @param arr the old array
     * @param x the element to add at the end of the array
     * @return int[n+1] the new array with x
     */
    public static Integer[] addLast(Integer n, Integer arr[], Integer x)
    {
        Integer i;
  
        // create a new array of size n+1
        Integer newarr[] = new Integer[n + 1];
        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];
  
        newarr[n] = x;
  
        return newarr;
    }
}