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
		PagingVo pagingVo =  boardService.getMessagePaging(no, keyWord);
		List<BoardVo> list = boardService.getMessageList(pagingVo, keyWord);	
		
		model.addAttribute("pagingVo", pagingVo);
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("list", list);
		
		// 접근 제어(Access Control)
		UserVo authUser = (UserVo) session.getAttribute("authUser");	
	
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
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo vo, HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		vo.setUser_no(authUser.getNo());

		boardService.addMessage(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model, HttpSession session) {
		BoardVo vo= boardService.getMessage(no);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// hit update
		boardService.updateMessage(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);
		
		return "board/view";
	}
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(@PathVariable("no") Long no, Model model) {
		BoardVo vo= boardService.getMessage(no);
		
		model.addAttribute("vo", vo);
		
		return "board/reply";
	}
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.POST)
	public String reply(
						@PathVariable("no") Long no,
						@RequestParam(value="title", required=true, defaultValue="") String title,
						@RequestParam(value="content", required=true, defaultValue="") String content,
						HttpSession session) {
		BoardVo vo= boardService.getMessage(no);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		boardService.addMessage(vo.getG_no(), vo.getO_no(), vo.getDepth(), title, content, authUser);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model) {
		BoardVo vo= boardService.getMessage(no);
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(
						@PathVariable("no") Long no,
						@RequestParam("title") String title,
						@RequestParam("content") String content) {
		boardService.updateMessage(no, title, content);
		
		return "redirect:/board";
	}
}
