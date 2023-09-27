import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import os
SMTP_HOST = "smtp.gmail.com"
SMTP_PORT = 587
SMTP_USERNAME = os.getenv("SMTP_USERNAME")
SMTP_PASSWORD = os.getenv("SMTP_PASSWORD")
sender_email = 'mobile@uitprojects.com'
def send_email_otp(random_otp:str,to_recipient:str = "ngovuminhdat@gmail.com"):
    message = MIMEMultipart()
    message['From'] = sender_email
    message['Subject'] = "Recovery password"
    with open(file='otp.html', mode='r', encoding='utf-8') as file_reader:
        html_content_string = file_reader.read()
    message['To'] = to_recipient
    recovery_email_html_string = html_content_string.format(random_otp=random_otp)

    message.attach(MIMEText(recovery_email_html_string, 'html'))
    with smtplib.SMTP(SMTP_HOST, SMTP_PORT) as server:
        server.starttls()
        server.login(SMTP_USERNAME, SMTP_PASSWORD)
        server.send_message(message)