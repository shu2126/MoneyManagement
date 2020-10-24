package money;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Client {
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	private Member m = null;
	
	private String ID = "",PW = "";
	Scanner scan = new Scanner(System.in);

	String string = "";
	int count = 0;

	public Client() throws IOException {
		int num = 0;
		while (true) {
			System.out.println("0.서버 연결 및 로그인\n1.가계부 데이터 입력\n2.가계부 리스트 보기");
			System.out.println("3.신규회원정보 입력\n4.회원 리스트 보기\n5.회원 정보 수정");
			System.out.println("6.회원 정보 삭제\n7.종료\n==================");
			num = scanNum();
			if (num == 6)
				break;
			else if (num == 0) {
				connectServer();
				break;
			} else if (num > 0 && num < 7)
				System.out.println("서버와 연결후 가능합니다.");
			else
				System.out.println("다시입력해주세요.");
		}
		playMenu();

	}

	public int scanNum() {
		while (true) {
			try {
				int num = scan.nextInt();
				return num;
			} catch (InputMismatchException e) {
				scan = new Scanner(System.in);
				System.out.println("정수를 입력해주세요");
			}
		}
	}

	public void playMenu() {

		int num = 0;
		while (true) {
			System.out.println("0.서버 연결 및 로그인\n1.가계부 데이터 입력\n2.가계부 리스트 보기");
			System.out.println("3.신규회원정보 입력\n4.회원 리스트 보기\n5.회원 정보 수정");
			System.out.println("6.회원 정보 삭제\n7.종료\n==================");
			num = scanNum();
			if (num == 0)
				System.out.println("서버와 연결이 되어있습니다.");
			else if (num > 0 && num < 8)
				playMenu2(num);
			else
				System.out.println("다시입력해주세요.");
		}
	}

	public void playMenu2(int num) {
		if (num == 1)
			inputData();
		else if (num == 2)
			showList();
		else if (num == 3)
			newUser();
		else if (num == 4)
			showMemberList();
		else if (num == 5)
			inputNew();
		else if (num == 6)
			deleteMember();
		else
			saveFile();
	}
	
	public void inputNew() {
		
	}
	
	public void showMemberList() {
		
		int j = 0, num = 0;
		String read = "";
		writerData(4);
		num = scanNum();
		System.out.println("번호  Id  이름     pw  가입년도\n===========================");
		
		if (num == 1 || num == 2 || num == 3 || num == 4 ) {
			System.out.println("번호  Id  이름     pw  가입년도\n===========================");
			try {
				writerData(num);
				read = in.readLine();
				while (!read.equals("end")) {
					System.out.println((++j) + "  " + read);
					read = in.readLine();
				}
			} catch (IOException e1) {
				return;
			}
		} else System.out.println("없는 번호 입니다.");


		
	}
	
	public void newUser() {
		
		writerData(3);
		m = new Member();
		m.setId(scan.next());
		m.setName(scan.next());
		m.setPw(scan.next());
		m.setYear(scan.nextInt());
		
		try {
			out.write(m.toString());
			out.flush();
			System.out.println(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteMember() {

		int j = 0, num = 0;
		String read = "";
		writerData(6);
		System.out.println("번호  Id  이름     pw  가입년도\n===========================");
		try {
			writerData(3);
			read = in.readLine();
			while (!read.equals("end")) {
				System.out.println((++j) + "  " + read);
				read = in.readLine();
			}
		} catch (IOException e1) {
			return;
		}
		System.out.print("지울 번호를 선택해 주세요 : ");
		num = scanNum();

		if (num > 0 && num <= j) {
			try {
				writerData(num - 1);
				System.out.println(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("없는 번호 입니다.");
	}

	public void showList() {
		int j = 0;
		String read = "";
		writerData(2);
		System.out.println("번호  분류  항목명  날짜      사용자명\n===========================");
		try {
			read = in.readLine();
			while (!read.equals("end")) {
				System.out.println((++j) + "  " + read);
				read = in.readLine();
			}
		} catch (IOException e1) {
			return;
		}
	}

	public void inputData() {
		MoneyLog ml = new MoneyLog();

		writerData(1);
		System.out.println("세부사항을 입력해주세요");
		System.out.print("분류 : ");
		ml.setInOut(scan.next());
		System.out.print("항목 : ");
		ml.setState(scan.next());
		System.out.print("날짜: ");
		ml.setDate(scan.next());
		System.out.print("사용자 명: ");
		ml.setName(scan.next());
		System.out.print("가격 : ");
		ml.setPrice(scanNum());
		
		try {
			out.write(ml.toString());
			out.flush();
			System.out.println(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveFile() {

		writerData(5);
		try {
			System.out.println(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writerData(int num) {
		try {
			out.write(num + "\n");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectServer() throws IOException {
		String sStr = "";

		System.out.print("서버 IP 입력(localhost) : ");
		sStr = scan.next();
		try {
			System.out.print("포트번호 입력  : ");
			socket = new Socket(sStr, scanNum());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("서버와 연결이 되었습니다.");
		} catch (IOException e) {
			System.out.println("찾을 수 없는 포트번호 입니다. 다시입력해주세요");
		}
		loginUser();
	}

	public void loginUser() throws IOException {
		
		System.out.print("아이디 : ");
     	ID = scan.next();
		System.out.print("p/w : ");
		PW=scan.next();
		out.write(ID+"\n"+PW+"\n");
		
		out.flush();
	}
	
	public static void main(String[] args) throws IOException {
		new Client();
	}
}
