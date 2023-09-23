from encryt_module import AES_module
import os
SYMMETRIC_KEY = os.getenv("symmetric_key")

aes_module = AES_module(SYMMETRIC_KEY)
aes_module.encrypt("Ngô Vũ Minh Đạt")