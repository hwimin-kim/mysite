package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		/* page */
		////////////////////////////////////////////////////////////////////
		int currentPage;	
		
		if((request.getParameter("page") == null))
			currentPage = 1;
		else if(Integer.parseInt(request.getParameter("page"))<= 0)
			currentPage = 1;
		else
			currentPage = Integer.parseInt(request.getParameter("page"));
		
		
		PagingVo vo = new PagingVo();
		vo.setPageCount(5);
		vo.setListCount(new BoardRepository().findCount());
		vo.setCurrentPage(currentPage);
		vo.calcPage();
		vo.calcMaxPage();
		
		request.setAttribute("startPage", vo.getStartPage());
		request.setAttribute("endPage", vo.getEndPage());
		request.setAttribute("pageCount", vo.getPageCount());
		request.setAttribute("currentPage", vo.getCurrentPage());
		request.setAttribute("maxPage", vo.getMaxPage());
		request.setAttribute("minPage", 1);
		////////////////////////////////////////////////////////////////////
		
		List<BoardVo> list = new BoardRepository().findAll(currentPage, vo.getPageCount());
		request.setAttribute("list", list);	
		
		WebUtil.forward(request, response, "board/index");
	}

}
