<%-- 
    Document   : index
    Created on : 29/10/2018, 06:48:02 PM
    Author     : chelo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Almacenar Imagenes</title>
        <link rel="stylesheet" href="css/main.css">
        <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans:400,300'>
    </head>
    <body>
        <div id="login">
            <h1><strong>Bienvenido</strong></h1>
            <h1><strong>Login</strong></h1>
            <form action="Insertar" enctype="multipart/form-data" method="POST">
                <fieldset>
                    <p><input type="text" placeholder="Id" name="id" required></p>
                    <p><input type="text" placeholder="Nombre" name="nombre" required></p>
                    <p><input type="text" placeholder="Descripción" name="descripcion" required></p>
                    <p><input type="file" name="img" size="29" required></p>
                    <p><input type="submit" value="Guargar"></p>
                </fieldset>
            </form>
        </div>
    </body>
</html>
