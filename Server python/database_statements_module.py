
general_statements:dict[str,str] = {
    'authentication': "SELECT * FROM mobile_project.account where username_primary='{username_primary}' and hashed_password ='{hashed_password}' ",
    'insert': "INSERT INTO `mobile_project`.`account`(`username_primary`,`hashed_password`)"
              "VALUES('{username_primary}','{hashed_password}');"}
# print(general_statements["select"].format(username_primary="username1",hashed_password="mypassword"))
# print(general_statements["insert"].format(username_primary="username1",hashed_password="mypassword"))