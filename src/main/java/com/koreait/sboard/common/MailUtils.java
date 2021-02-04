package com.koreait.sboard.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {
	private String userId;
	private String userPw;
	private String host;
	private String port;
	private String fromEmail;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public int sendFindPwEmail(final String toEmail, final String user_id, final String code) {
		String subject = "sboard 비밀번호 찾기 인증 이메일 입니다.";
		StringBuilder sb = new StringBuilder("<div>"); // for문에서 str을 더할땐 StringBuilder 쓰는게 메모리적으로 절약
		// 체인 기법
		sb.append("<a href=\"http://localhost:8090/user/findPwAuth?cd=")
		.append(code)
		.append("&user_id=")
		.append(user_id)
		.append("\" target=\"_blank\">비밀번호 변경하러 가기</a>") // target="_blank" 새창 띄우기
		.append("</div>");

		// <a href="http://localhost:8090/user/findPwAuth?cd=1234&user_id=mic" target="_blank">비밀번호 변경하러 가기</a></div>
		String body = String.format("<div>안녕하세요 코드는 %s입니다.</div>", code);
		return sendMail(toEmail, subject, sb.toString());
	}

	public int sendMail(final String toEmail, final String subject, final String body) {
		try {
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.ssl.trust", host);

			// Session 생성
			Session session = Session.getDefaultInstance(prop, new Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(userId, userPw);
				}
			});
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(body, "text/html; charset=UTF-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}