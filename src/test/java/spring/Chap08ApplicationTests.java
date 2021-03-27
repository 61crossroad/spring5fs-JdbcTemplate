package spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class Chap08ApplicationTests {
	@Autowired
	MemberDao memberDao;

	@Test
	public void testSelectAll() {
		List<Member> memberList = memberDao.selectAll();
		System.out.println("[testSelectAll]");
		memberList.forEach(member -> System.out.println(member.getEmail() + " (" + member.getRegisterDateTime() + ")"));
	}

	@Test
	public void testSelectByEmail() {
		Member member = memberDao.selectByEmail("61crossroad@gmail.com");
		System.out.println("[testSelectByEmail]\n" + member);
	}

	@Test
	public void testSelectMember() {
		Member member = memberDao.selectMember(1L);
		System.out.println("[testSelectMember 1L]\n" + member);
	}

	@Test
	public void testUpdate() {
		Member member = new Member(
				"61crossroad@gmail.com",
				"1234",
				"ModifiedName",
				LocalDateTime.now()
		);
		memberDao.update(member);
		testSelectByEmail();
	}

	@Test
	public void testInsert() {
		Member member = new Member(
				"inserTest@test.com",
				"1234",
				"Test Name",
				LocalDateTime.now()
		);
		memberDao.insert(member);
		System.out.println("[testInsert]\n" + member);
	}
}
