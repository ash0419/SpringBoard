package com.koreait.sboard.common;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {

	@Autowired
	private ServletContext ctx; // application 객체

	// base경로 리턴
	public String getBasePath(String more) {
		return ctx.getRealPath(more); // 상대경로 getServletContext : 객체
	}

	// 경로 폴더 만들기
	public void makeDirectories(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	// 확장자 리턴
	public String getExt(String fileNm) {
//		return FilenameUtils.getExtension(fileNm);
//		String[] arrExt = fileNm.split(".");
//		String ext = arrExt[arrExt.length - 1];

		return fileNm.substring(fileNm.lastIndexOf(".") + 1);
	}

	public String getRandomFileNm() {
		return UUID.randomUUID().toString();
	}

	public String getRandomFileNm(String originalFileNm) {
		return getRandomFileNm() + "." + getExt(originalFileNm);
	}

	public String saveFile(MultipartFile mf, String folder) {
		String basePath = getBasePath(folder);
		makeDirectories(basePath);
		String fileNm = getRandomFileNm(mf.getOriginalFilename());

		try {
			File file = new File(basePath, fileNm);
			mf.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fileNm;
	}
}