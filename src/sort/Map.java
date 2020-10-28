package sort;

public class Map {

	public static void main(String[] args) {
		System.out.println(map(120, 0, 255, 0, 1024));
	}
	
	private static int map(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
		return (int)((Double.parseDouble(Integer.toString(value)) - Double.parseDouble(Integer.toString(fromLow)))/(Double.parseDouble(Integer.toString(fromHigh)) - Double.parseDouble(Integer.toString(fromLow))) * (Double.parseDouble(Integer.toString(toHigh)) - Double.parseDouble(Integer.toString(toLow))) + Double.parseDouble(Integer.toString(toLow)));
	}
}
