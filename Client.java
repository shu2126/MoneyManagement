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
			System.out.println("0.���� ���� �� �α���\n1.����� ������ �Է�\n2.����� ����Ʈ ����");
			System.out.println("3.�ű�ȸ������ �Է�\n4.ȸ�� ����Ʈ ����\n5.ȸ�� ���� ����");
			System.out.println("6.ȸ�� ���� ����\n7.����\n==================");
			num = scanNum();
			if (num == 6)
				break;
			else if (num == 0) {
				connectServer();
				break;
			} else if (num > 0 && num < 7)
				System.out.println("������ ������ �����մϴ�.");
			else
				System.out.println("�ٽ��Է����ּ���.");
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
				System.out.println("������ �Է����ּ���");
			}
		}
	}

	public void playMenu() {

		int num = 0;
		while (true) {
			System.out.println("0.���� ���� �� �α���\n1.����� ������ �Է�\n2.����� ����Ʈ ����");
			System.out.println("3.�ű�ȸ������ �Է�\n4.ȸ�� ����Ʈ ����\n5.ȸ�� ���� ����");
			System.out.println("6.ȸ�� ���� ����\n7.����\n==================");
			num = scanNum();
			if (num == 0)
				System.out.println("������ ������ �Ǿ��ֽ��ϴ�.");
			else if (num > 0 && num < 8)
				playMenu2(num);
			else
				System.out.println("�ٽ��Է����ּ���.");
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
		System.out.println("��ȣ  Id  �̸�     pw  ���Գ⵵\n===========================");
		
		if (num == 1 || num == 2 || num == 3 || num == 4 ) {
			System.out.println("��ȣ  Id  �̸�     pw  ���Գ⵵\n===========================");
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
		} else System.out.println("���� ��ȣ �Դϴ�.");


		
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
		System.out.println("��ȣ  Id  �̸�     pw  ���Գ⵵\n===========================");
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
		System.out.print("���� ��ȣ�� ������ �ּ��� : ");
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
			System.out.println("���� ��ȣ �Դϴ�.");
	}

	public void showList() {
		int j = 0;
		String read = "";
		writerData(2);
		System.out.println("��ȣ  �з�  �׸��  ��¥      ����ڸ�\n===========================");
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
		System.out.println("���λ����� �Է����ּ���");
		System.out.print("�з� : ");
		ml.setInOut(scan.next());
		System.out.print("�׸� : ");
		ml.setState(scan.next());
		System.out.print("��¥: ");
		ml.setDate(scan.next());
		System.out.print("����� ��: ");
		ml.setName(scan.next());
		System.out.print("���� : ");
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

		System.out.print("���� IP �Է�(localhost) : ");
		sStr = scan.next();
		try {
			System.out.print("��Ʈ��ȣ �Է�  : ");
			socket = new Socket(sStr, scanNum());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("������ ������ �Ǿ����ϴ�.");
		} catch (IOException e) {
			System.out.println("ã�� �� ���� ��Ʈ��ȣ �Դϴ�. �ٽ��Է����ּ���");
		}
		loginUser();
	}

	public void loginUser() throws IOException {
		
		System.out.print("���̵� : ");
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
