package org.afive.wecheck.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.res.FilePathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileService {

	public FileBean fileUpload(MultipartFile uploadFile, String path, String key) throws IllegalStateException, IOException{
		return upload(uploadFile, path, key);
	}
	
	//업로드
    public FileBean upload(MultipartFile upLoadFile, String path, String key) throws IllegalStateException, IOException{
        File dir = new File(path);

        if (upLoadFile == null) {
        	return null;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }

        long fileSize = upLoadFile.getSize();

        String readFilePath = "";
        if (fileSize > 0) {
            //업로드 경로
            readFilePath = path + "/" + key + "/" + upLoadFile.getOriginalFilename();

            //폴더 경로 없을 경우 폴더 만들기
            File targetDir = new File(path + "/" + key);
            if(!targetDir.exists()){
                targetDir.mkdirs();
            }

            //업로드
            FileCopyUtils.copy(upLoadFile.getBytes(), new FileOutputStream(new File(readFilePath)));

            FileBean fileBean = new FileBean();
            fileBean.setFileName(upLoadFile.getOriginalFilename());
            fileBean.setFilePath(FilePathResource.SERVER_URL + readFilePath.replace(FilePathResource.COMMON_PATH, ""));
            return  fileBean;
        }

        return null;
    }
    
    public boolean fileDelete(String filePath) {
    	
    	System.out.println("fileDelete에 들어옴 받은 파일 경로 : "+filePath);
    	boolean check = false;
    	
    	filePath=filePath.replace("http://we-check.org", "");
    	filePath=FilePathResource.COMMON_PATH+filePath;
 //   	filePath="/usr/local/tomcat"+filePath;
    	
    	System.out.println("path 변경시킴 : "+filePath);
    	File file2 = new File(filePath);
    	if(file2.exists()) {
    		System.out.println("파일 존재합니당");
    		check = file2.delete();
    	}else { 
    		
    		System.out.println("파일 존재 안하구");
    	}
    	
    	System.out.println("지웠는지 결과값 : "+check);
    	return check;
    }
}