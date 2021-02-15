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
import javax.swing.WindowConstants;

public class ArrayVisualizer extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JFrame frame = new JFrame();
	private static SortAndAnimate a = new SortAndAnimate();
	private static PlaySound sound;
	private static int nums[];
	private static int temp, temp2, temp3 = -1;
	
	//parameters
	private static int x, y, n;
	private static int sleepTime = 50;
	private static int max;
	private static int pL = 10;
	private static int pH = 300;
	private static int mode = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //loop drawing all the elements of the array in a bar graph manner
    	for(int i = 0; i < nums.length; i++) {
    		//determines when to draw some of the bars in red
    		if((i==temp || i==temp2) && a.getState()!=Thread.State.NEW && a.getState()!=Thread.State.TERMINATED && temp!=-1) {
    	        g.setColor(Color.red);
    		}
    		else if(temp3 != -1 && i==temp3) {
    			g.setColor(Color.green);
    		}
    		else {
    	        g.setColor(Color.black);
    		}
    		//draws the actual bars
    		g.fillRect(10 + i*15, 10, 10, map(nums[i], 0, max, pL, pH));
    	}
    }

    //this code could be in the main but it is not 
    private static void createAndShowGui(int xVal, int yVal) {
		x = xVal;
		y = yVal;
				
		initNums();
        frame.add(new ArrayVisualizer());
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("Visualizer");
        frame.setBounds(x, y, 100, 300);
        frame.setVisible(true);

    }

    public static void main(String[] args, int x, int y) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(x, y);
            }
        });

    }

    //creates and starts the sorting algorithm in a separate thread
    public static void startSort() {
    	a = new SortAndAnimate();
    	a.start();
    }
    
    //method to change the speed outside of this class #encapsulationBRUH
    public static void changeSpeed(int speed) {
    	sleepTime = speed;
    	a.interrupt();
    }
    
    //method to change the mode outside of this class #encapsulationBRUH
    public static void changeMode(int modeVar) {
    	mode = modeVar;
    }
    
    //method to change the mode outside of this class #encapsulationBRUH
    public static void changeN(int nVar) {
    	n = nVar;
    	initNums();
    }
    
    //randomizes the array
    public static void initNums() {
    	Random rnd = new Random();

        nums = new int[n];
        frame.setBounds(x, y, (nums.length*15 + 30), pH+60);
    	//System.out.println("mode is " + mode);
    	switch(mode) {
    	//random nums from 1 to 1000
    	case 0:
        	max = 1000;
        	for(int i = 0; i < nums.length; i++) {
        		nums[i] = rnd.nextInt(max);
        	}
        	refresh();
    		break;

    	//1 to n in ascending order
    	case 1:
        	max = nums.length;
        	for(int i = 0; i < nums.length; i++) {
        		nums[i] = i+1;
        	}
        	refresh();
    		break;
    	//randomly ordered from 1 to n
    	case 2:
    		max = nums.length;
        	for(int i = 0; i < nums.length; i++) {
        		int r = rnd.nextInt(nums.length-1);
        		nums[r] = i+1;
        		nums[i] = r+1;
        	}
        	refresh();
    		break;
    	}
    	refresh();
    }
    
    
    //converts a number from one numerical range to another
    private static int map(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
		return (int)((Double.parseDouble(Integer.toString(value)) - Double.parseDouble(Integer.toString(fromLow)))/(Double.parseDouble(Integer.toString(fromHigh)) - Double.parseDouble(Integer.toString(fromLow))) * (Double.parseDouble(Integer.toString(toHigh)) - Double.parseDouble(Integer.toString(toLow))) + Double.parseDouble(Integer.toString(toLow)));
	}
    
    //refreshes the canvas or smth
    private static void refresh() {
    	frame.repaint();
    }
   
    //this is the lovely sorting class
    private static class SortAndAnimate implements Runnable {
    	
		private Thread t;
				
		SortAndAnimate() {
			//System.out.println("Creating sortAndAnimate");
		}
		
		public void run() {
			//System.out.println("Running sortAndAnimate");
			boolean swapped;
    		for (int i = 0; i < nums.length / 2; i++) {
    			swapped = false;
    			int j;
    			for (j = i; j < nums.length - i - 1; j++) {
    				if (nums[j] < nums[j + 1]) {
    					
    					int tmp = nums[j];
    					nums[j] = nums[j + 1];
    					nums[j + 1] = tmp;
    					
    					
    					
    					
    					sound = new PlaySound(map(nums[j], 0, max, 200, 4000));
    					//System.out.print("og val=" + nums[j] + "   mapped val="+map(nums[j], 0, 1000, 200, 4000));
    					sound.start();
    					temp = j;
    					temp2 = j + 1;
    					refresh();
    					try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    					
    					
    					
    					
    					swapped = true;
    				}
    					
    			}
    			for (j = nums.length - 2 - i; j > i; j--) {
    				if (nums[j] > nums[j - 1]) {
    							
    					int tmp = nums[j];
    					nums[j] = nums[j - 1];
    					nums[j - 1] = tmp;
    					
    					
    					
    					
    					sound = new PlaySound(map(nums[j], 0, max, 200, 4000));
    					//System.out.print("og val=" + nums[j+1] + "   mapped val="+map(nums[j+1], 0, 1000, 200, 4000));
    					sound.start();
    					temp = j;
    					temp2 = j - 1;
    					refresh();
    					try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    					
    					
    					
    					
    					
    					swapped = true;
    				}
    			}
    			if (!swapped) break;
    		}
    		temp = -1;
    		oneMorePass();
		}
		
		//one last pass to visualize that the array is sorted
		private static void oneMorePass() {
	    	for(int i = nums.length-1; i >= 0; i--) {
	    		try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		temp3 = i;
				refresh();
				
				sound = new PlaySound(map(nums[i], 0, max, 200, 2500));
				//System.out.print("og val=" + nums[i] + "   mapped val="+map(nums[i], 0, 1000, 200, 4000));
				sound.start();
	    	}
	    	temp3 = -1;
	    	refresh();
	    }
		
		public void interrupt() {
		}
		
		public void start () {
			//System.out.println("Starting sortAndAnimate");
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
    
    //one more lovely class that plays the frequency we kindly provide
    private static class PlaySound implements Runnable {
    	
		private Thread t;
		private int frequency;

		protected static final int SAMPLE_RATE = 16 * 1024;
		private static AudioFormat af = null;
		private static SourceDataLine line;
				
		PlaySound(int freq) {
			this.frequency = freq;
			//System.out.println("   received frequency=" + frequency);
			//System.out.println("Creating PlaySound");
		}
		
		public void run() {
			//System.out.println("Running PlaySound");
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
	        if(sleepTime >= 50) {
		        byte [] toneBuffer = createSineWaveBuffer(frequency, 50);
		        line.write(toneBuffer, 0, toneBuffer.length);
	            line.drain();
	            line.close();
	            if(sleepTime-50 > 0) {
		            try {
						Thread.sleep(sleepTime-50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
	            }
	        }
	        else {
		        byte [] toneBuffer = createSineWaveBuffer(frequency, sleepTime);
		        line.write(toneBuffer, 0, toneBuffer.length);
	            line.drain();
	            line.close();
	        }
           
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
			//System.out.println("Starting PlaySound");
			if (t == null) {
				t = new Thread (this);
				t.start ();
			}
			af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
		}
	}
    
}