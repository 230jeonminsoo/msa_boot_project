package com.reco.calendar.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;

@Repository
public class CalendarDAOOracle implements CalendarDAOInterface {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private Logger logger = LoggerFactory.getLogger(CalendarDAOOracle.class.getName());

	@Override
	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<CalInfo> list = session.selectList("com.reco.calendar.CalendarMapper.findCalsByUIdx", uIdx);
			logger.warn("list.size=" + list.size());
			
			/*
			 3	1	운동	ex.jpg
			 3	2	책	book.jpg
			 3	3	음식	food1.jpg
			 */
			
			if(list.size() == 0) {
				throw new FindException("회원번호에 해당하는 캘린더가 없습니다.");
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}			
		}

	}
	
	@Override
	public CalInfo addCal(CalInfo calInfo) throws AddException{
		SqlSession session = null;
				try {
					session = sqlSessionFactory.openSession();
					/*
					 UIDX
					 CALIDX
					 CALCATEGORY
					 CALTHUMBNAIL
					 CALCREATEAT*/

					int uIdx = calInfo.getCustomer().getUIdx();
					//---다음캘린더 글번호 얻기//--cal_info테이블에 추가
					int calIdx;
					session.insert("com.reco.calendar.CalendarMapper.addCal",calInfo);
					calIdx = calInfo.getCalIdx();
					System.out.println("addCal함수 : uIdx=" + uIdx + ", calIdx =" + calIdx);
					
					//----------------------------------------------------------------------
					//CAL_POST_uIdx값_calIdx값 이름의 테이블 생성
					String postTableName = "cal_post_" + uIdx + "_"  + calIdx;
					String createCalPostSQL = "CREATE TABLE " + postTableName +"(\r\n"
							+ "   cal_Date DATE CONSTRAINT cal_post_" + uIdx + "_" + calIdx + "_pk PRIMARY KEY,\r\n"
							+ "   cal_Memo VARCHAR2(1000) NOT NULL,\r\n"
							+ "   cal_Main_Img VARCHAR2(50) NOT NULL,\r\n"
							+ "   cal_Post_CreateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP\r\n"
							+ ") ";
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("createCalPostSQL", createCalPostSQL);     
					session.update("CreateTable",map);
					session.commit();
				} catch (Exception e) {
					e.printStackTrace();   
					throw new AddException(e.getMessage());
				}finally {
					if(session != null) {
						session.close();
					}
				}
				return calInfo;

			}


	

//	@Override
	public void modifyCal(CalInfo calinfo) throws ModifyException{
//
//		int uIdx = calinfo.getCustomer().getUIdx();
//		int calIdx = calinfo.getCalIdx();
////		String calCategory = calinfo.getCalCategory();
////		String calThumbnail = calinfo.getCalThumbnail();
//		try {
//			List<CalInfo> list = findCalsByUIdx(uIdx);
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			con = MyConnection.getConnection();
//			con.setAutoCommit(false);
//			String modifySQL = "update cal_info set cal_Category = ? where cal_idx = ?";
//			String modifySQL1 = "update cal_info set cal_Thumbnail = ? where cal_idx = ?";
//
//			pstmt = con.prepareStatement(modifySQL);
//			pstmt.setString(1, calinfo.getCalCategory());
//			pstmt.setInt(2, calinfo.getCalIdx());
//			pstmt.executeUpdate();
//			pstmt = con.prepareStatement(modifySQL1);
//			pstmt.setString(1, calinfo.getCalThumbnail());
//			pstmt.setInt(2, calinfo.getCalIdx());
//			pstmt.executeUpdate();
//			con.commit();
//		} catch (FindException e) {
//			throw new ModifyException(e.getMessage());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
	}

