let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let selectYear = document.getElementById("year");
let selectMonth = document.getElementById("month");

let months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

let monthAndYear = document.getElementById("monthAndYear");
showCalendar(currentMonth, currentYear);


function next() {
    currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

function previous() {
    currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
    currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

function showCalendar(month, year) {

    let firstDay = (new Date(year, month)).getDay();
    let daysInMonth = 32 - new Date(year, month, 32).getDate();

    let tbl = document.getElementById("calendar-body"); // body of the calendar

    // clearing all previous cells
    tbl.innerHTML = "";

    // filing data about month and in the page via DOM.
    monthAndYear.innerHTML = months[month] + " " + year;
    selectYear.value = year;
    selectMonth.value = month;

    // creating all cells
    let date = 1;
    for (let i = 0; i < 6; i++) {
        // creates a table row
        let row = document.createElement("tr");

        //creating individual cells, filing them up with data.
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                let cell = document.createElement("td");
                let cellText = document.createTextNode("");
                cell.appendChild(cellText);
                row.appendChild(cell);
            }
            else if (date > daysInMonth) {
                break;
            }

            else {
                let cell = document.createElement("td");
                cell.height = 100;
                cell.width = "14.28%";
                cell.style.minHeight = 100;
                let cellText = document.createTextNode(date);
                let dateDiv = document.createElement("div");
                dateDiv.innerText = date;
                if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                    cell.style.backgroundColor = "#ebffba";
                } // color today's date
                cell.appendChild(dateDiv);

                let newInt = document.getElementById("newInterviewByDate" + date);
                if (newInt) {
                    let newIntCount = newInt.innerText;
                    let div = document.createElement("label");
                    div.classList.add("badge", "bg-success", "mx-1")
                    div.innerText = newIntCount;
                    cell.appendChild(div);
                }

                let pastInt = document.getElementById("pastInterviewByDate" + date);
                if (pastInt) {
                    let pastIntCount = pastInt.innerText;
                    let div = document.createElement("label");
                    div.classList.add("badge", "bg-secondary", "mx-1")
                    div.innerText = pastIntCount;
                    cell.appendChild(div);
                }
                
                
                row.appendChild(cell);
                date++;
            }


        }

        tbl.appendChild(row); // appending each row into calendar body.
    }

}