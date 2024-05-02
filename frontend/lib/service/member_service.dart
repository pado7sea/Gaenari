import 'dart:convert';
import 'package:forsythia/models/users/LoginUser.dart';
import 'package:forsythia/models/users/loginForm.dart';
import 'package:http/http.dart' as http;

class MemberService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';

  // 중복된 부분을 함수로 빼서 재사용하도록 함.
  static Future<dynamic> fetchData(String endpoint, LoginForm loginForm) async {
    final response = await http.post(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(loginForm.toJson()),
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data;
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
  static Future<LoginUser> fetchLogin(LoginForm loginForm) async {
    return fetchData('login', loginForm)
        .then((data) => LoginUser.fromJson(data));
  }
}
