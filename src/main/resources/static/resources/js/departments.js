$(document).ready(function () {
	$("#searchForm").on('submit', function (event){
        event.preventDefault();

        let form = $(this);

        loadDataOfForm(form.serialize());
	});

    let pageLinks = $("#pageNav a")
    pageLinks.each((i, source) => {
        source.addEventListener('click', function(event) {
            event.preventDefault();
            loadDataOfUrl(event.target.getAttribute('href'));
        });
    });

    let btnClearForm = $('#btnClearForm')
    btnClearForm.on('click', function(event) {
        event.preventDefault();
        $("#keyword").val("");
        $("#searchForm").submit();
    });
});

