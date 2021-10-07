$(document).ready(function() {
    //작성자 자동 입력
    if($("#write-name").val() != null) {
        $("#write-name").attr("readonly", true);
    }
});

function validation() {
    let title = $("#write-title").val();
    let content = $("#write-content").val();

    if(title == null) {
        alert("제목을 입력하세요.");
        $("#write-title").focus();
        return;
    }

    if(content == null) {
        alert("내용 입력하세요.");
        $("#write-content").focus();
        return;
    }
}