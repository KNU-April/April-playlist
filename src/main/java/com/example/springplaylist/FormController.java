package com.example.springplaylist;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
public class FormController {
    @RequestMapping("/form")
    @ResponseBody
        public String form(@RequestParam("id") String id,
                       @RequestParam("pwd") String pwd){
        return "id :" + id + "    pwd :" + pwd;
    }

    @RequestMapping("/form2")
    @ResponseBody
    public String form2(@ModelAttribute MovieDto movie, @RequestParam("age") int age){
        return movie.toString() + "  age=" + age;
    }


    @RequestMapping("/form3")
    @ResponseBody
    public String form3(@RequestParam Map<String, String> map){
        return map.toString();
    }

    @RequestMapping("/form4")
    public String form4(Model model) {
        model.addAttribute("movie",
                new MovieDto(10, "제목", "test.jpg", "재미있음" ));
        return "form4";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("image") MultipartFile file, String title) throws IOException {
        if (file.isEmpty() ) {
            return "info : no file   title="+title;
        }
        file.transferTo(new File("d:/" + file.getOriginalFilename()));
        return "info : "  + file.getOriginalFilename() + "      title=" + title;
    }

    @RequestMapping(value="/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image() throws IOException {
        InputStream in =  new FileInputStream(new File("d:/food1.png"));
        return IOUtils.toByteArray(in);
    }

}
