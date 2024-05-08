import 'dart:convert';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// RecordSevice 클래스
// 기록, 통계 api
class RecordSevice {
  static const String baseUrl =
      'https://api.gaenari.kr/api/exercise-record-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // // 검색한 친구목록 불러오기
  // static Future<SearchMateList> fetchSearchMateList(keyword) async {
  //   return fetchGetData('mate/search?nickName=$keyword')
  //       .then((data) => SearchMateList.fromJson(data));
  // }

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
