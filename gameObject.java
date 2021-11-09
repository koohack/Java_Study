public class gameObject {
	private char type;
	private char color;
	private char target;
	private int x;
	private int y;
	
	public gameObject(int x, int y, char type, char color, char target) {
		setX(x);
		setY(y);
		this.type = type;
		this.color = color;
		this.target = target;
	}
	
	public char getType() {
		return type;
	}
	
	public char getColor() {
		return color;
	}

	public char getTarget() {
		return target;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setType(char type) {
		this.type = type;
	}

	public void setColor(char color) {
		this.color = color;
	}

	public void setTarget(char target) {
		this.target = target;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
