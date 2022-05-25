package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.guestbookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String formPassword = request.getParameter("password");
		guestbookVo vo = new GuestbookRepository().findByPassword(Long.parseLong(no)).get(0);
		String guestbookPassword = vo.getPassword();
		
		if(formPassword.equals(guestbookPassword)){
			new GuestbookRepository().delete(Long.parseLong(no));
			WebUtil.redirect(request, response, request.getContextPath() + "/guestbook?a=list");
		} else {
			WebUtil.redirect(request, response, request.getContextPath() + "/guestbook?a=deleteform&no=" + no);
		}
	}

}
