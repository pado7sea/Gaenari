//예시임 똑같이 쓰면 조짐

import 'dart:convert';
import 'package:forsythia/models/user.dart';
import 'package:http/http.dart' as http;

class UserServise {
  static const String baseUrl = 'https://api.example.com/';

  //로그인  get
  static Future<List<User>> fetchUsers() async {
    final response = await http.get('$baseUrl/users' as Uri);

    if (response.statusCode == 200) {
      // API에서 받아온 JSON 데이터를 파싱하여 User 객체의 리스트로 반환합니다.
      List<dynamic> jsonData = json.decode(response.body);
      return jsonData.map((json) => User.fromJson(json)).toList();
    } else {
      // 만약 요청이 실패하면 예외를 던집니다.
      throw Exception('Failed to load users');
    }
  }

  //회원가입 post
  static Future<void> createUser(User user) async {
    final response = await http.post(
      '$baseUrl/users' as Uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(user.toJson()), // Dart 객체를 JSON 형식으로 변환합니다.
    );

    if (response.statusCode != 201) {
      // 만약 요청이 실패하면 예외를 던집니다.
      throw Exception('Failed to create user');
    }
  }
}
