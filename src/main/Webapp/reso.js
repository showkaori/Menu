//ページが読み込まれたら情報をDBから取得

window.addEventListener('load', function(){
	fetch("/menu/api/resolution")
		.then(function(response){
			return response.json();
		})
		.then(function(json){
			let reso = json;
            let array = reso.ranking;
			console.log(array);
			let str1 = "【1ヶ月間ランキング】";
			for(i = 0; i < array.length; i++){
				let kana = array[i];
				str1 += '<li>' + kana + '</li>';
			}
			document.getElementById('t4').innerHTML = str1;
			document.getElementById('t4').style.display = "";

			let array1 = reso.countJenre;
			console.log(array1);
			let str2 = "【ジャンル別】";
			for(i = 0; i < array1.length; i++){
				let kana = array1[i];
				str2 += '<li>' + kana + '</li>';
			}
			document.getElementById('t5').innerHTML = str2;
			document.getElementById('t5').style.display = "";
		})
		.catch(function(error){
		});
})
