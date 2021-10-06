$(document).ready(function() {
    $("#write-post").click(function() {
        let title = $("#write-title").val();
        let name = $("#write-name").val();
        let content = $("#write-content").val();
        console.log("title: " + title + ", name: " + name + ", content: " + content);
        $.ajax({
            url: "/posts/post",
            contentType: "POST",
            data: {
                title: title,
                name: name,
                content: content
            },
            success: function() {
                document.location.href = "/";
            }
        });
    });
});