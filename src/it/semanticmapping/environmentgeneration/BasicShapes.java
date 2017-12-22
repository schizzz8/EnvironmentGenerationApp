package it.semanticmapping.environmentgeneration;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;


class Surface extends JPanel {

	public Surface(int w_, int h_, int x_size_, int y_size_, int n_,double[][] shapes_) {
		w = w_;
		h = h_;
		x_size = x_size_;
		y_size = y_size_;
		
		n = n_;
		shapes = shapes_;
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(new Color(150, 150, 150));

		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		int x_step = w/x_size;
		int y_step = h/y_size;

		for(int i=0; i <= x_size; i++) {
			g2d.drawLine(i*x_step, 0, i*x_step, h);
		}
		for(int j=0; j <= y_size; j++) {
			g2d.drawLine(0, j*y_step, w, j*y_step);
		}

		for(int i=0; i<n-2; i++) {
			double t_x=shapes[i][0];
			double t_y=shapes[i][1];
			double t_z=shapes[i][2];
			
			g2d.setPaint(Color.red);
			g2d.fill(new Ellipse2D.Double(t_x*x_step ,t_y*y_step ,x_step,y_step));
			g2d.setPaint(Color.blue);
			g2d.drawLine((int) (t_x*x_step+x_step/2), 
					(int) (t_y*y_step+y_step/2), 
					(int)(t_x*x_step+x_step/2 + x_step/2*Math.cos(t_z)),
					(int)(t_y*y_step+y_step/2 + y_step/2*Math.sin(t_z)));			
		}
		for(int i=n-2; i < n; i++) {
			double t_x=shapes[i][0];
			double t_y=shapes[i][1];
			double t_z=shapes[i][2];
			
			g2d.setPaint(Color.green);
			g2d.fillRect((int) (t_x*x_step), 
					(int) (t_y*y_step), x_step, y_step);
			
		}
	} 

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}  
	
	private int w;
	private int h;

	private int x_size;
	private int y_size;
	
	private int n;
	private double[][] shapes;
}

public class BasicShapes extends JFrame {

	public BasicShapes(int w_, int h_, int x_size_, int y_size_, int n_, double[][] shapes_) {
		w = w_;
		h = h_;
		x_size = x_size_;
		y_size = y_size_;
		n = n_;
		shapes = shapes_;
		initUI();
	}

	private void initUI() {
		add(new Surface(w,h,x_size,y_size,n,shapes));

		setTitle("Environment Generation");
		setSize(w, h+25);
		setLocationRelativeTo(null);        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private int w;
	private int h;
	private int x_size;
	private int y_size;
	private int n;
	private double[][] shapes;
}