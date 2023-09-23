import ssl
import socket
import os

SERVER_ADDRESS = ('localhost', 2509)
SERVER_CERT = os.getenv("server-cert")
SERVER_KEY = os.getenv("server-key")
server_socket = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
server_socket.bind(SERVER_ADDRESS)
server_socket.listen(1)

print("Server listening")
while True:
    client_socket,client_address = server_socket.accept()
    print(f"connect from {client_address}")
    data = client_socket.recv(1024)
    message = data.decode("utf8")
    print(message)
