<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

    <link href="https://fonts.googleapis.com/css?family=Fredoka+One|Roboto:300,400" rel="stylesheet">
    <link rel="stylesheet" href="../css/bootstrap4-hello-world.min.css">
</head>

<body>
<div class="jumbotron jumbotron-fullheight jumbo-vertical-center text-light text-center bg-dark mb-0 radius-0">
    <div class="container">
        <h3 class="display-2 text-light text-uppercase">Choose the Repo to set on fire!</h3>
        <%
            String profile_msg = (String) request.getAttribute("profile_msg");
            if (profile_msg != null) {
                out.print(profile_msg);
            }
            String login_msg = (String) request.getAttribute("login_msg");
            if (login_msg != null) {
                out.print(login_msg);
            }
        %>
        <br/>
        <form action="/lightitup" method="get">
            <span class="lead lead-lg">Username: </span><input class="lead lead-lg" type="text" name="login"/><br/><br/>
            <span class="lead lead-lg">Repository: </span><input class="lead lead-lg" type="text" name="repo"/><br/><br/>
            <input class="btn btn-danger btn-lg m-2 btn-wide" type="submit" value="Light It Up!"/>
        </form>
    </div>
</div>
</body>
</html>