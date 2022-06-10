package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int findCount(String keyWord) {
		if(keyWord != null)
			keyWord = "%"+keyWord+"%";
		
		return sqlSession.selectOne("board.findCount", keyWord);
	}
	
	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

	public List<BoardVo> findAll(PagingVo pagingVo, String keyWord) {
		if(keyWord != null)
			keyWord = "%"+keyWord+"%";
			
		Map<String, Object> map = new HashMap<>();
		map.put("keyWord", keyWord);
		map.put("currentPage", Long.valueOf((pagingVo.getCurrentPage()-1)*pagingVo.getPageCount()));
		map.put("pageCount", Long.valueOf(pagingVo.getPageCount()));
		
		return sqlSession.selectList("board.findAll", map);
	}
	
	public void insert(BoardVo vo) {
		int num = sqlSession.insert("board.insert", vo);
	}
	
	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}
	
	public void delete(Long no) {
		sqlSession.delete("board.delete", no);
	}
	
}
