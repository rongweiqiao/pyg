package com.pyg.manager.controller;

import com.pyg.utils.FastDFSClient;
import com.pyg.utils.PygResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FilE_SERVER_URL;

    @RequestMapping("/upload")
    public PygResult upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf(".") + 1);
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/client.conf");
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);
            String url = FilE_SERVER_URL + path;
            return new PygResult(true, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "上传失败");
        }

    }
}
