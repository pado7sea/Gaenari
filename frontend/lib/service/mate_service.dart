import 'dart:convert';
import 'package:forsythia/models/mates/accept_form.dart';
import 'package:forsythia/models/mates/mate_add.dart';
import 'package:forsythia/models/mates/mate_list.dart';
import 'package:forsythia/models/mates/search_mate_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// MemberService 클래스
// 친구 관련 서비스 전부
class MateService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 검색한 친구목록 불러오기
  static Future<SearchMateList> fetchSearchMateList(keyword) async {
    return fetchGetData('mate/search?nickName=$keyword')
        .then((data) => SearchMateList.fromJson(data));
  }

  // 요청보낸 친구목록 불러오기
  static Future<MateList> fetchSentMateList() async {
    return fetchGetData('mate/list/sent')
        .then((data) => MateList.fromJson(data));
  }

  // 요청받은 친구목록 불러오기
  static Future<MateList> fetchReceivedMateList() async {
    return fetchGetData('mate/list/received')
        .then((data) => MateList.fromJson(data));
  }

  // 친구목록 불러오기
  static Future<MateList> fetchMateList() async {
    return fetchGetData('mate').then((data) => MateList.fromJson(data));
  }

  // 친구추가하기
  static Future<MateAdd> fetchAddMate(id) async {
    return fetchPostData('mate/add/$id').then((data) => MateAdd.fromJson(data));
  }

  // 친구삭제하기
  static Future<MateAdd> fetchDeleteMate(id) async {
    return fetchPutData('mate/delete/$id')
        .then((data) => MateAdd.fromJson(data));
  }

  // 친구요청 거절/수락
  static Future<dynamic> fetchAcceptMate(AcceptForm acceptForm) async {
    String? token = await secureStorageService.getToken();
    final response = await http.post(
      Uri.parse('$baseUrl/mate/check'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token'
      },
      body: json.encode(acceptForm.toJson()),
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data;
      } else {
        throw Exception('내잘못');
      }
    } else {
      throw Exception('백잘못 ${response.statusCode}');
    }
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

  // put요청
  static Future<dynamic> fetchPutData(String endpoint) async {
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
