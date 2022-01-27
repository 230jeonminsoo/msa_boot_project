package com.reco.notice.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.vo.Notice;



@Repository
public class NoticeDAOOracle implements NoticeDAOInteface {
	

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private Logger logger = LoggerFactory.getLogger(NoticeDAOOracle.class.getName());
	
	@Override
	public List<Notice> findNtcAll() throws FindException{

		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<Notice> list = session.selectList("com.reco.notice.NoticeMapper.findNtcAll");
			
			if(list.size() == 0) {
				throw new FindException("저장된 글이 없습니다");
			}
			return list;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}			
		}
	}
	
	@Override
	public Notice addNtc(Notice n) throws AddException,FindException{
		SqlSession session =null;
		try {
			session = sqlSessionFactory.openSession();
			session.insert("com.reco.notice.NoticeMapper.addNtc",n);
			int ntcIdx = n.getNtcIdx();
			Notice notice = findNtcByIdx(ntcIdx);
			return notice;
		} catch (FindException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
}

	@Override
	public Notice findNtcByIdx(int ntcIdx) throws FindException {
		SqlSession session =null;
		try {
			session = sqlSessionFactory.openSession();
			Notice notice= session.selectOne("com.reco.notice.NoticeMapper.findNtcByIdx",ntcIdx);
			plusViewCount(ntcIdx);
			return notice;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
			
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Notice> findNtcByTitle(String word) throws FindException{
		SqlSession session =null;
		try {
			session = sqlSessionFactory.openSession();
			List<Notice> list = session.selectList("com.reco.notice.NoticeMapper.findNtcByTitle",word);
			if(list.size() == 0) {
				throw new FindException("단어를 포함하는 글이 없습니다.");
			}
			return list;
			
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Notice> findNtcByWord(String word) throws FindException{
		SqlSession session =null;
		
		try {
			session = sqlSessionFactory.openSession();
			List<Notice> list = session.selectList("com.reco.notice.NoticeMapper.findNtcByWord",word);
			if(list.size() == 0) {
				throw new FindException("단어를 포함하는 글이 없습니다.");
			}
			return list;
			
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
		
	}

	@Override
	public Notice modifyNtc(Notice n) throws ModifyException,FindException{
		SqlSession session =null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.reco.notice.NoticeMapper.modifyNtc",n);
			Notice notice=findNtcByIdx(n.getNtcIdx());
			return notice;
		}catch(FindException e){
			throw new ModifyException(e.getMessage());
		}catch(Exception e) {
			throw new ModifyException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}	
	}

	@Override
	public void removeNtc(int ntcIdx) throws RemoveException {
		SqlSession session =null;
		try {
			session = sqlSessionFactory.openSession();
			int deleterow = session.delete("com.reco.notice.NoticeMapper.removeNtc",ntcIdx);
	
			if(deleterow == 0) {
				System.out.println("해당 공지사항이 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}		
	}

	public void plusViewCount(int ntcIdx) {

		SqlSession session =null;
		session = sqlSessionFactory.openSession();
		session.update("com.reco.notice.NoticeMapper.plusViewCount",ntcIdx);
	}

	public static void main(String[] args) {
	System.out.println();
}	

}
