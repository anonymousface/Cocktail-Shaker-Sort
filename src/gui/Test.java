package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import javax.swing.JFrame;


public class Test extends Canvas {
	
	private static JFrame f;
	private static final long serialVersionUID = 1L;
	private static Test m = new Test();
	private static int sleepTime = 1000;
	private static int nums[];
	
	public Test() {
		
	}
	
	public void init() {
		setBackground(Color.yellow);
    }
	
    public void paint(Graphics g) {
    	g.setColor(Color.black);
    	for(int i = 0; i < nums.length; i++) {
    		g.fillRect(10 + i*15, 10, 10, map(nums[i], 0, 1000, 10, 200));
    	}
	    
    }
    
    public static void main(String[] args, int x, int y, int width, int height, int n) {  
		nums = new int[n];
		initNums();
		
    	f = new JFrame();
		f.setResizable(false);
		f.setBackground(Color.WHITE);
		f.getContentPane().add(m);
		f.setBounds(x, y, (nums.length*15 + 30), height);
		//f.setLayout(null);
		f.setVisible(true);
		
    }
    
    private static void initNums() {
    	Random rnd = new Random();
    	for(int i = 0; i < nums.length; i++) {
    		nums[i] = rnd.nextInt(1000);
    	}
    }
    
    private static int map(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
		return (int)((Double.parseDouble(Integer.toString(value)) - Double.parseDouble(Integer.toString(fromLow)))/(Double.parseDouble(Integer.toString(fromHigh)) - Double.parseDouble(Integer.toString(fromLow))) * (Double.parseDouble(Integer.toString(toHigh)) - Double.parseDouble(Integer.toString(toLow))) + Double.parseDouble(Integer.toString(toLow)));
	}
    
    public static void refresh() {
    	//m.repaint();
    	m.repaint();
    }
    
    public static void startSort() {
    	SortAndAnimate a = new SortAndAnimate();
    	a.start();
    }
    
    static class SortAndAnimate implements Runnable {
		private Thread t;
				
		SortAndAnimate() {
			System.out.println("Creating sortAndAnimate");
		}
		
		public void run() {
			System.out.println("Running sortAndAnimate");
			boolean swapped;
    		for (int i = 0; i < nums.length / 2; i++) {
    			swapped = false;
    			int j;
    			for (j = i; j < nums.length - i - 1; j++) {
    				if (nums[j] < nums[j + 1]) {
    					try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    					refresh();
    					int tmp = nums[j];
    					nums[j] = nums[j + 1];
    					nums[j + 1] = tmp;
    					swapped = true;
    					try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    					refresh();
    				}
    			}
    			for (j = nums.length - 2 - i; j > i; j--) {
    				if (nums[j] > nums[j - 1]) {
    					try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    					refresh();
    					int tmp = nums[j];
    					nums[j] = nums[j - 1];
    					nums[j - 1] = tmp;
    					swapped = true;
    					try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    					refresh();
    				}
    			}
    			if (!swapped) break;
    		}
		}
		
		public void start () {
			System.out.println("Starting sortAndAnimate");
			if (t == null) {
				t = new Thread (this);
				t.start ();
			}
		}
	}
    

}

