package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BST extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame = new JFrame();
	private static SortAndAnimate a = new SortAndAnimate();
	private static int nums[];
	private static int temp;
	
	//parameters
	private static int sleepTime = 50;
	private static int pL = 10;
	private static int pH = 300;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    	for(int i = 0; i < nums.length; i++) {
    		if((i==temp || i==temp+1) && a.getState()!=Thread.State.NEW && a.getState()!=Thread.State.TERMINATED) {
    	        g.setColor(Color.red);
    		}
    		else {
    	        g.setColor(Color.black);
    		}
    		g.fillRect(10 + i*15, 10, 10, map(nums[i], 0, 1000, pL, pH));
    	}
    }

    private static void createAndShowGui(int x, int y, int n) {
        nums = new int[n];
		initNums();
		
        frame.add(new BST());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(x, y, (nums.length*15 + 30), pH+60);

    }

    public static void main(String[] args, int x, int y, int n) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(x, y, n);
            }
        });
    }
    
    public static void initNums() {
    	Random rnd = new Random();
    	for(int i = 0; i < nums.length; i++) {
    		nums[i] = rnd.nextInt(1000);
    	}
    	refresh();
    }
    
    private static int map(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
		return (int)((Double.parseDouble(Integer.toString(value)) - Double.parseDouble(Integer.toString(fromLow)))/(Double.parseDouble(Integer.toString(fromHigh)) - Double.parseDouble(Integer.toString(fromLow))) * (Double.parseDouble(Integer.toString(toHigh)) - Double.parseDouble(Integer.toString(toLow))) + Double.parseDouble(Integer.toString(toLow)));
	}
    
    private static void refresh() {
    	frame.repaint();
    }
    
    public static void startSort() {
    	a = new SortAndAnimate();
    	a.start();
    }
    
    private static class SortAndAnimate implements Runnable {
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
    					temp = j;
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
    					temp = j;
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
		
		public Thread.State getState() {
	        if(this.t!= null) return t.getState();
	        return Thread.State.NEW;
	    }
	}
}