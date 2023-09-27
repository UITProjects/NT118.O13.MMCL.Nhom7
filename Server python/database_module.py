import io

import mysql.connector
import os

from PIL import Image

from database_statements_module import general_statements
from hash_password_module import hash_password
USERNAME = os.getenv("username_mysql")
PASSWORD = os.getenv("password_mysql")
HOST = "app.mariadb.uitprojects.com"
PORT = 3306
DATABASE = "mobile_project"
SSL_CERT = os.getenv("ssl_client_cert")
SSL_key = os.getenv("ssl_client_key")

def access_database(statement:str,param_binary_data = None):
    mysql_connection = mysql.connector.connect(user=USERNAME, password=PASSWORD,
                                      host=HOST,
                                      database='mobile_project',
                                      ssl_cert=SSL_CERT,
                                      ssl_key=SSL_key,)
    execute_command_interpreter = mysql_connection.cursor()
    execute_command_interpreter.execute(statement,param_binary_data)
    response_tuple = execute_command_interpreter.fetchone()
    mysql_connection.commit()
    mysql_connection.close()
    return response_tuple


# image_byte_object = io.BytesIO(access_database("SELECT * FROM mobile_project.account where username_primary = 'test_account1';")[3])
# image = Image.open(image_byte_object)
# image.show()

# image_byte_object = io.BytesIO(access_database("SELECT * FROM mobile_project.account where username_primary = 'test_account2';")[3])
# image = Image.open(image_byte_object)
# image.show()