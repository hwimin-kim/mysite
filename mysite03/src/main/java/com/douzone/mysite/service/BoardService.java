package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {
//	@Autowired
//	private BoardVo boardVo;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private PagingVo pagingVo;

	// 글 목록
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

	// 글 삭제
	public void deleteMessage(Long no) {
		boardRepository.delete(no);
	}
		
	// 글 쓰기
	public void addMessage(BoardVo vo) {
		boardRepository.insert(vo);
	}

	// 답글달기 페이지 정보 가져오기
	public BoardVo getMessage(Long no) {
		return boardRepository.findByNo(no);
	}

	// 답글달기
	public void addMessage(Long g_no, Long o_no, Long depth, String title, String content, UserVo authUser) {
		BoardVo boardVo = new BoardVo();
		boardVo.setG_no(g_no);
		boardVo.setO_no(o_no += 1);
		boardVo.setDepth(depth += 1);
		boardRepository.update(boardVo);
		
		boardVo.setTitle(title);
		boardVo.setContents(content);
		boardVo.setUser_no(authUser.getNo());
		boardRepository.insert(boardVo);
		
		
		System.out.println("답글 달기 서비스:" + boardVo);
	}

	// 글 수정
	public void updateMessage(Long no, String title, String content) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		boardVo.setTitle(title);
		boardVo.setContents(content);
		
		System.out.println("글 수정:" + boardVo);
		
		boardRepository.update(boardVo);
	}

	// 조회수
	public void updateMessage(Long no) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		boardRepository.update(boardVo);
	}
}
