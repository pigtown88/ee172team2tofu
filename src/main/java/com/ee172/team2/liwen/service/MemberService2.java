//package com.ee172.team2.liwen.service;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.util.Base64;
//import java.util.Collections;
//import java.util.Optional;
//
//import javax.imageio.ImageIO;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ee172.team2.liwen.dto.MemberRegisterDTO;
//import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
//import com.ee172.team2.liwen.model.Member;
//import com.ee172.team2.liwen.repository.InterestRepository;
//import com.ee172.team2.liwen.repository.MemberRepository;
//
//@Service
//public class MemberService2 {
//
//	@Autowired
//	private MemberRepository memberDAO;
//
//	@Autowired
//	private PasswordEncoder pwdEncoder;
//
////	// 可以用
////	public Member addMember(Member member) {
//////		member.setMemberPwd(member.getMemberPwd());
////		member.setMemberPwd(pwdEncoder.encode(member.getMemberPwd()));
////		member.setMemberPhone(member.getMemberPhone());
////		member.setMemberBirth(member.getMemberBirth());
////		member.setMemberGender(member.getMemberGender());
////		member.setMemberPhoto(member.getMemberPhoto());
////		member.setInsId(member.getInsId());
////		return memberDAO.save(member);
////	}
//
////	// 可以用
////	public String addMemberAndGenerateToken(Member member) {
////		// 調用 addMember 方法來存儲會員資訊
////		Member savedMember = addMember(member);
////
////		// 直接使用會員ID作為驗證 token
////		String verificationToken = String.valueOf(savedMember.getMemberId());
////
////		// 返回生成的驗證 token
////		return verificationToken;
////	}
//
//	// DTO
//	public Member addMember(MemberRegisterDTO memberRegisterDTO) {
//		Member member = new Member();
//
//		// member.setMemberPwd(member.getMemberPwd());
//		member.setMemberPwd(pwdEncoder.encode(memberRegisterDTO.getMemberPwd()));
//		member.setMemberPhone(memberRegisterDTO.getMemberPhone());
//		member.setMemberBirth(memberRegisterDTO.getMemberBirth());
//		member.setMemberGender(memberRegisterDTO.getMemberGender());
//		member.setMemberPhoto(memberRegisterDTO.getMemberPhoto());
//		member.setInsId(memberRegisterDTO.getInsId());
//		return memberDAO.save(member);
//	}
//
//	// DTO
//	public String addMemberAndGenerateToken(MemberRegisterDTO memberRegisterDTO) {
//		// 調用 addMember 方法來存儲會員資訊
//		Member savedMember = addMember(memberRegisterDTO);
//
//		// 直接使用會員ID作為驗證 token
//		String verificationToken = String.valueOf(savedMember.getMemberId());
//
//		// 返回生成的驗證 token
//		return verificationToken;
//	}
//
//	public void completeEmailVerification(Integer memberId) {
//		// 完成郵件驗證後，修改資料庫中 <Member> 的權限
//		updatePermission(memberId, 1);
//	}
//
//	public void updatePermission(Integer memberId, Integer newPermission) {
//		// 根據 memberId 更新會員的權限
//		Optional<Member> optionalMember = memberDAO.findById(memberId);
//		if (optionalMember.isPresent()) {
//			Member member = optionalMember.get();
//			member.setPermission(newPermission);
//			memberDAO.save(member);
//		}
//		// 如果需要處理找不到會員的情況，可以加入相應的邏輯
//	}
//
//	public Member checkLogin(String memberEmail, String memberPwd) {
//		Member dbMember = memberDAO.findByMemberEmail(memberEmail);
//
//		if (dbMember != null) {
//			if (pwdEncoder.matches(memberPwd, dbMember.getMemberPwd())) {
//				return dbMember;
//			}
//		}
//		return null;
//	}
//
//	public SessionLoginMemberDTO googleEmailLogin(String memberEmail) {
//		Member dbMember = memberDAO.findByMemberEmail(memberEmail);
//
//		SessionLoginMemberDTO memberDTO = new SessionLoginMemberDTO();
////		 SessionLoginMemberDTO memberDTO  =  new SessionLoginMemberDTO(dbMember.getMemberId(),dbMember.getMemberEmail(),true);
//
//		return memberDTO;
//	}
//
//	// 將Google登入使用者資料存進資料庫,如果使用者已存在資料庫，return資料
//	@Transactional
//	public Member saveGoogleUserData(String memberName, String memberEmail) {
//		Member dbMember = memberDAO.findByMemberEmail(memberEmail);
//
//		if (dbMember != null) {
//			return dbMember;
//		}
//		Member member = new Member();
//		member.setMemberName(memberName);
//		member.setMemberEmail(memberEmail);
////		member.setMemberPhoto(payloadPicture);		
//		return memberDAO.save(member);
//	}
//
//	public boolean checkMemberEmailIfExist(String memberEmail) {
//		Member dbMember = memberDAO.findByMemberEmail(memberEmail);
//
//		if (dbMember != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	// DTO
//	public String registerMember(MemberRegisterDTO memberRegisterDTO, MultipartFile memberPhoto) {
//	    try {
//	        // Email不重複
//	        if (isEmailDuplicate(memberRegisterDTO.getMemberEmail())) {
//	            throw new RuntimeException("該信箱已被註冊，請使用其他信箱。");
//	        }
//
//	        // Email&密碼不得為空值
//	        if (memberRegisterDTO.getMemberEmail() == null || memberRegisterDTO.getMemberPwd() == null) {
//	            throw new RuntimeException("電子郵件與密碼不得為空值");
//	        }
//
//	        // 信箱格式驗證
//	        if (!isValidEmail(memberRegisterDTO.getMemberEmail())) {
//	            throw new RuntimeException("無效的信箱格式");
//	        }
//
//	        // 二次確認密码
//	        // 在前端處理確認密碼匹配的邏輯
//	        if (!memberRegisterDTO.getMemberPwd().equals(memberRegisterDTO.getConfirmPassword())) {
//	            throw new RuntimeException("密碼不同，請再確認");
//	        }
//
//	        // 密碼正則表達式驗證
//	        if (!memberRegisterDTO.getMemberPwd().matches("[A-Za-z0-9]+") || memberRegisterDTO.getMemberPwd().length() < 8) {
//	            throw new IllegalArgumentException("密碼必須至少包含1個大寫字母、1個小寫字母、1個數字，且長度至少為8個字元。");
//	        }
//
//	        // 驗證上傳圖檔的限制
//	        validateProfilePicture(memberPhoto);
//
//	        // 將選擇的興趣儲存到資料庫
//	        // ... （你的邏輯）
//
//	        // 保存用戶到資料庫
//	        Member savedMember = memberService.addMember(memberRegisterDTO);
//
//	        // 如果保存成功，則返回成功訊息
//	        if (savedMember != null) {
//	            return "註冊成功";
//	        } else {
//	            throw new RuntimeException("註冊失敗"); // 或者其他錯誤消息
//	        }
//	    } catch (Exception e) {
//	        return e.getMessage();
//	    }
//	}
//	
//
////	public String registerMember(Member member) {
////
////		try {
////			// Email不重複
////			if (isEmailDuplicate(member.getMemberEmail())) {
////				throw new RuntimeException("該信箱已被註冊，請使用其他信箱。");
//////				System.out.println("該信箱已被註冊，請使用其他信箱。");
////			}
////
////			// Email&密碼不得為空值
////			if (member.getMemberEmail() == null || member.getMemberPwd() == null) {
////				throw new RuntimeException("電子郵件與密碼不得為空值");
////			}
////
////			// 信箱格式驗證
////			if (!isValidEmail(member.getMemberEmail())) {
////				throw new RuntimeException("無效的信箱格式");
////			}
////
////			// 二次確認密码
////			// 在前端處理確認密碼匹配的邏輯
////			if (!member.getMemberPwd().equals(member.getConfirmPassword())) {
////				throw new RuntimeException("密碼不同，請再確認");
////			}
////
////			// 密碼正則表達式驗證
////			if (!member.getMemberPwd().matches("[A-Za-z0-9]+") || member.getMemberPwd().length() < 8) {
////				throw new IllegalArgumentException("密碼必須至少包含1個大寫字母、1個小寫字母、1個數字，且長度至少為8個字元。");
////			}
////
////			// 上傳圖檔的限制
//////			validateProfilePicture(memberPhoto);
////
////			// 將選擇的興趣儲存到資料庫
//////			List<Interest> selectedInsList = new ArrayList<>();
//////			List<Integer> selectedInterestIds = selectedInterestsDTO.getSelectedInterestIds();
//////			for (Integer insId : selectedInterestIds) {
//////				
//////				Interest interest = new Interest();
//////				interest.setInsId(insId);
//////				selectedInsList.add(interest);
//////			}
//////			 member.setInsId(selectedInsList);
////
////			// 保存用戶到資料庫
////			Member savedMember = memberDAO.save(member);
////
////			// 如果保存成功，則返回成功訊息
////			if (savedMember != null) {
////				return "註冊成功";
////			} else {
////				throw new RuntimeException("註冊失敗"); // 或者其他錯誤消息
////			}
////
////		} catch (Exception e) {
////			return e.getMessage();
////		}
////
//////		return "註冊成功";
////	}
//
//	// 在資料庫中檢查Email是否已存在
//	private boolean isEmailDuplicate(String email) {
//		return memberDAO.findByMemberEmail(email) == null;
//	}
//
//	// 使用正則表達式驗證信箱格式
//	private boolean isValidEmail(String email) {
//		return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
//	}
//
//	// 驗證上傳圖檔的大小、尺寸、類型
//	private void validateProfilePicture(MultipartFile memberPhoto) throws IOException {
//		// 檢查檔案類型
//		String contentType = memberPhoto.getContentType();
//		if (contentType == null || !contentType.startsWith("image/")) {
//			throw new IllegalArgumentException("上傳的檔案不是有效的圖片檔案。");
//		}
//
//		// 檢查檔案大小
//		if (memberPhoto.getSize() > 5 * 1024 * 1024) {
//			throw new IllegalArgumentException("圖片檔案大小不能超過5MB。");
//		}
//
//		// 檢查圖檔尺寸
//		BufferedImage image = ImageIO.read(memberPhoto.getInputStream());
//		int width = image.getWidth();
//		int height = image.getHeight();
//		if (width > 800 || height > 600) {
//			throw new IllegalArgumentException("圖片寬度不能超過800像素，高度不能超過600像素。");
//		}
//	}
//
//}
