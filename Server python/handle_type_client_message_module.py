from database_statements_module import general_statements
import database_module
from hash_password_module import hash_password

def authentication(argument:dict) -> dict[str, str]:
    print(argument["username_primary"],argument["password"])
    full_statement = general_statements["authentication"].format(username_primary=argument["username_primary"],
                                                                 hashed_password=hash_password(argument["password"]))
    response_from_mysql = database_module.access_database(full_statement)
    if len(response_from_mysql) != 0:
        print(len(response_from_mysql))
        return {"login": "success"}
    else:
        return {"login": "failed"}


type_client_message = {"authentication": authentication}


def process(client_message:dict):
    print(client_message["type"])
    return type_client_message[client_message["type"]](client_message)


#process("authentication", "test", "123456789")
