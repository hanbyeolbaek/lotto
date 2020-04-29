import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class myButton extends JButton {
	
	//색변경 메소드 초기값
	private Color txtColor = Color.black;
	private Color bgColor = Color.white;
	
	public myButton(String text) {
		super(text); // = JButton 동적할당 받은것과 동일
		setBorderPainted(false); //border 없음
		setOpaque(false); //투명하게
	}
	
	//버튼 색지정 메소드
	public void setTextColor(Color c) {
		txtColor = c;
	}
	public void setBgColor(Color c) {
		bgColor = c;
	}
	
	//button의 모양
	@Override
	public void paint(Graphics g) { //paint는 JButton내의 메소드, Graphics는 interface
		int w = getWidth();
		int h = getHeight();
		
		g.setColor(bgColor); //배경색
		g.fillRoundRect(0, 0, w, h, 60, 60); //위치선정 x, y, 크기 w, h, round u, d -> 라운딩주면서 채우기
		
		g.setColor(txtColor); //글자색
		g.drawString(getText(), getWidth()/2-10, getHeight()/2+4); //"내용(getText()=super(text)", 위치 x, y -> 글자
	}
}
