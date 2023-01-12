package com.forum.forum.controller;

import com.forum.forum.dto.ForumRequest;
import com.forum.forum.dto.ForumResponse;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/forum")
public class ForumController {
    private final ForumService forumService;
    @PostMapping    //게시글 작성
    public ResponseEntity<StatusResponse> createForum(@RequestBody ForumRequest forumRequest, HttpServletRequest request){
        StatusResponse forum = forumService.createForum(forumRequest, request);
        return new ResponseEntity<>(forum, HttpStatus.valueOf(forum.getStatusCode()));
    }

    @GetMapping     //게시글 가져오기
    public List<ForumResponse> getForum(){
        return forumService.getForum();
    }

    @GetMapping("/{id}")    //X번쨰 게시글 가져오기
    public ForumResponse getForum(@PathVariable Long id){
        return forumService.getForum(id);
    }

    @PatchMapping("/update/{id}")  //X번째 게시글 수정하기
    public ForumResponse updateForum(@PathVariable Long id, @RequestBody ForumRequest forumRequest,HttpServletRequest request){ //게시글 구분번호, 제목 및 내용,
        return forumService.updateForum(id,forumRequest,request);
    }

    @DeleteMapping("/delete/{id}") // X번째 게시글 삭제하기
    public ResponseEntity<StatusResponse> deleteForum(@PathVariable Long id, HttpServletRequest request){       //id와 requset를 파라미터 갖기
        StatusResponse forum = forumService.deleteForum(id,request);                        //에러메세지, 에러코드 산줄문 forum넣기
        return new ResponseEntity<>(forum, HttpStatus.valueOf(forum.getStatusCode()));
    }
}

