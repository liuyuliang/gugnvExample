package com.gugnv.example.fastjson;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gugnv.example.fastjson.entity.User;

public class Test {
	
	public static void main(String[] args) {
		User u = new User();
		u.setAge(3);
		u.setName("Tom");
		u.setBirthday(new Date());
		User uu = new User();
		uu.setAge(3);
		uu.setName("Tom");
		uu.setBirthday(new Date());
		// 对象转json
		System.out.println(JSON.toJSONString(u));
		// 对象转json并格式化时间
		String userString = JSON.toJSONStringWithDateFormat(u, "yyyy-MM-dd HH:mm:ss");
		// JSON串转对象
		User user = JSON.parseObject(userString, User.class);
		System.out.println(user.getName());
		User[] users = new User[2];
		users[0] = u;
		users[1] = uu;
		String usersString = JSON.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss");
		
		List<User> list = JSON.parseArray(usersString, User.class);
		System.out.println(list.get(1).getName());
		System.out.println(JSON.toJSONString(list));

	}
}
