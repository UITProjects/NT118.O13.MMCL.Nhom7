import mysql.connector
import os
USERNAME = os.getenv("username_mysql")
PASSWORD = os.getenv("password_mysql")
HOST = "app.mariadb.uitprojects.com"
PORT = 3306
DATABASE = "mobile_project"
SSL_CERT = os.getenv("ssl_client_cert")
SSL_key = os.getenv("ssl_client_key")
print(SSL_CERT,SSL_key)
connect_mysql = mysql.connector.connect(user=USERNAME, password=PASSWORD,
                                  host=HOST,
                                  database='mobile_project',
                                  ssl_cert=SSL_CERT,
                                  ssl_key=SSL_key,
                                        )
if connect_mysql.is_connected():
    print("connected")
execute_command_interpreter = connect_mysql.cursor()
execute_command_interpreter.execute("SHOW TABLES;")
print(execute_command_interpreter.fetchall())
connect_mysql.close()