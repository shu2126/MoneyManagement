package money;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server {
	private ArrayList<Member> memberList = new ArrayList<Member>();
	private HashMap<String, Member> memberMap = new HashMap<String, Member>();

	private ArrayList<MoneyLog> list = new ArrayList<MoneyLog>();
	private HashMap<String, MoneyLog> map = new HashMap<String, MoneyLog>();
	
	private String fileload = "d:\\";
	
	private Member m = null;
	private MoneyLog ml = null;
	
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;

	private File file = null;
	private FileWriter fileWriter = null;
	private String fileName = "";
	int count = 0;

	public Server() {
		new ServerThread().start(); // 서비스 시작
	}

	class ServerThread extends Thread {
		@Override
		public void run() {
			ServerSocket listener = null;
			Socket socket = null;
			try {
				listener = new ServerSocket(9998);
				while (true) {
					socket = listener.accept();
					System.out.println("클라이언트 연결됨\n");
					new ServiceThread(socket).start();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (listener != null)
					listener.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	public void saveFile() throws IOException {
//		BufferedWriter br = new BufferedWriter(fileWriter);
//		for (Book i : list) {
//			br.write(i.toString());
//			br.newLine();
//
//			br.flush();
//			br.close();
//		}
//	}

	public void readUserFile() throws IOException {
		file = new File("d:\\login.txt");
		Scanner read = new Scanner(new FileReader("d:\\login.txt"));
		if (file.exists()) {
			while (read.hasNext()) {
				m = new Member();
				m.setId(read.next());
				m.setName(read.next());
				m.setPw(read.next());
				m.setYear(read.nextInt());

				memberList.add(m);
				memberMap.put(m.getId(), m);
			}
			System.out.println("텍스트 읽기 완료");
			out.write("텍스트 읽기 완료\n");
		}
		read.close();
	}
	
	public void readFile() throws IOException {
		file = new File("d:\\text.txt");
		Scanner read = new Scanner(new FileReader("d:\\text.txt"));
		if (file.exists()) {
			while (read.hasNext()) {
				ml = new MoneyLog();
				ml.setInOut(read.next());
				ml.setState(read.next());
				ml.setDate(read.next());
				ml.setPrice(read.nextInt());
				ml.setName(read.next());

				list.add(ml);
				map.put(ml.getDate(), ml);
			}
			System.out.println("텍스트 읽기 완료");
			out.write("텍스트 읽기 완료\n");
		}
		else System.out.println("파일이 비어있습니다 ");
		read.close();
	}
	
	public void loginUser(String id, String pw) throws IOException {
		readUserFile();
		Member member = memberMap.get(id);
		if(member==null) System.out.println(id+"는(은) 없는 ID입니다.");
		else {
			if((member.getPw()).equals(pw)) {
				System.out.println("로그인 완료");
				out.write("로그인 완료\n");
			}
			else {
				System.out.println("비밀번호가 맞지 않습니다.");
				out.write("비밀번호가 맞지 않습니다.");
			}
			out.flush();
			out.close();
		}
	}

	class ServiceThread extends Thread {

		public ServiceThread(Socket s) { // 클라이언트와 통신할 소켓을 전달받음
			socket = s;
			try {
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			// fileName = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			while (true) {
				try {
					String num = in.readLine();
					String text = in.readLine(); // 클라이언트로부터 이름 받음
					
					if (num.equals("0")) 
						loginUser(text,in.readLine());
					
					else if (num.equals("1")) {
						inputData(text);						
					}

					else if (num.equals("2"))
						showList(text);

					else if (num.equals("3")) 
						inputNewUser(text);

					else if (num.equals("4"))
						showList2(text);

					else if (num.equals("5"))
//					    saveFile();
//					else if (num.equals("6"))
						if (text.equals("3"))
							deleteBook(text, 3);
						else
							deleteBook(text, 0);

					out.flush();
				} catch (IOException e) {
					System.out.println("연결 종료");
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return; // 스레드 종료
					// e.printStackTrace();
				}

			}
		}
	}

	public void deleteBook(String text, int number) throws IOException {

		String string = "";

		for (Member i : memberList) {
			string += i;
		}
		System.out.println(string + "end\n");
		out.write(string + "end\n");
		out.flush();
		if (number == 3) {
			int n = Integer.parseInt(in.readLine());
			m = memberList.get(n);
			list.remove(n);
			map.remove(m.getId());
			System.out.println("삭제 완료");
			out.write("삭제가 왼료되었습니다\n");
		}
	}

	public void showList(String text) throws IOException {
		String string = "";
		ml = new MoneyLog();
		Collections.sort(list, ml.sortDate);
			
		for (MoneyLog i : list) {
			string += i;
		}
		System.out.println(string + "end\n");
		out.write(string + "end\n");
	}
	
	public void showList2(String text) throws IOException {
		String string = "";
		
		m = new Member();
		// readFile();
		
		if (text.equals("1"))
			Collections.sort(memberList);
		else if (text.equals("2"))
			Collections.sort(memberList, m.sortYear);
		else if (text.equals("3"))
			Collections.sort(memberList, m.sortName);
		else
			Collections.sort(memberList, m.sortId);
		for (Member i : memberList) {
			string += i;
		}
		System.out.println(string + "end\n");
		out.write(string + "end\n");
	}

	public void inputData(String text){
		Scanner reader = new Scanner(text);
		while (reader.hasNext()) {
			ml = new MoneyLog();
			ml.setInOut(reader.next());
			ml.setState(reader.next());
			ml.setDate(reader.next());
			ml.setName(reader.next());
			ml.setPrice(reader.nextInt());

			list.add(ml);
			map.put(ml.getDate(), ml);
			
			System.out.print(ml);
			System.out.println("입력 완료");
			try {
				out.write("텍스트 읽기 완료\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("텍스트를 읽지 못했습니다.");
			}
		}
		reader.close();
	}
	
	public void inputNewUser(String text) throws IOException {
		Scanner reader = new Scanner(text);
		while (reader.hasNext()) {
			m = new Member();
			m.setId(reader.next());
			m.setName(reader.next());
			m.setPw(reader.next());
			m.setYear(reader.nextInt());

			memberList.add(m);
			memberMap.put(m.getId(), m);
		}
		reader.close();
	}

	public static void main(String[] args) {
		new Server();
	}
}
