package com.ee172.team2.liwen.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.ee172.team2.liwen.config.GoogleOauthConfig;
import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.liwen.service.MemberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GoogleOAuth2NativeHttpController {
	// 原生使用(okhttp) http request, response 執行 OAuth2 的寫法

	@Autowired
	private GoogleOauthConfig googleOauth2Config;

	@Autowired
	private MemberService memberService;

	@Autowired
	private RestTemplate restTemplate;

	private final String scope = "https://www.googleapis.com/auth/userinfo.email";

	@GetMapping("/google-login") // 前端叫後端
	public String googleLogin(HttpServletResponse response) {

		// 直接 redirect 導向 Google OAuth2 API
		String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" + "client_id="
				+ googleOauth2Config.getClientId() + "&response_type=code" + "&scope=openid%20email%20profile"
				+ "&redirect_uri=" + googleOauth2Config.getRedirectUri() + "&state=state";
		return "redirect:" + authUrl;
	}

	@GetMapping("/google-callback") // Google給後端
	public String oauth2Callback(@RequestParam(required = false) String code, HttpSession httpSession, Model model)
			throws IOException {
		if (code == null) {
			String authUri = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code" + "&client_id="
					+ googleOauth2Config.getClientId() + "&redirect_uri=" + googleOauth2Config.getRedirectUri()
					+ "&scope=" + scope;
			return "redirect:" + authUri;
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<>(); // key 可能重複再用
			map.add("code", code);
			map.add("client_id", googleOauth2Config.getClientId());
			map.add("client_secret", googleOauth2Config.getClientSecret());
			map.add("redirect_uri", googleOauth2Config.getRedirectUri());
			map.add("grant_type", "authorization_code");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

			ResponseEntity<String> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request,
					String.class);
			String credentials = response.getBody();
			// System.out.println("credentials" + credentials);

			JsonNode jsonNode = new ObjectMapper().readTree(credentials);
			String accessToken = jsonNode.get("access_token").asText(); // code換token, Google稱為access_token
			String idToken = jsonNode.get("id_token").asText(); // 未使用到

			HttpHeaders headers2 = new HttpHeaders();
			headers2.setBearerAuth(accessToken);

			HttpEntity<String> entity = new HttpEntity<>(headers2);
			ResponseEntity<String> response2 = restTemplate.exchange(
					"https://www.googleapis.com/oauth2/v1/userinfo?alt=json", HttpMethod.GET, entity, String.class);

			String payloadResponse = response2.getBody();

			JsonNode payloadJsonNode = new ObjectMapper().readTree(payloadResponse);

			// Extract data from payloadJsonNode and process it
			String payloadGoogleId = payloadJsonNode.get("id").asText();
			String payloadEmail = payloadJsonNode.get("email").asText();
			String payloadName = payloadJsonNode.get("name").asText();
			String payloadPicture = payloadJsonNode.get("picture").asText();
			String payloadLocale = payloadJsonNode.get("locale").asText();

			System.out.println("payloadGoogleId: " + payloadGoogleId);
			System.out.println("payloadEmail: " + payloadEmail);
			System.out.println("payloadName: " + payloadName);
			System.out.println("payloadPicture: " + payloadPicture);
			System.out.println("payloadLocale: " + payloadLocale);

//      // 將圖片數據轉換為 byte[]
//      byte[] pictureData = payloadPicture.getBytes(StandardCharsets.UTF_8);

//			// 透過 URL 下載圖片
//			URL url = new URL(payloadPicture);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("GET");
//			InputStream inputStream = connection.getInputStream();
//			System.out.println("inputStream=====================" + inputStream);
//
//			// 將 InputStream 轉換為 byte[]
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			byte[] buffer = new byte[1024];
//			int bytesRead;
//			while ((bytesRead = inputStream.read(buffer)) != -1) {
//				outputStream.write(buffer, 0, bytesRead);
//			}
//			byte[] pictureData = outputStream.toByteArray();
//			System.out.println("pictureData=====================" + pictureData);

			// 將資料存進資料庫
//			memberService.saveGoogleUserData(payloadName, payloadEmail, pictureData);
			memberService.saveGoogleUserData(payloadName, payloadEmail);

//			// 將圖片轉換為 Base64 字串
//			String base64Image = Base64.getEncoder().encodeToString(pictureData);
//			System.out.println("base64Image=====================" + base64Image);

//			// 驗證使用者，獲取登入資訊
//			SessionLoginMemberDTO memberDTO = new SessionLoginMemberDTO();
//			memberDTO.setMemberEmail(payloadEmail);
//			memberDTO.setMemberName(payloadName);
////			memberDTO.setMemberPhoto(base64Image);
//			memberDTO.setMemberPhoto(payloadPicture);
//			httpSession.setAttribute("loginMember", memberDTO);
			
//			model.addAttribute("base64Image", base64Image);
			
			// 驗證使用者，獲取登入資訊
			SessionLoginMemberDTO memberDTO = new SessionLoginMemberDTO();
			memberDTO.setMemberEmail(payloadEmail);
			memberDTO.setMemberName(payloadName);
			memberDTO.setPhotoURL(payloadPicture);
			httpSession.setAttribute("loginMember", memberDTO);
		}

		return "redirect:http://localhost:8087/ee172/home"; // 路徑導向首頁
	}

}