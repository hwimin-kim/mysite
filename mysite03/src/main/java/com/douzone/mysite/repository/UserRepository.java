package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;

	public UserVo findByno(Long authUserNo) {
		UserVo result = null;
		
		Connection connecion = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connecion = dataSource.getConnection();
			
			// 3. SQL 준비
			String sql = " select no, name, email, gender from user where no = ?";
			pstmt = connecion.prepareStatement(sql);
			
			// 4. Parameter Mapping
			pstmt.setLong(1, authUserNo);
			
			// 5. SQL 실행
			rs =pstmt.executeQuery();		
			
			// 6. 결과처리
			if(rs.next()) {
				Long no =  rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);

				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
			
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(connecion != null)
					connecion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public UserVo findByEmailAndPassword(UserVo vo) {
		UserVo result = null;
		
		Connection connecion = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connecion = dataSource.getConnection();
			
			// 3. SQL 준비
			String sql = "select no, name from user where email = ? and password = ?";
			pstmt = connecion.prepareStatement(sql);
			
			// 4. Parameter Mapping
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			// 5. SQL 실행
			rs =pstmt.executeQuery();		
			
			// 6. 결과처리
			if(rs.next()) {
				Long no =  rs.getLong(1);
				String name = rs.getString(2);

				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				//vo.setNo(no);
				//vo.setName(name);
			}
			
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(connecion != null)
					connecion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection connecion = null;
		PreparedStatement pstmt = null;
		
		try {
			connecion = dataSource.getConnection();
			
			String sql = "insert into user values(null, ?, ?, ?, ?, now())";
			pstmt = connecion.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			int count =pstmt.executeUpdate();
			result = count == 1;
				
		}  catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(connecion != null)
					connecion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public boolean updateByno(String name, String password, String gender, Long no) {
		UserVo vo = new UserVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);
		vo.setNo(no);
		
		if("".equals(password)) {
			boolean result = false;
			Connection connecion = null;
			PreparedStatement pstmt = null;
			
			try {
				connecion = dataSource.getConnection();
				
				String sql = "update user set name = ?, gender = ? where no = ?";
				pstmt = connecion.prepareStatement(sql);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getNo());
				
				int count =pstmt.executeUpdate();
				result = count == 1;
					
			}  catch (SQLException e) {
				System.out.println("드라이버 로딩 실패:" + e);
			} finally {
				try {
					if(pstmt != null)
						pstmt.close();
					if(connecion != null)
						connecion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
		} else 
			return updateByno(vo);
	}
	public boolean updateByno(UserVo vo) {
		boolean result = false;
		Connection connecion = null;
		PreparedStatement pstmt = null;
		
		try {
			connecion = dataSource.getConnection();
			
			String sql = "update user set name = ?, password = ?, gender = ? where no = ?";
			pstmt = connecion.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			
			int count =pstmt.executeUpdate();
			result = count == 1;
				
		}  catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(connecion != null)
					connecion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
