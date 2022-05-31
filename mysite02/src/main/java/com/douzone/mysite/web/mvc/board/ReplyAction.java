package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		Long groupNo = Long.parseLong(request.getParameter("gno"));
		Long otherNo = Long.parseLong(request.getParameter("ono"));
		Long depth = Long.parseLong(request.getParameter("dep"));			
	
		// 접근제어 (Access Control)
		////////////////////////////////////////////////////////////////////
		HttpSession session = request.getSession();
		if(session == null) {
			WebUtil.redirect(request, response, request.getContextPath());
			return ;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			WebUtil.redirect(request, response, request.getContextPath());
			return ;
		}
		////////////////////////////////////////////////////////////////////
		
		BoardVo vo = new BoardVo();
		vo.setGroupNo(groupNo);
		vo.setOtherNo(otherNo += 1);
		vo.setDepth(depth += 1);
		new BoardRepository().updateBygnoANDoNo(vo);
		
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(authUser.getNo());
		new BoardRepository().insert(vo);
			
		
		WebUtil.redirect(request, response, request.getContextPath()+"/board");
	}

}
