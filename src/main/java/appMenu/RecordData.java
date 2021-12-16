package appMenu;

import java.util.List;

public class RecordData {
	//Recordテーブル
	public int id;					//Id
	public String day;				//作った日付
	public String week;				//作った曜日
	public List<WeekDay> wdList;	//それぞれの曜日に何回作ったか
	public String genre;			//料理のジャンル
	public String name;				//料理の名前
	public int times;				//何回作ったか


	public RecordData(int times) {
		this.times = times;
	}
	public RecordData(int times, List<WeekDay> wdList) {
		this.times = times;
		this.wdList = wdList;
	}
	public RecordData(String day, String name) {
		this.day = day;
		this.name = name;
	}
	public RecordData(String name, String genre, int times) {
		this.name = name;
		this.genre = genre;
		this.times = times;
	}

	public int getId() {
		return id;
	}
	public String getDay() {
		return day;
	}
	public List<WeekDay> getWdList() {
		return wdList;
	}
	public String getGenre() {
		return genre;
	}
	public String getName() {
		return name;
	}
	public int getTimes() {
		return times;
	}


}
