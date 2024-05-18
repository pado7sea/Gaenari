import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/mates/mate_add.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/models/users/login_form.dart';
import 'package:forsythia/models/users/nickname_check.dart';
import 'package:forsythia/models/users/password_check.dart';
import 'package:forsythia/models/users/signup_user.dart';
import 'package:forsythia/screens/login/error_screen.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// MemberService 클래스
// 회원가입, 로그인, 계정설정관련 apid요청
class MemberService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 아이디중복체크
  static Future<Check> fetchIdCheck(
      BuildContext context, String endpoint) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/dupl/accountId?accountId=$endpoint'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return Check.fromJson(data);
        } else {
          throw Exception("status : ${data['status']}");
        }
      } else {
        throw Exception('statusCode : ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Id Check: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  //닉네임중복체크
  static Future<Check> fetchNickNameCheck(
      BuildContext context, String endpoint) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/dupl/nickname?nickName=$endpoint'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return Check.fromJson(data);
        } else {
          throw Exception("status : ${data['status']}");
        }
      } else {
        throw Exception('statusCode : ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching NickName Check: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  //회원가입
  static Future<SignupOk> fetchSignup(
      BuildContext context, SignupUser signupUser) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/members'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(signupUser.toJson()),
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return SignupOk.fromJson(data['data']);
        } else {
          print(data);
          throw Exception('없음');
        }
      } else {
        print(response.statusCode);
        throw Exception('그냥 잘못함');
      }
    } catch (e) {
      print('Error fetching Signup: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  //로그인
  static Future<LoginUser> fetchLogin(
      BuildContext context, LoginForm loginForm) async {
    try {
      return fetchLogin2(context, 'login', loginForm).then((result) {
        final SecureStorageService secureStorageService =
            SecureStorageService();
        final dynamic data = result['data'];
        final http.Response response = result['response'];
        String? token = response.headers["token"];
        if (token != null) {
          secureStorageService.saveToken(token);
        }
        return LoginUser.fromJson(data);
      });
    } catch (e) {
      print('Error fetching Login: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  static Future<dynamic> fetchLogin2(
      BuildContext context, String endpoint, LoginForm loginForm) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/$endpoint'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(loginForm.toJson()),
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return {'data': data, 'response': response};
        } else {
          print(data);
          throw Exception('없음');
        }
      } else {
        print(response.statusCode);
        throw Exception('그냥 잘못함');
      }
    } catch (e) {
      print('Error fetching Login2: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 닉네임 수정
  static Future<MateAdd> fetchEditNickName(
      BuildContext context, nickname) async {
    try {
      return fetchPutData(context, 'member/nickname?nickName=$nickname')
          .then((data) => MateAdd.fromJson(data));
    } catch (e) {
      print('Error fetching Edit NickName: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // put요청
  static Future<dynamic> fetchPutData(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.put(
        Uri.parse('$baseUrl/$endpoint'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token'
        },
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return data;
        } else {
          throw Exception('내잘못');
        }
      } else {
        throw Exception('닉네임수정${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Put Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 신체정보 수정
  static Future<MateAdd> fetchEditBodyInfo(
      BuildContext context, jsonData) async {
    try {
      return fetchBodyInfoPutData(context, 'member/info', jsonData)
          .then((data) => MateAdd.fromJson(data));
    } catch (e) {
      print('Error fetching Edit Body Info: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // put요청-바디
  static Future<dynamic> fetchBodyInfoPutData(
      BuildContext context, String endpoint, dynamic jsonData) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.put(
        Uri.parse('$baseUrl/$endpoint'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token',
        },
        body: jsonData,
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return data;
        } else {
          throw Exception('내잘못');
        }
      } else {
        throw Exception('신체정보수정${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Body Info Put Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 현재 비밀번호 확인
  static Future<PasswordCheck> fetchNowPassWord(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.post(
        Uri.parse('$baseUrl/member/password'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token',
        },
        body: endpoint,
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return PasswordCheck.fromJson(data);
        } else {
          throw Exception("status : ${data['status']}");
        }
      } else {
        throw Exception('statusCode : ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Now Password: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 비밀번호 수정
  static Future<MateAdd> fetchEditPassWord(
      BuildContext context, password) async {
    try {
      return fetchPassWordPutData(context, 'member/password', password)
          .then((data) => MateAdd.fromJson(data));
    } catch (e) {
      print('Error fetching Edit Password: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // put요청
  static Future<dynamic> fetchPassWordPutData(
      BuildContext context, String endpoint, String password) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.put(
        Uri.parse('$baseUrl/$endpoint'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token'
        },
        body: password,
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return data;
        } else {
          throw Exception('내잘못');
        }
      } else {
        throw Exception('비밀번호수정${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Password Put Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }
}
