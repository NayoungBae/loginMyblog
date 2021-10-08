let post_user_id_wrote = {};

$(document).ready(function() {
    //댓글 조회
    getComments($("#post-id").val());
    console.log(post_user_id_wrote);

    $("#show-detail").show();
    $("#edit-post").hide();

    //상세보기 화면에서 수정 버튼 클릭
    $("#edit-btn").click(function () {
        $("#show-detail").hide();
        $("#edit-post").show();
    });

    $("#write-comment-btn").click(function () {
        writePost();
    });
});

function getComments(postId) {
    $.ajax({
        url:"/comments/comment/" + postId,
        type:"GET",
        success: function(response) {
            if(response.length > 0) {
                for(let i=0; i<response.length; i++) {
                    console.log(response);
                    let id = response[i]["id"];
                    let user_id = response[i]["userId"];
                    let name = response[i]["name"];
                    let comment = response[i]["comment"];
                    let modified_at = response[i]["modifiedAt"];
                    console.log(id, user_id, name, comment, modified_at);

                    addCommentsHTML(id, user_id, name, comment, modified_at);
                }
            }
        }
    });
}

//댓글 형태 만들기
function addCommentsHTML(id, user_id, name, comment, modified_at) {
    let current_logined_user_id = $("#logined-user-id").val();
    let temp_html = ``;
    let temp_html_1 =
        `<article class="message">
            <div class="comment-info">
              <input type="hidden" id="${id}-user-id" value="${user_id}">
              <div id="${id}-name" class="comment-name">${name}</div>&nbsp;&nbsp;|&nbsp;&nbsp;
              <div class="comment-createdate">${modified_at}</div>`
    let temp_edit_delete_html =
              `<div id="${id}-comment-edit-delete-div" class="comment-edit-delete-div">
                <a id="delet-comment" onclick="deleteComment('${id}')">삭제</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                <a onclick="editMode('${id}')">수정</a>
              </div>`
    let temp_html_2 =
            `</div>
            <div class="message-body">
              <pre id="${id}-comment">${comment}</pre>
              <div class="edit-comment-textarea-div" id="${id}-edit-comment-div" hidden>
                <textarea id="${id}-edit-comment" class="textarea"></textarea>
                <div class="edit-comment-a-div" >
                    <a onclick="cancelEditComment('${id}')">취소</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a onclick="editComment('${id}')">저장</a>
                </div>
              </div>
            </div>
          </article>`;

    //현재 로그인한 계정이 쓴 댓글일 경우 수정/삭제 보이게 하기
    if(current_logined_user_id == user_id) {
        temp_html = temp_html_1 + temp_edit_delete_html + temp_html_2;
    } else {
        temp_html = temp_html_1 + temp_html_2;
    }
    $("#comments-div").append(temp_html);
}

//댓글 쓰기
function writePost() {
    //중복 작성 방지
    $("#write-comment-btn").attr("disabled", true);

    let userId = $("#logined-user-id").val();
    let postId = $("#post-id").val();
    let name = $("#logined-name").val();
    let comment = $("#comment-textarea").val();

    if(comment == "") {
        alert("댓글 내용을 입력해주세요.");
        $("#comment-textarea").focus();
        $("#write-comment-btn").attr("disabled", false);
        return;
    }
    console.log(userId, postId, comment);

    let data = {userId:userId, postId:postId,
                name:name, comment:comment};
    $.ajax({
        url:"/comments/comment",
        type:"POST",
        contentType:"application/json",
        data:JSON.stringify(data),
        success: function(response) {
            console.log(response);
            window.location.reload();
        },
        error: function(error) {
            alert("로그인이 필요한 기능입니다.");
            window.location.replace("/user/login");
        }
    });
}

//댓글 수정 부분 열기
function editMode(id) {
    $(`#${id}-edit-comment-div`).show();
    $(`#${id}-comment`).hide();
}

//댓글 수정하기
function editComment(id) {
    let userId = $(`#${id}-user-id`).val();
    let postId = $("#post-id").val();
    let name = $(`#${id}-name`).text();
    let comment = $(`#${id}-edit-comment`).val();
    let pre_comment = $(`#${id}-comment`).text();

    console.log(userId, postId, name, comment, pre_comment);

    let data = {};
    if(comment == "") {
        data = {userId:userId, postId:postId, name:name, comment:pre_comment};
    } else {
        data = {userId:userId, postId:postId, name:name, comment:comment};
    }

    $.ajax({
        url:"/comments/comment/" + id,
        type:"PUT",
        contentType:"application/json",
        data:JSON.stringify(data),
        success: function(response) {
            console.log(response);
            window.location.reload();
        }
    });
}

//댓글 수정 취소
function cancelEditComment(id) {
    $(`#${id}-edit-comment`).val("");
    $(`#${id}-edit-comment-div`).hide();
    $(`#${id}-comment`).show();
}

//댓글 삭제
function deleteComment(id) {
    if(confirm("삭제하시겠습니까?")) {
        $.ajax({
            url:"/comments/comment/" + id,
            type:"DELETE",
            contentType:"application/json",
            success: function(response) {
                window.location.reload();
            }
        });
    }
}