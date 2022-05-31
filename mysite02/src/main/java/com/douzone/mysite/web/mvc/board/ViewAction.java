package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo vo = new BoardRepository().findByNo(request.getParameter("no"));
	
		request.setAttribute("vo", vo);
		request.setAttribute("no", request.getParameter("no"));
		
		/* hit update */
		new BoardRepository().updateByno(Long.parseLong(request.getParameter("no")));
		
		WebUtil.forward(request, response, "board/view");
	}

}
