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
	$("#money").text(parseInt($("#money").text) - 200)
})

$("#blankyBuy").click(function() {
	//300
	$.get("/buyItem/2")
	$("#money").text(parseInt($("#money").text) - 300)
})

$("#foodBuy").click(function() {
	//100
	$.get("/buyItem/0")
	$("#money").text(parseInt($("#money").text) - 100)
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

setInterval(updateEvents(),300000);
setInterval(displayNotifications(), 10000)