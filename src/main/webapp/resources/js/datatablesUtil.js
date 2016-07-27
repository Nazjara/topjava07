function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.deleteMeal').click(function () {
        deleteMealRow($(this).parent().parent().attr("id"));
    });

    $('.deleteUser').click(function () {
        deleteUserRow($(this).parent().parent().attr("id"));
    });

    $('.checkbox').click(function () {
        changeStatus($(this).parent().parent().attr("id"));
    });

    $('#date_submit').click(function () {
        updateTableWithFilter();
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function changeStatus(attr) {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "enabled/"+attr,
        success: function () {
            updateTable();
            successNoty('Status has changed');
        }
    });
}

function updateTableWithFilter() {
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    $.get(ajaxUrl + "filter?startDate="+startDate+"&startTime="+startTime+"&endDate="+endDate+"&endTime="+endTime, function (data) {
        datatableApi.fnClearTable();
        $.each(data, function (key, item) {
            datatableApi.fnAddData(item);
        });
        datatableApi.fnDraw();
        successNoty('Filtered');
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.fnClearTable();
        $.each(data, function (key, item) {
            datatableApi.fnAddData(item);
        });
        datatableApi.fnDraw();
        successNoty('Updated');
    });
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

function deleteMealRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTableWithFilter();
            successNoty('Deleted');
        }
    });
}

function deleteUserRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 3000
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
