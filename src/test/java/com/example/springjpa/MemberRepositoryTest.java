package com.example.springjpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//강의에선 JUnit4f를 써서 @RunWith을 사용하지만 JUnit5에는 생략 된다.
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    //트랜잭션 적용
    @Transactional
    @Rollback(value = false)
    public void save() {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 쿼리가 insert문 하나만 나간다.
        // 같은 트랜잭션 안에 있다. -> 같은 영속성 컨텍스트에 존재함 -> 조회하고자 하는 엔티티가 영속성 컨텍스트에 있으므로 캐시에서 가져옴
         Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void find() {
    }
}