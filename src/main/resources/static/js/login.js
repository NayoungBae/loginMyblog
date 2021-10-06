$(document).ready(function() {
    isErrorLogin();

    // $("#signup-a").click(function() {
    //     document.location.href="/user/signup";
    // });
});

function isErrorLogin() {
    let href = location.href;
    let queryString = href.substring(href.indexOf("?")+1);

    if(queryString === "error") {
        $("#errorMessage").text("닉네임 또는 패스워드를 확인해주세요");
        //alert("아이디나 비밀번호를 잘못 입력하였거나, 회원이 아닙니다.");
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