package appMenu;

import java.util.List;

public class PostMenuLogic {

	//作る料理の登録
	public boolean execute(RecodMenu rm) {
		RecordDAO dao = new RecordDAO();
		boolean result = dao.create(rm);
		return result;
	}

	//料理情報の取得
	public RecordData execute2(RecodMenu rm) {
		RecordDAO dao = new RecordDAO();
		//作った回数の取得
		int times = dao.findData(rm);
		//作ったことがある場合のみ何曜日に作ることが多いか
		if(times != 0) {
			List<WeekDay> wdList = dao.countWeek(rm);
			RecordData rd = new RecordData(times, wdList);
			return rd;
		}else {
			RecordData rd = new RecordData(times);
			return rd;
		}

	}
}
