import 'dart:convert';
import 'package:forsythia/models/mates/mate_add.dart';
import 'package:forsythia/models/mates/search_mate_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// MemberService 클래스
// 친구 관련 서비스 전부
class MateService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 친구목록 불러오기
  static Future<SearchMateList> fetchSearchMateList(keyword) async {
    return fetchGetData('mate/search?nickName=$keyword')
        .then((data) => SearchMateList.fromJson(data));
  }

  // 친구추가하기
  static Future<MateAdd> fetchAddMate(id) async {
    return fetchPostData('mate/add/$id').then((data) => MateAdd.fromJson(data));
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

  // post요청
  static Future<dynamic> fetchPostData(String endpoint) async {
    String? token = await secureStorageService.getToken();
    final response = await http.post(
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
