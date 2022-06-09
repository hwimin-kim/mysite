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
		for(BoardVo vo : list)
		System.out.println(vo);
		model.addAttribute("pagingVo", pagingVo);
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("list", list);
		
		// 접근 제어(Access Control)
		UserVo authUser = (UserVo) session.getAttribute("authUser");	
		System.out.println(authUser);
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
}
