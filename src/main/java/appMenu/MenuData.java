package appMenu;

public class MenuData {
	//Randomテーブル
	public int code;
	public int genreCode;
	public String name;

	public MenuData() {
	}

	public MenuData(int genreCode, String name) {
		this.genreCode = genreCode;
		this.name = name;
	}
}
