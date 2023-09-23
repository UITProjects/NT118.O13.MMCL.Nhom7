import ssl
import socket
import os
from encryt_module import AES_module

class Server_module:
    def __init__(self):
        self.SERVER_ADDRESS_TUPLE = ('localhost', 2509)
        self.server_socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        self.server_socket.bind(self.SERVER_ADDRESS_TUPLE)
        self.aes_module = AES_module(os.getenv("symmetric_key"))

    def listen(self, listen: bool):
        if listen:
            self.server_socket.listen(5)
            print("Server is listening")
            while True:
                client_socket, client_address = self.server_socket.accept()
                print(f"connect from {client_address}")
                while True:
                    header_length_bytearray = bytearray(4)
                    client_socket.recv_into(header_length_bytearray, 4)
                    header_length_int = int.from_bytes(header_length_bytearray, "big")
                    print(header_length_int)
                    buffer_data_bytearray = bytearray(header_length_int)
                    bytes_received_int = 0
                    while bytes_received_int<header_length_int:
                        bytes_received_int = client_socket.recv_into(buffer_data_bytearray,
                                                                     header_length_int - bytes_received_int)
                    message = buffer_data_bytearray.decode()
                    print(self.aes_module.decryt(message))
