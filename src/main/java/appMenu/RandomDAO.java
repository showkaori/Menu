package appMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RandomDAO {
	//選ばれたジャンルの料理をすべて取得
	public List<String> findAll(int genre){
			String dbname = "C:\\sqlite3\\menu.db"; // 利用するデータベースファイル
	        Connection conn = null;
	        List<String> jenreList = new ArrayList<>();
	        try {
	            Class.forName("org.sqlite.JDBC");
	            conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);

	            String sql ="select name from random where genre = ?";

	            PreparedStatement ps = conn.prepareStatement(sql);
	    		ps.setInt(1, genre);

	    		ResultSet rs = ps.executeQuery();
	    		while(rs.next()) {
		    		String name = rs.getString("name");
		    		jenreList.add(name);
	    		}


	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;

	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return jenreList;

		}

}
