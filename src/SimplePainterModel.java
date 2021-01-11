import java.awt.Color;
import java.awt.Point;
import java.util.Stack;

public class SimplePainterModel {
	
	public int nDrawMode;
	public Point ptOne, ptTwo;
	public int nSize;
	public boolean bFill;
	public Color selectedColor;
	public static final Stack<Integer> stack = new Stack<Integer>();
	
	public SimplePainterModel() {
		nDrawMode = Constants.DOT;
		ptOne = new Point();
		ptTwo = new Point();
		nSize = 1;
		bFill = false;
		selectedColor = Color.black;
	}
	
	public SimplePainterModel(SimplePainterModel data) {
		nDrawMode = data.nDrawMode;
		ptOne = data.ptOne;
		ptTwo = data.ptTwo;
		nSize = data.nSize;
		bFill = data.bFill;
		selectedColor = data.selectedColor;
	}
}
