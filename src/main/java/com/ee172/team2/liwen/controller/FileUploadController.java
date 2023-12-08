//package com.ee172.team2.liwen.controller;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//
//@RestController
//public class FileUploadController {
//
//    @PostMapping("/upload")
//    public Map<String, String> handleFileUpload(
//            @RequestParam("file") MultipartFile file) throws IOException {
//
//        Map<String, String> response = new HashMap<>();
//
//        // 驗證檔案大小
//        if (file.getSize() > 5 * 1024 * 1024) { // 5MB限制
//            response.put("error", "檔案大小超過限制");
//            return response;
//        }
//
//        // 驗證圖片類型
//        String contentType = file.getContentType();
//        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
//            response.put("error", "不支援的檔案類型");
//            return response;
//        }
//
//        
//
//       
//
//        response.put("message", "文件上傳成功");
//        return response;
//    }
//}