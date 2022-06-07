package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public Boolean deleteMessage(Long no, String password) {
		GuestbookVo vo = guestbookRepository.findPasswordByNo(no);
		
		if(vo.getPassword().equals(password)) {
			guestbookRepository.delete(no);
			return true;
		}
		else
			return false;
	}
	
	public Boolean addMessage(GuestbookVo vo) {
		return guestbookRepository.insert(vo);
	}
}
