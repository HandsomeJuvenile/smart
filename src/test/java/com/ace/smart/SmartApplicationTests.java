package com.ace.smart;

import com.ace.smart.config.shiro.ShiroConfig;
import com.ace.smart.email.SendEmail;
import com.ace.smart.entity.*;
import com.ace.smart.entity.vo.PRoleVo;
import com.ace.smart.jump.JumpHelper;
import com.ace.smart.service.PRoleService;
import com.ace.smart.service.PUserService;
import com.ace.smart.serviceimpl.*;
import com.ace.smart.util.IdGen;
import com.ace.smart.util.PasswordUtil;
import com.example.smart.entity.*;
import com.ace.smart.page.LeavePage;
import com.ace.smart.service.PPermissionService;
import com.ace.smart.service.PRolePermissionService;
import com.example.smart.serviceimpl.*;
import com.ace.smart.util.DateUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(SmartApplicationTests.class);
	@Autowired
	private PUserService pUserService;
	@Autowired
	private UUserServiceImpl uUserService;
	@Autowired
	private TsMenuInfoServiceImpl tsMenuInfoService;
	@Autowired
	private UroleServiceImpl uroleService;
	@Autowired
	private UpermissionServiceImpl upermissionService;
	@Autowired
	private UUserRoleServiceImpl uUserRoleService;
	@Test
	public void testMenuItem() {
		List<TsMenuinfo> tsMenuinfoList = tsMenuInfoService.sysMenuItems("000000000000000");
		System.out.println(tsMenuinfoList.size());
	}

	@Test
	public void testLogin() {
		SecurityManager securityManager = new ShiroConfig().securityWebManager();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			//4、登录，即身份验证
			subject.login(token);
			logger.info("登录成功");
		} catch (AuthenticationException e) {
			logger.info("登录失败");
			e.printStackTrace();
			//5、身份验证失败
		}
		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户是否已经登录成功
		//退出登录
		subject.logout();
	}

	//通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现。
	@Test
	public void testSuan() {
		String username = "wang";
		String salt = "123";
		String sha1 = new SimpleHash("SHA-1", username, salt).toString();
		System.out.println(sha1);
	}

	@Test
	public void testAddUser() {
		UUser uUser = new UUser();
		long id = IdGen.getAtomicCounter();
		uUser.setId(id);
		uUser.setCreateTime(new Date().toString());
		uUser.setEmail("123");
		uUser.setLastLoginTime(new Date().toString());
		uUser.setNickname("迪士尼");
		uUser.setPswd(PasswordUtil.encryptPassword(id+"","111111"));
		try{
			uUserService.insert(uUser);
			logger.error("添加用户成功");
		}catch (Exception e){
			logger.error("添加用户失败");
			e.printStackTrace();
		}
	}

	@Test
	public void testAddPUser() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PUser uUser = new PUser();
		long id = IdGen.getAtomicCounter();
		uUser.setId(id);
		uUser.setCreateTime(simpleDateFormat.format(new Date()));
		uUser.setEmail("123");
		uUser.setLastLoginTime(simpleDateFormat.format(new Date()));
		uUser.setNickname("三傻");
		uUser.setPhone("1214138");
		uUser.setSelfIntroduction("傻得三笔");
		uUser.setStatus("1");
		uUser.setAddress("安庆");
		uUser.setAge(58);
		uUser.setUserLoginName("sansha");
		uUser.setPswd(PasswordUtil.encryptPassword(id+"","111111"));
		try{
			pUserService.insert(uUser);
			logger.error("添加用户成功");
		}catch (Exception e){
			logger.error("添加用户失败");
			e.printStackTrace();
		}
	}

	@Test
	public void testAddRoel(){
		URole role = new URole();
		role.setId(IdGen.getAtomicCounter());
		role.setName("admin");
		role.setType("0");
		try{
			uroleService.insert(role);
			logger.error("添加角色成功");
		}catch (Exception e){
			logger.error("添加角色失败");
			e.printStackTrace();
		}
	}

	@Test
	public void testAddPermiss(){
		UPermission role = new UPermission();
		role.setId(IdGen.getAtomicCounter());
		role.setName("库存管理");
		role.setUrl("/number/index");
		try{
			upermissionService.insert(role);
			logger.error("添加资源成功");
		}catch (Exception e){
			logger.error("添加资源失败");
			e.printStackTrace();
		}
	}

	@Test
	public void testRolePermisson(){
		List<Long> per_ids = new ArrayList<>();
		per_ids.add(150984519673001l);
		per_ids.add(150984512698201l);
		//uRolePerService.rolePermisson(150984501384101l,per_ids);
	}

	@Test
	public void testUserRole(){
		UUser uUsers = uUserService.findUserRole(150978126134401l);
		System.out.println("");
	}

	@Test
	public void testdelUserRole(){
		UUserRole userRole = new UUserRole();
		userRole.setRid(150984501384101l);
	}

	@Test
	public void testCheckUsername(){
		UUser uUser = uUserService.checkUsername(150978126134401l);
		System.out.println(uUser.getNickname());
	}

	@Test
	public void testPass(){
		System.out.println(PasswordUtil.encryptPassword("150978126134401","13"));
	}

	@Test
	public void testPUser(){
		PageInfo pageInfo = pUserService.selectAll(2,1);
		List<PUser> userList = pageInfo.getList();
		System.out.println(userList.size());
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.isIsFirstPage());
		System.out.println(LeavePage.setLeavePage(pageInfo));
	}

	@Test
	public void testPUservalidation(){
		PUserVo pUser = new PUserVo();
		pUser.setUserLoginName("hhhh");
		pUser.setPhone("12345678920");
		pUser.setEmail("12345678974@163.com");
		String falg  = pUserService.validation(pUser);
		System.out.println(falg);
	}

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private SendEmail sendEmail;
	@Value("${spring.mail.username}")
	private String Sender; //读取配置文件中的参数
	@Autowired
	private TemplateEngine templateEngine;

	@Test
	public void sendSimpleMail() throws Exception {
		String [] strings = {"18356090837@163.com"};
		String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎加入Ace !<br/><br/>帐户需要激活才能使用，" +
				"赶紧激活成为Ace正式的一员吧:)<br/><br/>请在24小时内输入下面的激活码立即激活帐户:1234";
		Email email = new Email();
		email.setSubject("你好");
		email.setContent(content);
		email.setRecives(strings);
		sendEmail.sendTimerSimpleMail(email);
	}

	@Test
	public void sendSieMail() throws Exception {
		Email email = new Email();
		email.setRecive(Sender);
		email.setToken("1234");
		email.setContent("ok?");
		sendEmail.sendTimerSimpleMail(email);
	}

	@Test
	public void testFujianMail(){
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(Sender);
			helper.setSubject("发送附件");
			helper.setTo(Sender);
			FileSystemResource fileSystemResource = new FileSystemResource(new File("src/main/resources/static/assets/images/gallery/image-2.jpg"));
			helper.addAttachment("p\t\t\thelper.setText(\"看附件\");\nic.jpg",fileSystemResource);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(message);
	}


	@Test
	public void sendHappyMail(){
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(message, true);
			IContext context = new Context();
			// 获取模板html代码
			String process =templateEngine.process("christmas",context);
			helper.setFrom(Sender);
			helper.setSubject("发送happy");
			helper.setTo("zhuzhenzhen@ofo.com");
			helper.setText(process,true);
			FileSystemResource fileSystemResource = new FileSystemResource(new File("src/main/resources/static/assets/images/gallery/image-2.jpg"));
			helper.addAttachment("pic.jpg",fileSystemResource);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(message);
	}

	@Test
	public void set() throws MessagingException {
		String [] strings = {"18356090837@163.com","595186151@qq.com"};
		Email email = new Email();
		email.setImgPath("/static/img/blackwaitwhite.png");
		email.setRecives(strings);
		email.setSubject("hello");
		sendEmail.sendPicEmail(email);
		System.out.println("邮件发送成功");
	}

	@Autowired private PPermissionService pPermissionService;
	@Test
	public void testSort(){
		List<PPermission> list = pPermissionService.sortMenu(0);
		System.out.println(list.size());
	}

	@Test
	public void testMap(){
		Map<String,Integer> map = new HashMap<>();
		map.put("count",1);
		map.put("count",2);
		System.out.println(map.get("count").toString());

	}


	@Test
	public void testJumpHeper(){
		JumpHelper jumpHelper = new JumpHelper();
		try {
			int[] result = jumpHelper.getHalmaAndBoardXYValue(new File("C:\\Users\\Administrator\\Documents\\Tencent Files\\1414790478\\FileRecv\\MobileFile\\current2.png"));
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void removePic() throws IOException {
		StringBuilder sb = new StringBuilder("C:\\Users\\Administrator\\Documents\\Tencent Files\\1414790478\\FileRecv\\");
		sb.append(DateUtil.getPicCurrentDay());
		sb.append(".png");
		File file = new File(sb.toString()); // 源文件
		if(!file.exists()){
			System.out.println("文件还未下载!");
			return;
		}
		File pastfile  = new File("E:\\code\\smart\\src\\main\\resources\\static\\img\\"+DateUtil.getPicCurrentDay()+".png"); // 目标文件
		if(pastfile.exists()){
			System.out.println("文件已存在!");
			return;
		}
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(pastfile,true));
		byte [] fileB = new byte[1024];
		int len = 0;
		while(-1 !=(len = is.read(fileB))){
			bufferedOutputStream.write(fileB);
		}
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		is.close();
	}

	@Test
	public void testObv(){
		Observable observer = new Observable();
	}


	@Test
	public void textStatus(){
		Map<String,Object> map = new HashMap<>();
		map.put("menuId","151608302119801");
		map.put("status","1");
		pPermissionService.updateState(map);
	}

	@Autowired
	private PRoleService pRoleService;

	@Test
	public void textRole(){
		PageInfo list = pRoleService.selectAll(1,10,"");
		System.out.println("13");
	}


	@Test
	public void testAddRole () {
		PRoleVo pRoleVo = new PRoleVo();
		pRoleVo.setrStatus("1");
		pRoleVo.setRemark("123465");
		pRoleVo.setrName("ceo2");
		pRoleVo.setPpers("on,150984519673022,150984519673036,150984519673037");
		pRoleService.addRolePer(pRoleVo);
	}

	@Autowired
	private PRolePermissionService pRolePermissionService;

	@Test
	public void testPRole(){
		List<PRolePermission> pRolePermissions = pRolePermissionService.selectRolePperm(151789557580601l);
		System.out.println(pRolePermissions.size());
	}

	@Test
	public void testSortMenu(){
		List<PPermission> list = pPermissionService.sortMenu(0);
		List<PPermission> permissionList = digui(list);
		System.out.println(permissionList.size());
	}



	private List<PPermission> digui(List<PPermission> list){
		List<PRolePermission> pRolePermissions = pRolePermissionService.selectRolePperm(151789611486001l);
		for (PPermission pPermission: list) {
			for (PRolePermission pRolePermission:pRolePermissions) {
				if (pRolePermission.getPid().equals(pPermission.getMenuId()) ) {
						pPermission.setChecked(true);
						if (pPermission.getChildren()!=null) {
							digui(pPermission.getChildren());
						}
				}
			}
		}
		return list;
	}
}