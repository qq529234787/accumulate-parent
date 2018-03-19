function selectPage(obj) {
    var pageNum = $(obj).val();
    $("#currentPage").val(pageNum);
    $("#seachForm").submit();
}

function nextPage() {
    $("#currentPage").val(parseInt($("#currentPage").val()) + 1);
    $("#seachForm").submit();
}

function prevPage() {
    $("#currentPage").val($("#currentPage").val() - 1);
    $("#seachForm").submit();
}
