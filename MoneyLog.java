package money;

import java.util.Comparator;

public class MoneyLog  implements Comparable<Member>{
	public String inOut,state,date,name;
	public int price;

	@Override
	public String toString() {
		return getInOut()+" "+getState()+" "+getDate()+" "+getName()+" "+getPrice()+"\n";
	}
	Comparator<MoneyLog> sortDate = new Comparator<MoneyLog>() {
		@Override
		public int compare(MoneyLog m1, MoneyLog m2) {
			return m1.getDate().compareTo(m2.getDate());
		}
	};
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public int compareTo(Member o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}