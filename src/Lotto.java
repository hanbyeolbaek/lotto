import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.json.simple.JSONObject;

@SuppressWarnings("serial")
public class Lotto extends JFrame implements MouseListener, KeyListener { //extends JFrame하면 JFrame 동적할당 안해도됨
	//배열 선언
	myButton[] mbt = new myButton[7]; //생성자메소드 소환 X
	
	//로또번호 입력 텍스트칸 배열 선언
	JTextField[] rotTxt = new JTextField[7];

	JButton checkBtn = new JButton("확인");
	JTextField turnTxt = new JTextField();
	
	//오류문구 넣을 lable
	JLabel erLbl = new JLabel("");
	
	JLabel titleLbl = new JLabel("로또번호");
	JLabel turnLbl = new JLabel("");
	
	JLabel rankLbl = new JLabel("");
	
	JLabel plusLbl = new JLabel(new ImageIcon(new ImageIcon("image/plus3.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
	
	public void init() {
		//배열로 버튼안에 텍스트 넣기
		for(int i=0;i<7;i++) {
			//String으로 값 넣는 방법 2가지
//			mbt[i] = new myButton("0"+String.valueOf(i+1)); //생성자메소드 불러서 값 넣어줘야함
			mbt[i] = new myButton("0"+(i+1)); //문자+숫자 = 문자
		}
		
		//로또번호 입력칸 생성자 만들기
		for(int i=0;i<7;i++) {
			rotTxt[i] = new JTextField();
		}
		
		getContentPane().setLayout(null);
		titleLbl.setBounds(20, 10, 150, 50);
		getContentPane().add(titleLbl);
		
		//배열로 버튼 위치 지정 및 보너스번호 배경색상과 글자색 변경
		int w = 20;
		for(int i=0;i<6;i++) {
			mbt[i].setBounds(w, 70, 60, 60);
			w += 70;
		}
		
		//이미지 아이콘 넣기
		plusLbl.setBounds(w, 70, 60, 60);
		getContentPane().add(plusLbl);
		w += 70;
				
		mbt[6].setBounds(w, 70, 60, 60);
		mbt[6].setBgColor(Color.black);
		mbt[6].setTextColor(Color.white);

		//배열로 버튼 구현
		for(int i=0;i<7;i++) {
			getContentPane().add(mbt[i]);
		}
			
		//오류문구 lable 위치
		erLbl.setBounds(120, 250, 400, 100);
		getContentPane().add(erLbl);
		erLbl.setFont(erLbl.getFont().deriveFont(20f));
		
		turnLbl.setBounds(120, 350, 150, 30);
		getContentPane().add(turnLbl);
		turnTxt.setBounds(220, 350, 150, 30);
		getContentPane().add(turnTxt);
		
		//로또번호 입력칸 위치 설정
		w = 20;
		for(int i=0;i<6;i++) {
			rotTxt[i].setBounds(w, 150, 60, 60);
			w += 70;
		}
		rotTxt[6].setBounds(w+70, 150, 60, 60);
		//로또번호 입력칸 구현
		for(int i=0;i<7;i++) {
			rotTxt[i].setForeground(Color.black);
			getContentPane().add(rotTxt[i]);
		}
				
		checkBtn.setBounds(400, 350, 100, 30);
		getContentPane().add(checkBtn);
		
		//등수
		rankLbl.setBounds(120, 200, 400, 100);
		getContentPane().add(rankLbl);
		rankLbl.setFont(erLbl.getFont().deriveFont(45f));
	}
	private myButton myButton(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	public void event() {
		checkBtn.addMouseListener(this); //동작하게하는 아이템, this : 이 객체(클래스)에서
//		mbt1.addMouseListener(this);
		for(int i=0;i<mbt.length;i++) {
			mbt[i].addMouseListener(this);
		}
		turnTxt.addKeyListener(this);
		for(int i=0;i<rotTxt.length;i++) {
			rotTxt[i].addKeyListener(this);
		}
	}
	
	//Button reset method
	void resetButton() {
		//배열로 버튼 리셋
		for(int i=0;i<7;i++) {
			mbt[i].setText("");
		}	
	}
	
	//로또번호 입력칸 리셋
	void resetRotTxt() {
		for(int i=0;i<7;i++) {
			rotTxt[i].setText("");
		}
	}
	
	// reset -> 입력칸 비우기
	void resetTurnTxt() {
		turnTxt.setText("");
	}
	//turnLbl reset
	void resetTurnLbl() {
		turnLbl.setText("");
	}
	//erLbl reset
	void resetErLbl( ) {
		erLbl.setText("");
	}
	
	void resetRankLbl() {
		rankLbl.setText("");
	}

	
	void showResult() {
		//회차번호 문자입력 시 오류
		String turnNum = turnTxt.getText();
		try {
			int a = Integer.parseInt(turnNum);
			if(a<=0||a>10000) {
				erLbl.setText("회차 번호를 다시 입력해주세요.");
				resetButton();
				resetTurnTxt();
				resetTurnLbl();
				resetRankLbl();
				return;
			}
		} catch(Exception e) {
			erLbl.setText("숫자로 입력해주세요.");
			resetButton();
			resetTurnTxt(); 
			resetTurnLbl();
			resetRankLbl();
			return;
		}
		
		//로또번호 문자 입력 시 오류문구 메소드 호출
		String[] rotNum = new String[7];
		int[] a1 = new int[7];
		try {
			for(int i=0;i<7;i++) {
				rotNum[i] = rotTxt[i].getText();
				a1[i] = Integer.parseInt(rotNum[i]);
				if(a1[i]<=0||a1[i]>45) {
					erLbl.setText("로또 번호를 다시 입력해주세요.");
					resetButton();
					resetRotTxt();
					resetTurnLbl();
					resetRankLbl();
					return;
//					break;
				}
			} 
		} catch(Exception e) {
			erLbl.setText("숫자로 입력해주세요.");
			resetButton();
			resetRotTxt();
			resetTurnLbl();
			resetRankLbl();
			return;
		}
		
		try {
			JsonReader jr = new JsonReader();
			JSONObject jo = jr.connectionUrlToJSON(turnTxt.getText());
			
			if(jo==null) {
				erLbl.setText("접속에 실패하였습니다. 다시 시도해주세요.");
				resetButton();
				resetRotTxt();
				resetTurnLbl();
				resetTurnTxt();
				resetRankLbl();
				return;
			}
			
			//마지막회차 넘겼을때 오류문구 입력
			if(jo.get("returnValue").equals("fail")) {
				erLbl.setText("회차정보가 없습니다. 다시 입력해주세요.");
				//button reset
				resetButton();
				resetTurnTxt();
				resetTurnLbl();
				resetRankLbl();
				return;
			}
//			else {
//				resetErLbl();
//			}
			
			//배열로 구획별 버튼색상 변경
			for(int i=0;i<6;i++) {
				int a = Integer.parseInt(String.valueOf(jo.get("drwtNo"+(i+1))));
				if(a>40) {
					mbt[i].setBgColor(Color.magenta);
				} else if(a>30) {
					mbt[i].setBgColor(Color.cyan);
				} else if(a>20) {
					mbt[i].setBgColor(Color.lightGray);
				} else if(a>10) {
					mbt[i].setBgColor(Color.pink);
				} else {
					mbt[i].setBgColor(Color.orange);
				}
			}
			int a = Integer.parseInt(String.valueOf(jo.get("bnusNo")));
			if(a>40) {
				mbt[6].setBgColor(Color.magenta);
			} else if(a>30) {
				mbt[6].setBgColor(Color.cyan);
			} else if(a>20) {
				mbt[6].setBgColor(Color.lightGray);
			} else if(a>10) {
				mbt[6].setBgColor(Color.pink);
			} else {
				mbt[6].setBgColor(Color.orange);
			}
				
			//배열로 버튼 값 받아오기
			for(int i=0;i<6;i++) {
				mbt[i].setText(String.valueOf(jo.get("drwtNo"+(i+1))));
			}
			mbt[6].setText(String.valueOf(jo.get("bnusNo")));
			
			//로또번호 비교 메소드 호출
			checkRotNum();
				
			turnLbl.setText(turnTxt.getText()+" 회차");
			
			erLbl.setText("다른 번호를 입력하려면 esc를 눌러주세요.");
			
			//정상입력 시 텍스트박스 초기화
			resetTurnTxt();
//			resetRotTxt();
			
		} catch (Exception e) {
			e.printStackTrace();
			erLbl.setText("접속에 실패하였습니다. 다시 시도해주세요.");
			return;
		}
	}
	
	//로또번호 불러온 값과 입력한 값 비교
	void checkRotNum() {
		int cnt = 0;
		int[] index = new int[7];
		int bnusCnt = 0;
		for(int i=0;i<6;i++) {
			for(int j=0;j<6;j++) {
				if(rotTxt[i].getText().equals(mbt[j].getText())) {
					cnt++;
					index[i] = i+1;
				}
			}
			if(index[i]>0) {
//				mbt[index[i]-1].setTextColor(Color.black);
				rotTxt[index[i]-1].setForeground(Color.black);
			}
			else {
//				mbt[i].setTextColor(Color.red);
				rotTxt[i].setForeground(Color.red);
			}
		}
		if(rotTxt[6].getText().equals(mbt[6].getText())) {
			bnusCnt++;
			rotTxt[6].setForeground(Color.black);
//			mbt[6].setTextColor(Color.black);
		}
		else {
			rotTxt[6].setForeground(Color.red);
//			mbt[6].setTextColor(Color.red);
		}
		
		switch(cnt+bnusCnt) {
		case 7:
		case 6:
			if((cnt+bnusCnt)==6) {
				rankLbl.setText("2등");
			}
			else
				rankLbl.setText("1등");
			break;
		case 5:
			rankLbl.setText("3등");
			break;
		case 4:
			rankLbl.setText("4등");
			break;
		case 3:
			rankLbl.setText("5등");
			break;
		default:
			rankLbl.setText("꽝!");
			break;
		}
	}
	
	Lotto() {
		super("로또번호 조회");
		//화면구성 component 초기화
		init();
		//이벤트 event 등록
		event();
		
		setSize(600, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws Exception {
		String fType = "Dokdo-Regular.ttf"; //다운로드 받은 폰트 적용
		//Exception 발생소지가 있으므로 try catch로 묶어주거나 내 메소드에 throws를 추가해줌
		Font f1 = Font.createFont(Font.TRUETYPE_FONT, new File(fType));
		setUIFont(new FontUIResource(f1.deriveFont(19f)));
//		System.out.printf(("%f\n"), 25f); //정수뒤에 f를 붙이면 실수형태로 나타남
		new Lotto(); //Lotto 동적할당
	}
	//폰트 적용
	private static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource)
				UIManager.put(key, f);
		}
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			//회차 결과를 버튼 1 ~ 7에 보여주기
			showResult();
		}
		
		//esc를 누르면 화면이 초기화(trLbl, trTxt, mbt1~7, erLbl
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE) {
			resetButton();
			resetTurnTxt();
			resetTurnLbl();
			resetErLbl();
			resetRotTxt();
			resetRankLbl();
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	@Override
	public void mouseClicked(MouseEvent e) { //누르고 땠을때
		if(e.getSource()==checkBtn) {
			//회차 결과를 버튼 1 ~ 7에 보여주기
			showResult();
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) { //누르는 시점
	}
	@Override
	public void mouseExited(MouseEvent e) { //누르고 난 후
	}
	@Override
	public void mousePressed(MouseEvent e) { //눌러진 상태
	}
	@Override
	public void mouseReleased(MouseEvent e) { //땠을때 상태
	}
}