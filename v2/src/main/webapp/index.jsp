<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes copains !</title>
</head>
<body>
<h1>Bienvenue sur Mes Copains !</h1>
<form method="post" action=<%= request.getContextPath() + "/Init" %>>
    <p>
        Entrez votre pseudo :
        <input type="text" name="pseudo">
        <input type="submit" value="Connexion">
    </p>
</form>
</body>
</html>