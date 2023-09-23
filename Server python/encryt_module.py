from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
import base64
from Crypto.Util.Padding import pad
from Crypto.Util.Padding import unpad
class AES_module:
    def __init__(self, key_encoded_base64_string:str = None):
        self.key_bytes = get_random_bytes(32)
        print(base64.b64encode(self.key_bytes).decode())
        self.key_bytes = base64.b64decode(key_encoded_base64_string)
        self.cipher_ecbmode = AES.new(self.key_bytes,AES.MODE_ECB)
    def encrypt(self, plaintext_string:str) ->str:
        plaintext_bytes = plaintext_string.encode()
        plaintext_padding_bytes = pad(plaintext_bytes,16)
        encrypted_plaintext_bytes = self.cipher_ecbmode.encrypt(plaintext_padding_bytes)
        encrypted_plaintext_base64_string = base64.b64encode(encrypted_plaintext_bytes).decode()
        return encrypted_plaintext_base64_string
    def decryt(self,ciphertext_string:str)->str:
        cipher_text_bytes = base64.b64decode(ciphertext_string)
        plaintext_bytes = self.cipher_ecbmode.decrypt(cipher_text_bytes)
        plaintexttext_unpadding_bytes = unpad(plaintext_bytes,16)
        plaintext_string = plaintexttext_unpadding_bytes.decode()
        return plaintext_string




