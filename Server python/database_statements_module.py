general_statements: dict[str, str] = {
    'authentication': "SELECT * FROM mobile_project.account "
                      "where username_primary='{username_primary}' and "
                      "hashed_password ='{hashed_password}' ",
    'create_account': "INSERT INTO `mobile_project`.`account`(`username_primary`,`hashed_password`,`email`)"
                      "VALUES('{username_primary}','{hashed_password}','{email}');",
    'forgot_password': "SELECT * FROM mobile_project.account "
                       "where email = '{email}' and username_primary = '{username_primary}'",
    'change_new_password': "UPDATE `mobile_project`.`account` "
                           "SET `hashed_password` = '{new_hashed_password}' "
                           "WHERE (`username_primary` = '{username_primary}' and `email` = '{email}');",
    'upload_image_profile': "UPDATE `mobile_project`.`account`"
                            " SET `image_profile` = %s "
                            "WHERE (`username_primary` = '{username_primary}');",
    'load_profile_image': "SELECT * FROM mobile_project.account where username_primary = '{username_primary}';"
}
