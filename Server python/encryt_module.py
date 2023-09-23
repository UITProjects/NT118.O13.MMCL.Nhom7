from Crypto.Cipher import AES
from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
import base64
from Crypto.Util.Padding import pad
from Crypto.Util.Padding import unpad
import os
key_bytes = get_random_bytes(32)
print(base64.b64encode(key_bytes).decode())
message_string = b"Ngo Vu Minh Dat"
cipher_ecbmode = AES.new(key_bytes, AES.MODE_ECB)
message_padding_bytes = pad(message_string, 16,"pkcs7")
encryted_message_bytes = cipher_ecbmode.encrypt(message_padding_bytes)

print (base64.b64encode(encryted_message_bytes).decode())
cipher_decrypt = AES.new(key_bytes, AES.MODE_ECB)
decrypted_message_bytes = cipher_decrypt.decrypt(encryted_message_bytes)
print(unpad(decrypted_message_bytes, 16).decode())
