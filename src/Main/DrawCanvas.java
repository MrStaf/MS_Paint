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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mr_Staf
 */
public class DrawCanvas extends javax.swing.JPanel {
    public GraphShape List;
    public Color cbg;
    public Color cb;
    public int bordersize;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean actZoomer;
    private boolean dragger;
    public boolean actDragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private Point StartDrawPoint;
    private Point endPoint;
    private boolean preview;
    public String typeOfTools;
    private Integer Xi[];
    private Integer Yi[];
    
    /**
     * Creates new form DrawCanvas
     */
    public DrawCanvas() {
        this.typeOfTools = "rect";
        this.bordersize = 1;
        actDragger = false;
        this.List = new GraphShape();
        this.startPoint = new Point(0,0);
        this.StartDrawPoint = new Point(0,0);
        this.endPoint = new Point(0,0);
        initComponents();
    }
    
    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g2D.transform(at);
            zoomer = false;
        }

        if (dragger && actDragger) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFactor, zoomFactor);
            g2D.transform(at);

            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragger = false;
            }
        }
        this.List.drawAll(g2D);
        //this.List.getLast().drawExt(g);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        setPreferredSize(new java.awt.Dimension(690, 668));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point curPoint = evt.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;
        if (!actDragger) {
            int xEndRel = (int)(MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX());
            int yEndRel = (int)(MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY());
            if (!this.preview) {
                this.List.deletePreview();
            } else {
                this.preview = false;
            }
            switch (typeOfTools) {
                case "Line":
                    this.List.createLine(StartDrawPoint.x, xEndRel, StartDrawPoint.y, yEndRel, bordersize, this.cb);
                    break;
                case "Rectangle":
                    this.List.createRect(StartDrawPoint.x, xEndRel, StartDrawPoint.y, yEndRel, bordersize, this.cb, this.cbg);
                    break;
                case "Oval":
                    this.List.createOval(StartDrawPoint.x, xEndRel, StartDrawPoint.y, yEndRel, bordersize, this.cb, this.cbg);
                    break;
                case "Brush":
                    Xi = Utils.addLast(Xi.length, Xi, xEndRel);
                    Yi = Utils.addLast(Yi.length, Yi, yEndRel);
                    if (Xi.length > 0) {
                        this.List.createDrawFree(Xi, Yi, bordersize, cb);
                    }
                    break;
            }
            repaint();
        }
        if (actDragger) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            dragger = true;
            repaint();
        }
    }//GEN-LAST:event_formMouseDragged

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        zoomer = true;

        //Zoom in
        if (evt.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //Zoom out
        if (evt.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }//GEN-LAST:event_formMouseWheelMoved

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
        
        this.preview = true;
        Point p = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(p, this);
        if (this.typeOfTools.equals("Brush")) {
            Xi = Utils.addLast(0, Xi, p.x);
            Yi = Utils.addLast(0, Yi, p.y);
        }
        StartDrawPoint = p;
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (!actDragger) {
            int xEndRel = (int)(MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX());
            int yEndRel = (int)(MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY());
            this.List.deletePreview();
            switch(this.typeOfTools) {
                case "Line":
                    this.List.createLine(StartDrawPoint.x, xEndRel, StartDrawPoint.y, yEndRel, bordersize, cb);
                    break;
                case "Rectangle":
                    this.List.createRect(StartDrawPoint.x, xEndRel, StartDrawPoint.y, yEndRel, bordersize, this.cb, this.cbg);
                    break;
                case "Oval":
                    this.List.createOval(StartDrawPoint.x, xEndRel, StartDrawPoint.y, yEndRel, bordersize, this.cb, this.cbg);
                    break;
                case "Brush":
                    Xi = Utils.addLast(Xi.length, Xi, xEndRel);
                    Yi = Utils.addLast(Yi.length, Yi, yEndRel);
                    if (Xi.length > 0) {
                        this.List.createDrawFree(Xi, Yi, bordersize, this.cb);
                    }
                    Xi = null;
                    Yi = null;
                    break;
            }
        }
        
        released = true;
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        repaint();
    }//GEN-LAST:event_formMouseReleased
    
    public void deleteLast() {
        this.List.deleteLast();
        repaint();
    }
    public void undo() {
        this.List.undo();
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
