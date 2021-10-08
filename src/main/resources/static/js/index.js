$(document).ready(function() {
    $("#pagination").hide(); //페이징 기능 제거

    //체크박스 하나 선택해서 게시물 삭제
    $("#delete-btn").click(function() {
        let checked = $("input:checkbox[name=check]").val();
        let checked_count = $("input:checkbox[name=check]:checked").length;
        if(checked_count > 1) {
            alert("하나만 선택해주세요");
            $("input:checkbox[name=check]").prop("checked", false);
            return;
        }
        if(checked_count == 0) {
            alert("삭제할 게시물을 선택해주세요");
            return;
        }
        if(checked_count == 1) {
            if (confirm("삭제하시겠습니까?")) {
                deletePost(checked);
            }
        }
    });

});

// 글 삭제
function deletePost(id) {
    $("#delete-form")
        .attr("action","/posts/post/" + id)
        .submit();
}
