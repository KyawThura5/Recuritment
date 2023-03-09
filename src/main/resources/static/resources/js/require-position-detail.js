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


});

function closeAlert() {
    let modelElement=document.getElementById("alert");
    modelElement.style.display = "none";
}

function showModal(id) {
    let modelElement=document.getElementById(id);
    modelElement.style.display = "block";
    window.onclick = function(event) {
        if (event.target == modelElement) {
            modelElement.style.display = "none";
        }
    }
}

function closeModal(id) {
    let modelElement=document.getElementById(id);
    modelElement.style.display = "none";
}

function changePageSize() {
      $("#searchForm").submit();
}

function showChangeStatusDialog(id) {

    $.ajax({
        type: "GET",
        url: "/applicant/requirePositionDetail/status/change/api?id=" + id,
        success: function (data) {
            console.log(data);
            
            let form = $("#changeStatusForm");
            let applicantIdInput = form.find("input[name='applicantId']");
            applicantIdInput.val(data.applicantId);
            

            let statusSelect = form.find("select[name='status']");
            statusSelect.val(data.status);
            statusSelect.prop("disabled", !data.updatable);

            let remarkTextarea = form.find("textarea[name='remark']");
            remarkTextarea.val("");
            remarkTextarea.prop("disabled", !data.updatable);

            let modelElement=document.getElementById("changeStatusModal");
            modelElement.style.display = "block";
            window.onclick = function(event) {
                if (event.target == modelElement) {
                    modelElement.style.display = "none";
                }
            }

            let selectedStatus = data.status;
            let btnSubmit = $('#btnStatusSubmit');
            btnSubmit.prop("disabled", !data.updatable);
            btnSubmit.addClass("disabled");
            $('#status').on('change', function(event) {
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