from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
import base64
from Crypto.Util.Padding import pad
class AES_module:
    def __init__(self, key_encoded_base64_string:str = None):
        self.key_bytes = get_random_bytes(32)
        print(base64.b64encode(self.key_bytes).decode())
        self.key_bytes = base64.b64decode(key_encoded_base64_string)
        self.cipher_ecbmode = AES.new(self.key_bytes,AES.MODE_ECB)
    def encrypt(self,message_string:str):
        message_bytes = message_string.encode()
        message_padding_bytes = pad(message_bytes,16)
        encrypted_message_bytes = self.cipher_ecbmode.encrypt(message_padding_bytes)
        encrypted_message_base64_string = base64.b64encode(encrypted_message_bytes).decode()
        print(encrypted_message_base64_string)





