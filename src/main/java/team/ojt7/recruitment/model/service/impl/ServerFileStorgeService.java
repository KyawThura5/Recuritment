package team.ojt7.recruitment.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public class ServerFileStorgeService {
	
	@Autowired
	private HttpSession session;

	public String store(String folder, String fileNamePrefix, CommonsMultipartFile file) {
		
		try {
			String serverPath = session.getServletContext().getRealPath("/");
			String originalFileName = file.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
			String fileName = fileNamePrefix + extension;
			Path folderPath = Path.of(serverPath, folder);
			Path filePath = Path.of(serverPath, folder, fileName);
			if (!Files.exists(folderPath)) {
				Files.createDirectories(folderPath);
			}
			file.transferTo(filePath);
			return folder + File.separator + fileName;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(String path) {
		try {
			String serverPath = session.getServletContext().getRealPath("/");
			Path filePath = Path.of(serverPath, path);
			if (Files.exists(filePath)) {
					Files.delete(filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
