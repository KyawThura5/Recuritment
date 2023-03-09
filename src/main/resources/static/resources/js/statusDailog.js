function showChangeStatusDialog(id) {

    $.ajax({
        type: "GET",
        url: "/interview/status/change/api?id=" + id,
        success: function (data) {
            console.log(data);
            
            let form = $("#changeStatusForm");
            let applicantIdInput = form.find("input[name='id']");
            applicantIdInput.val(data.id);
            

            let statusSelect = form.find("select[name='status']");
            statusSelect.val(data.status);

            let remarkTextarea = form.find("textarea[name='comment']");
            remarkTextarea.val("");

            let modelElement=document.getElementById("changeStatusModal");
            modelElement.style.display = "block";
            window.onclick = function(event) {
                if (event.target == modelElement) {
                    modelElement.style.display = "none";
                }
            }

            let selectedStatus = data.status;
            let btnSubmit = $('#btnStatusSubmit');
            btnSubmit.addClass("disabled");
           $('#status1').on('change', function(event) {
                let changeValue = this.value;
                if (changeValue == selectedStatus) {
                    btnSubmit.addClass("disabled");
                } else {
                    btnSubmit.removeClass("disabled");
                }
            });
        },
        error: function (e) {
            console.log('error');
            console.log(e);
        }
    });
}
function closeModal(id) {
    let modelElement=document.getElementById(id);
    modelElement.style.display = "none";
}
