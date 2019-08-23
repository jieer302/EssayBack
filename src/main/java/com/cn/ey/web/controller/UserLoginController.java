package com.cn.ey.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Encoder;

import com.cn.ey.web.controller.UserLoginController;
import com.cn.ey.web.dao.IUserDao;
import com.cn.ey.web.pojo.User;


@Controller
@RequestMapping("/login")
public class UserLoginController {

	private static Logger logger = LoggerFactory
			.getLogger(UserLoginController.class);

	@Resource
	private IUserDao userDao;

	@RequestMapping("/login")
	public void loginAction(HttpSession session,
			HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, NoSuchAlgorithmException {

		String userName = httpServletRequest
				.getParameter("userName");
		String password = httpServletRequest
				.getParameter("password");
		
		if(userName == "" || password == ""){
			logger.info("Be careful : the userName or password is null!");
			return;
		}

		List<User> list = userDao.selectByUserName(userName);

		String loginFlag = "0";

		if ((list.get(0).getPassword()).equals(EncoderByMd5(password))) {
			loginFlag = "1";
			session.setAttribute("userName", userName);
			session.setAttribute("password", password);
			logger.info("user (name={}) login success", userName);
		}

		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// JSON可以将对象转为 json格式
		out.print(loginFlag);
	}
	
	public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}