/*function checkEvents() {
	$.get("/url", function(data) {
		let messages = data.split(',')
		messages.forEach(function(n) {
			$(".alert ul").append("<li>"+n+"</li>")
		})
	})
	//$(".alert").load("/url")
}*/

$("#map11").click(function() {
	alert("clicked");
	$.get("/showPet")
})

$("#map12").click(function() {
	$.get("/walkPet")
})

$("#map21").click(function() {
	$.get("/groomPet")
})

$("#treatBuy").click(function() {
	//200
	$.get("/buyItem/1")
})

$("#blankyBuy").click(function() {
	//300
	$.get("/buyItem/2")
})

$("#foodBuy").click(function() {
	//100
	$.get("/buyItem/0")
})

$("#changePW").click(function() {
	$.get("/changePasswordView")
})

$("#abandonPet").click(function() {
	alert("clicked");
	$.get("/abandonPet")
})

//shop buy

//settings change pw

//settings abandon pet


/*setInterval(checkEvents(),100);*/