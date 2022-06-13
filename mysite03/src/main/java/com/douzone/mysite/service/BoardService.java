package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.exception.PagingNumberFormatException;
import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {
	@Autowired
	private BoardVo boardVo;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private PagingVo pagingVo;

	// 글 목록
	public List<BoardVo> getMessageList(PagingVo pagingVo, String keyWord) {
		return boardRepository.findAll(pagingVo, keyWord);
	}
	
	public int getMessageTotalCount(String keyWord) {
		return boardRepository.findCount(keyWord);
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
			// throw new PagingNumberFormatException(e);
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

	// 해당 게시글 정보
	public BoardVo getMessage(Long no) {
		return boardRepository.findByNo(no);
	}

	// 답글달기
	public void addMessage(BoardVo prevVo, BoardVo nextVo, UserVo authUser) {	
		prevVo.setO_no(prevVo.getO_no() + 1);
		prevVo.setDepth(prevVo.getDepth() + 1);	
		boardRepository.update(prevVo);
		
		prevVo.setTitle(nextVo.getTitle());
		prevVo.setContents(nextVo.getContents());
		prevVo.setUser_no(authUser.getNo());
		boardRepository.insert(prevVo);
	}

	// 글 수정
	public void updateMessage(Long no, BoardVo vo) {	
		vo.setNo(no);
		boardRepository.update(vo);
	}

	// 조회수
	public void updateHitMessage(Long no) {
		boardVo.setNo(no);
		boardRepository.update(boardVo);
	}
}
