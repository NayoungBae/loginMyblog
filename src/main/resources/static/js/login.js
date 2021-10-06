$(document).ready(function() {
    isLogined();
    isErrorLogin();

    // $("#signup-a").click(function() {
    //     document.location.href="/user/signup";
    // });
});

function isErrorLogin() {
    let href = location.href;
    let queryString = href.substring(href.indexOf("?")+1);

    if(queryString === "error") {
        $("#errorMessage").text("닉네임 또는 비밀번호를 확인해주세요");
        //alert("아이디나 비밀번호를 잘못 입력하였거나, 회원이 아닙니다.");
        return;
    }
}

//로그인 되어있는 사용자가 로그인 화면으로 이동 시 alert
function isLogined() {
    let message = $("#isLoginedMessage").val();
    console.log(message);
    if(message != "") {
        alert(message);
        document.location.href="/";
        return;
    }
}

// function isRegister() {
//     let href = location.href;
//     let queryString = href.substring(href.indexOf("?")+1);
//
//     if(queryString === "fail") {
//         alert("회원가입에 실패하였습니다.");
//         $("#login-section").hide();
//         $("#signup-section").show();
//         return;
//     }
//     if(queryString === "error") {
//         alert("아이디나 비밀번호를 잘못 입력하였거나, 회원이 아닙니다.");
//         $("#login-section").hide();
//         $("#signup-section").show();
//         return;
//     }
// }