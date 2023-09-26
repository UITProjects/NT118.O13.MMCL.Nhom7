import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

SMTP_HOST = "smtp.gmail.com"
SMTP_PORT = 587
SMTP_USERNAME = "21521935@gm.uit.edu.vn"
SMTP_PASSWORD = "fbqcvsehdtdejehb"
sender_email = 'mobile@uitprojects.com'
message = MIMEMultipart()
message['From'] = sender_email
message['Subject'] = "Recovery password"


def send_email_otp(random_otp:str,to_recipient:str = "ngovuminhdat@gmail.com"):
    with open(file='otp.html', mode='r', encoding='utf-8') as file_reader:
        html_content_string = file_reader.read()
    message['To'] = to_recipient
    recovery_email_html_string = html_content_string.format(random_otp=random_otp)

    message.attach(MIMEText(recovery_email_html_string, 'html'))
    with smtplib.SMTP(SMTP_HOST, SMTP_PORT) as server:
        server.starttls()
        server.login(SMTP_USERNAME, SMTP_PASSWORD)
        server.send_message(message)
