import 'dart:convert';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/models/users/login_form.dart';
import 'package:forsythia/models/users/signup_user.dart';
import 'package:forsythia/provider/token_provider.dart';
import 'package:http/http.dart' as http;

// MemberService 클래스
class MemberService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';

  //회원가입
  static Future<SignupOk> fetchSignup(SignupUser signupUser) async {
    final response = await http.post(
      Uri.parse('$baseUrl/members'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(signupUser.toJson()),
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        // SignupOk 객체로 변환하여 반환
        return SignupOk.fromJson(data['data']);
      } else {
        print(data);
        throw Exception('없음');
      }
    } else {
      print(response.statusCode);
      throw Exception('그냥 잘못함');
    }
  }

  //로그인
  static Future<dynamic> fetchData(String endpoint, LoginForm loginForm) async {
    final response = await http.post(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(loginForm.toJson()),
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return {'data': data, 'response': response}; // 응답과 데이터를 함께 반환
      } else {
        print(data);
        throw Exception('없음');
      }
    } else {
      print(response.statusCode);
      throw Exception('그냥 잘못함');
    }
  }

  // 로그인
  static Future<LoginUser> fetchLogin(
      LoginForm loginForm, TokenProvider tokenProvider) async {
    return fetchData('login', loginForm).then((result) {
      // 응답과 데이터를 받음
      final dynamic data = result['data'];
      final http.Response response = result['response'];
      // 응답에서 토큰을 추출
      String? token = response.headers["token"];
      // 토큰이 null이 아니라면 토큰을 업데이트
      if (token != null) {
        tokenProvider.updateToken(token);
      }
      // JSON 데이터를 LoginUser 객체로 변환해서 반환
      return LoginUser.fromJson(data);
    });
  }
}
