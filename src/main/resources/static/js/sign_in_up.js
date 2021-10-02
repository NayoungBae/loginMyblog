$(document).ready(function() {
    isRegister();

    $("#login-section").show();
    $("#signup-section").hide();

    $("#login-a").click(function () {
        $("#login-section").show();
        $("#signup-section").hide();
    });

    $("#login-btn").click(function () {

    });

    $("#signin-a").click(function () {
        $("#login-section").hide();
        $("#signup-section").show();
    });
});

function isRegister() {
    let href = location.href;
    let queryString = href.substring(href.indexOf("?")+1);

    if(queryString === "fail") {
        alert("회원가입에 실패하였습니다");
        $("#login-section").hide();
        $("#signup-section").show();
        return;
    }
}