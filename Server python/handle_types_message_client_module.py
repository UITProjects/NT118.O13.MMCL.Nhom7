import base64
import mysql.connector.errors

import server_core
import smtp
from database_statements_module import general_statements
import database_module
from cipher_module import hash_password


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
    database_module.access_database(fullstatement, (image_bytes,))
    return {"type": "upload_image_profile", "status": "success"}


def load_profile_image(argument: dict):
    fullstatement:str = general_statements["load_profile_image"].format(username_primary=argument["username_primary"])
    # fullstatement: str = general_statements["load_profile_image"].format(username_primary="test_account2")

    response_from_mysql = database_module.access_database(fullstatement)
    load_image_bytes: bytes = response_from_mysql[3]
    server_core.Handle_android_app_socket.large_data = load_image_bytes
    return {"type": "load_profile_image", "status": "pending_download", "large_file_size": str(len(load_image_bytes)),"large_data":"true"}


type_client_message = {
    "authentication": authentication,
    "create_account": create_account,
    "forgot_password": forgot_password,
    "upload_image_profile": upload_image_profile,
    "load_profile_image":load_profile_image
}


# forgot_password()


def process(client_message: dict):
    return type_client_message[client_message["type"]](client_message)
