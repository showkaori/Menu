package appMenu;

import java.util.ArrayList;
import java.util.List;

public class GetInfoDataLogic {

	//recordテーブルから情報を受け取ろう
	public InfoData execute() {
		//①1か月以内の料理回数が多い料理TOP3
		List<String> ranking = new ArrayList<>(); //一番最初はnull
		RecordDAO dao = new RecordDAO();
		List<RecordData> rankRD = new ArrayList<>();
		rankRD = dao.findTop3();
		for(int i = 0; i < rankRD.size(); i++ ) {
			String str = (i+1) + "位：" + rankRD.get(i).getName() + "　料理回数：" + rankRD.get(i).getTimes() + "回　ジャンル：" + rankRD.get(i).getGenre();
			ranking.add(str);
		}

		//②1か月以内ジャンルごとの料理回数
		List<String> countJenre = new ArrayList<>();
		countJenre = dao.count();

		//③本日のおすすめ(同じ曜日に一番作っている料理)
		/*現在の曜日（番号）
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK);
		RecordData rd = dao.weekdayCook(week);*/



		//レスポンスする内容
		InfoData data = new InfoData(ranking, countJenre);
		return data;
	}
}
