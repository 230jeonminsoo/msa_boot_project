package com.reco.board.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reco.board.vo.Board;
import com.reco.exception.FindException;

@SpringBootTest
class boarddaooracletest {
	@Autowired
	private BoardDAOOracle dao;
	@Test
	void testFindBrdByIdx() {
		int idx = 9;
		try {
			Board b = dao.findBrdByIdx(idx);
			System.out.println(b);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}