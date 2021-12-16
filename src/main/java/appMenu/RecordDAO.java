package appMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecordDAO {
	//作った料理を登録
	public boolean create(RecodMenu rm) {
		String dbname = "C:\\sqlite3\\menu.db"; // 利用するデータベースファイル
	    Connection conn = null;
	    try {
	    	Class.forName("org.sqlite.JDBC");
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

	        String sql ="insert into record(week, genre, name) values(?, ?, ?)";

	        PreparedStatement ps = conn.prepareStatement(sql);
	        //現在の曜日（番号）
	        Calendar cal = Calendar.getInstance();
	        int week = cal.get(Calendar.DAY_OF_WEEK);
	        ps.setInt(1, week);
	        ps.setInt(2, rm.genreCode);
	        ps.setString(3, rm.name);

	    	int result = ps.executeUpdate();
	    	if(result != 1) {
	    		return false;
	    	}
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	        	if (conn != null) {
	        		conn.close();
	            }
	        } catch (SQLException e) {
	                e.printStackTrace();
	        }
	    }
			return true;
	}

	//作った回数を取得
	public int findData(RecodMenu rm) {
		String dbname = "C:\\sqlite3\\menu.db"; // 利用するデータベースファイル
        Connection conn = null;
        int times = 0;
        try {
        	Class.forName("org.sqlite.JDBC");
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

	        String sql ="select ran.name, count(rec.name) from random as ran left join record as rec on ran.name = rec.name where ran.name =? group by ran.name";

	        PreparedStatement ps = conn.prepareStatement(sql);
	        //System.out.println("１週間以内に作っていないので" + rm.name + "を作った回数をDBに問い合わせる");
	        ps.setString(1, rm.name);

	        ResultSet rs = ps.executeQuery();
	        //結果がない時も0を返したい
	        //1項目目名前、2項目目がカウント数
	        times = rs.getInt(2);
	        /* System.out.println("作った回数:" + times); */

        }catch (Exception e) {
            e.printStackTrace();
            return times;
        }finally {
        	try {
        		if(conn != null) {
        			conn.close();
        		}
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
		return times;
	}

	//各曜日に何回作ったか
	public List<WeekDay> countWeek(RecodMenu rm) {
		String dbname = "C:\\sqlite3\\menu.db"; // 利用するデータベースファイル
        Connection conn = null;
        List<WeekDay> wdList= new ArrayList<>();
        try {
        	Class.forName("org.sqlite.JDBC");
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

	        String sql ="select w.kana, count(rec.week) from weekDay as w left join record as rec on w.week = rec.week where rec.name = ? group by w.week";

	        PreparedStatement ps = conn.prepareStatement(sql);
	        /* System.out.println(rm.name + "を作った曜日をDBに問い合わせる"); */
	        ps.setString(1, rm.name);

	        ResultSet rs = ps.executeQuery();

	        while(rs.next()) {
	        	String weekDay = rs.getString(1);
		        int times = rs.getInt(2);
		        WeekDay wd = new WeekDay(weekDay, times);
		        wdList.add(wd);
	        }

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
        	try {
        		if(conn != null) {
        			conn.close();
        		}
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
		return wdList;
	}

	//同ジャンルで一週間以内に作った料理リスト取得
	public List<String> findOneWeek(int genre){
		List<String> oneWeekList = new ArrayList<>();
		String dbname = "C:\\sqlite3\\menu.db"; // 利用するデータベースファイル
	    Connection conn = null;
	    try {
	    	Class.forName("org.sqlite.JDBC");
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

	        String sql = "select date(day), name from record where genre = ? and day >(select date('now', 'localtime', '-7 day'))";

	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, genre);
	        ResultSet rs = ps.executeQuery();

	        //作った料理名をArrayListに格納
	        while(rs.next()) {
	        	String name = rs.getString("name");
	        	oneWeekList.add(name);
	        }

	    }catch(Exception e){
	    	e.printStackTrace();
	    	return null;
	    }finally {
	    	try {
        		if(conn != null) {
        			conn.close();
        		}
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
	    }

		return oneWeekList;
	}

	//同ジャンルで今日と同じ曜日に作ったことがある料理
	public List<String> findRank(int genre, int week) {
		List<String> rankList = new ArrayList<>();
		String dbname ="C:\\sqlite3\\menu.db";
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

			String sql = "select name, count(*) from record where week = ? and genre = ? group by name order by count(*)";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, week);
			ps.setInt(2, genre);
	        ResultSet rs = ps.executeQuery();

	        while(rs.next()) {
	        	String name = rs.getString("name");
	        	rankList.add(name);
	        }

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return rankList;
	}

	//1か月以内の料理回数TOP3を求める
	public List<RecordData> findTop3() {
		List<RecordData> rankRD = new ArrayList<>();

		String dbname ="C:\\sqlite3\\menu.db";
		Connection conn = null;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

			String sql = "select name, genre, count(*) from record where day >(SELECT DATE('NOW','localtime','-1 MONTH')) group by name order by count(*) desc limit 3";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString(1);
				int genre1 = rs.getInt(2);
				String genre = null;
				switch(genre1) {
				case 1:
					genre = "和食";
					break;
				case 2:
					genre = "洋食";
					break;
				case 3:
					genre = "中華";
					break;
				}
				int times = rs.getInt(3);
				RecordData rd = new RecordData(name, genre, times);
				rankRD.add(rd);
	        }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return rankRD;
	}

	//各ジャンルの料理回数(1か月以内の条件を付けると0の時エラーになる）
	public List<String> count() {
		List<String> genreList = new ArrayList<>();
		String dbname ="C:\\sqlite3\\menu.db";
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

			String sql = "select num.genre, count(rec.genre) from number as num left join record as rec on num.code = rec.genre group by num.code";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String genre = rs.getString(1);
				int times = rs.getInt(2);
				String str = genre + "　料理回数：" + times + "回";
				genreList.add(str);
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return genreList;
	}

	/*同じ曜日に一番作られている料理
	public genreList weekdayCook(int week) {
		RecordData rd = null;
		String dbname ="C:\\sqlite3\\menu.db";
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

			String sql = "select name, count(*) genre from record where week = ? group by name order by count(*) desc limit 1";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, week);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String genre = rs.getString(1);
				int times = rs.getInt(2);
				String str = genre + "　料理回数：" + times + "回";
				genreList.add(str);
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return genreList;
	}
*/



}
