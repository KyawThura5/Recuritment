function closeAlert() {
    let modelElement=document.getElementById("alert");
    modelElement.style.display = "none";
}


function closeModal(id) {
    let modelElement=document.getElementById(id);
    modelElement.style.display = "none";
}

function changePageSize() {
    $("#searchForm").submit();
}

function pareDateFromTo(dateFromId, dateToId) {
    let dateFromInput = document.getElementById(dateFromId);
    let dateToInput = document.getElementById(dateToId);
    let dateFrom = dateFromInput.value;
    let dateTo = dateToInput.value;
    
    if (dateFrom) {
        dateToInput.min = dateFrom;
    }

    if (dateTo) {
        dateFromInput.max = dateTo;
    }

}