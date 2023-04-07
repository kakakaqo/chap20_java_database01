package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 1. book 테이블에서 "굿스포크" 출판사의 모든 책을 조회하세요.
 */

public class Jdbc10_select {

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		// 데이터베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		// 데이터베이스 계정명
		String dbId = "square";
		// 데이터베이스 비밀번호
		String dbPwd = "1234";

		// 데이터베이스에 연결하는 다리(bridge)와 같다.
		Connection con = null;
		
		// 쿼리문에 인자를 전달해서 SQL 구문을 실행해주는 객체
		PreparedStatement pstmt = null;  // PreparedStatement 객체 변수 pstmt 선언
		// 실행된 결과를 받아오는 객체
		ResultSet rs = null; // select 의 결과 객체 저장

		String sql;

		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");

			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("데이터 베이스 연결 성공!");

			String publisher = "굿스포크";
			sql = "SELECT B.BOOK_ID, B.BOOK_NAME, B.PRICE, B.PUBLISHER";
			sql += " FROM BOOK B";
			sql += " WHERE B.PUBLISHER = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, publisher);
			rs = pstmt.executeQuery();

			System.out.println("조회하신 출판사는 ?");

			while(rs.next()) {
				System.out.println(rs.getInt("book_id") + "\t" 
						+rs.getString("book_name") + "\t"
						+rs.getInt("price"));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR! " + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("자원해제 ERR! : " + e.getMessage());
			}
		}
	}
}