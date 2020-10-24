package money;

import java.util.Comparator;

public class Member implements Comparable<Member>{
	
	public String id,name,pw;
	public int year;
	
	@Override
	public String toString() {
		return getId()+" "+getName()+" "+getPw()+" "+getYear()+" "+"\n";
	}
	
	Comparator<Member> sortId = new Comparator<Member>() {
		@Override
		public int compare(Member m1, Member m2) {
			return m1.getId().compareTo(m2.getId());
		}
	};
	Comparator<Member> sortName = new Comparator<Member>() {
		@Override
		public int compare(Member m1, Member m2) {
			return m1.getName().compareTo(m2.getName());
		}
	};
	
	Comparator<Member> sortYear = new Comparator<Member>() {
		@Override
		public int compare(Member m1, Member m2) {
			if (m1.getYear() < m2.getYear())
				return 1;
			else if (m1.getYear() > m2.getYear())	return -1;
			else return 0;
		}
	};
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public int compareTo(Member m) {
		// TODO Auto-generated method stub
		return this.year - m.year;
	}
	
	
}