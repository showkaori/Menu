let text1 = "";
let genre = "";
// ジャンルボタンをクリックしたとき
function random(genreCode){
	console.log(genreCode);
	// 送信するjson
	const data = {genreCode : genreCode}
	const param = {
				method: "POST" ,
				headers : {"Content-Type" : "application/json"},
				body : JSON.stringify(data),
				};
	//fetchAPIでデータ送信
	fetch("/menu/api/getm", param)
		//成功時レスポンスを受け取る形
			.then(function(response){
			return response.json();
			})
		//成功時受け取ったデータをどうするか
		.then(function(json){
			let cook = json;
			genre = cook.genreCode;
			text1 = cook.name;
			document.getElementById("t1").innerHTML="本日の夕飯は【" + text1 + "】にしませんか？";

			//この料理を作った回数・曜日をrecordテーブルで調べる
			console.log(text1 + "を作った回数を調べる");
			const data1 = {name : text1}
			const param1 = {
				method: "POST" ,
				headers : {"Content-Type" : "application/json"},
				body : JSON.stringify(data1),
			};
			fetch("/menu/api/info", param1)
				//成功時レスポンスを受け取る形
				.then(function(response){
					return response.json();
					})
				//成功時受け取ったデータをどうするか
				.then(function(json){
					let info = json;
					times = info.times;
					document.getElementById("t2").innerHTML= text1 + "を作った回数：" + times;

					//作ったことがあった場合
					if(times != 0){
						let array = info.wdList;
						console.log(array);
						let str1 = "曜日別作った回数";
						for(i = 0; i < array.length; i++){
							let kana = array[i].kana;
							let times1 = array[i].times;
							str1 += '<li>' + kana + ':' + times1 + '回</li>';
						}
						document.getElementById('t3').innerHTML = str1;
						document.getElementById('t3').style.display = "";
					}
					//作ったことがなかった場合曜日を非表示に
					if(times == 0){
						document.getElementById('t3').style.display = "none";
					}
					})
				//失敗時
				.catch(function(error){
					console.log(error);
				});
			})
		//失敗時
		.catch(function(error){
			console.log(error);
		});


	// 意思決定ボタンの表示
	document.getElementById('b1').style.display = "";
}

// 違うものをクリックしたとき（リロード）
function change(){
	window.location.reload();
}

// 決定したとき(web検索)
function searchm(){
	console.log(text1 + "に決定");
	let url = 'https://www.bing.com/search?q=レシピ+' + text1;
	window.open(url,'_blank')

	//作った料理を記録する
	const data ={genreCode : genre, name : text1}
	const param = {
		method: "POST" ,
		headers : {"Content-Type" : "application/json"},
		body : JSON.stringify(data),
	};
	//fetchAPIでデータ送信
	fetch("/menu/api/rec", param)
		//成功時レスポンスを受け取る形
		.then(function(response){
			return response.text();
			})
		//成功時受け取ったデータをどうするか
		.then(function(text){
			console.log(text);
			})
		//失敗時
		.catch(function(error){
			console.log(error);
		});


}
