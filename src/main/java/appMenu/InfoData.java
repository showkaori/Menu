package appMenu;

import java.util.List;

public class InfoData {

	public List<String> ranking; //1か月以内作ったことが多い料理TOP3
	public List<String> countJenre;		//1か月以内各ジャンル何回作ったか
	public RecordData recommend; //おすすめ

	public InfoData(List<String> ranking, List<String> countJenre) {
		this.ranking = ranking;
		this.countJenre = countJenre;
	}
}
