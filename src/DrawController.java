import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawController extends JPanel{
	
	private static final DrawController singleInstance = new DrawController();
	private static SimplePainterModel nowData;
	private static ArrayList<SimplePainterModel> savedList;
	private DrawListener drawL;
	private boolean bDrag;
	
	private DrawController()
	{
		setBounds(10,10,800,500);
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		nowData = new SimplePainterModel();
		savedList = new ArrayList<SimplePainterModel>();
		
		drawL = new DrawListener();
		addMouseListener(drawL);
		addMouseMotionListener(drawL);
		bDrag = false;
	}//singleTonInstance, only one Instance
	
	public static DrawController getInstance() {return singleInstance;}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(SimplePainterModel data : savedList) {
			switch(data.nDrawMode) {
				case Constants.DOT:
					if(data.nSize < 10) {data.nSize = 10;}
					g.setColor(data.selectedColor);
					g.fillOval(data.ptOne.x - data.nSize/2, data.ptOne.y - data.nSize/2, data.nSize, data.nSize);
					break;
				case Constants.LINE:
				case Constants.CURVE:
					g.setColor(data.selectedColor);
					((Graphics2D) g).setStroke(new BasicStroke(data.nSize));
					g.drawLine(data.ptOne.x, data.ptOne.y, data.ptTwo.x, data.ptTwo.y);
					break;
				case Constants.RECT:
					g.setColor(data.selectedColor);
					if(data.bFill) {g.fillRect(data.ptOne.x, data.ptOne.y, data.ptTwo.x - data.ptOne.x, data.ptTwo.y - data.ptOne.y);}
					else{
						((Graphics2D) g).setStroke(new BasicStroke(data.nSize));
						g.drawRect(data.ptOne.x, data.ptOne.y, data.ptTwo.x - data.ptOne.x, data.ptTwo.y - data.ptOne.y);
					}
					break;
				case Constants.CLEARBOX:
					g.clearRect(data.ptOne.x, data.ptOne.y, data.ptTwo.x - data.ptOne.x, data.ptTwo.y - data.ptOne.y);
					break;
				case Constants.OVAL:
					g.setColor(data.selectedColor);
					if(data.bFill) {g.fillOval(data.ptOne.x, data.ptOne.y, data.ptTwo.x - data.ptOne.x, data.ptTwo.y - data.ptOne.y);}
					else{
						((Graphics2D) g).setStroke(new BasicStroke(data.nSize));
						g.drawOval(data.ptOne.x, data.ptOne.y, data.ptTwo.x - data.ptOne.x, data.ptTwo.y - data.ptOne.y);
					}
					break;
			}
	
		}
		if(bDrag) {
			switch(nowData.nDrawMode) {
				case Constants.LINE:
				case Constants.CURVE:
					g.setColor(nowData.selectedColor);
					((Graphics2D) g).setStroke(new BasicStroke(nowData.nSize));
					g.drawLine(nowData.ptOne.x, nowData.ptOne.y, nowData.ptTwo.x, nowData.ptTwo.y);
					break;
				case Constants.RECT:
					g.setColor(nowData.selectedColor);
					if(nowData.bFill) {
						g.fillRect(getLX(nowData), getUY(nowData), getRX(nowData) - getLX(nowData), getDY(nowData) - getUY(nowData));
					}
					else{
						g.drawRect(getLX(nowData), getUY(nowData), getRX(nowData) - getLX(nowData), getDY(nowData) - getUY(nowData));
					}
					break;
				case Constants.CLEARBOX:
					g.clearRect(getLX(nowData), getUY(nowData), getRX(nowData) - getLX(nowData), getDY(nowData) - getUY(nowData));
					break;
				case Constants.OVAL:
					g.setColor(nowData.selectedColor);
					if(nowData.bFill) {g.fillOval(getLX(nowData), getUY(nowData), getRX(nowData) - getLX(nowData), getDY(nowData) - getUY(nowData));}
					else{
						((Graphics2D) g).setStroke(new BasicStroke(nowData.nSize));
						g.drawOval(getLX(nowData), getUY(nowData), getRX(nowData) - getLX(nowData), getDY(nowData) - getUY(nowData));
					}
					break;
			}	
			
		}
	}
	public int getLX(SimplePainterModel data) {return data.ptOne.x < data.ptTwo.x ? data.ptOne.x : data.ptTwo.x ;}
	public int getRX(SimplePainterModel data) {return data.ptOne.x > data.ptTwo.x ? data.ptOne.x : data.ptTwo.x ;}
	public int getUY(SimplePainterModel data) {return data.ptOne.y < data.ptTwo.y ? data.ptOne.y : data.ptTwo.y ;}
	public int getDY(SimplePainterModel data) {return data.ptOne.y > data.ptTwo.y ? data.ptOne.y : data.ptTwo.y ;}
	
	public void setXY(SimplePainterModel data) {
		int x, y;
		
		if(data.ptOne.x > data.ptTwo.x) {
			x = data.ptOne.x;
			data.ptOne.x = data.ptTwo.x;
			data.ptTwo.x = x;
		}
		
		if(data.ptOne.y > data.ptTwo.y) {
			y = data.ptOne.y;
			data.ptOne.y = data.ptTwo.y;
			data.ptTwo.y = y;
		}
	}
	
	public static void setDrawMode(int mode) {
		nowData.nDrawMode = mode;
	}
	public static Color getSelectedColor() {return nowData.selectedColor;}
	public static void setSelectedColor(Color c) {nowData.selectedColor = c;}
	public static int getDataSize() {return nowData.nSize;}
	public static void setSize(int size) {
		if(nowData.nDrawMode == Constants.DOT && size < 10) {nowData.nSize = 10;}
		else {nowData.nSize = size;}
	}
	public static void setFill(boolean bool) {nowData.bFill = bool;}
	public static void unDo() {
		if(!savedList.isEmpty()) {
			if(savedList.get(savedList.size()-1).nDrawMode == Constants.CURVE) {
				while(SimplePainterModel.stack.peek()+1 < savedList.size()) {
					savedList.remove(savedList.size() - 1);
				}
				savedList.remove(savedList.size() - 1);
				SimplePainterModel.stack.pop();
			}
			else {savedList.remove(savedList.size() - 1);}
			singleInstance.repaint();
		}
	}
	public static void clear() {
		savedList.clear();
		singleInstance.repaint();
	}
	
	
	private class DrawListener implements MouseListener, MouseMotionListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(nowData.nDrawMode == Constants.DOT) {
				nowData.ptOne = e.getPoint();
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			if(nowData.nDrawMode == Constants.LINE || nowData.nDrawMode == Constants.OVAL || nowData.nDrawMode == Constants.RECT || nowData.nDrawMode == Constants.CLEARBOX) {
				bDrag = true;
				nowData.ptOne = e.getPoint();
			}
			else if(nowData.nDrawMode == Constants.CURVE) {
				bDrag = true;
				nowData.ptOne = e.getPoint();
				SimplePainterModel.stack.push(savedList.size());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if(nowData.nDrawMode == Constants.LINE || nowData.nDrawMode == Constants.CURVE) {
				bDrag = false;
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			}
			else if(nowData.nDrawMode == Constants.OVAL || nowData.nDrawMode == Constants.RECT || nowData.nDrawMode == Constants.CLEARBOX) {
				bDrag = false;
				nowData.ptTwo = e.getPoint();
				setXY(nowData);
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(nowData.nDrawMode == Constants.LINE || nowData.nDrawMode == Constants.OVAL || nowData.nDrawMode == Constants.RECT || nowData.nDrawMode == Constants.CLEARBOX) {
				nowData.ptTwo = e.getPoint();
				repaint();
			}
			else if(nowData.nDrawMode == Constants.CURVE) {
				nowData.ptTwo = e.getPoint();
				repaint();
				savedList.add(new SimplePainterModel(nowData));
				nowData.ptOne = e.getPoint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
}
