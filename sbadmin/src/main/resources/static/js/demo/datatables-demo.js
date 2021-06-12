// Call the dataTables jQuery plugin
$(document).ready(function() {
    var table = $('#dataTable').DataTable({
      "ajax": {
          url: "/listtAllAccount",
          dataSrc: ''
      },
      "columns": [
          { "data": "accountId", "width": "10%", "class": "context-menu-one"},
          { "data": "userName", "class": "context-menu-one"},
          { "data": "password", "class": "context-menu-one"},
          { "data": "email", "class": "context-menu-one"},
          { "data": "status", "width": "10%", "class": "context-menu-one"},
          { "data": "role", "width": "10%", "class": "context-menu-one"}
      ]
  });

    $.contextMenu({
        selector: '.context-menu-one',
        callback: function(key, options) {
            if (key == "edit") {
                clearDataModal();
                var data = table.row(this).data();
                setDataModal(data);
                $("#addModal").modal('show');
            }
            if (key == "delete") {
                var data = table.row(this).data();
                $("#accountIdDelete").html(data.accountId);
                $("#userNameIdDelete").html(data.userName);
                $("#deleteModal").modal('show');
            }
        },
        items: {
            "edit": {name: "Edit", icon: "edit"},
            "delete": {name: "Delete", icon: "delete"},
        }
    });

    $("#btn_create").click(function () {
        clearDataModal();
        $("#addModal").modal('show');
    })

    $("#btn_add_account").click(function () {
        var accountId = $("#inputAccountId").val();
        var email = $("#inputEmail").val();
        var userName = $("#inputUserName").val();
        var password = $("#inputPassword").val();
        var passwordRepeat = $("#inputPasswordRepeat").val();
        var role = $("#checkRole").is(":checked")? "1" : "0";
        var status = $("#selectStatus").val();

        if (passwordRepeat.localeCompare(password) != 0) {
            // Repeat pasword error
        }

        var account = {
            "accountId": accountId,
            "email": email,
            "userName": userName,
            "password": password,
            "role": role,
            "status": status
        };

        console.log(account);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/accounts/add",
            dataType: "json",
            data: JSON.stringify(account),
            success: function (data) {
                console.log(data);
                $("#addModal").modal('hide');
                var notification = alertify.notify('Thêm mới tài khoản thành công', 'success', 5, function(){  console.log('dismissed'); });
                //table.reload();
            },
            error: function (msg) {
                console.log(msg);
                $("#addModal").modal('hide');
                var notification = alertify.notify('Thêm mới tài khoản thất bại', 'error', 5, function(){  console.log('dismissed'); });
            }
        }).done(function (result) {
            $('#dataTable').DataTable().ajax.reload();
        });
    })

    $("#btn_delete_account").click(function () {
        var accountId = $("#accountIdDelete").html();
        $.ajax({
            type: "DELETE",
            url: "/accounts/delete/" + accountId,
            /*dataType: "json",
            data: JSON.stringify(account),*/
            success: function (msg) {
                $("#deleteModal").modal('hide');
                var notification = alertify.notify('Xóa tài khoản thành công', 'success', 5, function(){  console.log('dismissed'); });
            },
            error: function (msg) {
                $("#deleteModal").modal('hide');
                var notification = alertify.notify('Xóa tài khoản thất bại', 'error', 5, function(){  console.log('dismissed'); });
            }
        }).done(function (result) {
            $('#dataTable').DataTable().ajax.reload();
        });
    })
    
    function setDataModal(data){
        $("#inputAccountId").val(data.accountId);
        $("#inputEmail").val(data.email);
        $("#inputUserName").val(data.userName);
        $("#inputPassword").val(data.password);
        $("#checkRole").prop("checked", data.role == "1" ? true : false);
        $("#selectStatus").val(data.status);
    }

    function clearDataModal(){
        $("#inputAccountId").val("");
        $("#inputEmail").val("");
        $("#inputUserName").val("");
        $("#inputPassword").val("");
        $("#checkRole").prop("checked", false);
        $("#selectStatus").val("1");
    }
});
