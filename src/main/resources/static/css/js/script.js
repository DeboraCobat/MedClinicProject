


;(function ($) {

	'use strict';
	
 // SCROLL TO TOP
  
  $(window).on('scroll', function () {
    if ($(window).scrollTop() > 70) {
        $('.backtop').addClass('reveal');
    } else {
        $('.backtop').removeClass('reveal');
    }
});
 
	$('.portfolio-single-slider').slick({
		infinite: true,
		arrows: false,
		autoplay: true,
		autoplaySpeed: 2000

	});

	$('.clients-logo').slick({
		infinite: true,
		arrows: false,
		autoplay: true,
		slidesToShow: 6,
		slidesToScroll: 6,
		autoplaySpeed: 6000,
		responsive: [
		    {
		      breakpoint: 1024,
		      settings: {
		        slidesToShow:6,
		        slidesToScroll: 6,
		        infinite: true,
		        dots: true
		      }
		    },
		    {
		      breakpoint: 900,
		      settings: {
		        slidesToShow:4,
		        slidesToScroll: 4
		      }
		    },{
		      breakpoint: 600,
		      settings: {
		        slidesToShow: 4,
		        slidesToScroll: 4
		      }
		    },
		    {
		      breakpoint: 480,
		      settings: {
		        slidesToShow: 2,
		        slidesToScroll: 2
		      }
		    }
		  
  		]
	});




// // Calendar
// 	document.addEventListener("DOMContentLoaded", function() {
// 		var calendarContainer = document.getElementById("calendar");
//
// 		// Generate current month and year
// 		var currentDate = new Date();
// 		var currentMonth = currentDate.getMonth();
// 		var currentYear = currentDate.getFullYear();
//
// 		renderCalendar(currentMonth, currentYear);
//
// 		function renderCalendar(month, year) {
// 			var firstDay = new Date(year, month, 1).getDay();
// 			var totalDays = new Date(year, month + 1, 0).getDate();
//
// 			var table = document.createElement("table");
// 			var header = document.createElement("thead");
// 			var headerRow = document.createElement("tr");
//
// 			// Render month and year in the header
// 			var headerCell = document.createElement("th");
// 			headerCell.setAttribute("colspan", "7");
// 			headerCell.textContent = getMonthName(month) + " " + year;
// 			headerRow.appendChild(headerCell);
// 			header.appendChild(headerRow);
// 			table.appendChild(header);
//
// 			// Render day labels in the header
// 			var daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
// 			var labelsRow = document.createElement("tr");
// 			for (var i = 0; i < daysOfWeek.length; i++) {
// 				var labelCell = document.createElement("th");
// 				labelCell.textContent = daysOfWeek[i];
// 				labelsRow.appendChild(labelCell);
// 			}
// 			table.appendChild(labelsRow);
//
// 			// Render calendar cells
// 			var date = 1;
// 			for (var i = 0; i < 6; i++) {
// 				var row = document.createElement("tr");
//
// 				for (var j = 0; j < 7; j++) {
// 					var cell = document.createElement("td");
//
// 					if (i === 0 && j < firstDay) {
// 						// Render empty cells before the first day of the month
// 						cell.textContent = "";
// 					} else if (date > totalDays) {
// 						// Render empty cells after the last day of the month
// 						cell.textContent = "";
// 					} else {
// 						// Render cells with date values
// 						cell.textContent = date;
// 						cell.addEventListener("click", handleCellClick);
// 						cell.setAttribute("data-date", formatDate(date, month, year));
//
// 						// Highlight today's date
// 						if (date === currentDate.getDate() && month === currentDate.getMonth() && year === currentDate.getFullYear()) {
// 							cell.classList.add("selected");
// 						}
//
// 						date++;
// 					}
//
// 					row.appendChild(cell);
// 				}
//
// 				table.appendChild(row);
// 			}
//
// 			calendarContainer.innerHTML = "";
// 			calendarContainer.appendChild(table);
// 		}
//
// 		function handleCellClick(event) {
// 			var selectedCell = event.target;
// 			var selectedDate = selectedCell.getAttribute("data-date");
//
// 			// Remove previous selected cell
// 			var previousSelectedCell = document.querySelector(".selected");
// 			if (previousSelectedCell) {
// 				previousSelectedCell.classList.remove("selected");
// 			}
//
// 			// Add selected class to the clicked cell
// 			selectedCell.classList.add("selected");
//
// 			// Retrieve appointments for the selected date
// 			fetchAppointments(selectedDate);
// 		}
//
// 		function fetchAppointments(date) {
// 			// Make an AJAX request to retrieve appointments for the selected date
// 			var xhr = new XMLHttpRequest();
// 			xhr.open("GET", "/appointments?date=" + date, true);
//
// 			xhr.onreadystatechange = function() {
// 				if (xhr.readyState === 4             && xhr.status === 200) {
// 					var appointments = JSON.parse(xhr.responseText);
// 					displayAppointments(appointments);
// 				}
// 			};
//
// 			xhr.send();
// 		}
//
// 		function displayAppointments(appointments) {
// 			// Display the appointments on the page
// 			var appointmentsContainer = document.createElement("div");
//
// 			if (appointments.length === 0) {
// 				appointmentsContainer.textContent = "No appointments for this date.";
// 			} else {
// 				var appointmentsList = document.createElement("ul");
//
// 				appointments.forEach(function(appointment) {
// 					var appointmentItem = document.createElement("li");
// 					appointmentItem.textContent = appointment.title + " - " + appointment.time;
// 					appointmentsList.appendChild(appointmentItem);
// 				});
//
// 				appointmentsContainer.appendChild(appointmentsList);
// 			}
//
// 			calendarContainer.appendChild(appointmentsContainer);
// 		}
//
// 		function getMonthName(month) {
// 			var monthNames = [
// 				"January", "February", "March", "April", "May", "June",
// 				"July", "August", "September", "October", "November", "December"
// 			];
//
// 			return monthNames[month];
// 		}
//
// 		function formatDate(date, month, year) {
// 			var formattedMonth = month < 9 ? "0" + (month + 1) : (month + 1);
// 			var formattedDate = date < 10 ? "0" + date : date;
//
// 			return year + "-" + formattedMonth + "-" + formattedDate;
// 		}
// 	});


	// Shuffle js filter and masonry
    var Shuffle = window.Shuffle;
    var jQuery = window.jQuery;

    var myShuffle = new Shuffle(document.querySelector('.shuffle-wrapper'), {
        itemSelector: '.shuffle-item',
        buffer: 1
    });

    jQuery('input[name="shuffle-filter"]').on('change', function (evt) {
        var input = evt.currentTarget;
        if (input.checked) {
            myShuffle.filter(input.value);
        }
    });

})(jQuery);
