import 'dart:convert';
import 'package:forsythia/models/watch/watch.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// 워치 연동 서비스
class WatchService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 워치 인증번호 발급
  static Future<Watch> fetchWatch() async {
    return fetchGetData('watch').then((data) => Watch.fromJson(data));
  }

  // get요청
  static Future<dynamic> fetchGetData(String endpoint) async {
    String? token = await secureStorageService.getToken();
    final response = await http.get(
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
        throw Exception('Failed to load data');
      }
    } else {
      throw Exception('Failed to load data');
    }
  }
}
