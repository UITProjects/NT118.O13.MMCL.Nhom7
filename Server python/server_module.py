import json
import socket
from cipher_module import AES_module
import threading
import handle_types_message_client_module
from random import randint


class handle_client_connection:
    def __init__(self, server_handle_client_socket: socket):
        self.server_handle_client_socket = server_handle_client_socket
        new_thread = threading.Thread(target=self.listen_mode)
        new_thread.start()

    def listen_mode(self):
        while True:
            client_message_dict = self.listen()
            if client_message_dict["type"] == "forgot_password":
                random_otp = str(randint(a=100000, b=999999))
                client_message_dict["random_otp"] = random_otp
                valid_user_email_dict = handle_types_message_client_module.process(client_message_dict)
                if valid_user_email_dict["status"] == "incorrect_username_email":
                    break
                else:
                    self.response_to_client({"type": "forgot_password", "status": "otp_sent"})
                otp_response_from_client_dict = self.listen()
                if otp_response_from_client_dict["otp"] == random_otp:
                    self.response_to_client({"type": "forgot_password", "otp_valid": "valid"})
                    new_password_from_client_dict = self.listen()
                    self.response_to_client(handle_types_message_client_module.process(new_password_from_client_dict))

                else:
                    self.response_to_client({"type": "forgot_password", "otp_valid": "invalid"})
            else:
                self.response_to_client(handle_types_message_client_module.process(client_message_dict))

    def response_to_client(self, message: dict):
        response_to_client_message_json_string = json.dumps(message)
        response_to_client_message_json_bytes = response_to_client_message_json_string.encode()
        response_to_client_message_header_int = len(response_to_client_message_json_bytes)
        response_to_client_message_header_bytes = response_to_client_message_header_int.to_bytes(4, "big")
        self.server_handle_client_socket.send(response_to_client_message_header_bytes)
        self.server_handle_client_socket.send(response_to_client_message_json_bytes)

    def listen(self) -> dict[str, str]:
        while True:
            header_length_bytearray = bytearray(4)
            try:
                self.server_handle_client_socket.recv_into(header_length_bytearray, 4)
            except ConnectionError:
                print("client force close connection")
                self.server_handle_client_socket.close()
                break

            header_length_int = int.from_bytes(header_length_bytearray, "big")
            buffer_data_bytearray = bytearray(header_length_int)
            bytes_received_int = 0
            while bytes_received_int < header_length_int:
                bytes_received_int = self.server_handle_client_socket.recv_into(buffer_data_bytearray,
                                                                                header_length_int - bytes_received_int)
            message_encrypted_str = buffer_data_bytearray.decode()
            message_plaintext_str = AES_module.decryt(message_encrypted_str)
            print(message_plaintext_str)
            client_message: dict = json.loads(message_plaintext_str)
            return client_message

    pass


class server_module:
    handle_client_connection_list: list[handle_client_connection] = []

    def __init__(self, server_address: str, server_port: int):
        self.SERVER_ADDRESS_TUPLE = (server_address, server_port)
        self.server_socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        self.server_socket.bind(self.SERVER_ADDRESS_TUPLE)

    def listen_establish_from_new_client(self, listen: bool):
        if listen:
            self.server_socket.listen(5)
            print("Server is listening")
            while True:
                new_client_socket, client_address = self.server_socket.accept()
                new_handle_client_connection = handle_client_connection(new_client_socket)
                server_module.handle_client_connection_list.append(new_handle_client_connection)
                print(f"connect from {client_address}")
