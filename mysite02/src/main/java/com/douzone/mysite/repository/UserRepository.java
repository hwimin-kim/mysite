package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douzone.mysite.vo.UserVo;

public class UserRepository {
	private static final String ID = "webdb";
	private static final String PASSWORD = "webdb";
	
	public UserVo findByEmailAndPassword(UserVo vo) {
		UserVo result = null;
		
		Connection connecion = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connecion = getConnection();
			
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
			connecion = getConnection();
			
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
	
	private Connection getConnection() throws SQLException {
		Connection connecion = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.10.40:3306/webdb?charset=utf8";
			connecion = DriverManager.getConnection(url, ID, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}	
		return connecion;
	}
}
