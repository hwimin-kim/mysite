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
		List<BoardVo> list = null;
		String keyWord = request.getParameter("kwd");
		/* page */
		////////////////////////////////////////////////////////////////////
		int currentPage = 1;	
		
		PagingVo vo = new PagingVo();
		vo.setMinPage(1);
		vo.setPageCount(5);
		vo.setListCount(new BoardRepository().findCount(keyWord));
		vo.calcMaxPage();
		
		/* page 예외 체크 */
		try {	
			if(request.getParameter("page") == null)
				currentPage = 1;
			else if(Integer.parseInt(request.getParameter("page"))< 1)
				currentPage = 1;
			else if(Integer.parseInt(request.getParameter("page")) > vo.getMaxPage())
				currentPage = vo.getMaxPage();
			else
				currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			currentPage = 1;
		} finally {
			vo.setCurrentPage(currentPage);
			vo.calcPage();		
		}
		
		request.setAttribute("pagingVo", vo);
		////////////////////////////////////////////////////////////////////
		list = new BoardRepository().findAll(currentPage, vo.getPageCount(), keyWord);
		
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("list", list);	
		
		WebUtil.forward(request, response, "board/index");
	}

}
