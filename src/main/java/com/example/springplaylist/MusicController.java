package com.example.springplaylist;

import com.example.springplaylist.MusicDto;
import com.example.springplaylist.MusicService;
import com.example.springplaylist.PlaylistDto;
import com.example.springplaylist.PlaylistService;
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
    @Autowired
    private PlaylistService playlistService;

    @RequestMapping("/musicList") //플레이리스트 속 노래 목록
    public  String list(Model model) {
        model.addAttribute("musicLists",
                musicService.findAll());
        return "musiclist";
    }

    @RequestMapping("/read/{idx}/musicInsertForm")  //음악 추가
    public  String musicInsertForm(@PathVariable int idx, Model model) {
        model.addAttribute("playlist", playlistService.findById(idx));
        return "musicInsertForm";
    }
    @PostMapping("/musicInsert/{playlistIdx}")
    public String musicInsert(@RequestParam("musicFiles") MultipartFile file,
                              @PathVariable("playlistIdx") int playlistIdx,
                              @ModelAttribute MusicDto music) throws IOException {
        if (file.isEmpty()) {
            music.setImage("");
        } else {
            String originalFilename = file.getOriginalFilename();
            String root = (new File("")).getAbsolutePath() + "/download/";
            file.transferTo(new File(root + originalFilename));
            music.setImage("/download/" + originalFilename);
        }
        music.setIdx(-1);
        playlistService.addMusic(playlistIdx, music);

        // 리다이렉트 주소를 수정하여 read/{idx}로 이동하게 함
        return "redirect:/read/" + playlistIdx;
    }


    @RequestMapping("/musicList/{idx}")  //플레이리스트 속 노래하다
    public  String read(@PathVariable int idx, Model model) {
        model.addAttribute("music", musicService.findById(idx));
        return "music";
    }

    @RequestMapping("/musicDelete/{playlistIdx}/{musicIdx}")
    public String delete(@PathVariable("playlistIdx") int playlistIdx, @PathVariable("musicIdx") int musicIdx) {
        PlaylistDto playlist = playlistService.findById(playlistIdx);
        MusicDto musicDto = musicService.findById(musicIdx);
        if (playlist != null && musicDto != null) {
            // 플레이리스트에서 음악 삭제
            playlist.removeMusic(musicDto);
            // 음악 서비스에서 음악 삭제
            musicService.delete(musicIdx);
        }
        return "redirect:/read/" + playlistIdx;
    }

    @RequestMapping("/musicInsertForm")  //등록
    public String insertForm() {
        return "musicInsertForm";
    }
    // files가 musicDto와 이름이 겹치면 안됨
//    @PostMapping(value = "/musicInsert")
//    public  String insert(@RequestParam("musicFiles") MultipartFile file, MusicDto music) throws IOException {
//        if ( file.isEmpty() )  {
//            music.setImage("");
//        } else {
//            String originalFilename = file.getOriginalFilename();
//            String root = (new File("")).getAbsolutePath() + "/download/";
//            file.transferTo(new File(root + originalFilename));
//            music.setImage("/download/" + originalFilename);
//        }
//        music.setIdx(-1);
//        musicService.save(music);
//        return "redirect:/musicList";
//    }
    @RequestMapping(value="/musicDownload/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] download(@PathVariable("filename") String filename) throws IOException {
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
