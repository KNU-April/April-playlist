package com.example.springmusic;

import com.example.springplaylist.MusicDto;
import com.example.springplaylist.MusicService;
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

public class MusicController {
    @Autowired
    private MusicService musicService;

    @RequestMapping("/musicList") //플레이리스트 속 노래 목록
    public  String list(Model model) {
        model.addAttribute("musicLists",
                musicService.findAll());
        return "musicList";
    }

    @RequestMapping("/musicList/{idx}")  //플레이리스트 속 노래하다
    public  String read(@PathVariable int idx, Model model) {
        model.addAttribute("music", musicService.findById(idx));
        return "music";
    }

    @RequestMapping("/delete/{idx}")  //삭제
    public  String delete(@PathVariable int idx) {
        musicService.delete(idx);
        return "redirect:/musicList";
    }

    @RequestMapping("/musicInsertForm")  //등록
    public  String insertForm() {
        return "musicInsertForm";
    }


    // files가 musicDto와 이름이 겹치면 안됨
    @PostMapping(value = "/musicInsert")
    public  String insert(@RequestParam("musicFiles") MultipartFile file, MusicDto music) throws IOException {
        if ( file.isEmpty() )  {
            music.setImage("");
        } else {
            String originalFilename = file.getOriginalFilename();
            String root = (new File("")).getAbsolutePath() + "/download/";
            file.transferTo(new File(root + originalFilename));
            music.setImage("/download/" + originalFilename);
        }
        music.setIdx(-1);
        musicService.save(music);
        return "redirect:/musicList";
    }


    @RequestMapping(value="/download/{musicFilename}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] download(@PathVariable("musicFilename") String filename) throws IOException {
        String root = (new File("")).getAbsolutePath() + "/download/";
        InputStream in =  new FileInputStream(new File(root + filename));
        return IOUtils.toByteArray(in);
    }

    @RequestMapping("/musicUpdateForm/{idx}")
    public  String updateForm(@PathVariable int idx, Model model) {
        model.addAttribute("music", musicService.findById(idx));
        return "musicUpdateForm";
    }

    @RequestMapping("/MusicUpdate")
    public  String update(@RequestParam("musicFiles") MultipartFile file, MusicDto music) throws IOException {
        if ( ! file.isEmpty() ) {
            String originalFilename = file.getOriginalFilename();
            String root = (new File("")).getAbsolutePath() + "/download/";
            file.transferTo(new File(root + originalFilename));
            music.setImage("/download/" + originalFilename);
        }
        musicService.save(music);
        return "redirect:/musicList/" + music.getIdx();
    }

}
