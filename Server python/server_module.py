import socket
from cipher_module import AES_module
import threading
threading.active_count()
class Server_module:
    def __init__(self):
        self.SERVER_ADDRESS_TUPLE = ('localhost', 2509)
        self.server_socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        self.server_socket.bind(self.SERVER_ADDRESS_TUPLE)
        self.thread_list = []

    def handle_client_connection(self,client_socket:socket):
        while True:
            header_length_bytearray = bytearray(4)
            try:
                client_socket.recv_into(header_length_bytearray, 4)
            except ConnectionError:
                print("client force close connection")
                client_socket.close()
                break

            header_length_int = int.from_bytes(header_length_bytearray, "big")
            print(header_length_int)
            buffer_data_bytearray = bytearray(header_length_int)
            bytes_received_int = 0
            while bytes_received_int < header_length_int:
                bytes_received_int = client_socket.recv_into(buffer_data_bytearray,
                                                             header_length_int - bytes_received_int)
            message_encrypted_str = buffer_data_bytearray.decode()
            message_plaintext_str = AES_module.decryt(message_encrypted_str)
            print(message_plaintext_str)
    def listen(self, listen: bool):
        if listen:
            self.server_socket.listen(5)
            print("Server is listening")
            while True:
                new_client_socket, client_address = self.server_socket.accept()
                new_thread = threading.Thread(target = self.handle_client_connection,args=(new_client_socket,))
                self.thread_list.append(new_thread)
                new_thread.start()
                print(f"connect from {client_address}")