//	@Override
	public void removeCal(CalInfo calinfo) throws RemoveException {
//		// "drop table Cal_post_" + uIdx + "_" + calIdx;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			con = MyConnection.getConnection();
//			con.setAutoCommit(false);
//
//			int uIdx = calinfo.getCustomer().getUIdx();
//			//---다음캘린더 글번호 얻기
//			int calIdx = calinfo.getCalIdx();
//
////			CalInfo calinfo = null;
////			uIdx = calinfo.getCustomer().getUIdx();
////			int calIdx = calinfo.getCalIdx();
//			//---다음캘린더 글번호 얻기
//
//			System.out.println("uIdx=" + uIdx + ", calIdx =" + calIdx);
//			//----------------------------------------------------------------------
//
//			//Cal_Post 행삭제
//			String dropCalInfoSQL = "drop table Cal_post_" + uIdx + "_" + calIdx;
//
//			pstmt = con.prepareStatement(dropCalInfoSQL);
////			pstmt.setInt(1, uIdx);
////			pstmt.setInt(2, calIdx);
//			pstmt.executeUpdate();
//
//			int droptable = pstmt.executeUpdate();
//
//			if(droptable == 0) {
//				System.out.println("해당 캘린더가 존재하지 않습니다.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			MyConnection.close(pstmt, con);
//		}
	}


	@Override
	public CalPost addCalPost(CalPost calpost) throws AddException{
		SqlSession session = null;
			
		try {
			session = sqlSessionFactory.openSession();
			int uIdx = calpost.getCalInfo().getCustomer().getUIdx();
			int calIdx = calpost.getCalInfo().getCalIdx();
			CalPost cp = session.selectOne("com.reco.calendar.CalendarMapper.calIdxByUIdx",calpost);
			if(cp == null) {
				throw new AddException("고객번호에 해당하는 cal_info행이 없습니다");
			}
			System.out.println("addcalpost함수 : uIdx=" + uIdx + ", calIdx =" + calIdx);
			
		    String calDate = calpost.getCalDate();
		    String calMainImg = calpost.getCalMainImg();
		    session.insert("com.reco.calendar.CalendarMapper.addCalPost",calpost);

		
		}catch(Exception e) {
			e.printStackTrace();
			//throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return calpost;
	}






	@Override
	public List<CalPost> findCalsByDate (CalInfo calinfo, String calDate) throws FindException{
		SqlSession session = null;
		int uIdx = calinfo.getCustomer().getUIdx();
		int calIdx = calinfo.getCalIdx();
		System.out.println("findCalsByDate함수 : uIdx=" + uIdx + ", calIdx =" + calIdx);

		try {
			session = sqlSessionFactory.openSession();
			Map<String, Object> map = new HashMap<>(); // Map<Key형, Value형> mapName = new HashMap<>();
			map.put("calInfo", calinfo); // calinfo map에 넣는다
			map.put("calDate",  calDate); 
			List<CalPost> list = session.selectList("com.reco.calendar.CalendarMapper.findCalsByDate");
			if(list.size() == 0) {
				throw new FindException("해당하는 게시글이 없습니다");
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}

	}





//	@Override
	public void modifyCalPost(CalPost calpost) throws ModifyException{
//		Connection con =null;
//		PreparedStatement pstmt = null;
//
//		int uIdx = calpost.getCustomer().getUIdx();
//		int calIdx = calpost.getCalinfo().getCalIdx();
//		Date calDate = calpost.getCalDate();
//
//		try {
//
//			try {
//				List<CalPost> list = findCalsByDate(caldate);
//			} catch (FindException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			con = MyConnection.getConnection();
////			String modifySQL="update calpost set cal_memo,cal_Img1,cal_Img2,cal_Img3, main_Img  where cal_date=?";
//
//
//			pstmt = con.prepareStatement(modifySQL);
//			pstmt.setDate(1, (java.sql.Date) calpost.getCalDate());
//
//
//			pstmt.executeUpdate();
//
//		}catch(SQLException e) {
//			e.getStackTrace();
//		}finally {
//			MyConnection.close(pstmt, con);
//		}
	}


//	@Override
	public void removeCalPost(String calDate) throws RemoveException{
//		//" delete from Cal_post_1_1 where cal_Date = ? ";
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		//ResultSet rs = null;
//		try {
//			con = MyConnection.getConnection();
//			con.setAutoCommit(false);
//			/*
//			 CALDATE
//			 CALMEMO
//			 CALMAINIMG
//			 CALIMG1
//			 CALIMG2
//			 CALIMG3
//			 CALPOSTCREATEAT
//			 */
//
//			CalInfo calinfo = null;
//			int uIdx = calinfo.getCustomer().getUIdx();
//			int calIdx = calinfo.getCalIdx();
//			//---다음캘린더 글번호 얻기
//
//			System.out.println("uIdx=" + uIdx + ", calIdx =" + calIdx);
//			//----------------------------------------------------------------------
//
//			//Cal_Post 행삭제
//			String deledtCalPostSQL = "delete from Cal_post_" + uIdx + "_" + calIdx + " where cal_Date = ?";
//			pstmt = con.prepareStatement(deledtCalPostSQL);
//			pstmt.setInt(1, uIdx);
//			pstmt.setInt(2, calIdx);
//			pstmt.setString(3, calDate);
//			pstmt.executeUpdate();
//
//			int deleterow = pstmt.executeUpdate();
//			if(deleterow == 0) {
//				System.out.println("해당 캘린더글이 존재하지 않습니다.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			MyConnection.close(pstmt, con);
//		}
	}




	//테이블 생성
//	public static void main(String[] args) {
//		CalendarDAOInterface dao =  CalendarDAOOracle.getInstance();
//		//calTitle, calThumbnail은 요청전달데이터
//		//uidx 세션로그인정보
//		CalInfo calInfo = new CalInfo();
//		int uIdx = 2; //혜성 :1 , 다원:3 정은:2
//		Customer c = new Customer();
//		c.setUIdx(uIdx);
//		calInfo.setCustomer(c);
//		String calCategory = "독서";
//		calInfo.setCalCategory(calCategory);
//
//		String calThumbnail = "jpg";
//		calInfo.setCalThumbnail(calThumbnail);
//		try {
//			dao.addCal(calInfo);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}



	//테이블 삭제
//	public static void main(String[] args) {
//	CalendarDAOInterface dao =  CalendarDAOOracle.getInstance();
//	//calTitle, calThumbnail은 요청전달데이터
//	//uidx 세션로그인정보
//	CalInfo calInfo = new CalInfo();
//	int uIdx = 2; //혜성 :1 , 다원:3 정은:2
//	Customer c = new Customer();
//	c.setUIdx(uIdx);
//	calInfo.setCustomer(c);
//	int calIdx = 3;
//	calInfo.setCalIdx(calIdx);
//		try {
//			dao.removeCal(calInfo);
//		} catch (RemoveException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//}



}
