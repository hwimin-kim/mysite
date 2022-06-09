package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PagingVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;

	public int findCount(String keyWord) {
		if(keyWord != null)
			keyWord = "%"+keyWord+"%";
		
		return sqlSession.selectOne("board.findCount", keyWord);
	}
	
//	public BoardVo findByNo(String no) {
//		BoardVo result = null;
//		Connection connecion = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			connecion = dataSource.getConnection();
//			
//			// 3. SQL 준비
//			String sql = "select title, contents, g_no, o_no, depth from board where no = ?";
//			pstmt = connecion.prepareStatement(sql);
//			
//			// 4. Parameter Mapping
//			pstmt.setString(1, no);
//			// 5. SQL 실행
//			rs =pstmt.executeQuery();		
//			
//			// 6. 결과처리
//			while(rs.next()) {
//				String title = rs.getString(1);
//				String contents = rs.getString(2);
//				Long groupNo = rs.getLong(3);
//				Long otherNo = rs.getLong(4);
//				Long depth = rs.getLong(5);
//	
//				
//				BoardVo vo = new BoardVo();
//				vo.setTitle(title);
//				vo.setContents(contents);
//				vo.setG_no(groupNo);
//				vo.setO_no(otherNo);
//				vo.setDepth(depth);
//				
//				
//				result = vo;
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} finally {
//			try {
//				if(rs != null)
//					rs.close();
//				if(pstmt != null)
//					pstmt.close();
//				if(connecion != null)
//					connecion.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}

	public List<BoardVo> findAll(PagingVo pagingVo, String keyWord) {
		if(keyWord != null)
			keyWord = "%"+keyWord+"%";
			
		Map<String, Object> map = new HashMap<>();
		map.put("keyWord", keyWord);
		map.put("currentPage", Long.valueOf((pagingVo.getCurrentPage()-1)*pagingVo.getPageCount()));
		map.put("pageCount", Long.valueOf(pagingVo.getPageCount()));
		
		return sqlSession.selectList("board.findAll", map);
	}
	
//	public List<BoardVo> findAll2(int page, int pageCount) {
//		List<BoardVo> result = new ArrayList<>();
//		Connection connecion = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Long currentPage = Long.valueOf((page-1)*pageCount);
//		
//		try {
//			connecion = dataSource.getConnection();
//			
//			// 3. SQL 준비
//			String sql = "select a.no, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name from board a, user b where a.user_no = b.no order by g_no desc, o_no asc limit ?, ?";
//			pstmt = connecion.prepareStatement(sql);
//			
//			// 4. Parameter Mapping
//			pstmt.setLong(1, currentPage);
//			pstmt.setLong(2, Long.valueOf(pageCount));
//			// 5. SQL 실행
//			rs =pstmt.executeQuery();		
//			
//			// 6. 결과처리
//			while(rs.next()) {
//				Long no =  rs.getLong(1);
//				String title = rs.getString(2);
//				String contents = rs.getString(3);
//				Long hit = rs.getLong(4);	
//				String regDate = rs.getString(5);
//				Long groupNo = rs.getLong(6);
//				Long otherNo = rs.getLong(7);
//				Long depth = rs.getLong(8);
//				Long userNo = rs.getLong(9);
//				String userName = rs.getString(10);
//				
//				BoardVo vo = new BoardVo();
//				vo.setNo(no);
//				vo.setTitle(title);
//				vo.setContents(contents);
//				vo.setHit(hit);
//				vo.setRegDate(regDate);
//				vo.setG_no(groupNo);
//				vo.setO_no(otherNo);
//				vo.setDepth(depth);
//				vo.setUser_no(userNo);
//				vo.setName(userName);
//				
//				result.add(vo);
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} finally {
//			try {
//				if(rs != null)
//					rs.close();
//				if(pstmt != null)
//					pstmt.close();
//				if(connecion != null)
//					connecion.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}

//	public boolean insert(BoardVo vo) {
//		boolean result = false;
//		Connection connecion = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			connecion = dataSource.getConnection();
//			System.out.println(vo.getG_no());
//			if(vo.getG_no() == null ) {
//				String sql = "insert into board values(null, ?, ?, 1, now(), (select IF(ISNULL(g_no), 1, MAX(g_no) +1) from board t), 1, 1, ?)";
//				pstmt = connecion.prepareStatement(sql);
//			
//				pstmt.setString(1, vo.getTitle());
//				pstmt.setString(2, vo.getContents());
//				pstmt.setLong(3, vo.getUser_no());
//			} else {
//				String sql = "insert into board values(null, ?, ?, 1, now(), ?, ?, ?, ?)";
//				pstmt = connecion.prepareStatement(sql);
//				
//				pstmt.setString(1, vo.getTitle());
//				pstmt.setString(2, vo.getContents());
//				pstmt.setLong(3, vo.getG_no());
//				pstmt.setLong(4, vo.getO_no());
//				pstmt.setLong(5, vo.getDepth());
//				pstmt.setLong(6, vo.getUser_no());
//			}
//			int count =pstmt.executeUpdate();
//			result = count == 1;
//				
//		}  catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} finally {
//			try {
//				if(pstmt != null)
//					pstmt.close();
//				if(connecion != null)
//					connecion.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	
//	public boolean updateByno(Long no) {
//		boolean result = false;
//		Connection connecion = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			connecion = dataSource.getConnection();
//			
//			String sql = "update board set hit = hit + 1 where no = ?";
//			pstmt = connecion.prepareStatement(sql);
//			
//			pstmt.setLong(1, no);
//			
//			int count =pstmt.executeUpdate();
//			result = count == 1;
//				
//		}  catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} finally {
//			try {
//				if(pstmt != null)
//					pstmt.close();
//				if(connecion != null)
//					connecion.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	
//	public boolean updateByno(BoardVo vo) {
//		boolean result = false;
//		Connection connecion = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			connecion = dataSource.getConnection();
//			
//			String sql = "update board set title = ?, contents = ? where no = ?";
//			pstmt = connecion.prepareStatement(sql);
//			
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setLong(3, vo.getNo());
//			
//			int count =pstmt.executeUpdate();
//			result = count == 1;
//				
//		}  catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} finally {
//			try {
//				if(pstmt != null)
//					pstmt.close();
//				if(connecion != null)
//					connecion.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}

//	public boolean updateBygnoANDoNo(BoardVo vo) {
//		boolean result = false;
//		Connection connecion = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			connecion = dataSource.getConnection();
//			
//			String sql = "update board set o_no = o_no + 1 where g_no = ? and o_no >= ?";
//			pstmt = connecion.prepareStatement(sql);
//		
//			pstmt.setLong(1, vo.getG_no());
//			pstmt.setLong(2, vo.getO_no());
//			
//			int count =pstmt.executeUpdate();
//			result = count == 1;
//				
//		}  catch (SQLException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} finally {
//			try {
//				if(pstmt != null)
//					pstmt.close();
//				if(connecion != null)
//					connecion.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	
	public void delete(Long no) {
		sqlSession.delete("board.delete", no);
	}
	
}
