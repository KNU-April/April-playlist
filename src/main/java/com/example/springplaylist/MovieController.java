//package com.example.springplaylist;
//
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Controller
//public class MovieController {
//
//    @Autowired
//    private MovieService movieService;
//
//    @RequestMapping("/")
//    public  String home() {
//        return "home";
//    }
//
//    @RequestMapping("/about")
//    public  String about() {
//        return "about";
//    }
//
//
//    @RequestMapping("/list")
//    public  String list(Model model) {
//        model.addAttribute("movies",
//                movieService.findAll());
//        return "list";
//    }
//
//    @RequestMapping("/read/{idx}")
//    public  String read(@PathVariable int idx, Model model) {
//        model.addAttribute("movie", movieService.findById(idx));
//        return "read";
//    }
//
//    @RequestMapping("/delete/{idx}")
//    public  String delete(@PathVariable int idx) {
//        movieService.delete(idx);
//        return "redirect:/list";
//    }
//
//    @RequestMapping("/insertForm")
//    public  String insertForm() {
//        return "insertForm";
//    }
//
//
//    // files가 MovieDto와 이름이 겹치면 안됨
//    @PostMapping(value = "/insert")
//    public  String insert(@RequestParam("files") MultipartFile file, MovieDto movie) throws IOException {
//        if ( file.isEmpty() )  {
//            movie.setImage("");
//        } else {
//            String originalFilename = file.getOriginalFilename();
//            String root = (new File("")).getAbsolutePath() + "/download/";
//            file.transferTo(new File(root + originalFilename));
//            movie.setImage("/download/" + originalFilename);
//        }
//        movie.setIdx(-1);
//        movieService.save(movie);
//        return "redirect:/list";
//    }
//
//
//    @RequestMapping(value="/download/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public byte[] download(@PathVariable("filename") String filename) throws IOException {
//        String root = (new File("")).getAbsolutePath() + "/download/";
//        InputStream in =  new FileInputStream(new File(root + filename));
//        return IOUtils.toByteArray(in);
//    }
//
//    @RequestMapping("/updateForm/{idx}")
//    public  String updateForm(@PathVariable int idx, Model model) {
//        model.addAttribute("movie", movieService.findById(idx));
//        return "updateForm";
//    }
//
//    @RequestMapping("/update")
//    public  String update(@RequestParam("files") MultipartFile file, MovieDto movie) throws IOException {
//        if ( ! file.isEmpty() ) {
//            String originalFilename = file.getOriginalFilename();
//            String root = (new File("")).getAbsolutePath() + "/download/";
//            file.transferTo(new File(root + originalFilename));
//            movie.setImage("/download/" + originalFilename);
//        }
//        movieService.save(movie);
//        return "redirect:/read/" + movie.getIdx();
//    }
//}
