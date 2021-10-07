$(document).ready(function() {
    $("#show-detail").show();
    $("#edit-post").hide();

    //상세보기 화면에서 수정 버튼 클릭
    $("#edit-btn").click(function () {
        $("#show-detail").hide();
        $("#edit-post").show();
    });

});