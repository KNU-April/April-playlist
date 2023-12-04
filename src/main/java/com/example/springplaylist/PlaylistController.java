package com.example.springplaylist;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller

public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;
    @RequestMapping("/") //메인페이지
    public  String home() {
        return "home";
    }

    @RequestMapping("/about") //피치 웹사이트 소개
    public  String about() {
        return "about";
    }

    @RequestMapping("/form")
    @ResponseBody
    public String form(@RequestParam("id") String id,
                       @RequestParam("pwd") String pwd){
        return "id :" + id + "    pwd :" + pwd;
    }

    @RequestMapping("/list") //플레이리스트 목록
    public  String list(Model model) {
        model.addAttribute("playlists",
                playlistService.findAll());
        return "list";
    }

//    @RequestMapping("/musiclist") //플레이리스트 목록
//    public  String musiclist(Model model) {
//        model.addAttribute("playlists",
//                playlistService.findAll());
//        return "musiclist";
//    }

    @RequestMapping("/read/{idx}")  //플레이리스트 접속
    public  String read(@PathVariable int idx, Model model) {
        model.addAttribute("playlist", playlistService.findById(idx));
        return "read";
    }

    @RequestMapping("/delete/{idx}")  //삭제
    public  String delete(@PathVariable int idx) {
        playlistService.delete(idx);
        return "redirect:/list";
    }

    @RequestMapping("/insertForm")  //등록
    public  String insertForm() {
        return "insertForm";
    }


    // files가 playlistDto와 이름이 겹치면 안됨
    @PostMapping(value = "/insert")
    public  String insert(@RequestParam("files") MultipartFile file, PlaylistDto playlist) throws IOException {
        if ( file.isEmpty() )  {
            playlist.setImage("");
        } else {
            String originalFilename = file.getOriginalFilename();
            String root = (new File("")).getAbsolutePath() + "/download/";
            file.transferTo(new File(root + originalFilename));
            playlist.setImage("/download/" + originalFilename);
        }
        playlist.setIdx(-1);
        playlistService.save(playlist);
        return "redirect:/list";
    }


    @RequestMapping(value="/download/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] download(@PathVariable("filename") String filename) throws IOException {
        String root = (new File("")).getAbsolutePath() + "/download/";
        InputStream in =  new FileInputStream(new File(root + filename));
        return IOUtils.toByteArray(in);
    }

    @RequestMapping("/updateForm/{idx}")
    public  String updateForm(@PathVariable int idx, Model model) {
        model.addAttribute("playlist", playlistService.findById(idx));
        return "updateForm";
    }

    @RequestMapping("/update")
    public  String update(@RequestParam("files") MultipartFile file, PlaylistDto playlist) throws IOException {
        if ( ! file.isEmpty() ) {
            String originalFilename = file.getOriginalFilename();
            String root = (new File("")).getAbsolutePath() + "/download/";
            file.transferTo(new File(root + originalFilename));
            playlist.setImage("/download/" + originalFilename);
        }
        playlistService.save(playlist);
        return "redirect:/read/" + playlist.getIdx();
    }
}
