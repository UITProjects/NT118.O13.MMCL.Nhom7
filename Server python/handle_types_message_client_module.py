import base64
import io
import random
from socket import socket

import mysql.connector.errors
from PIL import Image

import server_core
import smtp
from database_statements_module import general_statements
import database_module
from hash_password_module import hash_password

otp_valid = False


def authentication(argument: dict) -> dict[str, str]:
    full_statement = general_statements["authentication"].format(username_primary=argument["username_primary"],
                                                                 hashed_password=hash_password(argument["password"]))
    response_from_mysql = database_module.access_database(full_statement)
    if response_from_mysql is not None:
        return {"type": "login", "status": "success"}
    else:
        return {"type": "login", "status": "failed"}


def create_account(argument: dict) -> dict[str, str]:
    full_statement = general_statements["create_account"].format(username_primary=argument["username_primary"],
                                                                 hashed_password=hash_password(argument["password"]),
                                                                 email=argument["email"]
                                                                 )
    try:
        database_module.access_database(full_statement)
    except mysql.connector.errors.IntegrityError:
        return {"create_account": "failed_because_username_exist"}
    return {"create_account": "successful"}


def forgot_password(argument: dict = None):
    if argument["otp_valid"] == "none":
        full_statement = general_statements["forgot_password"].format(email=argument["email"],
                                                                      username_primary=argument["username_primary"])
        response_from_mysql = database_module.access_database(full_statement)
        if response_from_mysql is not None:
            smtp.send_email_otp(argument["random_otp"], argument["email"])
            return {"type": "forgot_password", "status": "correct_username_email"}

        else:
            return {"type": "forgot_password", "status": "incorrect_username_email"}
    elif argument["otp_valid"] == "valid":
        full_statement = general_statements["change_new_password"].format(username_primary=argument["username_primary"],
                                                                          email=argument["email"],
                                                                          new_hashed_password=hash_password(
                                                                              argument["new_password"]))
        try:
            database_module.access_database(full_statement)
        except mysql.connector.errors.IntegrityError:
            pass
        return {"type": "forgot_password", "status": "change_password_success"}


def upload_image_profile(argument: dict):
    image_encoded_base64_string: str = argument["image_encoded_base64_string"]
    image_bytes = base64.b64decode(image_encoded_base64_string)
    fullstatement = general_statements["upload_image_profile"].format(username_primary=argument["username_primary"])
    param_image_bytes = (image_bytes,)
    database_module.access_database(fullstatement, (image_bytes,))
    return {"type": "upload_image_profile", "status": "success"}


type_client_message = {
    "authentication": authentication,
    "create_account": create_account,
    "forgot_password": forgot_password,
    "upload_image_profile": upload_image_profile
}


# forgot_password()


def process(client_message: dict):
    return type_client_message[client_message["type"]](client_message)
