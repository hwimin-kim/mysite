package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping({"/{no}","/{no}/{keyWord}"})
	public String index(@PathVariable(value="no", required=false) String no, @PathVariable(value="keyWord", required=false) String keyWord, Model model) {
		if(keyWord == null)
			System.out.println("ttnull");
		else
			System.out.println("qqqq"+keyWord);
		
		PagingVo pagingVo =  boardService.getMessagePaging(no, keyWord);
		model.addAttribute("pagingVo", pagingVo);
		
		model.addAttribute("keyword", keyWord);
		
		List<BoardVo> list = boardService.getMessageList(pagingVo, keyWord);
		model.addAttribute("list", list);
		return "board/index";
	}
}
