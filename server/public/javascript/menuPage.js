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
	$.get("/showPet")
	displayNotifications();
})

$("#map12").click(function() {
	$.get("/walkPet")
	displayNotifications();
})

$("#map21").click(function() {
	$.get("/groomPet")
	displayNotifications();
})

$("#treatBuy").click(function() {
	//200
	$.get("/buyItem/1")
	const currentMoney = parseInt($("#money").text().replace("$",""))
	$("#money").text("$" + (currentMoney <= 0 ? 0 : currentMoney - 200).toString())
	displayNotifications();
})

$("#blankyBuy").click(function() {
	//300
	$.get("/buyItem/2")
	const currentMoney = parseInt($("#money").text().replace("$",""))
	$("#money").text("$" + (currentMoney <= 0 ? 0 : currentMoney - 300).toString())
	displayNotifications();
})

$("#foodBuy").click(function() {
	//100
	$.get("/buyItem/0")
	const currentMoney = parseInt($("#money").text().replace("$",""))
	$("#money").text("$" + (currentMoney <= 0 ? 0 : currentMoney - 100).toString())
	displayNotifications();
})

function updateEvents() {
	$.get("/updateEvent")
}

function displayNotifications() {
	$.get("/newNotifications", function (data) {
		let notifs = data.split(";")
		for (let i = 0; i < notifs.length; i++) {
			$("#notifList").append("<li>"+notifs[i]+"</li>")
		}
	}, "text")
}


displayNotifications();
setInterval(updateEvents,300000);
setInterval(displayNotifications, 10000);