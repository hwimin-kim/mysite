package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private PagingVo pagingVo;

	public List<BoardVo> getMessageList(PagingVo pagingVo, String keyWord) {
		return boardRepository.findAll(pagingVo, keyWord);
	}

	public PagingVo getMessagePaging(String no, String keyWord) {
		int currentPage = 1;	
		
		pagingVo.setMinPage(1);
		pagingVo.setPageCount(5);
		pagingVo.setListCount(boardRepository.findCount(keyWord));
		pagingVo.calcMaxPage();
		
		/* page 예외 체크 */
		try {	
			if(no == null)
				currentPage = 1;
			else if(Integer.parseInt(no)< 1)
				currentPage = 1;
			else if(Integer.parseInt(no) > pagingVo.getMaxPage())	
				currentPage = pagingVo.getMaxPage();
			else
				currentPage = Integer.parseInt(no);
		} catch (NumberFormatException e) {
			currentPage = 1;
		} finally {
			pagingVo.setCurrentPage(currentPage);
			pagingVo.calcPage();		
		}
		return pagingVo;
	}

	public void deleteMessage(Long no) {
		boardRepository.delete(no);
	}
}
