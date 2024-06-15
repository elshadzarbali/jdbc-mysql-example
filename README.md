# jdbc-mysql-example

## [`DBUtil` class](src/main/java/org/example/util/DBUtil.java)
* Using `Class.forName` method in static block to load MySQL Driver class at runtime when `DBUtil` class is used fist time. So, driver class is loaded and registered once. We don't need additional method to load driver class every time we want to get connection.
* Since JDBC 4.0, we don't need to explicitly load driver class. But, it is good practice to load driver class explicitly for backward compatibility.
* Using `getConnection` method for getting Connection based on url, username and password.
* Using `handleSQLException` method to handle `SQLException`.

## `User` class