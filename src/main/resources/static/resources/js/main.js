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