$(document).ready(function() {
    isRegister();


    $("#login-a").click(function () {
        document.location.href="/user/login";
    });
});

function isRegister() {
    let href = location.href;
    let queryString = href.substring(href.indexOf("?")+1);

    if(queryString === "fail") {
        alert("회원가입에 실패하였습니다.");
        $("#login-section").hide();
        $("#signup-section").show();
        return;
    }
    if(queryString === "error") {
        alert("아이디나 비밀번호를 잘못 입력하였거나, 회원이 아닙니다.");
        $("#login-section").hide();
        $("#signup-section").show();
        return;
    }
}