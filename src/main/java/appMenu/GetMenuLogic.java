package appMenu;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class GetMenuLogic {
	//選択したジャンルの中からランダムメニューの取得
	public MenuData execute(MenuTarget mt) {
		//Stringのジャンルコードをint変換
		int genre = Integer.parseInt(mt.genreCode);
		switch(genre) {
		case 1:
			System.out.println("【和食から選びます】");
			break;
		case 2:
			System.out.println("【洋食から選びます】");
			break;
		case 3:
			System.out.println("【中華から選びます】");
			break;
		}

		//同ジャンルで一週間以内に作ったことのある料理をリストアップ
		RecordDAO rdao = new RecordDAO();
		List<String> oneWeekList = rdao.findOneWeek(genre);

		//現在の曜日（番号）
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.DAY_OF_WEEK);

        //ジャンル別同じ曜日に作ったことのある料理をリストアップ
        List<String> rankList = rdao.findRank(genre, week);

		boolean a = false;
		MenuData md = new MenuData();
		String result;

		//選ばれたジャンルの全料理をリストアップ
		RandomDAO dao = new RandomDAO();
		List<String> jenreList = dao.findAll(genre);

		//rankListにjenreList追加
		for(String s : jenreList) {
			rankList.add(s);
		}

		/*【確認用】結合したリスト内容一覧
		System.out.println("【下記からランダムに選択】");
		for(String t : rankList) {
			System.out.println(t);
		}
		*/

		do {
			int index = new Random().nextInt(rankList.size());
			result = rankList.get(index);
			System.out.println("ランダムに選んだ料理：" + result);
			//一週間以内で料理名が被っていないか
			/* System.out.println("【1週間以内に作った同ジャンルの料理】"); */
			if(oneWeekList != null) {
				a = oneWeekList.contains(result);
				/*【確認用】
				for(String s : oneWeekList) {
					System.out.println(s);
				}
				*/
			}
		}while(a) ;

		/* System.out.println("1週間以内に作っていないので" + result + "を奨める"); */
		md = new MenuData(genre, result);

		return md;
	}
}
