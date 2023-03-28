$(document).ready(function () {

    let alert = document.getElementById("alert");
    if(alert) {
        let modelElement=document.getElementById("alert");
        modelElement.style.display = "block";
        window.onclick = function(event) {
            if (event.target == modelElement) {
                modelElement.style.display = "none";
            }
        }
        window.setTimeout(closeAlert, 5000);
    }

    $("#applicantStatusCheck").on("change", function(event) {
        if (this.checked) {
            $("#applicantStatusWrapper").removeClass("d-none");
        } else {
            $("#applicantStatusWrapper").addClass("d-none");
        }
    });

    pareDateFromTo("dateFrom", "dateTo");

});

    

function closeAlert() {
    let modelElement=document.getElementById("alert");
    modelElement.style.display = "none";
}
