<%
    String cp = request.getContextPath();
%>
<div class="btn-group">
    <a class="btn" href="<%=cp%>/schedule">
        <i class="fa fa-clock-o"></i>
        <br/>
        Schedule
    </a>
    <a class="btn" href="<%=cp%>/join">
        <i class="fa fa-users"></i>
        <br/>
        Join Meeting
    </a>
    <a class="btn" href="<%=cp%>/list">
        <i class="fa fa-list-alt"></i>
        <br/>
        List Meeting
    </a>
</div>
<script type="text/javascript">
    String.prototype.endsWith = String.prototype.endsWith || function (suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };

    $('.btn-group').find('a.btn').removeClass('sel');
    $('.btn-group').find('a.btn').each(function (i, el) {
        el = $(el);
        if(location.href.endsWith(el.attr('href'))) {
            el.addClass('sel');
            return false;
        }
    });

    $(document.body).css('padding-bottom', $('.btn-group').outerHeight());
</script>