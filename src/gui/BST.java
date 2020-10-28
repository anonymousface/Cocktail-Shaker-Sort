package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
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
	private static PlaySound sound;
	private static int nums[];
	private static int temp, temp2 = -1;
	
	//parameters
	private static int sleepTime = 50;
	private static int pL = 10;
	private static int pH = 300;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    	for(int i = 0; i < nums.length; i++) {
    		if((i==temp || i==temp+1) && a.getState()!=Thread.State.NEW && a.getState()!=Thread.State.TERMINATED && temp!=-1) {
    	        g.setColor(Color.red);
    		}
    		else if(temp2 != -1 && i==temp2) {
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
    
    public static void changeSpeed(int speed) {
    	sleepTime = speed;
    }
    
    private static class SortAndAnimate implements Runnable {
		private Thread t;
				
		SortAndAnimate() {
			System.out.println("Creating sortAndAnimate");
		}
		
		private static void playFrequency(int f) {
			sound = new PlaySound(f);
			sound.start();
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
    					playFrequency(nums[j]);
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
    					playFrequency(nums[j]);
    					refresh();
    					int tmp = nums[j];
    					sound = new PlaySound(nums[j]);
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
    		temp = -1;
    		oneMorePass();
		}
		
		private static void oneMorePass() {
	    	for(int i = nums.length-1; i >= 0; i--) {
	    		try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		temp2 = i;
				refresh();
				playFrequency(nums[i]);
	    	}
	    	temp2 = -1;
	    	refresh();
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
    
    private static class PlaySound implements Runnable {
		private Thread t;
		private int frequency;

		protected static final int SAMPLE_RATE = 16 * 1024;
		private final static AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
		private static SourceDataLine line;
				
		PlaySound(int freq) {
			this.frequency = freq;
			System.out.println("Creating PlaySound");
		}
		
		public void run() {
			System.out.println("Running PlaySound");
	        try {
				line = AudioSystem.getSourceDataLine(af);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	        try {
				line.open(af, SAMPLE_RATE);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	        line.start();
	        byte [] toneBuffer = createSineWaveBuffer(map(frequency, 0, 1000, 200, 2000), sleepTime);
	        line.write(toneBuffer, 0, toneBuffer.length);
            line.drain();
            line.close();
		}
	    
	    public static byte[] createSineWaveBuffer(double freq, int ms) {
	        int samples = (int)((ms * SAMPLE_RATE) / 1000);
	        byte[] output = new byte[samples];
	            //
	        double period = (double)SAMPLE_RATE / freq;
	        for (int i = 0; i < output.length; i++) {
	            double angle = 2.0 * Math.PI * i / period;
	            output[i] = (byte)(Math.sin(angle) * 127f);  }

	        return output;
	    }
		
		public void start () {
			System.out.println("Starting PlaySound");
			if (t == null) {
				t = new Thread (this);
				t.start ();
			}
		}
	}
    
}