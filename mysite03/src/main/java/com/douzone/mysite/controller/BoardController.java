package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value={"", "/{no}","/{no}/{keyWord}"}, method=RequestMethod.GET)
	public String index(
						@PathVariable(value="no", required=false) String no,
						@PathVariable(value="keyWord", required=false) String keyWord,
						Model model,
						HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");	
		PagingVo pagingVo =  boardService.getMessagePaging(no, keyWord);
		List<BoardVo> list = boardService.getMessageList(pagingVo, keyWord);	
		int totalCount = boardService.getMessageTotalCount(keyWord);
		
		System.out.println("글 갯수:" + totalCount);
		
		model.addAttribute("pagingVo", pagingVo);
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("authUser", authUser);
		
		return "board/index";
	}
	
	@RequestMapping(value="/1", method = RequestMethod.POST)
	public String index(@RequestParam("keyWord") String keyWord) {
		return "redirect:/board/1/"+keyWord;
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no) {
		boardService.deleteMessage(no);
		
		return "redirect:/board";
	}
		
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model, HttpSession session) {
		boardService.updateHitMessage(no);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		model.addAttribute("vo", boardService.getMessage(no));
		model.addAttribute("authUser", authUser);
		
		return "board/view";
	}
	
	@RequestMapping(value={"/write", "/write/{no}"}, method=RequestMethod.GET)
	public String write(@PathVariable(value="no", required=false) Long no, Model model, HttpSession session) {
		// 접근 제어(Access Control)///////////////////////////////////
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null)
			return "redirect:/";
		////////////////////////////////////////////////////////////
		model.addAttribute("no", no);
		
		return "board/write";
	}
				
	@RequestMapping(value={"/write", "/write/{no}"}, method=RequestMethod.POST)
	public String reply(@PathVariable(value="no", required=false) Long no, BoardVo vo, HttpSession session) {
		// 접근 제어(Access Control)///////////////////////////////////
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null)
			return "redirect:/";
		////////////////////////////////////////////////////////////	
		if(no == null) {
			vo.setUser_no(authUser.getNo());
			boardService.addMessage(vo);
		} else
			boardService.addMessage(boardService.getMessage(no), vo, authUser);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model, HttpSession session) {
		// 접근 제어(Access Control)///////////////////////////////////
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null)
			return "redirect:/";
		////////////////////////////////////////////////////////////
		model.addAttribute("vo", boardService.getMessage(no));
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(@PathVariable("no") Long no, BoardVo vo, HttpSession session) {
		// 접근 제어(Access Control)///////////////////////////////////
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null)
			return "redirect:/";
		////////////////////////////////////////////////////////////
		boardService.updateMessage(no, vo);
		
		return "redirect:/board";
	}
}
