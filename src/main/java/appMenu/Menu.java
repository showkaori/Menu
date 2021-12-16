package appMenu;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class Menu {

	//ランダムで料理名の取得
	@Path("/getm")
	@POST
	@Produces(MediaType.APPLICATION_JSON)  //返す形
	public MenuData random(String data)throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		MenuTarget mt = mapper.readValue(data, MenuTarget.class);
		GetMenuLogic bo = new GetMenuLogic();
		MenuData md = bo.execute(mt); //料理名
		return md;
	}

	//作った料理を記録する
	@Path("/rec")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String record(String data) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		RecodMenu rm = mapper.readValue(data, RecodMenu.class);
		PostMenuLogic bo = new PostMenuLogic();
		boolean result = bo.execute(rm);
		String text;
		if(result) {
			text = "記録しました";
		}else {
			text = "記録出来ませんでした";
		}
		return text;
	}

	//料理を作った回数を調べる
	@Path("/info")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RecordData info(String data) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		RecodMenu rm = mapper.readValue(data, RecodMenu.class);
		PostMenuLogic bo = new PostMenuLogic();
		RecordData rd = bo.execute2(rm);

		return rd;
	}

	//最近の情報を受け取る
	@Path("/resolution")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public InfoData resolution() throws Exception{
		GetInfoDataLogic gidl = new GetInfoDataLogic();
		InfoData id = gidl.execute();
		return id;
	}

}
