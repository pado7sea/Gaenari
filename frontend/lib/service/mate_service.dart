import 'dart:convert';
import 'package:forsythia/models/users/nickname_check.dart';
import 'package:forsythia/provider/token_provider.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// MemberService 클래스
// 회원가입, 로그인, 계정설정관련 apid요청
class MemberService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  // static String? token = Provider.of<TokenProvider>(context).token;
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 친구목록 불러오기
  static Future<Check> fetchIdCheck(String endpoint) async {
    String? token = await secureStorageService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/dupl/email?email=$endpoint'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token'
      },
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data['data'];
      } else {
        throw Exception('Failed to load data');
      }
    } else {
      throw Exception('Failed to load data');
    }
  }
}
