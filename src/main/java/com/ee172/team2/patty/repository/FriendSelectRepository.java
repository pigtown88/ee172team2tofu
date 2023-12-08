package com.ee172.team2.patty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ee172.team2.liwen.model.Job;
import com.ee172.team2.liwen.model.MemberDating;

public interface FriendSelectRepository extends JpaRepository<MemberDating, Integer> {
//
//	@Query("SELECT m FROM MemberDating m WHERE (m.height BETWEEN :minHeight AND :maxHeight) "
//			+ "AND (m.salary BETWEEN :minSalary AND :maxSalary) ")
//	@Query(value="SELECT * FROM memberDating WHERE memberDating.height >= :minHeight AND memberDating.salary >= :minSalary"
//			,nativeQuery = true)
//	@Query(value = "SELECT md.height, md.salary, j.jobName, mg.memberGender " +
//	           "FROM member_dating md " +
//	           "JOIN job j ON md.jobId = j.jobId " +
//	           "JOIN member_gender mg ON md.memberId = mg.memberId " +
//	           "WHERE md.height >= :minHeight AND md.salary >= :minSalary AND j.jobName LIKE %:jobName% "
//	           + "AND mg.memberGender = :memberGender", nativeQuery = true)
//	@Query(value = "SELECT md.*" + "FROM member_dating md" + "JOIN job j ON md.jobId = j.jobId"
//			+ "JOIN member m ON md.memberId = m.memberId" + "WHERE md.height >= :minHeight"
//			+ "AND md.salary >= :minSalary " + "AND j.jobName = :jobName"
//			+ "AND m.memberGender = :gender", nativeQuery = true)

	@Query("SELECT md FROM MemberDating md WHERE md.height >= :minHeight  AND md.salary >= :minSalary  AND md.jobId.jobName = :jobName AND md.member.memberGender = :gender")
	List<MemberDating> findByHeightSalaryAndJob(@Param("minHeight") Integer minHeight,
			@Param("minSalary") Integer minSalary, @Param("jobName") String jobName, @Param("gender") String gender);
//	List<MemberDating> findByHeightSalaryAndJob(@Param("minHeight") Integer minHeight,
//			@Param("maxHeight") Integer maxHeight, @Param("minSalary") Integer minSalary,
//			@Param("maxSalary") Integer maxSalary, @Param("jobName") String jobName, @Param("gender") String gender);

	@Query("SELECT md FROM MemberDating md WHERE md.height >= :minHeight AND md.salary >= :minSalary AND md.jobId.jobName = :jobName AND md.member.memberGender = :gender")
	List<MemberDating> findByCriteria(@Param("minHeight") Integer minHeight, @Param("minSalary") Integer minSalary,
			@Param("jobName") String jobName, @Param("gender") String gender);

//	@Query("SELECT md FROM MemberDating md WHERE md.height BETWEEN :minHeight AND :maxHeight AND md.salary BETWEEN :minSalary AND :maxSalary AND md.jobId.jobName = :jobName AND md.memberId.memberGender = :gender")
//	List<MemberDating> findByCriteria(@Param("minHeight") Integer minHeight, @Param("maxHeight") Integer maxHeight, @Param("minSalary") Integer minSalary, @Param("maxSalary") Integer maxSalary, @Param("jobName") String jobName, @Param("gender") String gender);

}
